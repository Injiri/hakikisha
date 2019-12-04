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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.FileUtils;

import java.io.File;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerFormActivity extends AppCompatActivity {

    private static final String TAG = "CustomerFormActivity";
    private static final int CAMERA_CAPTURE_REQUEST_CODE = 5551;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 6661;
    CircleImageView customerProfileImageView;
    View closeFormView;
    EditText customerNamesEditText;
    EditText customerPhoneEditText;
    EditText customerEmailEditText;
    FloatingActionButton addCustomerFab;
    private File imageFile;
    private Uri photoUri;

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
        this.closeFormView = findViewById(R.id.activity_add_customer_close_action_view);

        this.addCustomerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Customer added successfuly",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CustomerFormActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.closeFormView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerFormActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.customerProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCameraImageCapture();
            }
        });
    }

    private void attemptCameraImageCapture() {
        if (ActivityCompat.checkSelfPermission(CustomerFormActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent camerCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageFile = FileUtils.createImageFile(getApplicationContext(), "hks_customer_"+ UUID.randomUUID().toString(), ",png");
            photoUri = FileProvider.getUriForFile(getApplicationContext(), "org.aplusscreators.hakikisha.fileProvider", imageFile);
            camerCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            if (camerCaptureIntent.resolveActivity(getPackageManager()) == null) {
                Toast.makeText(getApplicationContext(), "Unable to open camera, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            startActivityForResult(camerCaptureIntent, CAMERA_CAPTURE_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(CustomerFormActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK && photoUri != null) {
            try {
                Picasso.get().load(photoUri).into(customerProfileImageView);
                ObjectMapper objectMapper = new ObjectMapper();
                String imageFileSerialized = objectMapper.writeValueAsString(imageFile);
                HakikishaPreference.setUserProfileUriPref(CustomerFormActivity.this, imageFileSerialized);
            } catch (Exception ex) {
                Log.e(TAG, "onActivityResult: " + ex);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
