package MoEzwawi.GUIDANOMADI_BACKEND.functionalities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateParser {
    public static LocalDate parseDateForItaly (String date){
        LocalDate parsedDate = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY);
            parsedDate = LocalDate.parse(date, formatter);
        } catch(DateTimeParseException e){
            System.err.println("The entered date format is not valid!");
        }
        return parsedDate;
    }
    public static LocalDate parseDateForTheUS (String date){
        LocalDate parsedDate = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
            parsedDate = LocalDate.parse(date, formatter);
        } catch(DateTimeParseException e){
            System.err.println("The entered date format is not valid!");
        }
        return parsedDate;
    }
}