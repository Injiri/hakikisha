package org.aplusscreators.hakikisha.views.buyer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.model.Seller;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.HakikishaUtils;
import org.aplusscreators.hakikisha.utils.Sound;
import org.aplusscreators.hakikisha.views.common.ExitFormDialog;

import java.util.UUID;

public class RegisterPurchaseForm extends AppCompatActivity {
    private static final String TAG = "RegisterPurchaseForm";
    private static final int SMS_PERMISSION_REQUEST_CODE = 4321;
    private static final String FORM_DATA_RELOAD_KEY = "form_data_reload_key";
    private static final String PRODUCT_NAME_KEY = "product_name_key";
    private static final String COST_KEY = "product_cost_key";
    private static final String DELIVERY_ADDRESS_KEY = "delivery_address_key";
    private static final String QTY_KEY = "quantity_key";
    private static final String SELLER_PHONE_NUMBER_KEY = "seller_phone_key";
    private static final String SELLER_EMAIL_KEY = "seller_email_key";
    public static final String PURCHASE_SERIALIZED_KEY = "purchase_serialized_key";

    Button submitButton;
    ImageView cancelButton;
    Spinner purchasePlatformSpinner;
    EditText productNameEditText;
    EditText costEditText;
    EditText orderEditText;
    EditText sellerEmailEditText;
    EditText sellerPhoneNumber;
    ImageView addSellerButton;
    EditText descriptionEditText;
    EditText addressEditText;
    EditText qtyEditText;
    ProgressBar registerPurchaseProgressBar;
    Purchase purchase = new Purchase();
    ArrayAdapter<String> platformsArrayAdapter;
    Seller selectedSeller = new Seller();
    String purchaseSerialized;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_purchase_form);

        sellerEmailEditText = findViewById(R.id.register_purchase_seller_email);
        sellerPhoneNumber = findViewById(R.id.register_purchase_seller_phone);
        submitButton = findViewById(R.id.purchase_form_submit_button);
        cancelButton = findViewById(R.id.purchase_form_cancel_button);
        purchasePlatformSpinner = findViewById(R.id.purchase_platforms_spinner);
        costEditText = findViewById(R.id.register_purchase_cost_edittext);
        orderEditText = findViewById(R.id.register_purchase_order_id_edit_text);
        addSellerButton = findViewById(R.id.purchase_add_seller_buton);
        descriptionEditText = findViewById(R.id.register_purchase_description);
        addressEditText = findViewById(R.id.register_purchase_delivery_address_edit_text);
        qtyEditText = findViewById(R.id.register_purchase_qty_edit_text);
        registerPurchaseProgressBar = findViewById(R.id.register_purchase_progress_bar);
        productNameEditText = findViewById(R.id.purchase_product_name_editText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = validateFormData();
                if (valid) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_register_purchase_layout), "This might take a while, please wait...", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    registerPurchaseProgressBar.setVisibility(View.VISIBLE);
                    extractAndSubmitPurchaseData(snackbar);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitFormDialog exitFormDialog = new ExitFormDialog(RegisterPurchaseForm.this, RegisterPurchaseForm.this, BuyerDashboard.class);
                exitFormDialog.show();
            }
        });

        platformsArrayAdapter = new ArrayAdapter<>(
                RegisterPurchaseForm.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.purchase_eplatforms)
        );

        purchasePlatformSpinner.setAdapter(platformsArrayAdapter);

        requestSmsPermission();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent data = getIntent();
        ;
        boolean isFormReload = data.getBooleanExtra(FORM_DATA_RELOAD_KEY, false);

        if (isFormReload) {
            String productName = data.getStringExtra(PRODUCT_NAME_KEY);
            String cost = data.getStringExtra(COST_KEY);
            String sellerPhone = data.getStringExtra(SELLER_PHONE_NUMBER_KEY);
            String qty = data.getStringExtra(QTY_KEY);
            String sellerEmail = data.getStringExtra(SELLER_EMAIL_KEY);
            String deliveryAddress = data.getStringExtra(DELIVERY_ADDRESS_KEY);

            productNameEditText.setText(productName);
            costEditText.setText(cost);
            sellerPhoneNumber.setText(sellerPhone);
            qtyEditText.setText(qty);
            sellerEmailEditText.setText(sellerEmail);
            addressEditText.setText(deliveryAddress);

            Toast.makeText(RegisterPurchaseForm.this, "Purchase registration failed...", Toast.LENGTH_LONG).show();

        }

    }

    private boolean validateFormData() {
        if (productNameEditText.getText().toString().isEmpty()) {
            productNameEditText.setError("Missing product name");
            productNameEditText.requestFocus();
            Sound.vibrateDevice(RegisterPurchaseForm.this);
            return false;
        }

        if (costEditText.getText().toString().isEmpty()) {
            costEditText.setError("Product cost (Ksh) is required");
            costEditText.requestFocus();
            Sound.vibrateDevice(RegisterPurchaseForm.this);
            return false;
        }

        if (sellerPhoneNumber.getText().toString().isEmpty()) {
            sellerPhoneNumber.setError("Seller Phone Number is required");
            sellerPhoneNumber.requestFocus();
            return false;
        }

        if (addressEditText.getText().toString().isEmpty()) {
            addressEditText.setError("Delivery address is required");
            addressEditText.requestFocus();
            Sound.vibrateDevice(RegisterPurchaseForm.this);
            return false;
        }

//        if (qtyEditText.getText().toString().isEmpty()) {
//            qtyEditText.setError("Please tell us how much/how many of the products is to be delivered");
//            qtyEditText.requestFocus();
//            Sound.vibrateDevice(RegisterPurchaseForm.this);
//            return false;
//        }

        return true;
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(RegisterPurchaseForm.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterPurchaseForm.this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        }
    }

    private void extractAndSubmitPurchaseData(Snackbar snackbar) {
        purchase.setUuid(UUID.randomUUID().toString());
        purchase.setName(productNameEditText.getText().toString());
        purchase.setPlatform(purchasePlatformSpinner.getSelectedItem().toString());
        purchase.setCost(Double.parseDouble(costEditText.getText().toString()));
        purchase.setDescription(descriptionEditText.getText().toString());
        purchase.setDeliveryAddress(addressEditText.getText().toString());
        purchase.setQuantity(qtyEditText.getText().toString());
        purchase.setSellerEmail(sellerEmailEditText.getText().toString());
        purchase.setBuyerUuid(HakikishaPreference.getAccountUuidPrefs(RegisterPurchaseForm.this));
        purchase.setSellerPhone(sellerPhoneNumber.getText().toString());
        purchase.setStatus("payment_pending");

        serializePurchaseModel();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");
        Task task = databaseReference
                .child("buyer")
                //.child(HakikishaPreference.getAccountUuidPrefs(RegisterPurchaseForm.this))
                .child(UUID.randomUUID().toString())
                .child("purchases")
                .child(purchase.getUuid())
                .setValue(purchase);

        runDataSubmitCountDown(snackbar, task);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                registerPurchaseProgressBar.setVisibility(View.GONE);
                Intent intent = new Intent(RegisterPurchaseForm.this, MakePaymentActivity.class);
                intent.putExtra(PURCHASE_SERIALIZED_KEY, purchaseSerialized);
                Toast.makeText(RegisterPurchaseForm.this, "Purchase Registered", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registerPurchaseProgressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterPurchaseForm.this, "Having trouble saving this purchase, check your connection...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String serializePurchaseModel() {
        purchaseSerialized = null;
        try {
            purchaseSerialized = objectMapper.writeValueAsString(purchase);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return purchaseSerialized;
    }

    private void runDataSubmitCountDown(Snackbar snackbar, Task task) {
        CountDownTimer countDownTimer = new CountDownTimer(60000, 20000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, "onTick: millis " + millisUntilFinished);
                if (millisUntilFinished < 20000) {
                    snackbar.setText("This is taking too long...");
                }

                if (millisUntilFinished > 20000 && millisUntilFinished < 40000) {
                    snackbar.setText("Unable to register purchase, check your connection and try again...");
                }

                snackbar.show();
            }

            @Override
            public void onFinish() {
                if (!task.isComplete()) {
                    registerPurchaseProgressBar.setVisibility(View.GONE);
                    snackbar.setText("Unable to register purchase, check your connection and try again...");
                    snackbar.show();

                    Intent intent = new Intent(RegisterPurchaseForm.this, RegisterPurchaseForm.class);
                    intent.putExtra(FORM_DATA_RELOAD_KEY, true);
                    intent.putExtra(PRODUCT_NAME_KEY, productNameEditText.getText().toString());
                    intent.putExtra(COST_KEY, costEditText.getText().toString());
                    intent.putExtra(DELIVERY_ADDRESS_KEY, addressEditText.getText().toString());
                    intent.putExtra(QTY_KEY, qtyEditText.getText().toString());
                    intent.putExtra(SELLER_PHONE_NUMBER_KEY, sellerPhoneNumber.getText().toString());
                    intent.putExtra(SELLER_EMAIL_KEY, sellerEmailEditText.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }
}
