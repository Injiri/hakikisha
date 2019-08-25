package org.aplusscreators.hakikisha.launch;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.aplusscreators.hakikisha.views.MainActivity;
import org.aplusscreators.hakikisha.views.common.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
