package ru.yandex.practicum.sleeptracker;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.LongStream;

public class InsomniaNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        SleepingSession first = sessions.get(0);
        SleepingSession last = sessions.get(sessions.size() - 1);

        LocalDate startDate = first.getStart().toLocalDate();
        LocalDate firstNight = first.getStart().getHour() < 12 ? startDate : startDate.plusDays(1);
        LocalDate lastNight = last.getEnd().toLocalDate();

        long nights = Math.max(1, ChronoUnit.DAYS.between(firstNight, lastNight) + 1);
        long insomnia = LongStream.range(0, nights)
                .mapToObj(firstNight::plusDays)
                .filter(night -> !hasSleep(sessions, night))
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей", insomnia);
    }

    private boolean hasSleep(List<SleepingSession> sessions, LocalDate night) {
        return sessions.stream().anyMatch(session -> session.getStart().isBefore(night.atTime(6, 0)) && session.getEnd().isAfter(night.atStartOfDay()));
    }
}
