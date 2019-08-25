package org.aplusscreators.hakikisha.settings;

import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = "SettingsFragment";

    Preference planDayTimePreference;
    Preference reviewDayTimePreference;
    Preference enableTaskReminder;
    SwitchPreference darkThemePreference;
    SwitchPreference lightThemePreference;
    Preference helpPreference;
    Preference feedbackPreference;
    Preference faqsPreference;
    TimePickerDialog planDayTimePicker;
    TimePickerDialog reviewDayTimePicker;
    PlanDayTimePickerListener planDayTimePickerListener;
    ReviewDayTimePickerListener reviewDayTimePickerListener;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        //setPreferencesFromResource(R.xml.preferences,rootKey);

        planDayTimePreference = findPreference("user_wake_up_time");
        reviewDayTimePreference = findPreference("user_day_review_time");
        enableTaskReminder = findPreference("task_reminders_prefs");
        darkThemePreference = findPreference("dark_theme_pref");
        lightThemePreference = findPreference("light_theme_pref");
        helpPreference = findPreference("help_pref");
        feedbackPreference = findPreference("feedback_pref");
        faqsPreference = findPreference("faq_prefs");

        initPreferences();

        initTimePicker();

        planDayTimePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                planDayTimePicker.show();
                return false;
            }
        });

        reviewDayTimePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                reviewDayTimePicker.show();
                return false;
            }
        });

        helpPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startPlayStore(getActivity());
                return false;
            }
        });

        faqsPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startPlayStore(getActivity());
                return false;
            }
        });

        feedbackPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startPlayStore(getActivity());
                return false;
            }
        });


    }

    public void startPlayStore(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException e) {
            Log.w("MainActivity", e);
        }
    }

    private void initTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        planDayTimePickerListener = new PlanDayTimePickerListener();
        reviewDayTimePickerListener = new ReviewDayTimePickerListener();
        planDayTimePicker = new TimePickerDialog(getActivity(),planDayTimePickerListener,hour,minute,true);
        reviewDayTimePicker = new TimePickerDialog(getActivity(),reviewDayTimePickerListener,hour,minute,true);
    }

    private void initPreferences(){
        long wakeUpTimeLong = HakikishaPreference.getUserWakeUpTime(getActivity());
        long reviewDayTimeLong = HakikishaPreference.getUserDayReviewTime(getActivity());

        Date wakeUpTime = new Date(wakeUpTimeLong);
        Date reviewDayTime = new Date(reviewDayTimeLong);
        Calendar wakeUpTimeCalendar = Calendar.getInstance();
        Calendar reviewDayTimeCalendar = Calendar.getInstance();
        wakeUpTimeCalendar.setTime(wakeUpTime);
        reviewDayTimeCalendar.setTime(reviewDayTime);

        String wakeUpTimeAmPm = DateTimeUtils.getTimeAM_PM(wakeUpTimeCalendar,getActivity());
        String reviewDayTimeAmPm = DateTimeUtils.getTimeAM_PM(reviewDayTimeCalendar,getActivity());

        reviewDayTimePreference.setSummary(reviewDayTimeAmPm);
        planDayTimePreference.setSummary(wakeUpTimeAmPm);

        enableTaskReminder.setDefaultValue(true);

    }


    private class PlanDayTimePickerListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            String time = DateTimeUtils.getTimeAM_PM(calendar,getActivity());
            HakikishaPreference.setUserWakeUpTime(getActivity(),calendar.getTime().getTime());
            planDayTimePreference.setSummary(time);

        }
    }

    private class ReviewDayTimePickerListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            String time = DateTimeUtils.getTimeAM_PM(calendar,getActivity());
            HakikishaPreference.setUserDayReviewTime(getActivity(),calendar.getTime().getTime());
            reviewDayTimePreference.setSummary(time);

        }
    }
}
