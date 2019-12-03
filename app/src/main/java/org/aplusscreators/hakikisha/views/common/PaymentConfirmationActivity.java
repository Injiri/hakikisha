package org.aplusscreators.hakikisha.views.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.aplusscreators.hakikisha.R;

public class PaymentConfirmationActivity extends AppCompatActivity {

    private View closeView;
    private TextView transactionStatusTextView;
    private TextView transactionIdTextView;
    private TextView amountTextView;
    private TextView vendorNameTextView;
    private TextView vendorEmailTextView;
    private View vendorDetailsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_confirmation_layout);

        initializeResources();
    }

    private void initializeResources() {
        this.closeView = findViewById(R.id.payments_confirmation_close_view);
        this.transactionStatusTextView = findViewById(R.id.payment_confirmation_status_message_text_view);
        this.transactionIdTextView = findViewById(R.id.payment_confirmation_transaction_id_text_view);
        this.amountTextView = findViewById(R.id.payment_confirmation_amount_text_view);
        this.vendorNameTextView = findViewById(R.id.payment_confirmation_vendor_name_text_view);
        this.vendorEmailTextView = findViewById(R.id.payment_confirmation_vendor_email_text_view);
        this.vendorDetailsView = findViewById(R.id.payment_confirmation_vendor_details_view);

        this.closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentConfirmationActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PaymentConfirmationActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Snackbar.make(findViewById(R.id.payment_confirmation_container_view),"Payment made successfully",Snackbar.LENGTH_LONG).show();
    }
}
