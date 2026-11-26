package ru.yandex.practicum.sleeptracker;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
class BasicFunctionsTest {
    private final List<SleepingSession> sessions = List.of(SleepingSession.parse("01.10.25 22:15;02.10.25 08:00;GOOD"),
            SleepingSession.parse("02.10.25 23:00;03.10.25 08:00;NORMAL"),
            SleepingSession.parse("03.10.25 14:30;03.10.25 15:20;NORMAL"),
            SleepingSession.parse("03.10.25 23:30;04.10.25 06:20;BAD"));

    @Test
    void sessionsCountOfFour() {
        assertEquals(4L, new SessionsCountFunction().apply(sessions).getValue());
    }

    @Test
    void sessionsCountOfEmptyList() {
        assertEquals(0L, new SessionsCountFunction().apply(List.of()).getValue());
    }

    @Test
    void minDuration() {
        assertEquals(50L, new MinDurationFunction().apply(sessions).getValue());
    }

    @Test
    void minDurationOfEmptyList() {
        assertEquals(0L, new MinDurationFunction().apply(List.of()).getValue());
    }

    @Test
    void maxDuration() {
        assertEquals(585L, new MaxDurationFunction().apply(sessions).getValue());
    }

    @Test
    void maxDurationOfEmptyList() {
        assertEquals(0L, new MaxDurationFunction().apply(List.of()).getValue());
    }

    @Test
    void averageDuration() {
        List<SleepingSession> two = List.of(SleepingSession.parse("01.10.25 22:00;01.10.25 23:00;GOOD"), SleepingSession.parse("02.10.25 20:00;02.10.25 22:00;GOOD"));
        assertEquals(90.0, new AverageDurationFunction().apply(two).getValue());
    }

    @Test
    void averageDurationOfEmptyList() {
        assertEquals(0.0, new AverageDurationFunction().apply(List.of()).getValue());
    }

    @Test
    void badQualityCount() {
        assertEquals(1L, new BadQualityCountFunction().apply(sessions).getValue());
    }

    @Test
    void badQualityCountWhenNoBadSessions() {
        List<SleepingSession> good = List.of(SleepingSession.parse("01.10.25 22:15;02.10.25 08:00;GOOD"));
        assertEquals(0L, new BadQualityCountFunction().apply(good).getValue());
    }
}
