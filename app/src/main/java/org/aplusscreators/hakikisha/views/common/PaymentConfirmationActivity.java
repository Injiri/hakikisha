package org.aplusscreators.hakikisha.views.common;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.aplusscreators.hakikisha.R;

public class PaymentConfirmationActivity extends AppCompatActivity {

    private View closeView;
    private TextView transactionStatusTextView;
    private TextView transactionIdTextView;
    private TextView amountTextView;
    private TextView vendorNameTextView;
    private TextView vendorEmailTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_confirmation_layout);
    }
}
