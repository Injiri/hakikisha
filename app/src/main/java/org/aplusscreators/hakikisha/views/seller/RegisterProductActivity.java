package org.aplusscreators.hakikisha.views.seller;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Product;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;

import java.util.UUID;

public class RegisterProductActivity extends AppCompatActivity {

    EditText productNameEditText;
    Button submitButton;
    Spinner productCategorySpinner;
    EditText costEditText;
    EditText qtyEditText;
    TextView photoTextView;
    Switch fragileProductSwitch;
    Switch perishableProductSwitch;
    Switch secondHandSalesSwitch;
    EditText productDescriptionEditText;
    TextView addAttachmentTextView;
    ProgressBar registerProductProgressBar;
    Product product;

    ArrayAdapter<String> productCategoriesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product_form);

        productNameEditText = findViewById(R.id.purchase_product_name_editText);
        submitButton = findViewById(R.id.purchase_product_name_submit_button);
        productCategorySpinner = findViewById(R.id.product_categories_spinner);
        costEditText = findViewById(R.id.register_product_cost_edit_text);
        qtyEditText = findViewById(R.id.product_register_qty_edit_text);
        photoTextView = findViewById(R.id.register_product_take_photo_textview);
        fragileProductSwitch = findViewById(R.id.register_product_fragile_switch);
        perishableProductSwitch = findViewById(R.id.register_product_preishable_switch);
        secondHandSalesSwitch = findViewById(R.id.register_product_second_hand_sale_switch);
        productDescriptionEditText = findViewById(R.id.register_product_description_editText);
        addAttachmentTextView = findViewById(R.id.register_product_add_attachment_textview);
        registerProductProgressBar = findViewById(R.id.register_product_progress_bar);

        productCategoriesAdapter = new ArrayAdapter<>(
                RegisterProductActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.product_categories_arr)
        );

        productCategorySpinner.setAdapter(productCategoriesAdapter);

        product = new Product();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerProductProgressBar.setVisibility(View.VISIBLE);
                extractAndSubmitProductData();
            }
        });

    }

    private void extractAndSubmitProductData() {
        int fragile = fragileProductSwitch.isChecked() ? 1 : 0;
        int perishable = perishableProductSwitch.isChecked() ? 1 : 0;
        int second_hand = secondHandSalesSwitch.isChecked() ? 1 : 0;

        product.setUuid(UUID.randomUUID().toString());
        product.setName(productNameEditText.getText().toString());
        product.setQty(qtyEditText.getText().toString());
        product.setPhoto_uri("photo_uri");
        product.setFragile_product(fragile);
        product.setPerishable(perishable);
        product.setSecond_hand(second_hand);
        product.setDescription(productDescriptionEditText.getText().toString());
        product.setAttachment_uri(addAttachmentTextView.getText().toString());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");
        Task task = databaseReference
                .child("sellers")
                .child(HakikishaPreference.getAccountUuidPrefs(RegisterProductActivity.this))
                .child("products")
                .child(product.getUuid())
                .setValue(product);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                registerProductProgressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterProductActivity.this, "Product Saved", Toast.LENGTH_LONG).show();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registerProductProgressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterProductActivity.this, "Unable to save product, try again later...", Toast.LENGTH_LONG).show();
            }
        });
    }
}
