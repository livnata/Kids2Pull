//#preprocess
package com.kids2pull.android.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;

import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * There is no Split function in Java 1.3 :(
     */
    public static final String[] splitString(final String data, final char splitChar) {
        ArrayList<String> v = new ArrayList<String>();
        String working = data;
        int index = working.indexOf(splitChar);

        while (index != -1) {
            String tmp = "";
            if (index > 0) {
                tmp = working.substring(0, index);
            }
            v.add(tmp);

            working = working.substring(index + 1);
            index = working.indexOf(splitChar);
        }
        v.add(working);

        String[] retVal = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            retVal[i] = v.get(i);
        }
        return retVal;
    }

    public static String replace(String source, String pattern, String replacement) {
        if (source == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        int idx = -1;
        int patIdx = 0;

        while ((idx = source.indexOf(pattern, patIdx)) != -1) {
            sb.append(source.substring(patIdx, idx));
            sb.append(replacement);
            patIdx = idx + pattern.length();
        }
        sb.append(source.substring(patIdx));
        return sb.toString();
    }

    public static boolean isValidPhone(String phone) {
        if (StringUtils.isNullOrEmpty(phone)) {
            return false;
        }

        phone = phone.trim();

        Pattern pattern = Pattern.compile("^([0-9]{10,12})$");
        return pattern.matcher(phone).matches();
    }

    public static boolean isValidStringWithRegex(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text).matches();
    }

    public static boolean isValidEmailAddress(String emailAddress) {
        if (StringUtils.isNullOrEmpty(emailAddress)) {
            return false;
        }

        emailAddress = emailAddress.trim();

        Pattern pattern = Pattern.compile(
                "^([\\w\\-\\.]+)@((\\[([0-9]{1,3}\\.){3}[0-9]{1,3}\\])|(([\\w\\-]+\\.)+)([a-zA-Z]{2,10}))$");
        return pattern.matcher(emailAddress).matches();
    }

    static Pattern patternPostCode = Pattern.compile(
            "^(GIR[ ]?0AA)$" +
                    "|^([A-PR-UWYZ][0-9][ ]?[0-9][ABD-HJLNPQ-UW-Z]{2})$" +
                    "|^([A-PR-UWYZ][0-9][0-9][ ]?[0-9][ABD-HJLNPQ-UW-Z]{2})$" +
                    "|^([A-PR-UWYZ][A-HK-Y0-9][0-9][ ]?[0-9][ABD-HJLNPQ-UW-Z]{2})$" +
                    "|^([A-PR-UWYZ][A-HK-Y0-9][0-9][0-9][ ]?[0-9][ABD-HJLNPQ-UW-Z]{2})$" +
                    "|^([A-PR-UWYZ][0-9][A-HJKS-UW0-9][ ]?[0-9][ABD-HJLNPQ-UW-Z]{2})$" +
                    "|^([A-PR-UWYZ][A-HK-Y0-9][0-9][ABEHMNPRVWXY0-9][ ]?[0-9][ABD-HJLNPQ-UW-Z]{2})$"
    );

    public static boolean isValidPostCode(String postCode) {
        if (TextUtils.isEmpty(postCode)) {
            return false;
        }

        postCode = postCode.trim().toUpperCase();

        return patternPostCode.matcher(postCode).matches();
    }


    public static String join(String... strings) {
        StringBuilder result = new StringBuilder();
        for (String string : strings) {
            if (!StringUtils.isNullOrEmpty(string)) {
                if (result.length() > 0) {
                    result.append(", ");
                }
                result.append(string);
            }
        }
        return result.toString();
    }

    public static boolean isNullOrEmpty(CharSequence string) {
        if (string == null) {
            return true;
        }
        return string.toString().trim().equals("");
    }

    public static String capitalizeFirstLetters(String s) {
        if (s != null && s.length() > 1 && "en"
                .equalsIgnoreCase(LocalizationManager.getLanguage())) {
            String result = "";
            String[] parts = s.split(" ");
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (part.length() > 1) {
                    if (i == 0) {
                        result += part.toString().substring(0, 1).toUpperCase() + part.toString()
                                .substring(1);
                    } else {
                        result += " " + part.toString().substring(0, 1).toUpperCase() + part
                                .toString().substring(1);
                    }
                } else {
                    if (i > 0) {
                        result += " ";
                    }
                }
            }
            return result;
        }
        return s;
    }

    public static String buildFormattedPriceWithCurrency(double price, String currency,
                                                         NumberFormat formatter) {
        String result = String.valueOf(formatter.format(price < 0 ? Math.abs(price) : price));
        if (LocalizationManager.isCurrencyInLeft(currency)) {
            result = currency + result;
        } else {
            result = result + " " + currency;
        }

        if (price < 0) {
            result = "-" + result;
        }
        return result;
    }

    /**
     * build price with currency
     *
     * @return the price with price after dot or without if it integer
     */
    public static String buildIntegerFloatPriceWithCurrency(double price, String currency) {
        NumberFormat formatter = new DecimalFormat("#0.##");
        return buildFormattedPriceWithCurrency(price, currency, formatter);
    }

    public static String buildPriceWithCurrency(double price, String currency) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return buildFormattedPriceWithCurrency(price, currency, formatter);
    }

    public static String buildIntegerPriceWithCurrency(double price, String currency,
                                                       boolean saparateLines) {
        NumberFormat formatter = new DecimalFormat("#0");
        String result = String.valueOf(formatter.format(price < 0 ? Math.abs(price) : price));
        if (LocalizationManager.isCurrencyInLeft(currency)) {
            if (saparateLines) {
                result = currency + "\n" + result;
            } else {
                result = currency + result;
            }

        } else {
            if (saparateLines) {
                result = result + "\n" + currency;
            } else {
                result = result + (LocalizationManager.isCurrencyWithSpace(currency) ? " " : "")
                        + currency;
            }

        }

        return result;
    }

    /**
     * This function return url with the query encode in utf 8
     *
     * @param urlStr the url to encode
     * @return the encoded url
     */
    public static String encodeQueryUrl(String urlStr) {

        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
                    url.getPath(), url.getQuery(), url.getRef());
            url = uri.toURL();

            return url.toString();
        } catch (Exception e) {
        }

        return urlStr;

    }


    public static Spannable applyTextSizeStyle(Context context, Spannable sentence, String part,
                                               int sizeId) {

        int startIndex = sentence.toString().toLowerCase().indexOf(part.toLowerCase());
        SpannableString spannableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spannableString.setSpan(
                    new AbsoluteSizeSpan((int) context.getResources().getDimension(sizeId)),
                    startIndex, startIndex + part.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static Spannable applyTextColorStyle(Context context, String sentence, String part,
                                                int color) {

        int startIndex = sentence.toLowerCase().indexOf(part.toLowerCase());
        SpannableString spunnableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spunnableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)),
                    startIndex, startIndex + part.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spunnableString;
    }

    public static Spannable applyTextColorStyle(Context context, Spannable sentence, String part,
                                                int color) {

        int startIndex = sentence.toString().toLowerCase().indexOf(part.toLowerCase());
        SpannableString spunnableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spunnableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)),
                    startIndex, startIndex + part.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spunnableString;
    }

    public static Spannable applyTextWeight(Spannable sentence, String part, String family,
                                            int weight) {

        int startIndex = sentence.toString().toLowerCase().indexOf(part.toLowerCase());
        SpannableString spunnableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spunnableString
                    .setSpan(new TypefaceSpan(family), startIndex, startIndex + part.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spunnableString.setSpan(new StyleSpan(weight), startIndex, startIndex + part.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spunnableString;
    }

    public static Spannable applyTextWeight(String sentence, String part, String family,
                                            int weight) {

        int startIndex = sentence.toLowerCase().indexOf(part.toLowerCase());
        SpannableString spannableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spannableString
                    .setSpan(new TypefaceSpan(family), startIndex, startIndex + part.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(weight), startIndex, startIndex + part.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static Spannable applyCurrencyTextSizeStyle(String sentence, String part) {

        int startIndex = sentence.toLowerCase().indexOf(part.toLowerCase());
        SpannableString spannableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spannableString
                    .setSpan(new RelativeSizeSpan(0.5f), startIndex, startIndex + part.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static Spannable applyCurrencyTextSizeStyle(String sentence, String part,
                                                       float partSize) {

        int startIndex = sentence.toLowerCase().indexOf(part.toLowerCase());
        SpannableString spannableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spannableString
                    .setSpan(new RelativeSizeSpan(partSize), startIndex, startIndex + part.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static Spannable applyTextColorSizeAndStyle(Context context, String sentence, String part,
                                                       int color, int style, float partSize) {

        int startIndex = sentence.toLowerCase().indexOf(part.toLowerCase());
        SpannableString spannableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spannableString
                    .setSpan(new RelativeSizeSpan(partSize), startIndex, startIndex + part.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)),
                    startIndex, startIndex + part.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableString.setSpan(new StyleSpan(style), startIndex, startIndex + part.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    public static Spannable applyTextColorSizeAndWeightStyle(Context context, String sentence,
                                                             String part, int color, int sizeId, String famaly, int weight) {

        int startIndex = sentence.toLowerCase().indexOf(part.toLowerCase());
        SpannableString spannableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)),
                    startIndex, startIndex + part.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString
                    .setSpan(new TypefaceSpan(famaly), startIndex, startIndex + part.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(weight), startIndex, startIndex + part.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(
                    new AbsoluteSizeSpan((int) context.getResources().getDimension(sizeId)),
                    startIndex, startIndex + part.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static Spannable applyTextSizeAndWeightStyle(Context context, String sentence,
                                                        String part, int sizeId, String famaly, int weight) {

        int startIndex = sentence.toLowerCase().indexOf(part.toLowerCase());
        SpannableString spannableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spannableString
                    .setSpan(new TypefaceSpan(famaly), startIndex, startIndex + part.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(weight), startIndex, startIndex + part.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(
                    new AbsoluteSizeSpan((int) context.getResources().getDimension(sizeId)),
                    startIndex, startIndex + part.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    public static Spannable applyTextColorAndWeightStyle(Context context, String sentence,
                                                         String part, int color, String famaly, int weight) {

        int startIndex = sentence.toLowerCase().indexOf(part.toLowerCase());
        SpannableString spannableString = new SpannableString(sentence);
        if (startIndex >= 0) {
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)),
                    startIndex, startIndex + part.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString
                    .setSpan(new TypefaceSpan(famaly), startIndex, startIndex + part.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(weight), startIndex, startIndex + part.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    public static boolean isValidDriverID(String driverID) {
        if (TextUtils.isEmpty(driverID)) {
            return false;
        }

        return TextUtils.isDigitsOnly(driverID);
    }

    public static String locationAccuracyRangeChooser(float accuracy) {
        if (accuracy < 100) {
            return "<100";
        } else if (accuracy >= 100 && accuracy < 200) {
            return "100-200";
        } else if (accuracy >= 200 && accuracy < 300) {
            return "200-300";
        } else if (accuracy >= 300 && accuracy < 500) {
            return "300-500";
        } else if (accuracy >= 500 && accuracy < 750) {
            return "500-750";
        } else if (accuracy >= 750 && accuracy < 1000) {
            return "750-1000";
        } else if (accuracy >= 1000 && accuracy <= 1500) {
            return "1000-1500";
        } else {
            return ">1500";
        }


    }


    public static boolean isValidForRegex(String text) {
        return !(TextUtils.isEmpty(text) || text.length() > 20);
    }

    public static boolean isHebrewString(String text) {
        return text.matches(".*[א-ת]+.*");
    }

    public static boolean isRussianString(String text) {
        return text.matches(".*\\p{InCyrillic}.*");
    }

    public static boolean isDigitsOnlyString(String text) {
        return text.matches("[0-9]+");
    }


//    public static String getStringFromNode(Node root) throws IOException {
//
//        StringBuilder result = new StringBuilder();
//
//        if (root.getNodeType() == 3)
//            result.append(root.getNodeValue());
//        else {
//            if (root.getNodeType() != 9) {
//                StringBuffer attrs = new StringBuffer();
//                for (int k = 0; k < root.getAttributes().getLength(); ++k) {
//                    attrs.append(" ").append(
//                            root.getAttributes().item(k).getNodeName()).append("=\"").append(root.getAttributes().item(k).getNodeValue()).append("\" ");
//                }
//                result.append("<").append(root.getNodeName()).append(" ")
//                        .append(attrs).append(">");
//            } else {
//                result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//            }
//
//            NodeList nodes = root.getChildNodes();
//            for (int i = 0, j = nodes.getLength(); i < j; i++) {
//                Node node = nodes.item(i);
//                result.append(getStringFromNode(node));
//            }
//
//            if (root.getNodeType() != 9) {
//                result.append("</").append(root.getNodeName()).append(">");
//            }
//        }
//        return result.toString();
//    }

    //	public static Spannable applyTextWeight(Spannable sentence, String part, Typeface typeface) {
    //
    //		int startIndex = sentence.toString().indexOf(part);
    //		SpannableString spunnableString = new SpannableString(sentence);
    //		spunnableString.setSpan(new CustomTypefaceSpan(null,typeface), startIndex, startIndex+part.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    //		return spunnableString;
    //	}

    //	public static List<String> generateMenuList(Context context, String rideStatus, boolean isUsMode){
    //		String [] menuTitles = null;
    //
    //		if(rideStatus.equalsIgnoreCase(Enums.OrderStatus.Confirmed)
    //				|| rideStatus.equalsIgnoreCase(Enums.OrderStatus.Waiting)){
    //
    //			menuTitles = context.getResources().getStringArray(R.array.cancelable_drawer_menu);
    //		}else if(rideStatus.equalsIgnoreCase(Enums.OrderStatus.Driving)){
    //			menuTitles = context.getResources().getStringArray(R.array.non_cancelable_drawer_menu);
    //		}else{
    //			menuTitles = context.getResources().getStringArray(isUsMode ? R.array.default_drawer_menu_us : R.array.default_drawer_menu);
    //		}
    //		return Arrays.asList(menuTitles);
    //	}

}
