Bugs identificados no projeto

4. Título da Issue: Datas de empréstimo gravadas como placeholders em vez das datas reais

Descrição: Em LoanManager.borrowBook(), borrowDate e dueDate são definidos como "DataAtual" e "DataDevolucao" e não usam as datas realmente informadas pelo usuário em LibrarySystem.handleBorrowBook().

Classificação: Erro de Lógica

Impacto: Histórico de empréstimos e relatórios de prazo ficam com dados falsos, prejudicando rastreabilidade e cálculo de multas.

Causa Raiz Provável: Separação incorreta entre coleta de dados na UI (LibrarySystem) e criação do domínio (LoanManager), com datas não sendo passadas ao método de criação do empréstimo.

5. Título da Issue: Relatório ignora filtros de ano e categoria

Descrição: Em ReportGenerator.generateSimpleReport(), os parâmetros yearFilter e category são recebidos, mas nunca usados no relatório retornado.

Classificação: Erro de Regra de Negócio

Impacto: Usuário recebe relatório incorreto e acredita que filtros foram aplicados, o que prejudica decisões baseadas em dados.

Causa Raiz Provável: Implementação incompleta do método; parâmetros foram mantidos na assinatura, mas a lógica de filtragem não foi desenvolvida.
