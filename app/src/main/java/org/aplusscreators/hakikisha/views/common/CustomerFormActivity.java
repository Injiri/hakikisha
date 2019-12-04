package org.aplusscreators.hakikisha.views.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aplusscreators.hakikisha.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerFormActivity extends AppCompatActivity {

    CircleImageView customerProfileImageView;
    EditText customerNamesEditText;
    EditText customerPhoneEditText;
    EditText customerEmailEditText;
    FloatingActionButton addCustomerFab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        initializeResources();
    }

    private void initializeResources() {
        this.customerProfileImageView = findViewById(R.id.make_new_customer_image_view);
        this.customerNamesEditText = findViewById(R.id.customer_name_edit_text);
        this.customerPhoneEditText = findViewById(R.id.customer_phone_number_edit_text);
        this.customerEmailEditText = findViewById(R.id.customer_email_edit_text);
        this.addCustomerFab = findViewById(R.id.activity_new_customer_fab);

        this.addCustomerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Customer added successfuly",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CustomerFormActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
