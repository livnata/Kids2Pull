package com.kids2pull.android.utils;

/**
 * Created by IntelliJ IDEA.
 *
 * @author alximik Date: 11.01.11 Time: 1:14
 */
public class InteropConstants {
    // Request codes
    public static final int CHOOSE_COUNTRY = 1;
    public static final int CHOOSE_DESTINATION_LOCATION = 2;
    public static final int REQUEST_GPS = 21;
    public static final int RATE_DRIVER = 6;
    public static final int FUTURE_RIDE = 7;
    public static final int PAST_RIDE = 8;
    public static final int EDIT_EMAIL = 9;
    public static final int CHOOSE_FIX_PRICE = 13;
    public static final int PAYMENT_ACTIVITY = 15;
    public static final int EULA_ACTIVITY_ID = 16;
    public static final int CREDITCARDS_ACTIVITY = 17;
    public static final int REDIME_COUPONE = 18;
    public static final int SEARCH_FOR_PICKUP_LOCATION = 19;
    public static final int CHOOSE_PICKUP_LOCATION = 20;
    public static final int CLOSE_AFTER_VALIDIATION = 22;
    public static final int EMAIL_REGISTRATION_ACTIVITY = 27;
    public static final int PROMOTIONAL_ACTIVITY = 28;
    public static final int FRIEND_ACTIVITY = 29;
    public static final int SHARE_POST = 33;
    public static final int WELCOME_TOUR = 34;
    public static final int CHOOSE_PICKUP_LOCATION_FOR_FIXEDPRICE = 35;
    public static final int OPEN_BUSINESS_PROMO = 36;
    public static final int PAY_BY_GETTAXI = 37;
    public static final int TOU_DIVISION_ACTIVITY_ID = 38;
    public static final int WIFI_ACTIVITY_ID = 39;
    public static final int CHOOSE_LOCATION = 40;
    public static final int SPLIT_FARE_ACTIVITY = 41;
    public static final int CHOOSE_FLIGHT_NUMBER = 42;
    public static final int COUPON_OVERLAY_ACTIVITY = 43;
    public static final int OUTSTANDING_BALANCE_ACTIVITY_ID = 1116;

