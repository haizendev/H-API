package fr.haizen.hapi.cooldowns;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DurationFormatUtils;

public class TimeUtils
{
    private static final Pattern PATTERN;
    private static final long MINUTE;
    private static final long HOUR;
    private static final long DAY;
    private static final ThreadLocal<DecimalFormat> REMAINING_SECONDS;
    private static final ThreadLocal<DecimalFormat> REMAINING_SECONDS_TRAILING;
    private static final SimpleDateFormat FRENCH_DATE_FORMAT;
    
    public static String getRemaining(final long duration, final boolean milliseconds) {
        return getRemaining(duration, milliseconds, true);
    }
    
    public static String getRemaining(final long duration, final boolean milliseconds, final boolean trail) {
        if (milliseconds && duration < TimeUtils.MINUTE) {
            return (trail ? TimeUtils.REMAINING_SECONDS_TRAILING : TimeUtils.REMAINING_SECONDS).get().format(duration * 0.001) + 's';
        }
        if (duration >= TimeUtils.DAY) {
            return DurationFormatUtils.formatDuration(duration, "dd-HH:mm:ss");
        }
        return DurationFormatUtils.formatDuration(duration, ((duration >= TimeUtils.HOUR) ? "HH:" : "") + "mm:ss");
    }
    
    public static long getDuration(final String stringDuration) {
        if (stringDuration.toLowerCase().startsWith("perm")) {
            return 157680000000L;
        }
        long time = 0L;
        final Matcher matcher = TimeUtils.PATTERN.matcher(stringDuration);
        if (matcher.find()) {
            if (matcher.group(1) != null && !matcher.group(1).isEmpty()) {
                time += Long.parseLong(matcher.group(1)) * 31536000000L;
            }
            if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
                time += Long.parseLong(matcher.group(2)) * 2592000000L;
            }
            if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
                time += Long.parseLong(matcher.group(3)) * 604800000L;
            }
            if (matcher.group(4) != null && !matcher.group(4).isEmpty()) {
                time += Long.parseLong(matcher.group(4)) * 86400000L;
            }
            if (matcher.group(5) != null && !matcher.group(5).isEmpty()) {
                time += Long.parseLong(matcher.group(5)) * 3600000L;
            }
            if (matcher.group(6) != null && !matcher.group(6).isEmpty()) {
                time += Long.parseLong(matcher.group(6)) * 60000L;
            }
            if (matcher.group(7) != null && !matcher.group(7).isEmpty()) {
                time += Long.parseLong(matcher.group(7)) * 1000L;
            }
        }
        if (time == 0L) {
            throw new NumberFormatException("Null duration!");
        }
        return time;
    }
    
    public static String getDuration(final long duration) {
        return getDuration(duration, true, true);
    }
    
    public static String getDuration(final long duration, final boolean displaySeconds) {
        return getDuration(duration, true, displaySeconds);
    }
    
    public static String getDuration(final long duration, final boolean displayMinutes, final boolean displaySeconds) {
        final StringBuilder stringBuilder = new StringBuilder();
        int seconds = (int)(duration / 1000L);
        int years = 0;
        while (seconds >= 31536000) {
            ++years;
            seconds -= 31536000;
        }
        if (years > 0) {
            stringBuilder.append(years).append(" ann§e").append((years > 1) ? "s " : " ");
        }
        int months = 0;
        while (seconds >= 2592000) {
            ++months;
            seconds -= 2592000;
        }
        if (months > 0) {
            stringBuilder.append(months).append(" mois ");
        }
        int days = 0;
        while (seconds >= 86400) {
            ++days;
            seconds -= 86400;
        }
        if (days > 0) {
            stringBuilder.append(days).append(" jour").append((days > 1) ? "s " : " ");
        }
        int hours = 0;
        while (seconds >= 3600) {
            ++hours;
            seconds -= 3600;
        }
        if (hours > 0) {
            stringBuilder.append(hours).append(" heure").append((hours > 1) ? "s " : " ");
        }
        int minutes = 0;
        while (seconds >= 60) {
            ++minutes;
            seconds -= 60;
        }
        if ((minutes > 0 && displayMinutes) || (duration < 600000L && duration >= 60000L)) {
            stringBuilder.append(minutes).append(" minute").append((minutes > 1) ? "s " : " ");
        }
        if ((seconds > 0 && displaySeconds) || duration < 60000L) {
            stringBuilder.append(seconds).append(" seconde").append((seconds > 1) ? "s " : " ");
        }
        final String output = stringBuilder.toString();
        return (output.length() == 0) ? output : output.substring(0, output.length() - 1);
    }
    
    public static long getMINUTE() {
        return TimeUtils.MINUTE;
    }
    
    public static long getHOUR() {
        return TimeUtils.HOUR;
    }
    
    public static long getDAY() {
        return TimeUtils.DAY;
    }
    
    public static SimpleDateFormat getFRENCH_DATE_FORMAT() {
        return TimeUtils.FRENCH_DATE_FORMAT;
    }
    
    static {
        PATTERN = Pattern.compile("(?:([0-9]+)*y[a-z]*)?(?:([0-9]+)*mo[a-z]*)?(?:([0-9]+)*w[a-z]*)?(?:([0-9]+)*d[a-z]*)?(?:([0-9]+)*h[a-z]*)?(?:([0-9]+)*m[a-z]*)?(?:([0-9]+)*s[a-z]*)?", 2);
        MINUTE = TimeUnit.MINUTES.toMillis(1L);
        HOUR = TimeUnit.HOURS.toMillis(1L);
        DAY = TimeUnit.DAYS.toMillis(1L);
        REMAINING_SECONDS = ThreadLocal.withInitial(() -> new DecimalFormat("0.#"));
        REMAINING_SECONDS_TRAILING = ThreadLocal.withInitial(() -> new DecimalFormat("0.0"));
        FRENCH_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }
}
