# Templates de Issues de Divida Tecnica

## Issue 1
**Titulo:** Substituir `System.out` por logger no controller central e nas telas de UI

**Localizacao:** `library-maintenance-lab/src/LibrarySystem.java` e classes de UI como `BookManager.java`

**Descricao:**
Varias classes usam `System.out` diretamente para exibir informacoes. Isto quebra a consistencia de logging e impede o controle de log em ambientes reais.

**Justificativa:**
Saida padrao espalhada aumenta o custo de manutencao e o risco de perda de dados em producao. Usar um logger consistente reduz juros de divida tecnica e facilita auditoria.

## Issue 2
**Titulo:** Metodo `Library.addBook(...)` possui 8 parametros e esta com alta complexidade de uso

**Localizacao:** `library-maintenance-lab/src/Repository/Library.java`

**Descricao:**
O metodo expoe uma longa lista de parametros, tornando a chamada propensa a inversoes de argumento e dificil de manter.

**Justificativa:**
Metodos com muitos parametros sao dificeis de ler e testar. Isso aumenta o risco de bugs e tempo de manutencao quando o cadastro de livro evoluir.

## Issue 3
**Titulo:** Encapsular campos publicos em `Library.java` para preservar encapsulamento

**Localizacao:** `library-maintenance-lab/src/Repository/Library.java`

**Descricao:**
Os campos `systemMode`, `globalFinePerDay`, `globalMaxLoanDays` e `workaroundFlag` sao publicos e expõem o estado interno da classe.

**Justificativa:**
Campos publicos violam o encapsulamento e tornam o sistema mais fragil, pois qualquer classe pode altera-los diretamente. Isso eleva os juros e dificulta a evolucao segura do modelo.

## Issue 4
**Titulo:** Definir constante para literal duplicado `"Software"` em `Library.seedInitialData()`

**Localizacao:** `library-maintenance-lab/src/Repository/Library.java`

**Descricao:**
A categoria `"Software"` aparece repetida em multiplas chamadas de `addBook(...)`.

**Justificativa:**
Duplicacao de literais aumenta o custo de mudanca e a chance de inconsistencia. Consolidar em uma constante reduz a divida tecnica.

## Issue 5
**Titulo:** Remover parametros nao utilizados de `NotificationService.notifyLoanCreated()`

**Localizacao:** `library-maintenance-lab/src/NotificationService.java`

**Descricao:**
O metodo recebia parametros `template` e `managerName` que nao eram usados no corpo.

**Justificativa:**
Parametros mortos confundem a intencao do metodo e adicionam ruido ao contrato da API. Retira-los simplifica a logica e diminui a divida tecnica.
