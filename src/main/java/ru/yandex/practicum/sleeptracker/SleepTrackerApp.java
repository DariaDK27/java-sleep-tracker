package ru.yandex.practicum.sleeptracker;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {
    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> FUNCTIONS = List.of(new SessionsCountFunction(),
            new MinDurationFunction(),
            new MaxDurationFunction(),
            new AverageDurationFunction(),
            new BadQualityCountFunction(),
            new InsomniaNightsFunction(),
            new ChronotypeFunction());

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу с логом сна в аргументах запуска.");
            return;
        }

        List<SleepingSession> sessions = Files.readAllLines(Path.of(args[0])).stream()
                .filter(line -> !line.isBlank())
                .map(SleepingSession::parse)
                .collect(Collectors.toList());

        FUNCTIONS.stream()
                .map(function -> function.apply(sessions))
                .forEach(System.out::println);
    }
}