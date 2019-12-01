package org.aplusscreators.hakikisha.launch;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.Constants;
import org.aplusscreators.hakikisha.views.common.DashboardActivity;
import org.aplusscreators.hakikisha.views.common.LoginActivity;
import org.aplusscreators.hakikisha.views.common.ProfileFormActivity;
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
        Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(intent);

//        boolean authenticated = HakikishaPreference.getUserAuthenticatedPref(SplashActivity.this);
//        if (!authenticated){
//            HakikishaPreference.setAccountTypePref(SplashActivity.this, Constants.BUYER_ACCOUNT_TYPE);
//            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//
//            return;
//        }
//
//
//        String accountType = HakikishaPreference.getAccountTypePref(SplashActivity.this);
//        if (accountType == null) {
//            HakikishaPreference.setAccountTypePref(SplashActivity.this, Constants.BUYER_ACCOUNT_TYPE);
//            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//
//            return;
//        }
//
//        if (accountType.equalsIgnoreCase(Constants.BUYER_ACCOUNT_TYPE)) {
//            Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
//            startActivity(intent);
//        } else if (accountType.equalsIgnoreCase(Constants.SELLER_ACCOUNT_TYPE)) {
//            Intent intent = new Intent(SplashActivity.this, SellerDashboard.class);
//            startActivity(intent);
//        }

    }
}
