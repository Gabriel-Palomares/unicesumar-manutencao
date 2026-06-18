# Revisão de escopo — Atividade 5

**Projeto:** `library-maintenance-lab/src/admink/admink`  
**Resultado:** dentro do escopo.

A pasta `admink/` na raiz do repositório foi removida; toda a implementação está em `library-maintenance-lab/src/admink/`.

## Conformidade

| Item | OK |
|------|----|
| Broto isolado `GoogleCalendarService` | ✅ |
| `google/apiclient` + OAuth via `.env` | ✅ |
| Chamada após `$agendamento->save()` | ✅ |
| Controller sem lógica de API | ✅ |
| 2 testes com mock do Calendar | ✅ |
| `composer.json` com dependências necessárias | ✅ |

## Próximo passo de quem for usar

1. `cd library-maintenance-lab/src/admink/admink && composer install`
2. Configurar `.env` — ver `integracao_google_calendar.md`
3. `vendor/bin/phpunit tests/Unit/GoogleCalendarServiceTest.php`
