# 03 - Ciclo de Vida da Correção (Defect #2)

## Atividade 2 - Rastreamento e Triagem de Bugs
**Projeto:** Library Maintenance Lab  
**Data:** 13 de maio de 2026  
**Defect Selecionado para Correção:** Defect #2 - Inconsistência no LoanManager (borrowCopy)  
**Status Inicial:** To Do (Confirmed)  
**Status Final:** Done  

---

## Visão Geral do Ciclo de Vida

Este documento detalha o fluxo completo da correção do Defect #2, desde a triagem inicial até a conclusão, incluindo transições de estado em um sistema de issue tracking (GitHub Issues) e os artefatos de código gerados.

### Fluxo utilizado

Fluxo solicitado pela atividade:

```text
[To Do (Confirmed)] → [In Progress]
```

Fluxo expandido utilizado para simular um ambiente profissional:

```text
[To Do (Confirmed)] → [In Progress] → [In Review] → [Testing] → [Done]
```

Observação: etapas intermediárias foram adicionadas para representar um processo real de manutenção.

---

## Fase 1: To Do (Estado Inicial)

### Descrição do Problema

**Título:** Tratamento Inconsistente de Exceções em borrowCopy() (LoanManager)  
**Identificação:** Issue #ID_DA_ISSUE  
**Classificação:** Defect  
**Severidade:** Serious  
**Prioridade:** Alta  
**Triado em:** 13 de maio de 2026

### Responsável atribuído

**Nome:** Camilli Vitoria de Lara  
**GitHub:** @CamilliLara  
**Perfil:** https://github.com/CamilliLara

### Detalhamento

O método `LoanManager.borrowBook()` tenta capturar uma `IllegalStateException` proveniente de `book.borrowCopy()`, porém o método `borrowCopy()` apenas retorna `false`, sem lançar exceção.

Isso gera falha silenciosa permitindo inconsistência na lógica de empréstimos ao tentar emprestar livros sem estoque disponível.

### Critério de Aceitação

1. ✓ Empréstimos sem cópias devem lançar `IllegalStateException`
2. ✓ Mensagem clara deve ser exibida ao usuário
3. ✓ Sistema de logs deve registrar falha de empréstimo
4. ✓ Não deve haver regressões em empréstimos válidos

---

## Fase 2: In Progress (Iniciação da Correção)

### Comentário de Transição no GitHub (Comentário #1)

**Timestamp:** 13 de maio de 2026 — 14:30:00  
**Autor:** Engenheiro de Software (corrector)  
**Status:** To Do → In Progress

#### Texto do Comentário

```text
## Status: To Do → In Progress

O defeito foi triado com sucesso como "Defect",
de severidade "Serious"
e prioridade "Alta".

Iniciando correção da inconsistência de exceções
no método borrowCopy() da classe Model.Book.

Plano de ação:

1. Refatorar Book.borrowCopy()

2. Lançar IllegalStateException quando availableCopies <=0

3. Ajustar LoanManager

4. Implementar mensagens claras

5. Registrar logs

6. Validar testes
```

---

## Fase 3: Implementação

### Análise de Código Anterior (Pre-fix)

**Arquivo:** `library-maintenance-lab/src/Model/Book.java`

```java
public boolean borrowCopy() {

    if (this.availableCopies > 0) {

        this.availableCopies--;

        return true;
    }

    return false;
}
```

Problema identificado:

O método não lançava exceção.

Retornava apenas false.

Isso fazia LoanManager esperar comportamento inexistente.

---

### Código Corrigido (Post-fix)

**Arquivo:** `library-maintenance-lab/src/Model/Book.java`

```java
public void borrowCopy() {

    if (this.availableCopies <=0){

        throw new IllegalStateException(
            "Não há cópias disponíveis para empréstimo."
        );

    }

    this.availableCopies--;

}
```

Mudanças:

✓ assinatura alterada de boolean para void

✓ IllegalStateException implementada

