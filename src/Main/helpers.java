package Main;

import java.text.DecimalFormat;

public class helpers {

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static Double ConvertDouble(String str){

        // Zaokragla
        DecimalFormat df2 = new DecimalFormat(".##");

        double output;
        String tmp;

        output = Double.valueOf(str);
        tmp = df2.format(output);
        tmp = tmp.replace(',','.');
        output = Double.valueOf(tmp);

        return output;
    }
}
