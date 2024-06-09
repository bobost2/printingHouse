package bstefanov.printinghouse.ui.utils;

import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.text.ParseException;

public class DoubleStringConverter {
    private static final StringConverter<Double> doubleStringConverter = new StringConverter<>() {
        private final DecimalFormat df = new DecimalFormat("#.00");

        @Override
        public String toString(Double value) {
            if (value == null) {
                return "";
            }

            return df.format(value);
        }

        @Override
        public Double fromString(String string) {
            try {
                if (string == null) {
                    return null;
                }

                string = string.trim();

                if (string.isEmpty()) {
                    return null;
                }

                return df.parse(string).doubleValue();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    public static StringConverter<Double> getConverter() {
        return doubleStringConverter;
    }
}
