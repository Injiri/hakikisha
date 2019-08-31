package org.aplusscreators.hakikisha.views.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.views.buyer.dialog.ExitPurchaseFormDialog;
import org.aplusscreators.hakikisha.views.common.ExitFormDialog;

import java.io.IOException;
import java.util.Locale;

public class MakePaymentActivity extends AppCompatActivity {

    private static final String M_PESA_PAYMENT_METHOD = "m-pesa";
    private static final String GOOGLE_PAY_PAYMENT_METHOD = "g_pay";
    private static final String CARD_PAYMENT_METHOD = "card_pay";

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
                switch (selectedPaymentMethod) {
                    case M_PESA_PAYMENT_METHOD:
                        Toast.makeText(MakePaymentActivity.this,"Payment was successfull",Toast.LENGTH_LONG).show();
                        Intent mPesaIntent = new Intent(MakePaymentActivity.this, BuyerDashboard.class);
                        startActivity(mPesaIntent);
                        break;
                    case CARD_PAYMENT_METHOD:
                        Intent intent = new Intent(MakePaymentActivity.this, CardPaymentActivity.class);
                        intent.putExtra(RegisterPurchaseForm.PURCHASE_SERIALIZED_KEY,purchaseSerialized);
                        startActivity(intent);
                        break;
                    case GOOGLE_PAY_PAYMENT_METHOD:
                        Toast.makeText(MakePaymentActivity.this,"Payment was successfull",Toast.LENGTH_LONG).show();
                        Intent GPayIntent = new Intent(MakePaymentActivity.this, BuyerDashboard.class);
                        startActivity(GPayIntent);
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
                ExitPurchaseFormDialog exitFormDialog = new ExitPurchaseFormDialog(MakePaymentActivity.this, MakePaymentActivity.this, BuyerDashboard.class);
                exitFormDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ExitPurchaseFormDialog exitPurchaseFormDialog = new ExitPurchaseFormDialog(MakePaymentActivity.this, MakePaymentActivity.this, BuyerDashboard.class);
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
