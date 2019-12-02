package org.aplusscreators.hakikisha.views.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aplusscreators.hakikisha.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MakePaymentActivity extends AppCompatActivity {

    private View closeActionView;
    private FloatingActionButton activatePaymentFab;
    private View sellerDetailsView;
    private TextView vendorNameTextView;
    private TextView vendorEmailAddressTextView;
    private CircleImageView vendorImageView;
    private View orderNumberEntryView;
    private EditText orderNumberEditText;
    private View amountEntryFieldView;
    private EditText amountEntryEditText;
    private Spinner deliveryOptionsSpinner;

    private ArrayAdapter<String> deliveryOptionsAdapter;
    private String[] deliveryOptions = {
            "Hakikisha Delivery Service",
            "Postal Service",
            "Vendor Pick Up location"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        initializeResources();
    }

    private void initializeResources() {
        this.closeActionView = findViewById(R.id.activity_make_payment_close_action_view);
        this.activatePaymentFab = findViewById(R.id.activity_payments_pay_fab);
        this.sellerDetailsView = findViewById(R.id.make_payment_vendor_entry_view);
        this.vendorNameTextView = findViewById(R.id.make_payment_vendor_name_text_view);
        this.vendorEmailAddressTextView = findViewById(R.id.make_payment_vendor_email_text_view);
        this.vendorImageView = findViewById(R.id.make_payment_vendor_image_view);
        this.orderNumberEditText = findViewById(R.id.make_payments_order_number_edit_text);
        this.orderNumberEntryView = findViewById(R.id.make_payments_order_number_field_view);
        this.amountEntryFieldView = findViewById(R.id.make_payment_amount_entry_view);
        this.amountEntryEditText = findViewById(R.id.make_payment_amount_edit_text);
        this.deliveryOptionsSpinner = findViewById(R.id.payments_delivery_option_spinner);

        this.deliveryOptionsAdapter = new ArrayAdapter<>(MakePaymentActivity.this, android.R.layout.simple_spinner_dropdown_item, deliveryOptions);
        this.deliveryOptionsSpinner.setAdapter(this.deliveryOptionsAdapter);

        this.closeActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MakePaymentActivity.this)
                        .setMessage("Are you sure you want to cancel this payment?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MakePaymentActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                alertBuilder.create().show();
            }
        });

        this.activatePaymentFab.requestFocus();
        this.orderNumberEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderNumberEditText.setText("");
            }
        });

        this.activatePaymentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allFieldsValid = validatefields();
                if (allFieldsValid){
                    //todo proceed to make payments
                }
            }
        });
    }

    private boolean validatefields() {
        if (orderNumberEditText.getText().toString().isEmpty()) {
            orderNumberEditText.setError("Required field");
            return false;
        }
        if (amountEntryEditText.getText().toString().isEmpty()) {
            amountEntryEditText.setError("Required field");
            return false;
        }
        if (amountEntryEditText.getText().toString().contains(",")) {
            amountEntryEditText.setError("Invalid character [,]");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MakePaymentActivity.this)
                .setMessage("Are you sure you want to cancel this payment?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MakePaymentActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        alertBuilder.create().show();

    }
}