    // Params to pass between activities
    public static final String PARAM_LIST_DATA = "data";
    public static final String PARAM_ORDER = "PARAM_ORDER";
    public static final String PARAM_ORDER_ID = "PARAM_ORDER_ID";
    public static final String PARAM_ORDER_RATING = "PARAM_ORDER_RATING";
    public static final String PARAM_GEOCODE = "PARAM_GEOCODE";
    public static final String PARAM_URL = "PARAM_URL";
    public static final String PARAM_TERMS_BUTTON = "PARAM_TERMS_BUTTON";
    public static final String PARAM_TERMS_HEADER = "PARAM_TERMS_HEADER";
    public static final String PARAM_TERMS_TITLE = "PARAM_TERMS_TITLE";
    public static final String PARAM_REFERENCE = "PARAM_REFERENCE";
    public static final String PARAM_NOTE_TO_DRIVER = "PARAM_REFERENCE";
    public static final String PARAM_LOCATION_TYPE = "LOCATION_TYPE";
    public static final String PARAM_PAYMENT = "PARAM_PAYMENT";
    public static final String PARAM_COUNTRY = "COUNTRY";
    public static final String PARAM_COUPON = "PARAM_COUPON";
    public static final String PARAM_TYPE = "PARAM_TYPE";
    public static final String PARAM_MIXPANEL_SOURCE = "PARAM_SOURCE";
    public static final String PARAM_CHANGE_COUNTRY = "PARAM_CHANGE_COUNTRY";
    public static final String PARAM_ORIGIN_SOURCE = "PARAM_ORIGIN_SOURCE";
    public static final String PARAM_REGESTRATION_SCREEN = "PARAM_REGESTRATION_SCREEN";
    public static final String PARAM_PARENT_SCREEN = "PARAM_PARENT_SCREEN";
    public static final String PARAM_AUTOPAY_EXIST_USER = "PARAM_AUTOPAY_EXIST_USER";
    public static final String PARAM_PHONE = "PARAM_PHONE";
    public static final String PARAM_USER_ID = "PARAM_USER_ID";
    public static final String PARAM_RESEND_CODE_ENABLED = "PARAM_RESEND_CODE_ENABLED";
    public static final String PARAM_EVENT_TRIGGER = "PARAM_EVENT_TRIGGER";
    public static final String PARAM_ACTIVITY_DONE = "PARAM_ACTIVITY_DONE";
    public static final String ACTION_EMAIL = "ACTION_EMAIL";
    public static final String PARAM_EXTRA_FIELD = "PARAM_EXTRA_FIELD";
    public static final String ACTION_CREATE_RIDE = "ACTION_CREATE_RIDE";
    public static final String PARAM_COLLECT_REWARD_PUSH = "PARAM_COLLECT_REWARD_PUSH";
    public static final String PARAM_NOTIFICATION_ACTION = "PARAM_NOTIFICATION_ACTION";
    public static final String PARAM_ACTION = "PARAM_ACTION";
    public static final String PARAM_CODE_LENGTH = "PARAM_CODE_LENGTH";
    public static final String PARAM_FAVORITE_PRIORITY = "PARAM_FAVORITE_PRIORITY";
    public static final String PARAM_TIMESTAMP = "PARAM_TIMESTAMP";
    public static final String PARAM_RESET_PAYMENT_TYPE = "PARAM_RESET_PAYMENT_TYPE";
    public static final String PARAM_PUSH = "PARAM_PUSH";
    public static final String PARAM_RETURN_TO_CREDITCARD_LIST_ACTIVITY = "return_to_creditcard_list";
    public static final String PARAM_DONT_SEND = "PARAM_DONT_SEND";
    public static final String PARAM_RETURN_TO_PAY_BY_GETTAXI_ACTIVITY = "return_to_pay_by_gettaxi";
    public static final String PARAM_IS_CORPORATE = "PARAM_IS_CORPORATE";
    public static final String PARAM_STATE = "PARAM_STATE";
    public static final String PARAM_CHANGE_PHONE_NUMBER = "PARAM_CHANGE_PHONE_NUMBER";
    public static final String PARAM_SHOW_CITIES_ANIMATION = "SHOW_CITIES_ANIMATION";
    public static final String PARAM_ADDRESS_CHANGE_LOOP = "PARAM_ADDRESS_CHANGE_LOOP";
    public static final String PARAM_MODE = "PARAM_MODE";
    public static final String PARAM_FIELD_VALUE = "PARAM_FIELD_VALUE";
    public static final String PARAM_TRY_AGAIN_WITH_DIFFERENT_CLASS = "PARAM_TRY_AGAIN_WITH_DIFFERENT_CLASS";
    public static final String PARAM_TITLE = "PARAM_TITLE";
    public static final String PARAM_FULL_ADDRESS = "PARAM_FULL_ADDRESS";
    public static final String PARAM_SHORTCODE = "PARAM_SHORTCODE";
    public static final String PARAM_FORCE_SHOW_ADDITIONAL_ADDRESS = "PARAM_FORCE_SHOW_ADDITIONAL_ADDRESS";
    public static final String PARAM_LATLNG_CAM_POSTION ="PARAM_LATLNG_CAM_POSTION";
    public static final String PARAM_IS_ROUTING_WHEN_DONE = "PARAM_IS_ROUTING_WHEN_DONE";
    public static final String PARAM_IS_SOURCE_IS_SEARCH = "PARAM_IS_SOURCE_IS_SEARCH";
    public static final String PARAM_CREDIT_CARD_EXPIRY = "PARAM_CREDIT_CARD_EXPIRY";
    public static final String PARAM_PHONE_ONLY_MODE = "PARAM_PHONE_ONLY_MODE";
    public static final String PARAM_SINGLE_CHOICE_MODE = "PARAM_SINGLE_CHOICE_MODE";
    public static final String PARAM_PARTICIPANTS = "PARAM_PARTICIPANTS";
    public static final String PARAM_SPLIT_FARE_INVITATION = "PARAM_SPLIT_FARE_INVITATION";
    public static final String PARAM_MAX_PARTICIPANTS = "PARAM_MAX_PARTICIPANTS";
    public static final String PARAM_SPLIT_FARE_OWNER = "PARAM_SPLIT_FARE_OWNER";
    public static final String PARAM_FAVORITE_FLOW = "PARAM_FAVORITE_FLOW";
    public static final String PARAM_FAVORITE_ID = "PARAM_FAVORITE_ID";
    public static final String PARAM_PREVENT_EDIT_ADDRESS = "PARAM_PREVENT_EDIT_ADDRESS";
    public static final String PARAM_GOING_OUT_PANEL = "PARAM_GOING_OUT_PANEL";
    public static final String PARAM_FIRST_TIME = "PARAM_FIRST_TIME";
    public static final String PARAM_MAX_CONTACTS = "PARAM_MAX_CONTACTS";
    public static final String PARAM_RESTRICT_EXISTING_CONTACT = "PARAM_RESTRICT_EXISTING_CONTACT";
    public static final String PARAM_DISABLE_FAST_START = "PARAM_DISABLE_FAST_START";
    public static final String PARAM_BUSINESS_PROMO_SOURCE = "PARAM_BUSINESS_PROMO_SOURCE";
    public static final String PARAM_NOTIFICATION_MESSAGE = "PARAM_NOTIFICATION_MESSAGE";
    public static final String PARAM_NOTIFICATION_PAYLOAD = "PARAM_NOTIFICATION_PAYLOAD";
    public static final String PARAM_APP_OPEN_SOURCE = "PARAM_APP_OPEN_SOURCE";
    public static final String PARAM_OVERLAY_SOURCE = "PARAM_OVERLAY_SOURCE";
    public static final String PARAM_FLIGHT_NUMBER = "PARAM_FLIGHT_NUMBER";
    public static final String PARAM_TIME = "PARAM_TIME";
    public static final String PARAM_OPEN_ORDER_CONFIRMATION = "PARAM_OPEN_ORDER_CONFIRMATION";
    public static final String PARAM_COUPON_FOR_OVERLAY = "PARAM_COUPON_FOR_OVERLAY";
    public static final String PARAM_COUPON_RESERVATION = "PARAM_COUPON_RESERVATION";
    public static final String PARAM_CURRENT_LOCATION = "PARAM_CURRENT_LOCATION";
    public static final String PARAM_LATLNG = "PARAM_LATLNG";
    public static final String PARAM_INDEX = "PARAM_INDEX";
    public static final String PARAM_GMM_DATA = "PARAM_GMM_DATA";
    public static final String PARAM_PICKUP_SPECIAL_ACTION = "PARAM_PICKUP_SPECIAL_ACTION";
    public static final String PARAM_EDIT_ADDRESS_DESTINATION = "PARAM_EDIT_ADDRESS_DESTINATION";

