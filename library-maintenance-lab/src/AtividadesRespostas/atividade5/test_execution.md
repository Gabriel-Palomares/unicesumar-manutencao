# Execução de testes — Atividade 5

**Pasta do Admink:** `library-maintenance-lab/src/admink/admink`

## Pré-requisitos

```bash
cd library-maintenance-lab/src/admink/admink
composer install
```

## Rodar os testes

```bash
vendor/bin/phpunit tests/Unit/GoogleCalendarServiceTest.php
```

## Resultado esperado

```
..                                                                  2 / 2 (100%)
```

Os testes mockam `Google\Service\Calendar` e não exigem credenciais Google nem banco de dados.
