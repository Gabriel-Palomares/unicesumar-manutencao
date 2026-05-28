# 01 - Relatórios Profissionais de Bugs

## Atividade 2 - Rastreamento e Triagem de Bugs
**Projeto:** Library Maintenance Lab  
**Data:** 13 de maio de 2026  
**Analista QA:** Gabriel Palomares 
**Total de Bugs Identificados:** 5

---

## Bug #1: Violação de Estoque no Construtor (Book)

### 1. Título
Validação Incompleta de Disponibilidade de Cópias no Construtor de Book

### 2. Descrição Detalhada
No construtor da classe `Book`, a lógica de validação para `availableCopies` permite que o valor seja maior que `totalCopies`, o que viola a integridade do domínio (não faz sentido ter mais cópias disponíveis do que o total existente). Isso pode levar a inconsistências no estoque, como empréstimos impossíveis ou relatórios incorretos que revelam estados inválidos apenas em operações subsequentes.

### 3. Passos para Reproduzir
1. Criar uma instância de Book com `totalCopies = 5` e `availableCopies = 10`.
2. Executar `library.dumpState()` via Debug Area (opção 2-Print state).
3. Verificar se o valor de `availableCopies` permanece 10 (inválido).
4. Tentar emprestar uma cópia; o sistema permitirá empréstimos além do limite real.

### 4. Comportamento Atual
O construtor aceita `availableCopies > totalCopies` sem correção, mantendo o valor inválido na memória. Após múltiplos empréstimos, o valor de `availableCopies` pode ficar negativo implicitamente, sem mensagens de erro explícitas no console.

### 5. Comportamento Esperado
O construtor deve ajustar automaticamente `availableCopies` para no máximo `totalCopies` se o valor inicial for maior, garantindo a integridade do invariante de domínio: `0 <= availableCopies <= totalCopies`.

### 6. Ambiente de Execução
- **JDK:** 17 (OpenJDK 17.0.8 ou superior)
- **Sistema Operacional:** Windows 11 (ou Linux Ubuntu 22.04)
- **IDE:** VS Code com extensão Java (versão 1.80+)
- **Forma de Execução:** Linha de comando com `java -cp . Main` no diretório `library-maintenance-lab`
- **Dependências Externas:** Nenhuma (projeto educacional em modo console)

### 7. Evidências Técnicas
- **Classe:** `Model.Book`
- **Método:** Construtor `Book(int id, String title, ..., int availableCopies, ...)`
- **Linhas Críticas:** Aproximadamente linhas 25-30 (validação de `availableCopies`)
- **Código Problemático:**
  ```java
  this.availableCopies = (availableCopies < 0) ? this.totalCopies : availableCopies;
  // Não trata o caso availableCopies > totalCopies
  ```
- **Arquivo:** `library-maintenance-lab/src/Model/Book.java`

### 8. Referência de Evidência Visual
[Bug1_Violacao_Estoque.png](../evidencias/image_cb79a7.png)
**Imagem:** `image_cb79a7.png` - Screenshot do console mostrando dump de estado com `Book{id=1, availableCopies=10, totalCopies=5}`.

### 9. Logs de Console Simulados
```
Starting legacy library system...
Mode: Refactored OOP

==== Menu ====
1 - Register book
2 - Register user
...
Select option: 1
Title: Test Book
Author: John Doe
Year: 2023
Category: FICTION
Total copies: 5
Available copies: 10
Shelf code: A1
ISBN: ISBN-TEST-001
Book registered with id 1

...
Select option: 9
==== Debug Area ====
1-Print logs
2-Print state
3-Change mode
4-Unsafe field update (Adapted)
5-Loan histogram
6-Manual notify
0-Back
Debug option: 2

Library State:
Books: [Book{id=1, title='Test Book', author='John Doe', year=2023, 
category='FICTION', totalCopies=5, availableCopies=10, shelfCode='A1', 
isbn='ISBN-TEST-001'}]
Users: []
Loans: []
Logs: [book-added-1]
```

---

## Bug #2: Tratamento Inconsistente de Exceções em borrowCopy (LoanManager)

### 1. Título
Falha Silenciosa em Empréstimos sem Cópias Disponíveis - Exceção Não Lançada

### 2. Descrição Detalhada
O método `LoanManager.borrowBook()` tenta capturar uma `IllegalStateException` de `book.borrowCopy()`, mas `borrowCopy()` não lança essa exceção; ele apenas retorna `false`. Isso resulta em falha silenciosa ou comportamento inesperado, pois o empréstimo pode prosseguir mesmo sem cópias disponíveis, violando a lógica de negócio central.

### 3. Passos para Reproduzir
1. Criar um `Book` com `availableCopies = 0`.
2. Criar um `User`.
3. Chamar `loanManager.borrowBook()` com o `bookId` do livro sem cópias.
4. Observar que `borrowCopy()` retorna `false`, mas o try-catch não captura nada.
5. Verificar em Debug Area (opção 1-Print logs) que o sistema registrou "loan-created-success" mesmo com estoque zerado.

