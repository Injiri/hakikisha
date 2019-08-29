package org.aplusscreators.hakikisha.views.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

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
import org.aplusscreators.hakikisha.views.common.ExitFormDialog;

import java.util.UUID;

public class GoodsReceiptActivity extends AppCompatActivity {

    ImageView cancelButton;
    Button submitButton;
    TextView arrivalDateTextView;
    TextView arrivalTimeTextView;
    ImageView addSellerImageView;
    View attachmentsView;
    AppCompatRatingBar ratingBar;
    ProgressBar progressBar;
    RecyclerView ordersRecyclerView;
    RecyclerView sellerRecyclerView;

    DeliveryReport deliveryReport = new DeliveryReport();

    Order selectedOrder = new Order();
    Seller selectedSeller = new Seller();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_receipt_form);

        cancelButton = findViewById(R.id.receipt_product_receipt_exit_button);
        submitButton = findViewById(R.id.receipt_product_receipt_submit_button);
        arrivalDateTextView = findViewById(R.id.receipt_arrival_date);
        arrivalTimeTextView = findViewById(R.id.receipt_arrival_time);
        addSellerImageView = findViewById(R.id.receipt_add_seller_view);
        attachmentsView = findViewById(R.id.receipt_attachments);
        ratingBar = findViewById(R.id.receipt_ratings_bar);
        progressBar = findViewById(R.id.product_receipt_progress_bar);
        ordersRecyclerView = findViewById(R.id.receipt_order_recycler_view);
        sellerRecyclerView = findViewById(R.id.receipt_seller_recycler_view);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                extractAndSubmitData();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitFormDialog exitFormDialog = new ExitFormDialog(GoodsReceiptActivity.this,GoodsReceiptActivity.this,BuyerDashboard.class);
                exitFormDialog.show();
            }
        });
    }

    private void extractAndSubmitData() {
        progressBar.setVisibility(View.VISIBLE);
        deliveryReport.setUuid(UUID.randomUUID().toString());
        deliveryReport.setOrder_id(selectedOrder.getUuid());
        deliveryReport.setBuyerUuid(HakikishaPreference.getAccountUuidPrefs(GoodsReceiptActivity.this));
        deliveryReport.setSellerUuid(selectedSeller.getUuid());
        deliveryReport.setDeliveryDate(arrivalDateTextView.getText().toString());
        deliveryReport.setDeliveryTime(arrivalTimeTextView.getText().toString());
        deliveryReport.setAttachmentUri("attachment-uri");
        deliveryReport.setRating(ratingBar.getRating());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");
        Task task = databaseReference
                .child("goods_report")
                .child("receipts")
                .child(deliveryReport.getUuid())
                .setValue(deliveryReport);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GoodsReceiptActivity.this, "Delivery Report Sent Successfully...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(GoodsReceiptActivity.this,BuyerDashboard.class);
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
}
