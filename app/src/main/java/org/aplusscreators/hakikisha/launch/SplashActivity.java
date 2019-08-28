package org.aplusscreators.hakikisha.launch;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.views.buyer.GoodsReceiptActivity;

import java.util.UUID;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(SplashActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //temp code
        HakikishaPreference.setAccountUuidPrefs(SplashActivity.this,UUID.randomUUID().toString());
        Intent intent = new Intent(SplashActivity.this, GoodsReceiptActivity.class);
        startActivity(intent);

//
//        String accountType = HakikishaPreference.getAccountTypePref(SplashActivity.this);
//        if (accountType == null) {
//            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//            startActivity(intent);
//
//            return;
//        }
//
//        if (accountType.equalsIgnoreCase(Constants.CUSTOMER_ACCOUNT_TYPE)){
//            Intent intent = new Intent(SplashActivity.this, BuyerDashboard.class);
//            startActivity(intent);
//        } else if (accountType.equalsIgnoreCase(Constants.SELLER_ACCOUNT_TYPE)){
//            Intent intent = new Intent(SplashActivity.this, SellerDashboard.class);
//            startActivity(intent);
//        }

    }
}
