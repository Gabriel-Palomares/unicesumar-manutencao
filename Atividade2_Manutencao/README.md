# Atividade 2 - Manutenção de Software: Rastreamento e Triagem de Bugs

## Visão Geral do Projeto

Este diretório contém a documentação profissional completa da **Atividade 2 de Rastreamento e Triagem de Bugs** do projeto **Library Maintenance Lab**, uma atividade acadêmica de engenharia de software focada em:

- Identificação e triagem de bugs em código legacy
- Classificação por severidade e prioridade
- Documentação profissional seguindo padrões de engenharia
- Ciclo de vida de correção de bugs
- Padrões de commit (Conventional Commits)

---

## Estrutura de Diretórios

```
Atividade2_Manutencao/
├── README.md                          # Este arquivo
├── docs/                              # Documentação Markdown
│   ├── 01_bug_reports.md            # Relatórios dos 5 bugs identificados
│   ├── 02_triagem_tecnica.md        # Matriz de triagem e justificativas
│   └── 03_ciclo_de_vida_correcao.md # Fluxo de correção do Bug #2
└── evidencias/                        # Capturas de tela e evidências
    ├── image_cb79a7.png            # Bug #1 - Violação de Estoque
    ├── image_cb7969.png            # Bug #2 - Falha Silenciosa no LoanManager
    ├── image_cb7943.png            # Bug #3 - Null Pointer no returnBook
    ├── image_cb7988.png            # Bug #4 - Validação de Nome Vazio
    └── image_cb79cb.png            # Bug #5 - Duplicidade de ISBN
```

---

## Documentação Disponível

### 1. `01_bug_reports.md` - Relatórios Profissionais de Bugs
Contém relatórios detalhados de 5 bugs identificados no código-fonte, seguindo modelo profissional com:

- **Título:** Identificação breve do bug
- **Descrição Detalhada:** Análise técnica do problema
- **Passos para Reproduzir:** Instruções passo a passo
- **Comportamento Atual:** O que o sistema faz de errado
- **Comportamento Esperado:** O que deveria fazer
- **Ambiente de Execução:** JDK 17, SO, IDE, modo de execução
- **Evidências Técnicas:** Classes, métodos, linhas de código afetadas
- **Referência de Evidência Visual:** Link para imagem de screenshot
- **Logs de Console Simulados:** Output esperado em texto formatado

**Bugs Documentados:**
1. ✓ Bug #1 - Violação de Estoque no Construtor (Book)
2. ✓ Bug #2 - Inconsistência no LoanManager (borrowCopy)
3. ✓ Bug #3 - Null Pointer no returnBook (LoanManager)
4. ✓ Bug #4 - Validação de Nome Vazio (User)
5. ✓ Bug #5 - Duplicidade de ISBN (Library)

### 2. `02_triagem_tecnica.md` - Matriz de Triagem
Contém análise técnica de triagem para cada bug, com:

- **Classificação:** Tipo (Bug/Defect), Status de Validação
- **Severidade:** Catastrophic, Serious, Normal, Trivial
- **Prioridade:** Alta, Média, Baixa
- **Justificativa:** Explicação técnica de severidade e prioridade
- **Análise de Duplicidade:** Verificação se é duplicado, válido, rejected
- **Distribuição:** Tabelas resumidas de severidade e prioridade

**Inclui Também:**
- Rejeição de 1 bug hipotético como `wontfix` com justificativa
- Critérios de classificação utilizados
- Resumo de análise (5/5 bugs confirmados, 0 duplicados)

### 3. `03_ciclo_de_vida_correcao.md` - Ciclo de Vida do Bug #2
Detalha o fluxo completo de correção do Bug #2 (selecionado como exemplo), incluindo:

**Estados Documentados:**
1. **To Do:** Descrição inicial do problema
2. **In Progress:** Comentário #1 - Transição com plano de ação
3. **In Progress:** Comentário #2 - Implementação com código antes/depois
4. **Testing:** Cenários de teste e validação
5. **In Review:** Checklist de revisão e aprovação
6. **Done:** Comentário #3 - Conclusão e fechamento

**Artefatos Inclusos:**
- Código corrigido (Book.java e LoanManager.java)
- Comentários para GitHub em formato markdown
- Testes de validação com output esperado
- Mensagem de commit em **Conventional Commits**: `fix(loan-manager): enforce IllegalStateException in Book.borrowCopy for unavailable copies fixes #ID_DA_ISSUE`

---

## Instruções: Mapeamento de Evidências Visuais

### Passo 1: Localizar as Imagens
As imagens de evidência estão em seu local atual e precisam ser movidas para a pasta `evidencias/` com os nomes especificados.

### Passo 2: Mapeamento de Imagens
Execute as seguintes operações para mover e associar as imagens corretamente:

```bash
# Mapeamento de Bug para Imagem:
Bug #1 → image_cb79a7.png   (Violação de Estoque no Construtor)
Bug #2 → image_cb7969.png   (Inconsistência no LoanManager)
Bug #3 → image_cb7943.png   (Null Pointer no returnBook)
Bug #4 → image_cb7988.png   (Validação de Nome Vazio)
Bug #5 → image_cb79cb.png   (Duplicidade de ISBN)
```

