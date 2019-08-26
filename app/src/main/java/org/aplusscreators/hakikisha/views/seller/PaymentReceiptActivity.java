package org.aplusscreators.hakikisha.views.seller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.BuyersFormAdapter;
import org.aplusscreators.hakikisha.adapters.OrdersFormAdapter;
import org.aplusscreators.hakikisha.model.Buyer;
import org.aplusscreators.hakikisha.model.Order;
import org.aplusscreators.hakikisha.model.PaymentReport;
import org.aplusscreators.hakikisha.model.Seller;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class PaymentReceiptActivity extends AppCompatActivity implements OrdersFormAdapter.OnOrderClickedListener, BuyersFormAdapter.OnBuyerClickedListener {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    EditText amountReceivedEditText;
    RecyclerView ordersRecyclerView;
    TextView receivedDateTextView;
    TextView receivedTimeTextView;
    RecyclerView buyerRecyclerView;
    EditText commentEditText;
    AppCompatRatingBar ratingBar;
    ProgressBar progressBar;
    Button submitButton;
    PaymentReport paymentReport;
    OrdersFormAdapter ordersAdapter;
    BuyersFormAdapter buyersAdapter;
    View cancelView;
    List<Order> orderList = new ArrayList<>();
    List<Buyer> buyerList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_receipt_form);

        amountReceivedEditText = findViewById(R.id.payment_receipt_amount_received_edit_text);
        ordersRecyclerView = findViewById(R.id.payment_receved_order_id_recyclerview);
        receivedDateTextView = findViewById(R.id.payment_receved_date_edit_text);
        receivedTimeTextView = findViewById(R.id.payment_receved_time_edit_text);
        buyerRecyclerView = findViewById(R.id.payment_receved_buyer_recycler_view);
        commentEditText = findViewById(R.id.payment_receved_comments_edit_text);
        ratingBar = findViewById(R.id.payment_receved_rating_bar);
        progressBar = findViewById(R.id.payment_receipt_progress_bar);
        cancelView = findViewById(R.id.payment_receipt_cancel_button);
        submitButton = findViewById(R.id.payment_receved_submit_button);

        ordersAdapter = new OrdersFormAdapter(PaymentReceiptActivity.this,orderList,this);
        buyersAdapter = new BuyersFormAdapter(PaymentReceiptActivity.this, buyerList,this);

        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(PaymentReceiptActivity.this));
        buyerRecyclerView.setLayoutManager(new LinearLayoutManager(PaymentReceiptActivity.this));
        buyerRecyclerView.setAdapter(buyersAdapter);
        ordersRecyclerView.setAdapter(ordersAdapter);


        paymentReport = new PaymentReport();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                extractAndSubmitData();
            }
        });

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentReceiptActivity.this,SellerDashboard.class);
                startActivity(intent);
            }
        });

        String dateToday = simpleDateFormat.format(Calendar.getInstance().getTime());
        String timeAmPm = DateTimeUtils.getTimeAM_PM(Calendar.getInstance(),PaymentReceiptActivity.this);
        receivedDateTextView.setText(dateToday);
        receivedTimeTextView.setText(timeAmPm);

        receivedTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int minute = Calendar.getInstance().get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(PaymentReceiptActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        receivedTimeTextView.setText(DateTimeUtils.getTimeAM_PM(calendar,PaymentReceiptActivity.this));
                    }
                }, hour, minute, true);

                timePickerDialog.show();
            }
        });

        receivedDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PaymentReceiptActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        String date = simpleDateFormat.format(calendar.getTime());
                        receivedDateTextView.setText(date);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        populateBuyersList();

        populateOrdersRecyclerView();
    }

    private void populateOrdersRecyclerView(){
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setCustomer_uuid("uuid");
        order.setOrder_id("order-id-1232");

        orderList.add(order);
        orderList.add(order);
        orderList.add(order);
        orderList.add(order);

        ordersAdapter.notifyDataSetChanged();
    }

    private void populateBuyersList(){
        Buyer buyer = new Buyer();
        buyer.setUuid(UUID.randomUUID().toString());
        buyer.setFirstName("Michael");
        buyer.setLastName("Obunga");
        buyer.setEmail("munga@gmail.com");
        buyer.setRating(3.2f);
        buyer.setDob("dob");
        buyer.setAddress_1("address 1");
        buyer.setAddress_2("address 2");

        buyerList.add(buyer);
        buyerList.add(buyer);
        buyerList.add(buyer);

        buyersAdapter.notifyDataSetChanged();
    }

    private void extractAndSubmitData() {
        paymentReport.setReportUuid(UUID.randomUUID().toString());
        paymentReport.setAmount(amountReceivedEditText.getText().toString());
        paymentReport.setDateReceived(receivedDateTextView.getText().toString());
        paymentReport.setTimeReceived(receivedTimeTextView.getText().toString());
        paymentReport.setCustomerFirstName("Joel");
        paymentReport.setCustomerLastName("Munai");
        paymentReport.setBuyerUuid("buyer-uuid");
        paymentReport.setComments(commentEditText.getText().toString());
        paymentReport.setRating(ratingBar.getRating());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");
        Task task = databaseReference
                .child("sellers")
                .child(HakikishaPreference.getAccountUuidPrefs(PaymentReceiptActivity.this))
                .child("sales")
                .child(paymentReport.getReportUuid())
                .setValue(paymentReport);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PaymentReceiptActivity.this,"Payment Report Saved",Toast.LENGTH_LONG).show();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PaymentReceiptActivity.this,"Unable to save form, try again later...",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onOrderClicked(int position) {

    }

    @Override
    public void onBuyerClicked(int position) {

    }
}
