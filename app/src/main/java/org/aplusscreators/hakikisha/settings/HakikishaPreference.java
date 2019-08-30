package org.aplusscreators.hakikisha.settings;

import android.content.Context;
import android.util.Log;

public class HakikishaPreference {

    private final static String BUYER_NAMES_PREFS = "buyer_names_prefs";
    private final static String ACCOUNT_UUID_PREFS = "account_uuid_prefs";
    private final static String CONTACTS_RETRIEVE_PREF = "contact_retrieve_pref";
    private final static String ACCOUNT_TYPE_PREF = "account_type_pref";
    private final static String DEFAULT_PREFS_FILE_NAME = "org.aplus.planner.prefs";
    private final static String MONTHLY_REWARD_VIDEO_POINTS = "reminder_reward_video_pref";
    private final static String CREATED_DEFAULT_CHECKLISTS = "default_checklists_created_pref";
    private final static String USER_ONBOARDING_COMPLETE = "user_on_boarding_complete";
    private final static String USER_WAKE_UP_TIME = "user_wake_up_time";
    private final static String USER_DAY_REVIEW_TIME = "user_day_review_time";
    private final static String REGISTERED_USER_UUID = "REGISTERED_USER_UUID";
    private final static String DAILY_SCHEDULE_NOTIFICATION_SCHEDULED = "daily_notification_scheduled_pref";
    private final static String MONTHLY_SUBSCRIPTION_ACTIVATED = "monthly_subscription_activated";
    private final static String USER_SWIPE_CARD_ONBOARDING_COMPLETE = "user_swipe_onboarding_complete";
    private final static String USER_PROFILE_URI = "usere_profile_uri_prefs";

    public static void setBuyerNamesPrefs(Context context,String names){
        setStringPref(context,BUYER_NAMES_PREFS,names);
    }

    public static String getBuyerNamesPrefs(Context context){
        return getStringPrefs(context,BUYER_NAMES_PREFS,null);
    }

    public static void setAccountUuidPrefs(Context context,String uuid){
        setStringPref(context,ACCOUNT_UUID_PREFS,uuid);
    }

    public static String getAccountUuidPrefs(Context context){
        return getStringPrefs(context,ACCOUNT_UUID_PREFS,null);
    }

    public static void setAccountTypePref(Context context,String accountType){
        setStringPref(context,ACCOUNT_TYPE_PREF,accountType);
    }

    public static String getAccountTypePref(Context context){
        return getStringPrefs(context,ACCOUNT_TYPE_PREF,null);
    }

    public static void setUserProfileUriPref(Context context, String serializedUri) {
        Log.e(HakikishaPreference.class.getSimpleName(), "setUserProfileUriPref: " + serializedUri);
        setStringPref(context, USER_PROFILE_URI, serializedUri);
    }

    public static String getUserProfileUriPref(Context context) {
        return getStringPrefs(context, USER_PROFILE_URI, null);
    }

    public static void setUserCardOnboardingComplete(Context context, boolean isComplete) {
        setBooleanPrefs(context, USER_SWIPE_CARD_ONBOARDING_COMPLETE, isComplete);
    }

    public static boolean getUserCardOnBoardingComplete(Context context) {
        return getBooleanPrefs(context, USER_SWIPE_CARD_ONBOARDING_COMPLETE, false);
    }

    public static void setDailyScheduleNotificationScheduledPref(Context context, boolean isScheduled) {
        setBooleanPrefs(context, DAILY_SCHEDULE_NOTIFICATION_SCHEDULED, isScheduled);
    }

    public static boolean getDailyScheduleNotificationScheduledPref(Context context) {
        return getBooleanPrefs(context, DAILY_SCHEDULE_NOTIFICATION_SCHEDULED, false);
    }

    public static void setMonthlySubscriptionActivated(Context context, boolean activate) {
        setBooleanPrefs(context, MONTHLY_SUBSCRIPTION_ACTIVATED, activate);
    }

