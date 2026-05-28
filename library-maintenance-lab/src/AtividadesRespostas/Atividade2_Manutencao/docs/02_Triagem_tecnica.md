# 02 - Triagem Técnica e Matriz de Classificação

## Atividade 2 - Rastreamento e Triagem de Bugs
**Projeto:** Library Maintenance Lab  
**Data:** 14 de maio de 2026  
**Analista QA:** Engenheiro de QA Sênior  

---

## Matriz de Triagem - 5 Bugs Válidos

### Bug #1: Violação de Estoque no Construtor (Book)

#### Classificação
- **Tipo:** Defect
- **Status de Validação:** Confirmado
- **Severidade:** Normal
- **Prioridade:** Média

#### Justificativa de Severidade
**Normal** - Este bug viola invariantes de domínio (estoque disponível não pode exceder o total), causando inconsistências funcionais em relatórios e empréstimos subsequentes. Não leva a crashes imediatos ou perda de dados crítica, mas afeta a confiabilidade do sistema de inventário. O impacto é observável apenas em operações futuras (empréstimos, devoluções, relatórios), não bloqueando operações básicas.

#### Justificativa de Prioridade
**Média** - Impacta a confiabilidade do sistema de inventário e pode levar a bugs downstream em empréstimos, mas é corrigível com validação simples (uma linha de código) no construtor. Em um projeto educacional, não é urgente, mas deve ser endereçado rapidamente para evitar propagação de bugs relacionados.

#### Análise de Duplicidade/Validade
- **Não Duplicate:** Não há menção a este cenário específico em comentários de código ou commits anteriores.
- **Válido:** Reproduzível via teste simples (criar Book com availableCopies > totalCopies).
- **Não Rejected:** Afeta funcionalidade principal (gerenciamento de estoque).

---

### Bug #2: Inconsistência no LoanManager (borrowCopy)

#### Classificação
- **Tipo:** Defect
- **Status de Validação:** Confirmado
- **Severidade:** Serious
- **Prioridade:** Alta

#### Justificativa de Severidade
**Serious** - Este bug permite empréstimos inválidos (sem cópias disponíveis), comprometendo a lógica de negócio central da aplicação. Afeta múltiplas operações (empréstimos criam registros falsos, devoluções restauram estoque errado, relatórios mostram dados inconsistentes). A falha silenciosa (sem exceção) torna difícil detectar o problema, causando corrupção de dados sem alertas explícitos.

#### Justificativa de Prioridade
**Alta** - Crítico para a funcionalidade central (empréstimos) de um sistema de biblioteca. Deve ser corrigido urgentemente (no próximo sprint) para evitar violações de regras de negócio e facilitar testes de funcionalidades dependentes (devoluções, históricos, relatórios). O impacto em produção seria severo.

#### Análise de Duplicidade/Validade
- **Não Duplicate:** Problema único na inconsistência entre try-catch em LoanManager e implementação de borrowCopy em Book.
- **Válido:** Reproduzível via tentativa de empréstimo sem cópias; logs mostram criação de empréstimo inválido.
- **Não Rejected:** Afeta fluxo crítico de negócio.

---

### Bug #3: Null Pointer no returnBook (LoanManager)

#### Classificação
- **Tipo:** Defect
- **Status de Validação:** Confirmado
- **Severidade:** Normal
- **Prioridade:** Média

#### Justificativa de Severidade
**Normal** - Este bug causa perda de sincronização de estoque em devoluções (o livro não é restaurado), afetando relatórios e disponibilidade futura. Não crasha o sistema (há proteção com `if (book != null)`), mas a omissão leva a inconsistências funcionais sem impacto imediato em segurança. Afeta integridade de dados de forma indireta.

#### Justificativa de Prioridade
**Média** - Importante para integridade de dados em operações de devolução, mas não bloqueia o fluxo principal de interação. Corrigível com checagem de null e lançamento de exceção. Em um sistema de produção seria importante, mas em um projeto educacional pode ser endereçado em melhorias futuras.

#### Análise de Duplicidade/Validade
- **Não Duplicate:** Cenário específico de livro deletado/ausente na devolução não é mencionado em código.
- **Válido:** Reproduzível criando loan com bookId inválido.
- **Não Rejected:** Afeta fluxo de devolução.

---

### Bug #4: Validação de Nome Vazio no User

#### Classificação
- **Tipo:** Defect
- **Status de Validação:** Confirmado
- **Severidade:** Trivial
- **Prioridade:** Baixa

#### Justificativa de Severidade
**Trivial** - Este bug permite dados inválidos (nomes vazios), causando problemas menores em displays ou buscas, mas não afeta operações críticas nem segurança. Usuários com nomes vazios podem ser criados sem crashes imediatos; o impacto surge apenas em UIs que exibem o nome ou em logs que tentam usar a string vazia.

