package org.aplusscreators.hakikisha.views.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aplusscreators.hakikisha.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class VendorFormActivity extends AppCompatActivity {

    private View closeView;
    private CircleImageView profileImageView;
    private FloatingActionButton addVendorFab;
    private EditText vendorNameEditText;
    private EditText vendorPhoneNumberEditText;
    private EditText vendorEmailEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vendor_layout);

        initializeResources();
    }

    private void initializeResources() {
        this.closeView = findViewById(R.id.activity_add_vendor_close_action_view);
        this.profileImageView = findViewById(R.id.make_new_vendor_image_view);
        this.addVendorFab = findViewById(R.id.activity_new_vendor_fab);
        this.vendorNameEditText = findViewById(R.id.vendor_name_edit_text);
        this.vendorPhoneNumberEditText = findViewById(R.id.vendor_phone_number_edit_text);
        this.vendorEmailEditText = findViewById(R.id.vendor_email_edit_text);

        this.addVendorFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VendorFormActivity.this, "Vendor added successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(VendorFormActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VendorFormActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
