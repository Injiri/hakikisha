package org.aplusscreators.hakikisha.views.common;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aplusscreators.hakikisha.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PaymentRequestActivity extends AppCompatActivity {

    private View closeActionView;
    private FloatingActionButton sendRequestPaymentFab;
    private View customerDetailsView;
    private TextView customerNameTextView;
    private TextView customerEmailAddressTextView;
    private CircleImageView customerImageView;
    private View orderNumberEntryView;
    private EditText orderNumberEditText;
    private View amountEntryFieldView;
    private EditText amountEntryEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_request);

        initializeResources();
    }

    private void initializeResources() {
        this.closeActionView = findViewById(R.id.activity_request_payment_close_action_view);
        this.sendRequestPaymentFab = findViewById(R.id.)
    }
}
