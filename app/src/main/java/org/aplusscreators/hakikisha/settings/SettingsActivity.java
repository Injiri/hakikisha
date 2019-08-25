package org.aplusscreators.hakikisha.settings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.utils.Constants;
import org.aplusscreators.hakikisha.utils.FileUtils;
import org.aplusscreators.hakikisha.views.seller.SellerDashboard;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    SettingsFragment settingsFragment;
    FragmentManager fragmentManager;
    TextView usernameTextView;
    TextView emailTextView;
    ImageView profileHolderImageView;
    CircleImageView profileCircleImageView;
    Uri photoUri;
    File imageFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsFragment = new SettingsFragment();
        fragmentManager = getSupportFragmentManager();

        commitSettingsFragment();

        usernameTextView = findViewById(R.id.setting_user_name_text_view);
        emailTextView = findViewById(R.id.setting_email_text_view);
        profileHolderImageView = findViewById(R.id.settings_add_photo_image_view);
        profileCircleImageView = findViewById(R.id.settings_profile_circle_imageview);

        setUserProperties();

        profileHolderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureProfilePicture();
            }
        });

        profileCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureProfilePicture();
            }
        });
    }

    private void commitSettingsFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.settings_container, settingsFragment)
                .commit();
    }

    private void captureProfilePicture() {
        if (ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.CAMERA}, Constants.PERMISSION_CONSTANTS.CAMERA_REQUEST_CODE);
        } else {
            openCameraImageCapture();
        }
    }

    private void openCameraImageCapture() {
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = FileUtils.createImageFile(SettingsActivity.this, "planner_profile", ".png");
        photoUri = FileProvider.getUriForFile(SettingsActivity.this, "org.aplusscreators.com.planner.fileProvider", imageFile);
        captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(captureImageIntent, Constants.CAMERA.IMAGE_CAPTURE_REQUEST_CODE);
    }

    private void setUserProperties() {

        String username = "user name";
        String email = "user@email";

        usernameTextView.setText(username);
        emailTextView.setText(email);

        String serializedUri = HakikishaPreference.getUserProfileUriPref(SettingsActivity.this);

        Log.e(TAG, "setUserProperties: serializedUri " + serializedUri);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (serializedUri == null)
                return;

            File imageUri = objectMapper.readValue(serializedUri, File.class);
            if (imageUri != null)
                Picasso.get().load(imageUri).into(profileCircleImageView);

            profileHolderImageView.setVisibility(View.GONE);
            profileCircleImageView.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            Log.e(TAG, "setUserProperties Error: " + e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.CAMERA.IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {

            Uri imageUri = photoUri;
            if (imageUri != null) {
                Picasso.get()
                        .load(imageUri)
                        .into(profileCircleImageView);

                profileHolderImageView.setVisibility(View.GONE);
                profileCircleImageView.setVisibility(View.VISIBLE);

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String serializedImage = objectMapper.writeValueAsString(imageFile);

                    Log.e(TAG, "onActivityResult: " + serializedImage );

                    HakikishaPreference.setUserProfileUriPref(SettingsActivity.this, serializedImage);
                } catch (JsonProcessingException e) {
                    throw new AssertionError(e);
                }
            }
        } else {
            Log.e(TAG, "onActivityResult: result code " + resultCode);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String action;
        Intent intent = new Intent(SettingsActivity.this, SellerDashboard.class);
        startActivity(intent);
    }
}
