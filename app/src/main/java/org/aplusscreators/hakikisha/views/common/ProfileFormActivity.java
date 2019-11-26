package org.aplusscreators.hakikisha.views.common;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Buyer;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.Constants;
import org.aplusscreators.hakikisha.utils.FileUtils;
import org.aplusscreators.hakikisha.views.buyer.RegisterPurchaseForm;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFormActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 2342;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    View profileImageView;
    CircleImageView circleImageView;
    EditText userNamesEditText;
    EditText emailEditText;
    TextView dobTextView;
    View dobView;
    ProgressBar progressBar;
    Button submit;
    Buyer buyer;
    Uri photoUri;
    File imageFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_profile);

        userNamesEditText = findViewById(R.id.buyer_first_name);
        emailEditText = findViewById(R.id.buyer_email_edit_text);
        dobView = findViewById(R.id.buyer_dob_view);
        dobTextView = findViewById(R.id.dob_text_view);
        progressBar = findViewById(R.id.buyer_progress_bar);
        submit = findViewById(R.id.submit_buyer_profile_button);
        profileImageView = findViewById(R.id.profile_image_placeholder_image_view);
        circleImageView = findViewById(R.id.buyer_profile);

        buyer = new Buyer();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                extractAndSubmitFormData();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        dobView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ProfileFormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        dobTextView.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, 1995, 5, 5
                );

                datePickerDialog.show();
            }
        });
    }

    private void launchCamera() {
        if (ContextCompat.checkSelfPermission(ProfileFormActivity.this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageFile = FileUtils.createImageFile(ProfileFormActivity.this, "hakikisha_profile", ".png");
            photoUri = FileProvider.getUriForFile(ProfileFormActivity.this, "org.aplusscreators.hakikisha.fileProvider", imageFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, Constants.CAMERA.IMAGE_CAPTURE_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(ProfileFormActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            launchCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.CAMERA.IMAGE_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Picasso.get().load(photoUri).into(circleImageView);
            circleImageView.setVisibility(View.VISIBLE);
            profileImageView.setVisibility(View.GONE);

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void extractAndSubmitFormData() {
        buyer.setUuid(UUID.randomUUID().toString());
        buyer.setFirstName(userNamesEditText.getText().toString());
        buyer.setEmail(emailEditText.getText().toString());
        buyer.setDob(dobTextView.getText().toString());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");

        Task task = databaseReference
                .child("buyers")
                .child(buyer.getUuid())
                .setValue(buyer);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                HakikishaPreference.setAccountFullNamesPrefs(ProfileFormActivity.this,
                        String.format(Locale.ENGLISH,"%s %s",buyer.getFirstName(),buyer.getLastName()));
                HakikishaPreference.setAccountAddress1Prefs(ProfileFormActivity.this,buyer.getAddress_1());
                HakikishaPreference.setAccountAddress2Prefs(ProfileFormActivity.this,buyer.getAddress_2());
                HakikishaPreference.setAccountUuidPrefs(ProfileFormActivity.this,buyer.getUuid());

                Intent intent = new Intent(ProfileFormActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileFormActivity.this, "Unable to complete registration", Toast.LENGTH_LONG).show();
            }
        });


    }
}
