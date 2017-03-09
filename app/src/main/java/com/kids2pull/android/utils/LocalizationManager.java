package com.kids2pull.android.utils;

import java.util.Locale;

/**
 * User: tarapon Date: 6/2/11 Time: 5:41 PM
 */
public class LocalizationManager {

    public static String getLanguage() {
        String language = Locale.getDefault().getLanguage().substring(0, 2);
        return language;
    }


    public static boolean isRTL() {
        String language = getLanguage();
        if (language.equalsIgnoreCase("he") || language.equalsIgnoreCase("iw")) {
            return true;
        }
        return false;
    }

    public static boolean isCurrencyInLeft(String currency) {
        boolean inLeft = false;
        if (!StringUtils.isNullOrEmpty(currency)) {
            if (currency.trim().equalsIgnoreCase("$")
                    || currency.trim().equalsIgnoreCase("₪")
                    || currency.trim().equalsIgnoreCase("£")) {
                inLeft = true;
            }
        }
        return inLeft;
    }

    public static boolean isCurrencyWithSpace(String currency) {
        if (currency.trim().equalsIgnoreCase("$")
                || currency.trim().equalsIgnoreCase("₪")
                || currency.trim().equalsIgnoreCase("£")) {
            return false;
        }

        return true;
    }


}
