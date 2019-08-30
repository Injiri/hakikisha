package org.aplusscreators.hakikisha.views.buyer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.model.Seller;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.HakikishaUtils;
import org.aplusscreators.hakikisha.views.common.ExitFormDialog;

import java.util.UUID;

public class RegisterPurchaseForm extends AppCompatActivity {

    private static final int SMS_PERMISSION_REQUEST_CODE = 4321;
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
                registerPurchaseProgressBar.setVisibility(View.VISIBLE);
                extractAndSubmitPurchaseData();
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

    private void requestSmsPermission(){
        if (ContextCompat.checkSelfPermission(RegisterPurchaseForm.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RegisterPurchaseForm.this,new String[]{Manifest.permission.SEND_SMS},SMS_PERMISSION_REQUEST_CODE);
        }
    }

    private void extractAndSubmitPurchaseData() {
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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");
        Task task = databaseReference
                .child("buyer")
                //  .child(HakikishaPreference.getAccountUuidPrefs(RegisterPurchaseForm.this)) todo restore uuid
                .child(UUID.randomUUID().toString())
                .child("purchases")
                .child(purchase.getUuid())
                .setValue(purchase);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                registerPurchaseProgressBar.setVisibility(View.GONE);

                Intent intent = new Intent(RegisterPurchaseForm.this, BuyerDashboard.class);

                if (!sellerPhoneNumber.getText().toString().isEmpty())
                    intent.putExtra("send_purchase_sms",sellerPhoneNumber.getText().toString());
                else if (sellerEmailEditText.getText().toString().isEmpty())
                    intent.putExtra("send_purchase_email",sellerEmailEditText.getText().toString());

                Toast.makeText(RegisterPurchaseForm.this, "Purchase Registered", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registerPurchaseProgressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterPurchaseForm.this, "Unable to save product, try again later...", Toast.LENGTH_LONG).show();
            }
        });
    }
}
