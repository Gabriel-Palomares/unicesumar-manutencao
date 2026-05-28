@echo off
REM Execute este script a partir da pasta src\AtividadesRespostas\Atividade4
cd /d "%~dp0..\..\.."
if not exist pom.xml (
  echo ERRO: arquivo pom.xml nao encontrado. Execute o script a partir da pasta Atividade4.
  exit /b 1
)
echo Executando testes Maven em %cd%
where mvn >nul 2>&1
if %errorlevel% neq 0 (
  echo ERRO: Maven nao encontrado no PATH.
  echo Instale o Maven ou use a IDE para executar os testes.
  exit /b 1
)
mvn -q test
if %errorlevel% neq 0 (
  echo TESTES FALHARAM.
  exit /b %errorlevel%
)
echo TESTES CONCLUIDOS COM SUCESSO.
