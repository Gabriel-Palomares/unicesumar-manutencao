3. Título da Issue: Entrada numérica inválida é mascarada por valor padrão
Descrição: Em Util.DataUtil.askInt(), quando o usuário digita um número inválido, o método retorna o fallback em vez de alertar ou repetir a leitura. Em LibrarySystem, esse fallback muitas vezes é -1, o que pode ser tratado como um ID válido.
Classificação: Erro de Interface
Impacto: Comandos como empréstimo/devolução podem usar um ID inválido sem indicar claramente ao usuário o erro de entrada.
Causa Raiz Provável: Design de askInt() que oculta NumberFormatException e usa fallback automático em vez de validação explícita.

