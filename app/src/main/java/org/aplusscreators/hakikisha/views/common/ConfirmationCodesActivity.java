package org.aplusscreators.hakikisha.views.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.Sound;
import org.aplusscreators.hakikisha.views.seller.SellerProfileFormActivity;

import static org.aplusscreators.hakikisha.utils.Constants.BUYER_ACCOUNT_TYPE;
import static org.aplusscreators.hakikisha.utils.Constants.SELLER_ACCOUNT_TYPE;

public class ConfirmationCodesActivity extends AppCompatActivity {
    private static final String TAG = "ConfirmationCodesActivi";
    TextView confirmMessageTextView;
    Button submitCodeButton;
    ImageView wrongCodeImageView;
    View smsCodesView;
    EditText codeOne;
    EditText codeTwo;
    EditText codeThree;
    EditText codeFour;
    EditText codeFive;
    EditText codeZero;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sms_codes);

        confirmMessageTextView = findViewById(R.id.verification_code_text_view);
        submitCodeButton = findViewById(R.id.submit_verification_code_button);
        codeOne = findViewById(R.id.code_one);
        codeTwo = findViewById(R.id.code_two);
        codeThree = findViewById(R.id.code_three);
        codeFour = findViewById(R.id.code_four);
        codeFive = findViewById(R.id.code_five);
        codeZero = findViewById(R.id.code_zero);
        smsCodesView = findViewById(R.id.sms_codes_view);
        wrongCodeImageView = findViewById(R.id.wrong_code_image_view);

        wrongCodeImageView.setVisibility(View.GONE);

        Intent data = getIntent();
        String phoneNumber = data.getStringExtra("phone_number");
        String verification_id = data.getStringExtra("verification_id");

        confirmMessageTextView.setText(String.format("Waiting to automatically detect an SMS sent to %s.", phoneNumber));

        submitCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: verification ID " + verification_id);
                String code = extractVerificationCode();

                if (verification_id == null) {
                    throw new AssertionError("Verification Id is null");
                }

                confirmVerificationCode(verification_id, code);
            }
        });

        codeZero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    wrongCodeImageView.setVisibility(View.GONE);
                    codeOne.requestFocus();
                    codeOne.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        codeOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    wrongCodeImageView.setVisibility(View.GONE);
                    codeTwo.requestFocus();
                    codeTwo.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        codeTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    wrongCodeImageView.setVisibility(View.GONE);
                    codeThree.requestFocus();
                    codeThree.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        codeThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    wrongCodeImageView.setVisibility(View.GONE);
                    codeFour.requestFocus();
                    codeFour.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        codeFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    wrongCodeImageView.setVisibility(View.GONE);
                    codeFive.requestFocus();
                    codeFive.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private String extractVerificationCode() {

        return codeZero.getText().toString() +
                codeOne.getText().toString() +
                codeTwo.getText().toString() +
                codeThree.getText().toString() +
                codeFour.getText().toString() +
                codeFive.getText().toString();
    }


    private void confirmVerificationCode(String verification_id, String code) {

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification_id, code);
            signInUser(credential);
        } catch (IllegalArgumentException ex) {
            Sound.vibrateDevice(ConfirmationCodesActivity.this);
            wrongCodeImageView.setVisibility(View.VISIBLE);
        }

    }

    private void signInUser(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task task = firebaseAuth.signInWithCredential(credential);

        if (task == null) return;

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(ConfirmationCodesActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                HakikishaPreference.setUserAuthenticatedPref(ConfirmationCodesActivity.this,true);
                Intent intent = new Intent(ConfirmationCodesActivity.this, ProfileFormActivity.class);
                startActivity(intent);

            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                HakikishaPreference.setUserAuthenticatedPref(ConfirmationCodesActivity.this,false);
                Toast.makeText(ConfirmationCodesActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
