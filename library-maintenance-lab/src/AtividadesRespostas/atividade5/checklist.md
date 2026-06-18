# Checklist — Atividade 5

**Projeto Admink:** `library-maintenance-lab/src/admink/admink`

Legenda: ✅ feito | ⏭️ responsabilidade de quem for rodar/entregar

## Arquivos esperados

| Arquivo | Status |
|---------|--------|
| `app/Services/GoogleCalendarService.php` | ✅ |
| `tests/Unit/GoogleCalendarServiceTest.php` | ✅ |
| Ajuste em `AgendamentoController.php` | ✅ |
| `.env.example` | ✅ |

## Roteiro da atividade

| # | Item | Status |
|---|------|--------|
| 1 | Preparar ambiente | ⏭️ |
| 2 | Ponto de integração no `store()` | ✅ |
| 3 | Broto `GoogleCalendarService` | ✅ |
| 4 | Testes (feliz + erro, mock) | ✅ |
| 5 | Chamada pontual no controller | ✅ |
| 6 | Revisar solução | ✅ |

## Dependências para integração

| Item | Status |
|------|--------|
| `google/apiclient` no `composer.json` | ✅ |
| `guzzlehttp/guzzle` no `composer.json` | ✅ |
| Variáveis OAuth no `.env.example` | ✅ |
| Guia OAuth | ✅ `integracao_google_calendar.md` |