### 4. Comportamento Atual
`borrowCopy()` retorna `false` sem lançar exceção, e o código em `LoanManager.borrowBook()` continua executando, registrando o empréstimo incorretamente nos logs do sistema. Não há interrupção, erro ou mensagem de alerta.

### 5. Comportamento Esperado
`borrowCopy()` deve lançar `IllegalStateException` quando não há cópias (`availableCopies <= 0`), permitindo que `LoanManager.borrowBook()` trate o erro adequadamente e exiba uma mensagem clara de falha ao usuário.

### 6. Ambiente de Execução
- **JDK:** 17 (OpenJDK 17.0.8 ou superior)
- **Sistema Operacional:** Windows 11 (ou Linux Ubuntu 22.04)
- **IDE:** VS Code com extensão Java (versão 1.80+)
- **Forma de Execução:** Linha de comando com `java -cp . Main` no diretório `library-maintenance-lab`
- **Dependências Externas:** Nenhuma (projeto educacional em modo console)

### 7. Evidências Técnicas
- **Classes Envolvidas:** `LoanManager`, `Model.Book`
- **Métodos:** `LoanManager.borrowBook()` e `Book.borrowCopy()`
- **Linhas Críticas:** `LoanManager` linhas ~25-35 (try-catch); `Book` linhas ~35-40 (borrowCopy)
- **Código Problemático em Book:**
  ```java
  public boolean borrowCopy() {
      if (this.availableCopies > 0) {
          this.availableCopies--;
          return true;
      }
      return false; // Não lança exceção
  }
  ```
- **Código Problemático em LoanManager:**
  ```java
  try {
      book.borrowCopy(); // Esperava exceção, mas recebe false
  } catch (IllegalStateException e) {
      throw new IllegalStateException("Não foi possível emprestar: " + e.getMessage());
  }
  ```
- **Arquivos:** `library-maintenance-lab/src/LoanManager.java` e `library-maintenance-lab/src/Model/Book.java`

### 8. Referência de Evidência Visual
[Bug1_Violacao_Estoque.png](../evidencias/image_cb7969.png)
**Imagem:** `image_cb7969.png` - Screenshot do console mostrando "Empréstimo X realizado com sucesso" seguido de logs com "loan-created-success" apesar de estoque zerado.

### 9. Logs de Console Simulados
```
Starting legacy library system...
Mode: Refactored OOP

==== Menu ====
Select option: 1
Title: Zero Stock Book
Author: Test Author
Year: 2023
Category: GENERAL
Total copies: 1
Available copies: 0
Shelf code: Z0
ISBN: ISBN-ZERO-001
Book registered with id 1

Select option: 2
Name: Test User
Email: test@library.com
Phone: 5511999999999
Type: student
City: São Paulo
Document: 12345678900
Status: ACTIVE
User registered with id 1

Select option: 3
User ID: 1
Book ID: 1
Borrow date: 2023-10-01
Due date: 2023-10-15
Channel (email/sms): email
Empréstimo 1 realizado com sucesso para o usuário Test User.

==== Debug Area ====
Debug option: 1
Logs: [book-added-1, user-added-1, loan-created-success-1]

(Nenhuma exceção foi lançada; o empréstimo foi criado apesar do estoque zerado)
```

---

## Bug #3: Falta de Validação de Null Pointer em returnBook (LoanManager)

### 1. Título
Estoque Não Restaurado em Devoluções com Livro Inexistente

### 2. Descrição Detalhada
No método `LoanManager.returnBook()`, se o livro associado ao empréstimo não existir (`book == null`), o código continua executando sem chamar `book.returnCopy()`, potencialmente causando perda de sincronização de estoque ou estado inconsistente no sistema. A devolução é marcada como bem-sucedida, mas o inventário não é atualizado.

### 3. Passos para Reproduzir
1. Criar um `Loan` com `bookId` inválido (livro inexistente ou deletado).
2. Chamar `loanManager.returnBook()` com o `loanId`.
3. Verificar em Debug Area (opção 2-Print state) que `availableCopies` do livro não foi alterada.
4. Confirmar que o empréstimo foi marcado como "CLOSED" sem restaurar o estoque.

### 4. Comportamento Atual
Se `book` for `null`, o método pula a chamada de `book.returnCopy()`, não restaurando o estoque. O sistema exibe "Devolução processada com sucesso!" e registra "loan-closed-success" nos logs, mesmo sem atualizar o inventário.

### 5. Comportamento Esperado
O método deve validar se o livro existe e, caso não exista, lançar uma `IllegalStateException` ou registrar um erro claro, impedindo que o empréstimo seja fechado sem sincronizar o estoque. Alternativamente, registrar o livro como "inexistente" e alertar para inconsistências de dados.

