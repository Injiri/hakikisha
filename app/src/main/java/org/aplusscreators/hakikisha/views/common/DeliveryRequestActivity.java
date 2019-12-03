package org.aplusscreators.hakikisha.views.common;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.aplusscreators.hakikisha.R;

public class DeliveryRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DeliveryRequestActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
