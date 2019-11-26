package org.aplusscreators.hakikisha.utils;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.ShortNumberInfo;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ExternalAddressFormatter {

    private static final String TAG = ExternalAddressFormatter.class.getSimpleName();

    private static final Set<String> SHORT_COUNTRIES = new HashSet<String>() {{
        add("NU");
        add("TK");
        add("NC");
        add("AC");
    }};

    private String localNumberString;
    private String localCountryCode;

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private final Pattern ALPHA_PATTERN = Pattern.compile("[a-zA-Z]");

    public ExternalAddressFormatter(@NonNull String localNumberString) {
        try {
            Phonenumber.PhoneNumber localNumber = phoneNumberUtil.parse(localNumberString, null);

            this.localNumberString = localNumberString;
            this.localCountryCode = phoneNumberUtil.getRegionCodeForNumber(localNumber);
        } catch (NumberParseException e) {
            Log.e(TAG, "ExternalAddressFormatter: " + e);
        }
    }

    public ExternalAddressFormatter(@NonNull String localCountryCode, boolean countryCode) {
        this.localNumberString = "";
        this.localCountryCode = localCountryCode;
    }

    public String format(@Nullable String number) {
        if (number == null) return "Unknown";
//        if (GroupUtil.isEncodedGroup(number))     return number;
        if (ALPHA_PATTERN.matcher(number).find()) return number.trim();

        String bareNumber = number.replaceAll("[^0-9+]", "");

        if (bareNumber.length() == 0) {
            if (number.trim().length() == 0) return "Unknown";
            else return number.trim();
        }

        // libphonenumber doesn't seem to be correct for Germany and Finland
        if (bareNumber.length() <= 6 && ("DE".equals(localCountryCode) || "FI".equals(localCountryCode) || "SK".equals(localCountryCode))) {
            return bareNumber;
        }

        // libphonenumber seems incorrect for Russia and a few other countries with 4 digit short codes.
        if (bareNumber.length() <= 4 && !SHORT_COUNTRIES.contains(localCountryCode)) {
            return bareNumber;
        }

        try {
            Phonenumber.PhoneNumber parsedNumber = phoneNumberUtil.parse(bareNumber, localCountryCode);

            if (ShortNumberInfo.getInstance().isPossibleShortNumberForRegion(parsedNumber, localCountryCode)) {
                return bareNumber;
            }

            return phoneNumberUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            Log.w(TAG, e);
            if (bareNumber.charAt(0) == '+')
                return bareNumber;

            String localNumberImprecise = localNumberString;

            if (localNumberImprecise.charAt(0) == '+')
                localNumberImprecise = localNumberImprecise.substring(1);

            if (localNumberImprecise.length() == bareNumber.length() || bareNumber.length() > localNumberImprecise.length())
                return "+" + number;

            int difference = localNumberImprecise.length() - bareNumber.length();

            return "+" + localNumberImprecise.substring(0, difference) + bareNumber;
        }
    }
}