✓ validação adicionada

✓ mensagem clara de erro

---

**Arquivo:** `library-maintenance-lab/src/LoanManager.java`

```java
try{

    book.borrowCopy();

}catch(IllegalStateException e){

    System.out.println(
        "Erro: " + e.getMessage()
    );

    library.addLog(
        "loan-failed-no-copies-"+bookId
    );

    throw e;

}
```

Mudanças:

✓ tratamento consistente

✓ logs implementados

✓ mensagens ao usuário

✓ fail-fast mantido

---

### Comentário de Implementação no GitHub (Comentário #2)

**Timestamp:** 13 de maio de 2026 — 15:45:00

```text
Implementação em andamento

Mudanças realizadas:

✓ Refatoração Book.borrowCopy()

✓ alteração boolean → void

✓ IllegalStateException adicionada

✓ LoanManager atualizado

Próximos passos:

[ ] Compilar

[ ] Testar empréstimos

[ ] Validar logs

[ ] Testar regressões
```

---

## Fase 4: Testing

### Cenário 1: Empréstimo sem cópias

Pré-condição:

Livro:

availableCopies=0

Resultado esperado:

IllegalStateException lançada

Status:

✓ PASS

---

### Cenário 2: Empréstimo com cópias

Pré-condição:

availableCopies=5

Resultado esperado:

Empréstimo realizado corretamente

Status:

✓ PASS

---

### Cenário 3: Múltiplos empréstimos

Pré-condição:

Livro com:

availableCopies=2

Ação:

Executar borrowBook() três vezes

Resultado esperado:

Primeiro empréstimo: sucesso

Segundo empréstimo: sucesso

Terceiro empréstimo:

IllegalStateException lançada

Status:

✓ PASS

---

### Cenário 4: Compilação

Resultado esperado:

Projeto sem erros

Status:

✓ PASS

---

### Evidências

Teste de sucesso:

```text
Book registered with id 1

User registered with id 1

Empréstimo realizado com sucesso
```

Teste de falha:

```text
Erro:

Não há cópias disponíveis para empréstimo
```

---

## Fase 5: In Review

Checklist:

✓ Exceção lançada corretamente

✓ Sem regressões

✓ Logs implementados

✓ Fail-fast mantido

✓ Código padronizado

Aprovação:

Revisor: Engenheiro Software Sênior

Data:

13/05/2026

Status:

✓ APPROVED

---

## Fase 6: Done

### Comentário Final GitHub

**Timestamp:** 13/05/2026 — 17:15

```text
Correção concluída com sucesso

Resumo:

✓ Método Book.borrowCopy() atualizado

✓ IllegalStateException implementada

✓ LoanManager ajustado

✓ Logs adicionados

✓ Testes executados

✓ Sem regressões
```

---

## Mensagem de Commit (Conventional Commits)

```bash
fix(loan-manager): corrige tratamento de empréstimo sem cópias disponíveis fixes #ID_DA_ISSUE
```

---

## Evidências GitHub

✓ Screenshot da issue criada

✓ Screenshot severidade Serious

✓ Screenshot prioridade Alta

✓ Screenshot cartão To Do

✓ Screenshot cartão In Progress

✓ Screenshot cartão Done

✓ Screenshot commit realizado

✓ Logs dos testes

---

## Resumo do Ciclo de Vida

| Fase | Estado | Situação |
|------|----------|-----------|
| 1 | To Do | Concluído |
| 2 | In Progress | Concluído |
| 3 | Implementação | Concluído |
| 4 | Testing | Concluído |
| 5 | Review | Concluído |
| 6 | Done | Concluído |

---

## Artefatos Gerados

Código:

- Book.java
- LoanManager.java

Documentação:

- 01_bug_reports.md
- 02_triagem_tecnica.md
- 03_ciclo_de_vida_correcao.md

Responsável:

Camilli Vitoria de Lara

GitHub:

https://github.com/CamilliLara
