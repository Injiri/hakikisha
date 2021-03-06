package org.aplusscreators.hakikisha.views.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.Constants;
import org.aplusscreators.hakikisha.utils.ExternalAddressFormatter;
import org.aplusscreators.hakikisha.views.seller.SellerProfileFormActivity;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    View submitView;
    EditText phoneNumberEditText;
    ProgressBar progressBar;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneAuthCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        submitView = findViewById(R.id.submit_phone_view);
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        progressBar = findViewById(R.id.sign_in_progress_bar);

        phoneAuthCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressBar.setVisibility(View.GONE);
                HakikishaPreference.setUserAuthenticatedPref(LoginActivity.this,true);
                Intent intent = new Intent(LoginActivity.this, ProfileFormActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(findViewById(R.id.activity_sign_in), "Verification failed", Snackbar.LENGTH_LONG).show();
                Log.e(TAG, "onVerificationFailed: " + e);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(LoginActivity.this, ConfirmationCodesActivity.class);
                intent.putExtra("phone_number", phoneNumberEditText.getText().toString());
                intent.putExtra("verification_id", verificationId);
                startActivity(intent);

                Snackbar.make(findViewById(R.id.activity_sign_in), "Verification code sent", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(getApplicationContext(), "Code Auto Retrieval Timeout", Toast.LENGTH_LONG).show();
            }

        };

        submitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumberEditText.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter a valid phone number", Toast.LENGTH_LONG).show();
                    return;
                }

                if (phoneNumberEditText.getText().toString().length() < 9) {
                    Toast.makeText(LoginActivity.this, "Enter a valid phone number", Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                String phoneNumber = phoneNumberEditText.getText().toString();
                phoneNumber = formatE1PhoneNumber(phoneNumber);
                requestVerificationCode(phoneNumber, phoneAuthCallback);

                HakikishaPreference.setAccountPhoneNumberPref(LoginActivity.this, phoneNumber);
            }
        });


    }

    private String formatE1PhoneNumber(String phoneNumber) {
        ExternalAddressFormatter formatter = new ExternalAddressFormatter(phoneNumber);
        return formatter.format(phoneNumber);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(LoginActivity.this);
    }

    private void requestVerificationCode(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks) {
        FirebaseApp.initializeApp(this);
        PhoneAuthProvider authProvider = PhoneAuthProvider.getInstance();

        authProvider.verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallBacks
        );

    }
}