    public static boolean getIsMonthlySubscriptionActivated(Context context) {
        return getBooleanPrefs(context, MONTHLY_SUBSCRIPTION_ACTIVATED, false);
    }

    public static void setRegisteredUserUuid(Context context, String uuid) {
        setStringPref(context, REGISTERED_USER_UUID, uuid);
    }

    public static String getRegistredUserUuid(Context context) {
        return getStringPrefs(context, REGISTERED_USER_UUID, null);
    }

    public static void setUserWakeUpTime(Context context, long wakeUpTime) {
        setLongPreference(context, USER_WAKE_UP_TIME, wakeUpTime);
    }

    public static Long getUserWakeUpTime(Context context) {
        return getLongPreference(context, USER_WAKE_UP_TIME, 1L);
    }

    public static void setUserDayReviewTime(Context context, long reviewTime) {
        setLongPreference(context, USER_DAY_REVIEW_TIME, reviewTime);
    }

    public static Long getUserDayReviewTime(Context context) {
        return getLongPreference(context, USER_DAY_REVIEW_TIME, 1L);
    }

    public static void setUserOnboardingComplete(Context context, boolean complete) {
        setBooleanPrefs(context, USER_ONBOARDING_COMPLETE, complete);
    }

    public static boolean getUserOnBoardingComplete(Context context) {
        return getBooleanPrefs(context, USER_ONBOARDING_COMPLETE, false);
    }

    public static void setCreatedDefaultChecklists(Context context, boolean created) {
        setBooleanPrefs(context, CREATED_DEFAULT_CHECKLISTS, created);
    }

    public static boolean getCreatedDefaultChecklistsPref(Context context) {
        return getBooleanPrefs(context, CREATED_DEFAULT_CHECKLISTS, false);
    }

    public static void setMonthlyRewardVideoPoints(Context context, int earnedPoints) {
        int currentValue = getMonthlyRewardVideoPoints(context);
        int newValue = currentValue + earnedPoints;

        setIntPrefs(context, MONTHLY_REWARD_VIDEO_POINTS, newValue);
    }

    public static int getMonthlyRewardVideoPoints(Context context) {
        return getIntPref(context, MONTHLY_REWARD_VIDEO_POINTS, 0);
    }

    public static void setContactsRetrievePref(Context context, boolean value) {
        setBooleanPrefs(context, CONTACTS_RETRIEVE_PREF, value);
    }

    public static boolean getContactsRetrivePref(Context context) {
        return getBooleanPrefs(context, CONTACTS_RETRIEVE_PREF, true);
    }

    private static boolean getBooleanPrefs(Context context, String prefName, boolean defValue) {
        return context.getSharedPreferences(DEFAULT_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getBoolean(prefName, defValue);
    }

    private static void setBooleanPrefs(Context context, String prefName, boolean value) {
        context.getSharedPreferences(DEFAULT_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(prefName, value)
                .apply();
    }

    private static void setIntPrefs(Context context, String key, int value) {
        context.getSharedPreferences(DEFAULT_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(key, value)
                .apply();
    }

    private static int getIntPref(Context context, String prefName, int defaultVal) {
        return context.getSharedPreferences(DEFAULT_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getInt(prefName, defaultVal);
    }

    private static void setStringPref(Context context, String prefName, String value) {
        context.getSharedPreferences(DEFAULT_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(prefName, value)
                .apply();
    }

    private static String getStringPrefs(Context context, String prefName, String defaultVal) {
        return context.getSharedPreferences(DEFAULT_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(prefName, defaultVal);
    }


    private static void setLongPreference(Context context, String prefName, long value) {
        context.getSharedPreferences(DEFAULT_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putLong(prefName, value)
                .apply();
    }

    private static Long getLongPreference(Context context, String prefName, long defValue) {
        try {
            return context.getSharedPreferences(DEFAULT_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                    .getLong(prefName, defValue);
        } catch (ClassCastException ex) {
            return 1L;
        }

    }
}