#### Justificativa de Prioridade
**Baixa** - Menor impacto em um projeto educacional; pode ser endereçado em refatorações futuras. Não impede funcionalidades básicas como empréstimos. Baixa urgência, pois há proteção contra null (mesmo que não contra strings vazias).

#### Análise de Duplicidade/Validade
- **Não Duplicate:** Validação específica de strings vazias não é mencionada em código.
- **Válido:** Reproduzível registrando usuário com nome em branco.
- **Não Rejected:** Afeta qualidade de dados, mesmo que menor.

---

### Bug #5: Duplicidade de ISBN (Library)

#### Classificação
- **Tipo:** Defect
- **Status de Validação:** Confirmado
- **Severidade:** Normal
- **Prioridade:** Média

#### Justificativa de Severidade
**Normal** - Este bug permite duplicatas de ISBN, causando confusão em buscas, ambiguidade em seleções de livros e inconsistências em relatórios. Viola unicidade esperada em um domínio de biblioteca, afetando usabilidade sem comprometer segurança ou estabilidade do sistema.

#### Justificativa de Prioridade
**Média** - Relevante para integridade de dados em bibliotecas; deve ser endereçado para garantir unicidade de identificadores. Corrigível com checagem de ISBN em addBook. Em um projeto educacional, é apropriado para melhorias, não urgente em sprint atual.

#### Análise de Duplicidade/Validade
- **Não Duplicate:** Ausência de validação de unicidade de ISBN não é mencionada em comentários de código.
- **Válido:** Reproduzível adicionando dois livros com mesmo ISBN.
- **Não Rejected:** Afeta integridade de dados e funcionalidade de biblioteca.

---

## Rejeição de Bug Hipotético (wontfix)

### Bug Hipotético: Falta de Limite de Dias para Empréstimos

#### Descrição
"O sistema permite empréstimos sem limite de dias, ignorando completamente o campo `dueDate` e não aplicando multas para atrasos. Usuários podem manter livros indefinidamente sem penalidades."

#### Análise Técnica
- **Tipo Identificado:** Defect (omissão de funcionalidade)
- **Severidade:** Normal
- **Prioridade:** Baixa
- **Reprodução:** Criar empréstimo com data de devolução no passado; verificar que nenhuma multa é calculada nem aviso é exibido.

#### Razão de Rejeição: **WONTFIX**

**Justificativa:** O código contém comentários explícitos indicando que a funcionalidade de cálculo de multas é um placeholder:
```java
// No método LoanManager.returnBook():
String returnDate = "DataAtual";
double fine = 0.0; // Aqui entraria a lógica de cálculo de multa se estivesse atrasado
```

A funcionalidade é intencionalmente simplificada para foco educacional em refatoração de código legacy e padrões de design (injeção de dependência, fail-fast, early return), não em lógica de negócio completa. A multiplicidade de domínios (dates, multas, cálculos) é fora do escopo desta atividade de manutenção de software.

**Status:** Rejeitado com label `wontfix` - Aceito como limitação conhecida do projeto educacional.

---

## Resumo de Análise de Triagem

### Bugs Confirmados: 5/5
- **Defects (Falhas Funcionais):** 5
- **Duplicados:** 0
- **Inválidos:** 0
- **Rejeitados (wontfix/worksforme):** 1 hipotético

### Distribuição por Severidade
| Severidade | Quantidade | Bugs |
|-----------|-----------|------|
| Catastrophic | 0 | - |
| Serious | 1 | Bug #2 |
| Normal | 3 | Bugs #1, #3, #5 |
| Trivial | 1 | Bug #4 |

### Distribuição por Prioridade
| Prioridade | Quantidade | Bugs |
|-----------|-----------|------|
| Alta | 1 | Bug #2 |
| Média | 3 | Bugs #1, #3, #5 |
| Baixa | 1 | Bug #4 |

---

## Critérios de Classificação Utilizados

### Severidade
- **Catastrophic:** Falha completa do sistema, dados perdidos, segurança comprometida.
- **Serious:** Fluxo crítico afetado, múltiplas operações impactadas, corrupção de dados.
- **Normal:** Funcionalidade afetada, inconsistências funcionais, afeta relatórios ou operações específicas.
- **Trivial:** Problemas menores de dados, UI, ou validação; não afeta operações críticas.

### Prioridade
- **Alta:** Deve ser corrigido imediatamente (próximo sprint), afeta funcionalidade central.
- **Média:** Deve ser corrigido em curto prazo (próximo release), afeta múltiplas funcionalidades ou integridade de dados.
- **Baixa:** Pode ser corrigido em refatorações futuras, afeta qualidade de dados ou funcionalidades secundárias.

---

