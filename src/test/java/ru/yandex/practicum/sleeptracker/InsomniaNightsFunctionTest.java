package ru.yandex.practicum.sleeptracker;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InsomniaNightsFunctionTest {
    private final InsomniaNightsFunction function = new InsomniaNightsFunction();

    @Test
    void emptyList() {
        assertEquals(0L, function.apply(List.of()).getValue());
    }

    @Test
    void noInsomniaInExampleFile() {
        List<SleepingSession> sessions = List.of(SleepingSession.parse("01.10.25 22:15;02.10.25 08:00;GOOD"),
                SleepingSession.parse("02.10.25 23:00;03.10.25 08:00;NORMAL"),
                SleepingSession.parse("03.10.25 14:30;03.10.25 15:20;NORMAL"),
                SleepingSession.parse("03.10.25 23:30;04.10.25 06:20;BAD"));
        assertEquals(0L, function.apply(sessions).getValue());
    }

    @Test
    void sleepOnlyInTheMorningIsInsomnia() {
        List<SleepingSession> sessions = List.of(SleepingSession.parse("03.10.25 07:00;03.10.25 11:00;NORMAL"));
        assertEquals(1L, function.apply(sessions).getValue());
    }

    @Test
    void sleepOnlyInTheEveningIsInsomnia() {
        List<SleepingSession> sessions = List.of(SleepingSession.parse("01.10.25 17:00;01.10.25 23:00;NORMAL"));
        assertEquals(1L, function.apply(sessions).getValue());
    }

    @Test
    void sleepFrom2To5IsNotInsomnia() {
        List<SleepingSession> sessions = List.of(SleepingSession.parse("02.10.25 02:00;02.10.25 05:00;NORMAL"));
        assertEquals(0L, function.apply(sessions).getValue());
    }

    @Test
    void oneMissedNightBetweenTwoSessions() {
        List<SleepingSession> sessions = List.of(SleepingSession.parse("01.10.25 23:00;02.10.25 07:00;GOOD"), SleepingSession.parse("03.10.25 07:00;03.10.25 11:00;GOOD"));
        assertEquals(1L, function.apply(sessions).getValue());
    }

    @Test
    void logSpansTwoMonths() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.parse("30.09.25 23:00;01.10.25 07:00;GOOD"),
                SleepingSession.parse("02.10.25 23:00;03.10.25 07:00;GOOD"));
        assertEquals(1L, function.apply(sessions).getValue());
    }
}
