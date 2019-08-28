package org.aplusscreators.hakikisha.views.seller;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.BuyersFormAdapter;
import org.aplusscreators.hakikisha.model.Buyer;
import org.aplusscreators.hakikisha.model.Order;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.views.buyer.RegisterPurchaseForm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisterOrderActivity extends AppCompatActivity implements BuyersFormAdapter.OnBuyerClickedListener {

    ImageView cancelButton;
    Button submitButton;
    EditText productNameEditText;
    Spinner platformSpinner;
    EditText costEditText;
    EditText orderIdEditText;
    ImageView addBuyerImageView;
    RecyclerView buyersRecyclerView;
    EditText descriptionEditText;
    View addAttachmentsView;
    EditText addressEditText;
    EditText qtyEditText;
    ProgressBar progressBar;

    BuyersFormAdapter buyersFormAdapter;

    Order order = new Order();
    Buyer selectedBuyer = new Buyer();

    List<Buyer> buyersList = new ArrayList<>();

    ArrayAdapter<String> platformsArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        cancelButton = findViewById(R.id.order_cancel_form_button);
        submitButton = findViewById(R.id.order_submit_button);
        productNameEditText = findViewById(R.id.order_product_name_editText);
        platformSpinner = findViewById(R.id.order_platforms_spinner);
        costEditText = findViewById(R.id.order_cost_edit_text);
        orderIdEditText = findViewById(R.id.order_order_id_edit_text);
        addBuyerImageView = findViewById(R.id.add_buyer_image_view);
        buyersRecyclerView = findViewById(R.id.order_buyer_recycler_view);
        descriptionEditText = findViewById(R.id.order_description_edit_text);
        addAttachmentsView = findViewById(R.id.order_add_attachment_view);
        addressEditText = findViewById(R.id.order_delivery_address_edit_text);
        qtyEditText = findViewById(R.id.order_qty_address_edit_text);
        progressBar = findViewById(R.id.order_progress_bar);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                extractAndSubmitOrderData();
            }
        });

        platformsArrayAdapter = new ArrayAdapter<>(
            RegisterOrderActivity.this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.purchase_eplatforms)
        );

        buyersFormAdapter = new BuyersFormAdapter(
                RegisterOrderActivity.this,buyersList,this
        );

        buyersRecyclerView.setLayoutManager( new LinearLayoutManager(RegisterOrderActivity.this));
        buyersRecyclerView.setAdapter(buyersFormAdapter);

        platformSpinner.setAdapter(platformsArrayAdapter);

        populateBuyersList();

    }

    private void populateBuyersList(){
        Buyer buyer = new Buyer();
        buyer.setFirstName("Angela");
        buyer.setLastName("Michale");
        buyer.setEmail("buyer@mail.com");
        buyersList.add(buyer);
        buyersList.add(buyer);

    }

    private void extractAndSubmitOrderData() {
        order.setUuid(UUID.randomUUID().toString());
        order.setProduct_uuid("uuid");
        order.setOrder_id(orderIdEditText.getText().toString());
        order.setCustomer_uuid(selectedBuyer.getUuid());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");
        Task task = databaseReference
                .child("orders")
                .child(HakikishaPreference.getAccountUuidPrefs(RegisterOrderActivity.this))
                .child(order.getUuid())
                .setValue(order);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterOrderActivity.this, "Order Registered", Toast.LENGTH_LONG).show();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterOrderActivity.this, "Unable to save product, try again later...", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBuyerClicked(int position) {
        selectedBuyer = buyersList.get(position);
    }
}
