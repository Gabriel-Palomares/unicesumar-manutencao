<?php

namespace Tests\Unit;

use App\Agendamento;
use App\Services\GoogleCalendarService;
use Google\Service\Calendar;
use Google\Service\Calendar\Event as CalendarEvent;
use Google\Service\Calendar\Resource\Events;
use Google\Service\Exception as GoogleServiceException;
use Tests\TestCase;

class GoogleCalendarServiceTest extends TestCase
{
    public function test_sync_returns_true_when_google_calendar_api_succeeds()
    {
        $events = $this->createMock(Events::class);
        $events->expects($this->once())
            ->method('insert')
            ->with('primary', $this->isInstanceOf(CalendarEvent::class))
            ->willReturn(new CalendarEvent(['id' => 'event123']));

        $calendar = $this->createMock(Calendar::class);
        $calendar->events = $events;

        $agendamento = new Agendamento();
        $agendamento->id_agendamento = 1;
        $agendamento->observacao = 'Teste de sincronização';
        $agendamento->data_horario_inicio = new \DateTime('2026-06-17T10:00:00Z');
        $agendamento->data_horario_fim = new \DateTime('2026-06-17T11:00:00Z');

        $service = new GoogleCalendarService($calendar, 'primary');

        $this->assertTrue($service->sync($agendamento));
    }

    public function test_sync_returns_false_when_google_calendar_api_fails()
    {
        $events = $this->createMock(Events::class);
        $events->expects($this->once())
            ->method('insert')
            ->willThrowException(new GoogleServiceException('Bad Request', 400));

        $calendar = $this->createMock(Calendar::class);
        $calendar->events = $events;

        $agendamento = new Agendamento();
        $agendamento->id_agendamento = 2;
        $agendamento->observacao = 'Teste de erro';
        $agendamento->data_horario_inicio = new \DateTime('2026-06-17T10:00:00Z');
        $agendamento->data_horario_fim = new \DateTime('2026-06-17T11:00:00Z');

        $service = new GoogleCalendarService($calendar, 'primary');

        $this->assertFalse($service->sync($agendamento));
    }
}
