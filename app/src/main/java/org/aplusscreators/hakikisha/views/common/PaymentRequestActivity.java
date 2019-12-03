package org.aplusscreators.hakikisha.views.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aplusscreators.hakikisha.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PaymentRequestActivity extends AppCompatActivity {

    private View closeActionView;
    private FloatingActionButton sendRequestPaymentFab;
    private View customerDetailsView;
    private TextView customerNameTextView;
    private TextView customerEmailAddressTextView;
    private CircleImageView customerImageView;
    private View orderNumberEntryView;
    private EditText orderNumberEditText;
    private View amountEntryFieldView;
    private EditText amountEntryEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_request);

        initializeResources();
    }

    private void initializeResources() {
        this.closeActionView = findViewById(R.id.activity_request_payment_close_action_view);
        this.sendRequestPaymentFab = findViewById(R.id.activity_payments_pay_fab);
        this.customerDetailsView = findViewById(R.id.request_payment_customer_entry_view);
        this.customerNameTextView = findViewById(R.id.request_payment_customer_name_text_view);
        this.customerEmailAddressTextView = findViewById(R.id.request_payment_customer_email_text_view);
        this.customerImageView = findViewById(R.id.request_payment_customer_image_view);
        this.orderNumberEntryView = findViewById(R.id.request_payments_order_number_field_view);
        this.orderNumberEditText = findViewById(R.id.make_payments_order_number_edit_text);
        this.orderNumberEntryView = findViewById(R.id.request_payments_order_number_field_view);
        this.amountEntryFieldView = findViewById(R.id.request_payment_amount_entry_view);
        this.amountEntryEditText = findViewById(R.id.request_payment_amount_edit_text);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PaymentRequestActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
