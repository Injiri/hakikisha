package org.aplusscreators.hakikisha.utils;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import org.aplusscreators.hakikisha.views.buyer.RegisterPurchaseForm;

import java.util.UUID;

public class HakikishaUtils {

    private static final String TAG = "HakikishaUtils";

    public static void sendEmail(Context context, String from, String to, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            context.startActivity(Intent.createChooser(intent, "Notify seller..."));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_LONG).show();
        }
    }

    public static void sendSms(Context context, String sms, String phoneNumber, PendingIntent sentPendingIntent) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber,null,sms,sentPendingIntent,null);

    }

    public static String generateMpesaStyleAlphanumeric(){
        String uuid = UUID.randomUUID().toString();
        String stripped = uuid.replaceAll("-","");
        String alphaLength = stripped.substring(0,11);
        String upperCaseAlphanum = alphaLength.toUpperCase();

        return upperCaseAlphanum;
    }
}
