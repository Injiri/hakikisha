package org.aplusscreators.hakikisha.views.buyer;

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
import org.aplusscreators.hakikisha.model.Buyer;
import org.aplusscreators.hakikisha.views.seller.SellerDashboard;

import java.util.UUID;

public class BuyerProfileFormActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText address_1_ediEditText;
    EditText address_2_ediEditText;
    EditText emailEditText;
    EditText dobEditText;
    ProgressBar progressBar;
    Button submit;
    Buyer buyer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_profile);

        firstNameEditText = findViewById(R.id.buyer_first_name);
        lastNameEditText = findViewById(R.id.buyer_last_name);
        address_1_ediEditText = findViewById(R.id.buyer_address_1);
        address_2_ediEditText = findViewById(R.id.buyer_address_2);
        emailEditText = findViewById(R.id.buyer_email_edit_text);
        dobEditText = findViewById(R.id.buyer_dob);
        progressBar = findViewById(R.id.buyer_progress_bar);
        submit = findViewById(R.id.submit_buyer_profile_button);

        buyer = new Buyer();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                extractAndSubmitFormData();
            }
        });
    }

    private void extractAndSubmitFormData() {
        buyer.setUuid(UUID.randomUUID().toString());
        buyer.setFirstName(firstNameEditText.getText().toString());
        buyer.setLastName(lastNameEditText.getText().toString());
        buyer.setAddress_1(address_1_ediEditText.getText().toString());
        buyer.setAddress_2(address_2_ediEditText.getText().toString());
        buyer.setEmail(emailEditText.getText().toString());
        buyer.setDob(dobEditText.getText().toString());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");

        Task task = databaseReference
                .child("buyers")
                .child(buyer.getUuid())
                .setValue(buyer);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Intent intent = new Intent(BuyerProfileFormActivity.this, SellerDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BuyerProfileFormActivity.this, "Unable to complete registration", Toast.LENGTH_LONG).show();
            }
        });


    }
}
