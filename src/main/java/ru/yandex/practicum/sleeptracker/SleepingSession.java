package ru.yandex.practicum.sleeptracker;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final SleepQuality quality;

    public SleepingSession(LocalDateTime start, LocalDateTime end, SleepQuality quality) {
        this.start = start;
        this.end = end;
        this.quality = quality;
    }

    public static SleepingSession parse(String line) {
        String[] parts = line.split(";");
        return new SleepingSession(LocalDateTime.parse(parts[0].trim(), FORMAT), LocalDateTime.parse(parts[1].trim(), FORMAT), SleepQuality.valueOf(parts[2].trim()));
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public long getDurationMinutes() {
        return Duration.between(start, end).toMinutes();
    }
}