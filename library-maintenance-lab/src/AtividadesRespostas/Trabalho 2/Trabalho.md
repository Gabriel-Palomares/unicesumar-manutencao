Bugs identificados no projeto
1. Título da Issue: Empréstimo criado mesmo quando não há cópias disponíveis
   Descrição: Em LoanManager.borrowBook() o método book.borrowCopy() é chamado, mas seu valor de retorno não é verificado. Como Book.borrowCopy() retorna false em vez de lançar exceção quando não há cópias, o sistema continua e registra o empréstimo mesmo que não haja estoque.
   Classificação: Erro de Regra de Negócio
   Impacto: Usuário pode emprestar livros sem cópias, levando a inventário incorreto e à geração de empréstimos inválidos.
   Causa Raiz Provável: Falta de verificação do resultado de borrowCopy() e dependência errada de exceção onde o método apenas retorna false.

2. Título da Issue: Inventário de livro inconsistente quando availableCopies excede totalCopies
   Descrição: Em Model.Book o construtor aceita availableCopies maior que totalCopies sem correção, e apenas corrige valores negativos. Isso permite criar livros com disponibilidade superior ao total.
   Classificação: Erro de Lógica
   Impacto: Relatórios de estoque e regras de empréstimo podem ficar incorretos, e a contagem de cópias disponíveis pode ser impossível de conciliar.
   Causa Raiz Provável: Validação de parâmetros incompleta no construtor de Book, com fallback apenas para valores negativos e sem sincronização de availableCopies com totalCopies.