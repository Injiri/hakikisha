package org.aplusscreators.hakikisha.views.buyer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import org.aplusscreators.hakikisha.model.BadGoodsReport;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class GoodsRejectActivity extends AppCompatActivity {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");
    Spinner orderIdSpinner;
    Spinner rejectGoodsReasonSpinner;
    TextView deliveryDateEditText;
    TextView deliveryTimeEditText;
    Spinner selectSellerSpinner;
    EditText addAttachmentEditText;
    AppCompatRatingBar rateServiceBar;
    EditText notesEditText;
    ProgressBar progressBar;
    Button submitButton;
    BadGoodsReport goodsReport;
    String[] orderIds = {"222","111","333","444"};
    String[] sellerCompanyNames = {"Jumier Branch Ltd","Good Ware Lts","Woolworth Co."};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_reject_form);

        orderIdSpinner = findViewById(R.id.goods_reject_order_id_spinner);
        rejectGoodsReasonSpinner = findViewById(R.id.reject_goods_reason_spinner);
        deliveryDateEditText = findViewById(R.id.reject_goods_delivery_date_editText);
        deliveryTimeEditText = findViewById(R.id.reject_goods_delivery_time_editText);
        selectSellerSpinner = findViewById(R.id.reject_goods_select_seller_spinner);
        addAttachmentEditText = findViewById(R.id.reject_goods_add_attachment_view);
        rateServiceBar = findViewById(R.id.reject_goods_rating_bar);
        notesEditText = findViewById(R.id.reject_goods_more_note_edit_text);
        submitButton = findViewById(R.id.reject_goods_submit_button);
        progressBar = findViewById(R.id.submit_reject_goods_progress_bar);

        ArrayAdapter<String> orderIdsAdapter = new ArrayAdapter<String>(
                GoodsRejectActivity.this,android.R.layout.simple_spinner_dropdown_item,orderIds
        );

        ArrayAdapter<String> rejectReasonAdapter = new ArrayAdapter<String>(
                GoodsRejectActivity.this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.good_reject_reasons_arr)
        );

        ArrayAdapter<String> sellerSpinnerAdapter = new ArrayAdapter<String>(
                GoodsRejectActivity.this,android.R.layout.simple_spinner_dropdown_item,sellerCompanyNames
        );

        orderIdSpinner.setAdapter(orderIdsAdapter);
        rejectGoodsReasonSpinner.setAdapter(rejectReasonAdapter);
        selectSellerSpinner.setAdapter(sellerSpinnerAdapter);

        goodsReport = new BadGoodsReport();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                extractAndSubmitData();
            }
        });



        deliveryDateEditText.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));
        String timeAmPm = DateTimeUtils.getTimeAM_PM(Calendar.getInstance(),GoodsRejectActivity.this);
        deliveryTimeEditText.setText(timeAmPm);

        deliveryDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(GoodsRejectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCalendar = calendar.getInstance();
                        selectedCalendar.set(Calendar.YEAR,year);
                        selectedCalendar.set(Calendar.MONTH,month);
                        selectedCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        deliveryDateEditText.setText(simpleDateFormat.format(selectedCalendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        deliveryTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(GoodsRejectActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar selectCalendar = calendar.getInstance();
                        selectCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        selectCalendar.set(Calendar.MINUTE,minute);

                        String time = DateTimeUtils.getTimeAM_PM(selectCalendar,GoodsRejectActivity.this);
                        deliveryTimeEditText.setText(time);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
    }

    private void extractAndSubmitData() {
        goodsReport.setUuid(UUID.randomUUID().toString());
        goodsReport.setOrderId(orderIdSpinner.getSelectedItem().toString());
        goodsReport.setRejectReasone(rejectGoodsReasonSpinner.getSelectedItem().toString());
        goodsReport.setDeliveryDate(deliveryDateEditText.getText().toString());
        goodsReport.setDeliveryTime(deliveryTimeEditText.getText().toString());
        goodsReport.setSellerName(selectSellerSpinner.getSelectedItem().toString());
        goodsReport.setSellerUuid(UUID.randomUUID().toString());
        goodsReport.setAttachmentUri(addAttachmentEditText.getText().toString());
        goodsReport.setRating(rateServiceBar.getRating());
        goodsReport.setBuyerUuid(HakikishaPreference.getAccountUuidPrefs(GoodsRejectActivity.this));
        goodsReport.setComments(notesEditText.getText().toString());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha");
        Task task = databaseReference
                .child("goods_report")
                .child("rejects")
                .child(goodsReport.getUuid())
                .setValue(goodsReport);

        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GoodsRejectActivity.this,"Delivery Report Sent Successfully...",Toast.LENGTH_LONG).show();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GoodsRejectActivity.this,"Delivery Failed, Please try again later...",Toast.LENGTH_LONG).show();
            }
        });

    }
}
