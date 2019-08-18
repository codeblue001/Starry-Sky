package jide.delano.starrysky.view;

public class UserSelectionUtil {
    public static String ZIP = "ZIP";
    public static String UNIT = "UNIT";
    public static String INPUT_UNIT = "";
    public static String INPUT_ZIPCODE = "";


    public static boolean verifyZip(String zip) {
        return zip.length() == 5;
    }
}

