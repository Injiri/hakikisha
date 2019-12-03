package org.aplusscreators.hakikisha.views.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aplusscreators.hakikisha.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeliveryRequestActivity extends AppCompatActivity {

    private View closeActionView;
    private FloatingActionButton sendDeliveryRequestFab;
    private View sellerDetailsView;
    private TextView vendorNameTextView;
    private TextView vendorEmailAddressTextView;
    private TextView vendorPhoneTextView;
    private CircleImageView vendorImageView;
    private View orderNumberEntryView;
    private EditText orderNumberEditText;
    private Spinner deliveryOptionsSpinner;

    private ArrayAdapter<String> deliveryOptionsAdapter;

    private String[] deliveryOptions =  {
            "Hakikisha delivery service",
            "Vendor Pick Up Location",
            "Other"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_request);

        initializeResources();
    }

    private void initializeResources() {
        this.closeActionView = findViewById(R.id.activity_request_payment_close_action_view);
        this.sendDeliveryRequestFab = findViewById(R.id.activity_delivery_request_fab);
        this.sellerDetailsView = findViewById(R.id.request_payment_customer_entry_view);
        this.vendorNameTextView = findViewById(R.id.request_payment_vendor_name_text_view);
        this.vendorEmailAddressTextView = findViewById(R.id.request_payment_vendor_email_text_view);
        this.vendorPhoneTextView = findViewById(R.id.request_payment_vendor_phone_number_text_view);
        this.vendorImageView = findViewById(R.id.request_payment_vendor_image_view);
        this.orderNumberEditText = findViewById(R.id.request_payments_order_number_edit_text);
        this.deliveryOptionsSpinner = findViewById(R.id.payments_delivery_option_spinner);

        this.deliveryOptionsAdapter = new ArrayAdapter<>(DeliveryRequestActivity.this,android.R.layout.simple_spinner_dropdown_item,deliveryOptions);
        this.deliveryOptionsSpinner.setAdapter(deliveryOptionsAdapter);

        this.sendDeliveryRequestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validData = validatefields();
                if (!validData)return;
                Toast.makeText(DeliveryRequestActivity.this,"Delivery request sent successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DeliveryRequestActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean validatefields() {
        if (orderNumberEditText.getText().toString().isEmpty()) {
            orderNumberEditText.setError("Required field");
            orderNumberEditText.requestFocus();
            return false;
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DeliveryRequestActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
