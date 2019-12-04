package org.aplusscreators.hakikisha.views.common;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.utils.FileUtils;

import java.io.File;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class VendorFormActivity extends AppCompatActivity {

    private static final String TAG = "VendorFormActivity";
    private static final int CAMERA_CAPTURE_REQUEST_CODE = 7771;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 8881;
    private View closeView;
    private CircleImageView profileImageView;
    private FloatingActionButton addVendorFab;
    private EditText vendorNameEditText;
    private EditText vendorPhoneNumberEditText;
    private EditText vendorEmailEditText;
    private File imageFile;
    private Uri photoUri;

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
        this.profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCameraImageCapture();
            }
        });

    }

    private void attemptCameraImageCapture() {
        if (ActivityCompat.checkSelfPermission(VendorFormActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent camerCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageFile = FileUtils.createImageFile(getApplicationContext(), "hks_customer_"+ UUID.randomUUID().toString(), ".png");
            photoUri = FileProvider.getUriForFile(getApplicationContext(), "org.aplusscreators.hakikisha.fileProvider", imageFile);
            camerCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            if (camerCaptureIntent.resolveActivity(getPackageManager()) == null) {
                Toast.makeText(getApplicationContext(), "Unable to open camera, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            startActivityForResult(camerCaptureIntent, CAMERA_CAPTURE_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(VendorFormActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK && photoUri != null) {
            try {
                Picasso.get().load(photoUri).into(profileImageView);
                ObjectMapper objectMapper = new ObjectMapper();
            } catch (Exception ex) {
                Log.e(TAG, "onActivityResult: " + ex);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
