package seedu.duke.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// import com.google.gson.annotations.SerializedName;

/**
 * Customized class for showing date time and parsing supported string to date
 * time.
 */
public class DateTime {
    private static DateTimeFormatter[] formatters = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("yyyy-M-d HHmm"),
            DateTimeFormatter.ofPattern("yyyy-M-d H:m"),
            DateTimeFormatter.ofPattern("M-d-yyyy HHmm"),
            DateTimeFormatter.ofPattern("M-d-yyyy H:m"),
            DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm", Locale.ENGLISH), };
    private static DateTimeFormatter toStringFormatter = formatters[formatters.length - 1];
    public String standardString;
    transient LocalDateTime dateTime;

    // @SerializedName("rawData")

    /**
     * Create a new date time.
     *
     * @param rawData A String that needs to comply with a supported format and
     *                indicates a correct time that will be recorded by this Date
     *                instance time.
     * @throws TipsException Any excption will be throw in this type, which contains
     *                       information about this exception and the possible
     *                       solution.
     */
    public DateTime(String rawData) throws Exception {
        setRawData(rawData);
    }

    /**
     * Modifying an existing date time with a rawData String.
     *
     * @param rawData A String that needs to comply with a supported format and
     *                indicates a correct time that will be recorded by this Date
     *                instance.
     * @throws TipsException Any excption will be throw in this type, which contains
     *                       information about this exception and the possible
     *                       solution.
     */
    public void setRawData(String rawData) throws Exception {
        for (DateTimeFormatter formatter : formatters) {
            try {
                dateTime = LocalDateTime.parse(rawData, formatter);
                standardString = this.toString();
                return;
            } catch (Exception exception) {
                continue;
            }
        }
        throw new Exception("Unable to parse date time!");
    }

    @Override
    public String toString() {
        return dateTime.format(toStringFormatter);
    }

    /**
     * Compare the dates of this DateTime and another DateTime.
     *
     * @param dateTime The other DateTime that is used to compare.
     * @return If the date of current instance is earlier,it returns -1.
     *         If the date of current instance is later,it returns 1.
     *         Otherwise(when they're at exactly the same date) it returns 0.
     */
    public int compareDate(DateTime dateTime) {
        LocalDate date1 = this.dateTime.toLocalDate();
        LocalDate date2 = dateTime.dateTime.toLocalDate();
        if (date1.isBefore(date2)) {
            return 1;
        }
        if (date1.isAfter(date2)) {
            return -1;
        }
        return 0;
    }

    /**
     * Compare the dates of this DateTime and another Date.
     *
     * @param date The other Date that is used to compare.
     * @return If the date of current instance is earlier,it returns -1.
     *         If the date of current instance is later,it returns 1.
     *         Otherwise(when they're at exactly the same date) it returns 0.
     */
    public int compareDate(Date date) {
        LocalDate date1 = this.dateTime.toLocalDate();
        LocalDate date2 = date.date;
        if (date1.isBefore(date2)) {
            return -1;
        }
        if (date1.isAfter(date2)) {
            return 1;
        }
        return 0;
    }
}
