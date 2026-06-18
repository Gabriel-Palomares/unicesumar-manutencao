<?php

namespace App\Services;

use App\Agendamento;
use Exception;
use Google\Client as GoogleClient;
use Google\Service\Calendar;
use Google\Service\Calendar\Event as CalendarEvent;
use Google\Service\Calendar\EventDateTime;
use Google\Service\Exception as GoogleServiceException;
use Illuminate\Support\Facades\Log;

class GoogleCalendarService
{
    private $calendar;
    private $calendarId;

    public function __construct(Calendar $calendar = null, $calendarId = null)
    {
        $this->calendarId = $calendarId ?? config('services.google_calendar.calendar_id', 'primary');
        $this->calendar = $calendar ?? $this->buildCalendarService();
    }

    public function sync(Agendamento $agendamento): bool
    {
        try {
            $event = $this->buildEvent($agendamento);
            $this->calendar->events->insert($this->calendarId, $event);

            return true;
        } catch (GoogleServiceException | Exception $exception) {
            Log::error('GoogleCalendarService exception', [
                'message' => $exception->getMessage(),
                'agendamento_id' => $agendamento->id_agendamento,
            ]);

            return false;
        }
    }

    private function buildCalendarService(): Calendar
    {
        $client = new GoogleClient();
        $client->setApplicationName(config('app.name', 'Admink'));
        $client->setScopes(explode(',', config('services.google_calendar.scopes')));
        $client->setAuthConfig([
            'client_id' => config('services.google_calendar.client_id'),
            'client_secret' => config('services.google_calendar.client_secret'),
            'redirect_uris' => [config('services.google_calendar.redirect_uri')],
        ]);
        $client->setAccessType('offline');

        $refreshToken = config('services.google_calendar.refresh_token');
        if ($refreshToken) {
            $client->fetchAccessTokenWithRefreshToken($refreshToken);
        }

        return new Calendar($client);
    }

    private function buildEvent(Agendamento $agendamento): CalendarEvent
    {
        $timezone = config('app.timezone', 'UTC');

        $start = $agendamento->data_horario_inicio instanceof \DateTime
            ? $agendamento->data_horario_inicio->format(\DateTime::ATOM)
            : $agendamento->data_horario_inicio;
        $end = $agendamento->data_horario_fim instanceof \DateTime
            ? $agendamento->data_horario_fim->format(\DateTime::ATOM)
            : $agendamento->data_horario_fim;

        $startDateTime = new EventDateTime();
        $startDateTime->setDateTime($start);
        $startDateTime->setTimeZone($timezone);

        $endDateTime = new EventDateTime();
        $endDateTime->setDateTime($end);
        $endDateTime->setTimeZone($timezone);

        $event = new CalendarEvent();
        $event->setSummary('Agendamento #' . $agendamento->id_agendamento);
        $event->setDescription($agendamento->observacao ?? 'Agendamento do sistema Admink');
        $event->setStart($startDateTime);
        $event->setEnd($endDateTime);

        return $event;
    }
}
