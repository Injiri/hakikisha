package org.aplusscreators.hakikisha.views.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aplusscreators.hakikisha.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MakeNewSaleActivity extends AppCompatActivity {

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
    private ProgressBar requestPaymentProgressBar;

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
        this.orderNumberEditText = findViewById(R.id.request_payments_order_number_edit_text);
        this.orderNumberEntryView = findViewById(R.id.request_payments_order_number_field_view);
        this.amountEntryFieldView = findViewById(R.id.request_payment_amount_entry_view);
        this.amountEntryEditText = findViewById(R.id.request_payment_amount_edit_text);
        this.requestPaymentProgressBar = findViewById(R.id.request_payments_progress_bar);

        this.closeActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MakeNewSaleActivity.this)
                        .setMessage("Are you sure you want to cancel ?")
                        .setPositiveButton("Resume", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MakeNewSaleActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                alertBuilder.create().show();
            }
        });

        this.sendRequestPaymentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPaymentProgressBar.setVisibility(View.VISIBLE);
                boolean valideFields = validatefields();
                if (!valideFields)return;
                Toast.makeText(MakeNewSaleActivity.this,"Payment Request Sent", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MakeNewSaleActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.customerDetailsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeNewSaleActivity.this,CustomerFormActivity.class);
                startActivity(intent);
                finish();
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
        super.onBackPressed();
        Intent intent = new Intent(MakeNewSaleActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
