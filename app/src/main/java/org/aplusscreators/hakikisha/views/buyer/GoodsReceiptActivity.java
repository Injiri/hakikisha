package org.aplusscreators.hakikisha.views.buyer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.DeliveryReport;
import org.aplusscreators.hakikisha.model.Order;
import org.aplusscreators.hakikisha.model.Seller;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.Constants;
import org.aplusscreators.hakikisha.utils.DateTimeUtils;
import org.aplusscreators.hakikisha.utils.FileUtils;
import org.aplusscreators.hakikisha.utils.HakikishaUtils;
import org.aplusscreators.hakikisha.views.common.DashboardActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class GoodsReceiptActivity extends AppCompatActivity {

    private static final String GOODS_REJECTED_FLAG = "goods_rejected";
    private static final String GOODS_ACCEPTED_FLAG = "goods_accepted";
    private static final int SEND_SMS_REJECT_PERMISSION_CODE = 1232;
    private static final int SEND_SMS_ACCEPT_PERMISSION_CODE = 2343;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 3345;
    private static final int CAMERA_IMAGE_CAPTURE_REQUEST_CODE = 4455;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    ImageView cancelButton;
    Button acceptButton;
    Button rejectButton;
    View arrivalDateView;
    View arrivalTimeView;
    EditText sellerPhoneEditText;
    EditText sellerNameEditText;
    EditText productIdEditText;
    TextView arrivalDateTextView;
    TextView arrivalTimeTextView;
    ImageView addSellerImageView;
    TextView cameraCaptureTextView;
    EditText deliveryLocationEditText;
    View addPhotoView;
    AppCompatRatingBar ratingBar;
    ProgressBar progressBar;

    DeliveryReport deliveryReport = new DeliveryReport();

    Order selectedOrder = new Order();
    Seller selectedSeller = new Seller();
    private Uri photoUri;
    private File imageFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_receipt_form);

        cancelButton = findViewById(R.id.receipt_product_receipt_exit_button);
        acceptButton = findViewById(R.id.receipt_product_receipt_accept_button);
        rejectButton = findViewById(R.id.receipt_product_receipt_reject_button);
        arrivalDateView = findViewById(R.id.receipt_arrival_date);
        arrivalTimeView = findViewById(R.id.receipt_arrival_time);
        deliveryLocationEditText = findViewById(R.id.receipt_buyer_location_edit_view);
        arrivalTimeTextView = findViewById(R.id.receipt_arrival_time_text_view);
        arrivalDateTextView = findViewById(R.id.receipt_arrival_date_text_view);
        sellerPhoneEditText = findViewById(R.id.receipt_seller_phone_edit_text);
        sellerNameEditText = findViewById(R.id.receipt_seller_name_text_view);
        addSellerImageView = findViewById(R.id.receipt_add_seller_view);
        addPhotoView = findViewById(R.id.delivery_photo_image_view);
        ratingBar = findViewById(R.id.receipt_ratings_bar);
        cameraCaptureTextView = findViewById(R.id.camera_capture_text_view);
        progressBar = findViewById(R.id.product_receipt_progress_bar);
        productIdEditText = findViewById(R.id.delivery_product_id_edit_text);

        deliveryLocationEditText.setText("Hakikisha pick up location");

        deliveryLocationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address1 = HakikishaPreference.getAccountAddress1Prefs(GoodsReceiptActivity.this);
                String address2 = HakikishaPreference.getAccountAddress2Prefs(GoodsReceiptActivity.this);

                if (address1 != null) {
                    deliveryLocationEditText.setText(address1);
                    return;
                }

                if (address2 != null) {
                    deliveryLocationEditText.setText(address2);
                }
            }
        });

        arrivalDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        GoodsReceiptActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar date = calendar.getInstance();
                        date.set(Calendar.YEAR, year);
                        date.set(Calendar.MONTH, month);
                        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String setDate = simpleDateFormat.format(date.getTime());

                        arrivalDateTextView.setText(setDate);
                    }
                }, year, month, dayOfMonth
                );

                datePickerDialog.show();
            }
        });

        arrivalTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        GoodsReceiptActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar time = Calendar.getInstance();
                        time.set(Calendar.HOUR, hourOfDay);
                        time.set(Calendar.MINUTE, minute);
                        arrivalTimeTextView.setText(DateTimeUtils.getTimeAM_PM(time, GoodsReceiptActivity.this));

                    }
                }, hour, minute, true
                );

                timePickerDialog.show();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                extractAndSubmitData(Constants.PURCHASE_STATUS_FLAGS.PURCHASE_COMPLETE);
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
                boolean valideForm = validateReceiptForm();
                if (valideForm)
                    extractAndSubmitData(Constants.PURCHASE_STATUS_FLAGS.DELIVERY_REJECTED);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GoodsReceiptActivity.this);
                alertBuilder.setMessage("You have unsaved changes, do you want to keep editing ?");
                alertBuilder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(GoodsReceiptActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                alertBuilder.setNegativeButton("Keep editing", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertBuilder.show();
            }
        });

        addPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(GoodsReceiptActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                } else {
                    ActivityCompat.requestPermissions(GoodsReceiptActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    private boolean validateReceiptForm() {
        if (productIdEditText.getText().toString().isEmpty()) {
            productIdEditText.setError("Required field");
            productIdEditText.requestFocus();

            return false;
        }

        if (sellerPhoneEditText.getText().toString().isEmpty()) {
            sellerPhoneEditText.setText("Phone number is required");
            sellerPhoneEditText.requestFocus();

            return false;
        }

        if (deliveryLocationEditText.getText().toString().isEmpty()) {
            deliveryLocationEditText.setText("Delivery location is required");
            deliveryLocationEditText.requestFocus();
            return false;
        }

        return true;
    }

    private void launchCamera() {
        Intent mediaCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = FileUtils.createImageFile(GoodsReceiptActivity.this, "hakikisha_delivery_" + new Random().nextInt(), ".png");
        photoUri = FileProvider.getUriForFile(GoodsReceiptActivity.this, "org.aplusscreators.hakikisha.fileProvider", imageFile);
        mediaCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

        startActivityForResult(mediaCaptureIntent, CAMERA_IMAGE_CAPTURE_REQUEST_CODE);
    }

    private void extractAndSubmitData(String status) {
        progressBar.setVisibility(View.VISIBLE);

        deliveryReport.setUuid(UUID.randomUUID().toString());
        deliveryReport.setProductId(selectedOrder.getUuid());
        deliveryReport.setBuyerUuid(HakikishaPreference.getAccountUuidPrefs(GoodsReceiptActivity.this));
        deliveryReport.setDeliveryDate(arrivalDateTextView.getText().toString());
        deliveryReport.setDeliveryTime(arrivalTimeTextView.getText().toString());
        deliveryReport.setSellerName(sellerNameEditText.getText().toString());
        deliveryReport.setSellerPhone(sellerPhoneEditText.getText().toString());
        deliveryReport.setAttachmentUri("attachment-uri");
        deliveryReport.setStatus(status);
        deliveryReport.setLocation(deliveryLocationEditText.getText().toString());
        deliveryReport.setRating(ratingBar.getRating());

        switch (status) {
            case GOODS_REJECTED_FLAG:
                sendGoodsRejectSms();
                break;
            case GOODS_ACCEPTED_FLAG:
                sendGoodsAcceptedSms();
                submitDataToFirebase(GOODS_ACCEPTED_FLAG);
                break;
            default:
                break;
        }

    }

    private void submitDataToFirebase(String status) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");
        Task task = databaseReference
                .child("goods_report")
                .child(status)
                .child(deliveryReport.getUuid())
                .setValue(deliveryReport);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GoodsReceiptActivity.this, "Delivery Report Sent Successfully...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(GoodsReceiptActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GoodsReceiptActivity.this, "Delivery Failed, Please try again later...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showReleaseFundsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GoodsReceiptActivity.this);
        dialogBuilder.setMessage("Please confirm funds release to seller.");
        dialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Funds will be released to seller", Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        dialogBuilder.create().show();
    }

    private void sendGoodsAcceptedSms() {
        if (ContextCompat.checkSelfPermission(GoodsReceiptActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(GoodsReceiptActivity.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_ACCEPT_PERMISSION_CODE);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sellerPhoneEditText.getText().toString(), null, composeSuccessSmsToSeller(), null, null);
            smsManager.sendTextMessage(Constants.HAKIKISHA_SERVICE_PHONE, null, "[Transaction Code]\nBuyer [Buyer Id] [Buyer Name] received and accepted goods/service delivered by seller [seller Phone] [seller Name], valued at [Cost] on [CURENT DATE & TIME]", null, null);
        }
    }

    private void sendGoodsRejectSms() {
        if (ContextCompat.checkSelfPermission(GoodsReceiptActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(GoodsReceiptActivity.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REJECT_PERMISSION_CODE);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sellerPhoneEditText.getText().toString(), null, composeFailureSmsToSeller("Buyer Joel"), null, null);
        }

        submitDataToFirebase(GOODS_REJECTED_FLAG);

    }

    private String composeSuccessSmsToSeller() {
        String message = String.format(Locale.ENGLISH, "%s \nDear Seller, your customer has received and accepted the goods/service you have provided. You will receive payments soon.",
                HakikishaUtils.generateMpesaStyleAlphanumeric());
        return message;
    }

    private String composeFailureSmsToSeller(String buyerName) {
        return String.format(Locale.ENGLISH, "Dear Seller, Goods delivered to your customer %s , has been rejected by the customer, the said goods will be delivered back to you.", buyerName);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SEND_SMS_REJECT_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendGoodsRejectSms();
        }

        if (requestCode == SEND_SMS_ACCEPT_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendGoodsAcceptedSms();
        }

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            launchCamera();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_IMAGE_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            cameraCaptureTextView.append(imageFile.getName() + " \n");
        }
    }
}
