package ru.yandex.practicum.sleeptracker;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeFunctionTest {
    private final ChronotypeFunction function = new ChronotypeFunction();

    @Test
    void owl() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.parse("01.10.25 23:30;02.10.25 09:30;GOOD"));
        assertEquals(Chronotype.OWL, function.apply(sessions).getValue());
    }

    @Test
    void lark() {
        List<SleepingSession> sessions = List.of(SleepingSession.parse("01.10.25 21:00;02.10.25 06:00;GOOD"));
        assertEquals(Chronotype.LARK, function.apply(sessions).getValue());
    }

    @Test
    void tieMeansPigeon() {
        List<SleepingSession> sessions = List.of(SleepingSession.parse("01.10.25 23:30;02.10.25 09:30;GOOD"), SleepingSession.parse("02.10.25 21:00;03.10.25 06:00;GOOD"));
        assertEquals(Chronotype.PIGEON, function.apply(sessions).getValue());
    }

    @Test
    void daySessionsAreIgnored() {
        List<SleepingSession> sessions = List.of(SleepingSession.parse("01.10.25 23:30;02.10.25 09:30;GOOD"), SleepingSession.parse("02.10.25 14:00;02.10.25 15:00;GOOD"), SleepingSession.parse("02.10.25 16:00;02.10.25 17:00;GOOD"));
        assertEquals(Chronotype.OWL, function.apply(sessions).getValue());
    }
}
