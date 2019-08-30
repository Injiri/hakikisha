package org.aplusscreators.hakikisha.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class HakikishaUtils {

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

    public static void sendSms(Context context, String sms, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra("sms_body", sms);
        intent.setData(Uri.parse(phoneNumber));

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "Unable to send SMS, SMS Client Error.", Toast.LENGTH_LONG).show();
        }
    }
}