### 6. Ambiente de Execução
- **JDK:** 17 (OpenJDK 17.0.8 ou superior)
- **Sistema Operacional:** Windows 11 (ou Linux Ubuntu 22.04)
- **IDE:** VS Code com extensão Java (versão 1.80+)
- **Forma de Execução:** Linha de comando com `java -cp . Main` no diretório `library-maintenance-lab`
- **Dependências Externas:** Nenhuma (projeto educacional em modo console)

### 7. Evidências Técnicas
- **Classe:** `LoanManager`
- **Método:** `returnBook(int loanId)`
- **Linhas Críticas:** Aproximadamente linhas 50-65
- **Código Problemático:**
  ```java
  Book book = library.getBookById(loan.getBookId());
  if (book != null) {
      book.returnCopy(); // Sem else ou throw para tratar null
  }
  ```
- **Arquivo:** `library-maintenance-lab/src/LoanManager.java`

### 8. Referência de Evidência Visual
[Bug1_Violacao_Estoque.png](../evidencias/image_cb7943.png)
**Imagem:** `image_cb7943.png` - Screenshot do console mostrando "Devolução processada com sucesso!" e dump de estado com `availableCopies` inalterada para o livro associado.

### 9. Logs de Console Simulados
```
Starting legacy library system...
Mode: Refactored OOP

(Criar livro, usuário e empréstimo normalmente)
Book registered with id 1
User registered with id 1
Empréstimo 1 realizado com sucesso para o usuário Test User.

==== Debug Area ====
Debug option: 2
Library State:
Books: [Book{id=1, availableCopies=4, totalCopies=5}]
Loans: [Loan{id=1, status=OPEN}]

(Agora simular devolução com bookId inválido ou ausente)
Select option: 4
Loan ID: 1
Devolução processada com sucesso!

==== Debug Area ====
Debug option: 2
Library State:
Books: [Book{id=1, availableCopies=4, totalCopies=5}]  // Não foi restaurada
Loans: [Loan{id=1, status=CLOSED}]

Logs: [..., loan-closed-success-1]
```

---

## Bug #4: Validação Incompleta de Entradas no Construtor de User

### 1. Título
Aceita Nomes Vazios na Criação de Usuários

### 2. Descrição Detalhada
O construtor de `User` lança `RuntimeException` apenas se `name` ou `email` forem `null`, mas permite `name` ser uma string vazia (`""`). Isso não é detectado como erro, permitindo usuários com nomes inválidos no sistema, o que causa problemas em displays, logs e buscas posteriores.

### 3. Passos para Reproduzir
1. Executar o menu de registro de usuário (opção 2).
2. Deixar o campo de Name em branco (apenas pressionar Enter).
3. Preencher os demais campos normalmente.
4. Verificar em Debug Area (opção 2-Print state) que o usuário foi criado com `name=''`.

### 4. Comportamento Atual
O construtor aceita `name = ""` (string vazia) sem validação adicional além de null check. O usuário é registrado no sistema, mas pode causar `NullPointerException` ou strings vazias em logs de empréstimos.

### 5. Comportamento Esperado
O construtor deve validar que `name` e `email` não sejam apenas `null`, mas também não sejam vazios (ou apenas espaços em branco), lançando `IllegalArgumentException` para valores inválidos.

### 6. Ambiente de Execução
- **JDK:** 17 (OpenJDK 17.0.8 ou superior)
- **Sistema Operacional:** Windows 11 (ou Linux Ubuntu 22.04)
- **IDE:** VS Code com extensão Java (versão 1.80+)
- **Forma de Execução:** Linha de comando com `java -cp . Main` no diretório `library-maintenance-lab`
- **Dependências Externas:** Nenhuma (projeto educacional em modo console)

### 7. Evidências Técnicas
- **Classe:** `Model.User`
- **Método:** Construtor `User(int id, String name, String email, ...)`
- **Linhas Críticas:** Aproximadamente linhas 10-20 (validação no construtor)
- **Código Problemático:**
  ```java
  if (name == null || email == null) {
      throw new RuntimeException("name, email or phone invalid");
  }
  // Não trata strings vazias
  this.name = name; // Pode ser ""
  ```
- **Arquivo:** `library-maintenance-lab/src/Model/User.java`

### 8. Referência de Evidência Visual
[Bug1_Violacao_Estoque.png](../evidencias/image_cb7988.png)
**Imagem:** `image_cb7988.png` - Screenshot do console mostrando "User registered with id X" e dump de estado com `User{id=X, name='', email='...'}`.

