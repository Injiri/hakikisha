package org.aplusscreators.hakikisha.views.seller;

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
import org.aplusscreators.hakikisha.model.Seller;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.Constants;
import org.aplusscreators.hakikisha.utils.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerProfileFormActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1234;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText companyNameEditText;
    EditText address_1_ediEditText;
    EditText address_2_ediEditText;
    EditText emailEditText;
    TextView dobTextView;
    ProgressBar progressBar;
    ImageView sellerProfileImageView;
    CircleImageView circleImageView;
    Button submit;
    Seller seller;

    Uri photoUri;
    File imageFile;

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
        dobTextView = findViewById(R.id.seller_dob_edit_text);
        submit = findViewById(R.id.submi_seller_profile_button);
        progressBar = findViewById(R.id.seller_progress_bar);
        circleImageView = findViewById(R.id.seller_profile_view);
        sellerProfileImageView = findViewById(R.id.seller_photo_capture_image_view);

        progressBar.setVisibility(View.GONE);

        seller = new Seller();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                extractAndSubmitFormData();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        sellerProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SellerProfileFormActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED){
                    launchCamera();
                } else {
                    ActivityCompat.requestPermissions(SellerProfileFormActivity.this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });

        dobTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SellerProfileFormActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        calendar.set(Calendar.MONTH,month);

                        dobTextView.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, 1995, 5, 11
                );

                datePickerDialog.show();
            }
        });
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = FileUtils.createImageFile(SellerProfileFormActivity.this, "hakikisha_profile", ".png");
        photoUri = FileProvider.getUriForFile(SellerProfileFormActivity.this, "org.aplusscreators.hakikisha.fileProvider", imageFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, Constants.CAMERA.IMAGE_CAPTURE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            launchCamera();
        } else {
            ActivityCompat.requestPermissions(SellerProfileFormActivity.this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.CAMERA.IMAGE_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            circleImageView.setVisibility(View.VISIBLE);
            sellerProfileImageView.setVisibility(View.GONE);
            Picasso.get().load(photoUri).into(circleImageView);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void extractAndSubmitFormData() {
        seller.setUuid(UUID.randomUUID().toString());
        seller.setFirstName(firstNameEditText.getText().toString());
        seller.setLastName(lastNameEditText.getText().toString());
        seller.setCompany(companyNameEditText.getText().toString());
        seller.setAddress_1(address_1_ediEditText.getText().toString());
        seller.setAddress_2(address_2_ediEditText.getText().toString());
        seller.setEmail(emailEditText.getText().toString());
        seller.setDob(dobTextView.getText().toString());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");

        Task task = databaseReference
                .child("sellers")
                .child(seller.getUuid())
                .setValue(seller);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                HakikishaPreference.setAccountFullNamesPrefs(SellerProfileFormActivity.this,
                        String.format(Locale.ENGLISH,"%s %s",seller.getFirstName(),seller.getLastName()));
                HakikishaPreference.setAccountAddress1Prefs(SellerProfileFormActivity.this,seller.getAddress_1());
                HakikishaPreference.setAccountAddress2Prefs(SellerProfileFormActivity.this,seller.getAddress_2());

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
