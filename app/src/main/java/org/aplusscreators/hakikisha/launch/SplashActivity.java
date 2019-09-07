package org.aplusscreators.hakikisha.launch;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.Constants;
import org.aplusscreators.hakikisha.views.buyer.BuyerDashboard;
import org.aplusscreators.hakikisha.views.seller.SellerDashboard;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(SplashActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(SplashActivity.this, BuyerDashboard.class);
        startActivity(intent);

//        String accountType = HakikishaPreference.getAccountTypePref(SplashActivity.this);
//        if (accountType == null) {
//            Intent intent = new Intent(SplashActivity.this, ActivityAccountType.class);
//            startActivity(intent);
//
//            return;
//        }
//
//        if (accountType.equalsIgnoreCase(Constants.CUSTOMER_ACCOUNT_TYPE)) {
//            Intent intent = new Intent(SplashActivity.this, BuyerDashboard.class);
//            startActivity(intent);
//        } else if (accountType.equalsIgnoreCase(Constants.SELLER_ACCOUNT_TYPE)) {
//            Intent intent = new Intent(SplashActivity.this, SellerDashboard.class);
//            startActivity(intent);
//        }

    }
}
