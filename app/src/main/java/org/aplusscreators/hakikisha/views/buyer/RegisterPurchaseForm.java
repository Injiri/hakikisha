package org.aplusscreators.hakikisha.views.buyer;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.SellerFormAdapter;
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.model.Seller;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.views.seller.RegisterProductActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisterPurchaseForm extends AppCompatActivity implements SellerFormAdapter.OnSellerClickedListener {

    Button submitButton;
    ImageView cancelButton;
    Spinner purchasePlatformSpinner;
    EditText productNameEditText;
    EditText costEditText;
    EditText orderEditText;
    RecyclerView sellerRecyclerView;
    ImageView addSellerButton;
    EditText descriptionEditText;
    EditText addressEditText;
    EditText qtyEditText;
    ProgressBar registerPurchaseProgressBar;
    Purchase purchase;
    SellerFormAdapter sellerAdapter;
    ArrayAdapter<String> platformsArrayAdapter;
    Seller selectedSeller = new Seller();

    List<Seller> sellerList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_purchase_form);

        submitButton = findViewById(R.id.purchase_product_name_submit_button);
        cancelButton = findViewById(R.id.purchase_cancel_form_button);
        purchasePlatformSpinner = findViewById(R.id.purchase_platforms_spinner);
        costEditText = findViewById(R.id.register_purchase_cost_edittext);
        orderEditText = findViewById(R.id.register_purchase_order_id_edit_text);
        sellerRecyclerView = findViewById(R.id.register_purchase_seller_recycler_view);
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

        sellerAdapter = new SellerFormAdapter(RegisterPurchaseForm.this,sellerList,this);
        sellerRecyclerView.setLayoutManager(new LinearLayoutManager(RegisterPurchaseForm.this));
        sellerRecyclerView.setAdapter(sellerAdapter);

        platformsArrayAdapter = new ArrayAdapter<>(
                RegisterPurchaseForm.this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.purchase_eplatforms)
        );

        purchasePlatformSpinner.setAdapter(platformsArrayAdapter);

        populateSellersList();


    }

    private void populateSellersList() {
        Seller seller = new Seller();
        seller.setFirstName("Michael");
        seller.setLastName("Dawney");
        seller.setCompany("Compamy Ltd");

        sellerList.add(seller);
        sellerList.add(seller);
        sellerList.add(seller);
        sellerList.add(seller);
        sellerList.add(seller);

        selectedSeller = sellerList.get(0);
    }

    private void extractAndSubmitPurchaseData() {
        purchase.setUuid(UUID.randomUUID().toString());
        purchase.setName(productNameEditText.getText().toString());
        purchase.setPlatform(purchasePlatformSpinner.getSelectedItem().toString());
        purchase.setCost(Double.parseDouble(costEditText.getText().toString()));
        purchase.setSellerUuid(selectedSeller.getUuid());
        purchase.setDescription(descriptionEditText.getText().toString());
        purchase.setDeliveryAddress(addressEditText.getText().toString());
        purchase.setQuantity(qtyEditText.getText().toString());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");
        Task task = databaseReference
                .child("buyer")
                .child(HakikishaPreference.getAccountUuidPrefs(RegisterPurchaseForm.this))
                .child("purchases")
                .child(purchase.getUuid())
                .setValue(purchase);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                registerPurchaseProgressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterPurchaseForm.this, "Purchase Registered", Toast.LENGTH_LONG).show();
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

    @Override
    public void onSellerClicked(int position, LinearLayout view) {
        view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        selectedSeller = sellerList.get(position);

    }
}