### 9. Logs de Console Simulados
```
Starting legacy library system...
Mode: Refactored OOP

==== Menu ====
Select option: 2
Name: 
Email: test@library.com
Phone: 5511999999999
Type: student
City: São Paulo
Document: 12345678900
Status: ACTIVE
User registered with id 1

==== Debug Area ====
Debug option: 2
Library State:
Users: [User{id=1, name='', email='test@library.com', phone='5511999999999', 
userType='student', city='São Paulo', document='12345678900', status='ACTIVE', debt=0.0}]

(Nome vazio foi aceito sem validação)
```

---

## Bug #5: Falta de Verificação de Duplicatas em addBook na Library

### 1. Título
Permite Adição de Livros com ISBN Duplicado

### 2. Descrição Detalhada
O método `addBook()` em `Library` não verifica se um livro com o mesmo ISBN (ou título/autor) já existe, permitindo duplicatas que podem confundir usuários e causar inconsistências em relatórios. Isso viola a unicidade esperada em um sistema de biblioteca.

### 3. Passos para Reproduzir
1. Chamar `library.addBook()` (via menu opção 1) com ISBN "ISBN-TEST-001".
2. Chamar novamente `library.addBook()` com o mesmo ISBN "ISBN-TEST-001".
3. Executar Debug Area opção 5 (List books) ou via menu opção 5.
4. Verificar que ambos os livros aparecem na listagem com ISBN idêntico.

### 4. Comportamento Atual
O sistema adiciona livros sem checagem de duplicatas, permitindo múltiplas entradas com o mesmo ISBN, título e autor no repositório.

### 5. Comportamento Esperado
O método `addBook()` deve verificar se um livro com o mesmo ISBN já existe no repositório e, caso exista, lançar `IllegalArgumentException` para rejeitá-lo, garantindo unicidade.

### 6. Ambiente de Execução
- **JDK:** 17 (OpenJDK 17.0.8 ou superior)
- **Sistema Operacional:** Windows 11 (ou Linux Ubuntu 22.04)
- **IDE:** VS Code com extensão Java (versão 1.80+)
- **Forma de Execução:** Linha de comando com `java -cp . Main` no diretório `library-maintenance-lab`
- **Dependências Externas:** Nenhuma (projeto educacional em modo console)

### 7. Evidências Técnicas
- **Classe:** `Repository.Library`
- **Método:** `addBook(String title, String author, int year, String category, int totalCopies, int availableCopies, String shelfCode, String isbn)`
- **Linhas Críticas:** Aproximadamente linhas 25-35 (sem checagem de ISBN duplicado)
- **Código Problemático:**
  ```java
  public int addBook(String title, String author, int year, String category, 
                     int totalCopies, int availableCopies, String shelfCode, String isbn) {
      Book book = new Book(bookSeq++, title, author, year, category, totalCopies, 
                          availableCopies, shelfCode, isbn);
      books.add(book);
      // Não há loop ou checagem para ISBN duplicado
      addLog("book-added-" + book.getId());
      return book.getId();
  }
  ```
- **Arquivo:** `library-maintenance-lab/src/Repository/Library.java`

### 8. Referência de Evidência Visual
[Bug1_Violacao_Estoque.png](../evidencias/image_cb79cb.png)
**Imagem:** `image_cb79cb.png` - Screenshot do console mostrando listagem de livros com duas entradas contendo `ISBN: ISBN-TEST-001` idêntico.

### 9. Logs de Console Simulados
```
Starting legacy library system...
Mode: Refactored OOP

==== Menu ====
Select option: 1
Title: Test Book
Author: John Doe
Year: 2023
Category: FICTION
Total copies: 5
Available copies: 5
Shelf code: A1
ISBN: ISBN-TEST-001
Book registered with id 1

Select option: 1
Title: Another Test Book
Author: Jane Smith
Year: 2023
Category: FICTION
Total copies: 3
Available copies: 3
Shelf code: A2
ISBN: ISBN-TEST-001  // ISBN duplicado
Book registered with id 2

==== Menu ====
Select option: 5
Books
1 | Test Book | John Doe | 2023 | FICTION | 5 | 5 | A1 | ISBN-TEST-001
2 | Another Test Book | Jane Smith | 2023 | FICTION | 3 | 3 | A2 | ISBN-TEST-001

(Duas entradas com ISBN idêntico - duplicata permitida)
```

---

## Resumo de Bugs Identificados

| Bug # | Título | Severidade | Prioridade | Status |
|-------|--------|-----------|-----------|--------|
| 1 | Validação de Cópias no Construtor | Normal | Média | Confirmado |
| 2 | Tratamento Inconsistente no LoanManager | Serious | Alta | Confirmado |
| 3 | Null Pointer no returnBook | Normal | Média | Confirmado |
| 4 | Validação de Nome Vazio no User | Trivial | Baixa | Confirmado |
| 5 | Duplicidade de ISBN na Library | Normal | Média | Confirmado |

---

**Fim do Documento 01_bug_reports.md**
