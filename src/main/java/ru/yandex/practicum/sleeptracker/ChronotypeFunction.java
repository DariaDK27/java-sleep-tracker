package ru.yandex.practicum.sleeptracker;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<Chronotype> nights = sessions.stream()
                .filter(this::isNightSession)
                .map(this::defineType)
                .collect(Collectors.toList());

        long owls = count(nights, Chronotype.OWL);
        long larks = count(nights, Chronotype.LARK);
        long pigeons = count(nights, Chronotype.PIGEON);

        Chronotype result;
        if (owls > larks && owls > pigeons) {
            result = Chronotype.OWL;
        } else if (larks > owls && larks > pigeons) {
            result = Chronotype.LARK;
        } else {
            result = Chronotype.PIGEON;
        }
        return new SleepAnalysisResult("Хронотип пользователя", result);
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDate night = session.getEnd().toLocalDate();
        return session.getStart().isBefore(night.atTime(6, 0)) && session.getEnd().isAfter(night.atStartOfDay());
    }

    private Chronotype defineType(SleepingSession session) {
        LocalTime asleep = session.getStart().toLocalTime();
        LocalTime wake = session.getEnd().toLocalTime();

        boolean lateAsleep = asleep.isAfter(LocalTime.of(23, 0)) || asleep.isBefore(LocalTime.NOON);
        boolean earlyAsleep = asleep.isBefore(LocalTime.of(22, 0)) && !asleep.isBefore(LocalTime.NOON);

        if (lateAsleep && wake.isAfter(LocalTime.of(9, 0))) {
            return Chronotype.OWL;
        }
        if (earlyAsleep && wake.isBefore(LocalTime.of(7, 0))) {
            return Chronotype.LARK;
        }
        return Chronotype.PIGEON;
    }

    private long count(List<Chronotype> types, Chronotype type) {
        return types.stream().filter(t -> t == type).count();
    }
}
