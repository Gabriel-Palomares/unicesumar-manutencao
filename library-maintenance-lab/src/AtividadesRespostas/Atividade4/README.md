# Atividade 4 - Inventario Automatizado, SATD e Refatoracao Oportunista

Esta pasta contem a entrega final da atividade 4 para o projeto `library-maintenance-lab`.

## Conteudo entregue

- `README.md`: resumo da atividade, os principais problemas detectados e a refatoracao aplicada.
- `issue_templates.md`: cinco modelos de issue de divida tecnica para criar no GitHub.
- `board_link.md`: local para informar o link do board com as 5 issues registradas.
- `test_execution.md`: instrucoes para executar e registrar os testes.
- `images/`: evidencias visuais do SonarQube/SonarLint e do resultado do teste JUnit.

## O que foi feito

1. Identificacao de problemas de qualidade baseados no SonarQube.
2. Registro dos problemas em templates de issue de divida tecnica.
3. Refatoracao de codigo para reduzir problemas de manutenibilidade.
4. Adicao de teste automatizado JUnit para validar o comportamento.

## Refatoracao aplicada

- `NotificationService.java`: parametros nao utilizados removidos e log mantido.
- `Library.java`: encapsulamento de configuracoes globais e constante para o literal repetido "Software".

## Evidencias

- As evidencias sao as imagens presentes em `src/AtividadesRespostas/Atividade4/images/`.
- Imagens atuais:
  - `sonarqube-critical.png`
  - `sonarqube-medium-1.png`
  - `sonarqube-medium-2.png`
  - `sonarqube-medium-3.png`
  - `sonarqube-medium-4.png`
  - `junit-notificationservice.png`
- Coloque capturas de tela do SonarQube/SonarLint mostrando os code smells e seus graus de severidade.
- Inclua o print da barra verde do JUnit após a correção.

## Board de Issues

- Link do board com as 5 issues registradas: consulte `board_link.md`
- Links diretos das issues:
  1. https://github.com/Gabriel-Palomares/unicesumar-manutencao/issues/4
  2. https://github.com/Gabriel-Palomares/unicesumar-manutencao/issues/6
  3. https://github.com/Gabriel-Palomares/unicesumar-manutencao/issues/5
  4. https://github.com/Gabriel-Palomares/unicesumar-manutencao/issues/3
  5. https://github.com/Gabriel-Palomares/unicesumar-manutencao/issues/2

## Observacoes

- Use label `technical debt` ao criar as issues no GitHub.
- O codigo alterado foi mantido funcionalmente equivalente ao legado.
- Nao use acentos nem cedilhas nos arquivos de entrega para evitar problemas de visualizacao.
