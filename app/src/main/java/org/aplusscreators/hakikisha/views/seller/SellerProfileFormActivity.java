package org.aplusscreators.hakikisha.views.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import org.aplusscreators.hakikisha.model.Seller;

import java.util.UUID;

public class SellerProfileFormActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText companyNameEditText;
    EditText address_1_ediEditText;
    EditText address_2_ediEditText;
    EditText emailEditText;
    EditText dobEditText;
    ProgressBar progressBar;
    Button submit;
    Seller seller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        companyNameEditText = findViewById(R.id.company_name_edit_text);
        address_1_ediEditText = findViewById(R.id.address_1_edit_text);
        address_2_ediEditText = findViewById(R.id.address_2_edit_text);
        emailEditText = findViewById(R.id.seller_email_edit_text);
        dobEditText = findViewById(R.id.seller_dob_edit_text);
        submit = findViewById(R.id.submi_seller_profile_button);
        progressBar = findViewById(R.id.seller_progress_bar);

        progressBar.setVisibility(View.GONE);

        seller = new Seller();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                extractAndSubmitFormData();
            }
        });
    }

    private void extractAndSubmitFormData() {
        seller.setUuid(UUID.randomUUID().toString());
        seller.setFirstName(firstNameEditText.getText().toString());
        seller.setLastName(lastNameEditText.getText().toString());
        seller.setCompany(companyNameEditText.getText().toString());
        seller.setAddress_1(address_1_ediEditText.getText().toString());
        seller.setAddress_2(address_2_ediEditText.getText().toString());
        seller.setEmail(emailEditText.getText().toString());
        seller.setDob(dobEditText.getText().toString());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");

        Task task = databaseReference
                .child("sellers")
                .child(seller.getUuid())
                .setValue(seller);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Intent intent = new Intent(SellerProfileFormActivity.this, SellerDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SellerProfileFormActivity.this, "Unable to complete registration", Toast.LENGTH_LONG).show();
            }
        });


    }
}