### Passo 3: Mover Arquivos para Pasta evidencias
Use o comando abaixo no terminal (Windows PowerShell):

```powershell
# Navegar para o diretório do projeto
cd C:\Users\guife\IdeaProjects\unicesumar-manutencao\Atividade2_Manutencao\evidencias

# Mover imagens (substitua <CAMINHO_ORIGEM> pelo caminho correto das imagens)
Move-Item <CAMINHO_ORIGEM>\image_cb79a7.png -Destination "Bug1_Violacao_Estoque.png"
Move-Item <CAMINHO_ORIGEM>\image_cb7969.png -Destination "Bug2_Inconsistencia_LoanManager.png"
Move-Item <CAMINHO_ORIGEM>\image_cb7943.png -Destination "Bug3_NullPointer_returnBook.png"
Move-Item <CAMINHO_ORIGEM>\image_cb7988.png -Destination "Bug4_Validacao_NomeVazio.png"
Move-Item <CAMINHO_ORIGEM>\image_cb79cb.png -Destination "Bug5_Duplicidade_ISBN.png"
```

### Passo 4: Verificar Estrutura
Após mover os arquivos, a pasta `evidencias/` deve conter:

```
evidencias/
├── Bug1_Violacao_Estoque.png
├── Bug2_Inconsistencia_LoanManager.png
├── Bug3_NullPointer_returnBook.png
├── Bug4_Validacao_NomeVazio.png
└── Bug5_Duplicidade_ISBN.png
```

### Passo 5: Validar Referências
Os arquivos Markdown `01_bug_reports.md` já contêm referências às imagens:

```markdown
### Referência de Evidência Visual
**Imagem:** `image_cb79a7.png` - Screenshot do console mostrando dump de estado...
```

Se você renomear os arquivos (Passo 3), atualize as referências nos arquivos Markdown correspondentemente:

```markdown
### Referência de Evidência Visual
**Imagem:** `Bug1_Violacao_Estoque.png` - Screenshot do console mostrando dump de estado...
```

---

## Resumo de Bugs Identificados

### Matriz de Severidade x Prioridade

| Bug | Título | Severidade | Prioridade | Status |
|-----|--------|-----------|-----------|--------|
| #1 | Violação de Estoque (Book) | Normal | Média | Confirmado |
| #2 | Inconsistência (LoanManager) | Serious | Alta | Confirmado |
| #3 | Null Pointer (returnBook) | Normal | Média | Confirmado |
| #4 | Validação Nome Vazio (User) | Trivial | Baixa | Confirmado |
| #5 | Duplicidade ISBN (Library) | Normal | Média | Confirmado |

### Estatísticas de Triagem
- **Total de Bugs:** 5
- **Bugs Confirmados:** 5 ✓
- **Bugs Duplicados:** 0
- **Bugs Inválidos:** 0
- **Bugs Rejeitados (wontfix):** 1 hipotético

---

## Como Usar Esta Documentação

### Para Revisão de Qualidade
1. Leia `01_bug_reports.md` para entender cada bug
2. Consulte `02_triagem_tecnica.md` para classificação
3. Verifique as imagens em `evidencias/` para validação visual

### Para Correção de Código
1. Consulte `03_ciclo_de_vida_correcao.md` para o fluxo de Bug #2
2. Procure pelo código "Antes/Depois" na seção de implementação
3. Use a mensagem de commit como referência para versionamento

### Para Documentação Acadêmica
- Todos os arquivos seguem padrões de engenharia profissional
- Inclua referências aos arquivos em sua apresentação
- Use os logs de console simulados em exemplos

---

## Convenções Utilizadas

### Nomes de Arquivo
- Prefixo numérico `01_`, `02_`, `03_` para ordenação
- Sufixo descritivo em snake_case (ex: `bug_reports`)
- Extensão `.md` para Markdown

### Formato de Documentação
- **Markdown:** Para toda documentação textual
- **Code Blocks:** Para código-fonte e logs de console
- **Tabelas:** Para matrizes de classificação

### Padrões de Commit
- **Conventional Commits:** Tipo (fix, feat, docs), escopo, descrição
- **Referência de Issue:** `fixes #ID` para fechamento automático

---

## Próximos Passos

1. ✓ Validar que os 5 bugs foram triados corretamente
2. ✓ Revisar a estrutura de documentação
3. → Mover imagens para pasta `evidencias/`
4. → (Opcional) Implementar correção do Bug #2 seguindo `03_ciclo_de_vida_correcao.md`
5. → (Opcional) Submeter para repositório de entrega acadêmica

---

## Contato e Suporte

**Projeto:** Library Maintenance Lab - Atividade 2  
**Data de Criação:** 13 de maio de 2026  
**Autor da Documentação:** Engenheiro de QA Sênior / Engenheiro de Software  
**Instituição:** Unicesumar - Curso de Engenharia de Software

---

**Fim do README.md**
