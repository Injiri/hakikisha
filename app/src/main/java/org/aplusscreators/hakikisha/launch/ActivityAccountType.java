package org.aplusscreators.hakikisha.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.Constants;
import org.aplusscreators.hakikisha.views.common.LoginActivity;

public class ActivityAccountType extends AppCompatActivity {

    private static final String BUYER_ACCOUNT_TYPE = "buyer_account";
    private static final String SELLER_ACCOUNT_TYPE = "seller_account";
    private static final String NO_ACCOUNT_TYPE = "no_account_type";

    Button buyOptionButton;
    Button sellOptionButton;
    View proceedView;
    String accountType = NO_ACCOUNT_TYPE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_select);

        buyOptionButton = findViewById(R.id.buy_option_button);
        sellOptionButton = findViewById(R.id.sell_option_button);
        proceedView = findViewById(R.id.proceed_option_view);

        proceedView.setVisibility(View.GONE);

        buyOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedView.setVisibility(View.VISIBLE);
                if (accountType.equalsIgnoreCase(BUYER_ACCOUNT_TYPE)) {
                    accountType = BUYER_ACCOUNT_TYPE;
                    buyOptionButton.setTextColor(getResources().getColor(android.R.color.white));
                    buyOptionButton.setBackground(getResources().getDrawable(R.drawable.deep_blue_medium_curved_background));
                    sellOptionButton.setTextColor(getResources().getColor(android.R.color.black));
                    sellOptionButton.setBackground(getResources().getDrawable(R.drawable.curved_light_gray_background));
                } else if (accountType.equalsIgnoreCase(SELLER_ACCOUNT_TYPE)){
                    accountType = BUYER_ACCOUNT_TYPE;
                    buyOptionButton.setTextColor(getResources().getColor(android.R.color.white));
                    buyOptionButton.setBackground(getResources().getDrawable(R.drawable.deep_blue_medium_curved_background));
                    sellOptionButton.setTextColor(getResources().getColor(android.R.color.black));
                    sellOptionButton.setBackground(getResources().getDrawable(R.drawable.curved_light_gray_background));
                } else if (accountType.equalsIgnoreCase(NO_ACCOUNT_TYPE)){
                    accountType = BUYER_ACCOUNT_TYPE;
                    buyOptionButton.setTextColor(getResources().getColor(android.R.color.white));
                    buyOptionButton.setBackground(getResources().getDrawable(R.drawable.deep_blue_medium_curved_background));
                    sellOptionButton.setTextColor(getResources().getColor(android.R.color.black));
                    sellOptionButton.setBackground(getResources().getDrawable(R.drawable.curved_light_gray_background));
                }
            }
        });

        sellOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedView.setVisibility(View.VISIBLE);
                if (accountType.equalsIgnoreCase(SELLER_ACCOUNT_TYPE)) {
                    accountType = SELLER_ACCOUNT_TYPE;
                    buyOptionButton.setTextColor(getResources().getColor(android.R.color.black));
                    buyOptionButton.setBackground(getResources().getDrawable(R.drawable.curved_light_gray_background));
                    sellOptionButton.setTextColor(getResources().getColor(android.R.color.white));
                    sellOptionButton.setBackground(getResources().getDrawable(R.drawable.deep_blue_medium_curved_background));
                } else if (accountType.equalsIgnoreCase(BUYER_ACCOUNT_TYPE)){
                    accountType = SELLER_ACCOUNT_TYPE;
                    buyOptionButton.setTextColor(getResources().getColor(android.R.color.black));
                    buyOptionButton.setBackground(getResources().getDrawable(R.drawable.curved_light_gray_background));
                    sellOptionButton.setTextColor(getResources().getColor(android.R.color.white));
                    sellOptionButton.setBackground(getResources().getDrawable(R.drawable.deep_blue_medium_curved_background));
                } else if (accountType.equalsIgnoreCase(NO_ACCOUNT_TYPE)){
                    accountType = SELLER_ACCOUNT_TYPE;
                    buyOptionButton.setTextColor(getResources().getColor(android.R.color.black));
                    buyOptionButton.setBackground(getResources().getDrawable(R.drawable.curved_light_gray_background));
                    sellOptionButton.setTextColor(getResources().getColor(android.R.color.white));
                    sellOptionButton.setBackground(getResources().getDrawable(R.drawable.deep_blue_medium_curved_background));
                }
            }
        });

        proceedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountType.equalsIgnoreCase(SELLER_ACCOUNT_TYPE)){
                    HakikishaPreference.setAccountTypePref(ActivityAccountType.this, Constants.SELLER_ACCOUNT_TYPE);
                } else if (accountType.equalsIgnoreCase(BUYER_ACCOUNT_TYPE)){
                    HakikishaPreference.setAccountTypePref(ActivityAccountType.this,Constants.BUYER_ACCOUNT_TYPE);
                }

                Intent intent = new Intent(ActivityAccountType.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
