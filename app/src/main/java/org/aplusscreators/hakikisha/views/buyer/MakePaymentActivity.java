package org.aplusscreators.hakikisha.views.buyer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.Constants;
import org.aplusscreators.hakikisha.utils.HakikishaUtils;
import org.aplusscreators.hakikisha.views.buyer.dialog.ExitPurchaseFormDialog;
import org.aplusscreators.hakikisha.views.common.DashboardActivity;
import org.aplusscreators.hakikisha.views.seller.SellerDashboard;

import java.io.IOException;
import java.util.Locale;

public class MakePaymentActivity extends AppCompatActivity {

    private static final String M_PESA_PAYMENT_METHOD = "m-pesa";
    private static final String GOOGLE_PAY_PAYMENT_METHOD = "g_pay";
    private static final String CARD_PAYMENT_METHOD = "card_pay";
    private static final int SEND_SMS_INTENT_REQUEST_CODE = 1111;
    private static final String SMS_SENT_INTENT_FILTER = "SMS_SENT";

    Purchase purchaseData = new Purchase();
    ObjectMapper objectMapper = new ObjectMapper();
    View closeFormView;
    TextView productDetailsTextView;
    TextView totalCostTextView;
    TextView deliveryInfoTextView;
    View mpesaPaymentMethodView;
    ImageView mpesaSelectedImageView;
    View googlePayMethodView;
    ImageView googlePaySelectedImageView;
    View creditCardView;
    ImageView creditCardSelectedView;
    Button submitButton;
    String selectedPaymentMethod;
    String purchaseSerialized;
    private boolean isSmsFailDialogVisible;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        closeFormView = findViewById(R.id.close_payment_form_view);
        productDetailsTextView = findViewById(R.id.product_description_text_view);
        totalCostTextView = findViewById(R.id.product_price_text_view);
        deliveryInfoTextView = findViewById(R.id.product_delivery_address_text_view);
        mpesaPaymentMethodView = findViewById(R.id.mpesa_payment_method_view);
        mpesaSelectedImageView = findViewById(R.id.mpesa_method_select_image_view);
        googlePayMethodView = findViewById(R.id.google_payment_method_view);
        googlePaySelectedImageView = findViewById(R.id.google_pay_selected_image_view);
        creditCardView = findViewById(R.id.card_payment_method_view);
        creditCardSelectedView = findViewById(R.id.card_payment_method_selected_image_view);
        submitButton = findViewById(R.id.submit_payment_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendSms();

                switch (selectedPaymentMethod) {
                    case M_PESA_PAYMENT_METHOD:
                        sendSms();
                        break;
                    case CARD_PAYMENT_METHOD:
                        sendSms();
                        break;
                    case GOOGLE_PAY_PAYMENT_METHOD:
                        sendSms();
                        break;
                    default:
                        break;
                }
            }
        });

        mpesaPaymentMethodView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPaymentMethod = M_PESA_PAYMENT_METHOD;
                submitButton.setVisibility(View.VISIBLE);
                mpesaSelectedImageView.setVisibility(View.VISIBLE);
                googlePaySelectedImageView.setVisibility(View.GONE);
                creditCardSelectedView.setVisibility(View.GONE);
            }
        });

        googlePayMethodView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPaymentMethod = GOOGLE_PAY_PAYMENT_METHOD;
                submitButton.setVisibility(View.VISIBLE);
                mpesaSelectedImageView.setVisibility(View.GONE);
                googlePaySelectedImageView.setVisibility(View.VISIBLE);
                creditCardSelectedView.setVisibility(View.GONE);
            }
        });

        creditCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPaymentMethod = CARD_PAYMENT_METHOD;
                submitButton.setVisibility(View.VISIBLE);
                mpesaSelectedImageView.setVisibility(View.GONE);
                googlePaySelectedImageView.setVisibility(View.GONE);
                creditCardSelectedView.setVisibility(View.VISIBLE);
            }
        });

        closeFormView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MakePaymentActivity.this);
                alertBuilder.setMessage("Are you sure you want to cancel this purchase ?");
                alertBuilder.setPositiveButton("Cancel Purchase", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(MakePaymentActivity.this, SellerDashboard.class);
                        startActivity(intent);
                        finish();
                    }
                });

                alertBuilder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertBuilder.show();
            }
        });
    }

    private void sendSms() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                MakePaymentActivity.this
        );

        alertBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
                isSmsFailDialogVisible = false;
                sendSms();
            }
        });

        alertBuilder.setNegativeButton("Do this Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isSmsFailDialogVisible = false;
                dialog.dismiss();
                dialog.cancel();
                updateTransactionSmsFailedFlag();
            }
        });

        alertBuilder.setMessage("Unable to contact Seller, SMS Send Error");

        alertBuilder.create();

        PendingIntent smsSentPendingIntent = PendingIntent.getBroadcast(
                MakePaymentActivity.this, SEND_SMS_INTENT_REQUEST_CODE, new Intent(SMS_SENT_INTENT_FILTER), 0);

        MakePaymentActivity.this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int resultCode = getResultCode();

                boolean errorOccured = false;
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        completeTransaction();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        errorOccured = true;
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        errorOccured = true;
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        errorOccured = true;
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        errorOccured = true;
                        break;
                    case SmsManager.RESULT_ERROR_LIMIT_EXCEEDED:
                        errorOccured = true;
                        break;
                    default:
                        errorOccured = true;
                        break;
                }

                if (errorOccured && !isSmsFailDialogVisible) {
                    alertBuilder.show();
                    isSmsFailDialogVisible = true;
                }

                unregisterReceiver(this);

            }
        }, new IntentFilter(SMS_SENT_INTENT_FILTER));

        String sms = composeSmsMessage(HakikishaPreference.getAccountFullNamesPrefs(MakePaymentActivity.this), String.valueOf(purchaseData.getCost()), purchaseData.getName());
        HakikishaUtils.sendSms(MakePaymentActivity.this, sms, purchaseData.getSellerPhone(), smsSentPendingIntent);
    }

    private void updateTransactionSmsFailedFlag() {
        purchaseData.setStatus(Constants.PURCHASE_STATUS_FLAGS.SMS_DELIVERY_FAILED);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("hakikisha")
                .child("buyer")
                .child(HakikishaPreference.getAccountUuidPrefs(MakePaymentActivity.this))
                .child("purchases")
                .child(purchaseData.getUuid());

        Task task = reference.setValue(purchaseData);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MakePaymentActivity.this, "Payment was successfull", Toast.LENGTH_LONG).show();
                Intent mPesaIntent = new Intent(MakePaymentActivity.this, DashboardActivity.class);
                startActivity(mPesaIntent);
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MakePaymentActivity.this);
                alertBuilder.setMessage("Unable to update purchase data, check your connection");
                alertBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateTransactionSmsFailedFlag();
                    }
                });

                alertBuilder.setNegativeButton("Update Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MakePaymentActivity.this, "Payment was successfull", Toast.LENGTH_LONG).show();
                        Intent mPesaIntent = new Intent(MakePaymentActivity.this, DashboardActivity.class);
                        startActivity(mPesaIntent);
                    }
                });

                alertBuilder.show();

            }
        });
    }

    private void completeTransaction() {
        purchaseData.setStatus(Constants.PURCHASE_STATUS_FLAGS.BUYER_PAID);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("hakikisha")
                .child("buyer")
                .child(HakikishaPreference.getAccountUuidPrefs(MakePaymentActivity.this))
                .child("purchases")
                .child(purchaseData.getUuid());

        Task task = reference.setValue(purchaseData);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MakePaymentActivity.this, "Payment was successfull", Toast.LENGTH_LONG).show();
                Intent mPesaIntent = new Intent(MakePaymentActivity.this, DashboardActivity.class);
                startActivity(mPesaIntent);
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MakePaymentActivity.this);
                alertBuilder.setMessage("Unable to update purchase data, check your connection");
                alertBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        completeTransaction();
                    }
                });

                alertBuilder.setNegativeButton("Update Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MakePaymentActivity.this, "Payment was successfull", Toast.LENGTH_LONG).show();
                        Intent mPesaIntent = new Intent(MakePaymentActivity.this, DashboardActivity.class);
                        startActivity(mPesaIntent);
                    }
                });

                alertBuilder.show();

            }
        });


    }

    private String composeSmsMessage(String buyerNames, String amount, String productName) {
        return String.format("%s \nDear Seller, %s has deposited %s on Hakikisha... Please deliver %s to %s complete the transaction on your end.",
                HakikishaUtils.generateMpesaStyleAlphanumeric(),
                buyerNames,
                amount,
                productName,
                purchaseData.getDeliveryAddress());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ExitPurchaseFormDialog exitPurchaseFormDialog = new ExitPurchaseFormDialog(MakePaymentActivity.this, MakePaymentActivity.this, DashboardActivity.class);
        exitPurchaseFormDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent data = getIntent();
        purchaseSerialized = data.getStringExtra(RegisterPurchaseForm.PURCHASE_SERIALIZED_KEY);
        if (purchaseSerialized != null) {
            purchaseData = deserializePurchaseData(purchaseSerialized);
            productDetailsTextView.setText(String.format(Locale.ENGLISH, "Buy %s, %s on %s", purchaseData.getQuantity(), purchaseData.getName(), purchaseData.getPlatform()));
            totalCostTextView.setText(String.format(Locale.ENGLISH, "Ksh. %.2f", purchaseData.getCost()));
            deliveryInfoTextView.setText(String.format(Locale.ENGLISH, "This product will be delivered to: %s ", purchaseData.getDeliveryAddress()));
        }

    }

    private Purchase deserializePurchaseData(String purchaseSerialized) {
        try {
            return objectMapper.readValue(purchaseSerialized, Purchase.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
