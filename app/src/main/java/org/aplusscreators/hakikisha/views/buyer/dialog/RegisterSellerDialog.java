package org.aplusscreators.hakikisha.views.buyer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.aplusscreators.hakikisha.R;

public class RegisterSellerDialog extends Dialog {

    public RegisterSellerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register_seller_layout);
    }
}