    //Tabs
    public static final String PARAM_TAB_RECENT = "PARAM_TAB_RECENT";
    public static final String PARAM_DEFAULT_TAB = "PARAM_TAB_NEARBY";
    public static final int TAB_RECENT = 0;
    public static final int TAB_NEARBY = 1;

    //Order confirmation
    public static final String PARAM_DIVISION = "PARAM_DIVISION";
    public static final String PARAM_DIVISIONS_ID = "PARAM_DIVISIONS_ID";
    public static final String PARAM_DIVISIONS_NAME = "PARAM_DIVISIONS_NAME";
    public static final String PARAM_CREDITCARD_ID = "PARAM_CREDITCARDS_ID";
    public static final String PARAM_COMPANY_FIELD_POSITION = "PARAM_COMPANY_FIELD_POSITION";
    public static final String PREVENT_OB_SCREEN = "PARAM_PREVENT_OB_SCREEN";

    //SOCIAL INTENT
    public static final String ACTION_PROFILE = "ACTION_PROFILE";
    public static final String ACTION_SHARE_POST = "ACTION_SHARE_POST";
    public static final String ACTION_SHARE_PHOTO = "ACTION_SHARE_PHOTO";

    public static final String PARAM_TEXT = "PARAM_TEXT";
    public static final String PARAM_FILE_URI = "PARAM_FILE_URI";
    public static final String PARAM_HAS_ERROR = "PARAM_HAS_ERROR";
    public static final String PARAM_PROMO_EMAIL = "PARAM_PROMO_EMAIL";

    public static final String PARAM_SHARE_TEXT = "PARAM_SHARE_TEXT";
    public static final String PARAM_SHARE_LINK = "PARAM_SHARE_LINK";


    public static final int SHARE_PROVIDER_FACEBOOK = 0;
    public static final int SHARE_PROVIDER_GOOGLE = 1;
    public static final int SHARE_PROVIDER_WHATSAPP = 2;
    public static final int SHARE_PROVIDER_VK = 4;
    public static final int SHARE_PROVIDER_NATIVE = 5;


    public static final int RESULT_ERROR = 22;

    public static final boolean FORCE_SHOW = true;

    //Yozio
    public static final String PARAM_YOZIO_REGISTRATION = "registration_origin";
    public static final String PARAM_YOZIO_COUPON = "coupon";


    //Outstanding Balance
    public static final String PARAM_DEBT = "PARAM_DEBT";

    //WIDGET
    public static final String PARAM_WIDGET_FAVORITE = "PARAM_WIDGET_FAVORITE";
    public static final String PARAM_WIDGET_ORDER = "PARAM_WIDGET_ORDER";

    //CAll Costumer Care
    public static final String PARAM_CURRENT_ACTIVE_RIDE = "CURRENT_ACTIVE_RIDE";


    public static class NotificationActions {

        public static final int ACTION_IAMCOMMING = 1;
    }
}
