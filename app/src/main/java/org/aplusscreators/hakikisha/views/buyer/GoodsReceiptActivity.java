package org.aplusscreators.hakikisha.views.buyer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import org.aplusscreators.hakikisha.utils.DateTimeUtils;
import org.aplusscreators.hakikisha.views.common.ExitFormDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class GoodsReceiptActivity extends AppCompatActivity {

    private static final String GOODS_REJECTED_FLAG = "goods_rejected";
    private static final String GOODS_ACCEPTED_FLAG = "goods_accepted";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    ImageView cancelButton;
    Button acceptButton;
    Button rejectButton;
    View arrivalDateView;
    View arrivalTimeView;
    EditText sellerPhoneEditText;
    EditText sellerNameEditText;
    TextView arrivalDateTextView;
    TextView arrivalTimeTextView;
    ImageView addSellerImageView;
    View addPhotoView;
    AppCompatRatingBar ratingBar;
    ProgressBar progressBar;

    DeliveryReport deliveryReport = new DeliveryReport();

    Order selectedOrder = new Order();
    Seller selectedSeller = new Seller();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_receipt_form);

        cancelButton = findViewById(R.id.receipt_product_receipt_exit_button);
        acceptButton = findViewById(R.id.receipt_product_receipt_accept_button);
        rejectButton = findViewById(R.id.receipt_product_receipt_reject_button);
        arrivalDateView = findViewById(R.id.receipt_arrival_date);
        arrivalTimeView = findViewById(R.id.receipt_arrival_time);
        arrivalTimeTextView = findViewById(R.id.receipt_arrival_time_text_view);
        arrivalDateTextView = findViewById(R.id.receipt_arrival_date_text_view);
        sellerPhoneEditText = findViewById(R.id.receipt_seller_phone_edit_text);
        sellerNameEditText = findViewById(R.id.receipt_seller_name_text_view);
        addSellerImageView = findViewById(R.id.receipt_add_seller_view);
        addPhotoView = findViewById(R.id.delivery_photo_image_view);
        ratingBar = findViewById(R.id.receipt_ratings_bar);
        progressBar = findViewById(R.id.product_receipt_progress_bar);


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
                extractAndSubmitData(GOODS_ACCEPTED_FLAG);
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
                extractAndSubmitData(GOODS_REJECTED_FLAG);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitFormDialog exitFormDialog = new ExitFormDialog(GoodsReceiptActivity.this, GoodsReceiptActivity.this, BuyerDashboard.class);
                exitFormDialog.show();
            }
        });
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
        deliveryReport.setRating(ratingBar.getRating());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");

        switch (status) {
            case GOODS_REJECTED_FLAG:
                sendGoodsRejectSms();
                break;
            case GOODS_ACCEPTED_FLAG:
                sendGoodsAcceptedSms();
                showReleaseFundsDialog();
                break;
            default:
                break;
        }

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
                Intent intent = new Intent(GoodsReceiptActivity.this, BuyerDashboard.class);
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
        dialogBuilder.setMessage("Please Confirm Fundds Release to Seller.");
        dialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogBuilder.create().show();
    }

    private void sendGoodsAcceptedSms() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(sellerPhoneEditText.getText().toString(),null,composeSuccessSmsToSeller(),null,null);
    }

    private void sendGoodsRejectSms() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(sellerPhoneEditText.getText().toString(),null,composeFailureSmsToSeller("Buyer Joel"),null,null);
    }

    private String composeSuccessSmsToSeller() {
        String message = String.format(Locale.ENGLISH, "Dear Seller, your customer has received and accepted the goods/service you have provided. You will receive payments soon.");
        return message;
    }

    private String composeFailureSmsToSeller(String buyerName) {
        return String.format(Locale.ENGLISH, "Dear Seller, Goods delivered to your customer %s , has been rejected by the customer, the said goods will be delivered back to you.", buyerName);
    }
}
