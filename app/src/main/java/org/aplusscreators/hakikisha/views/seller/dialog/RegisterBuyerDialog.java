package org.aplusscreators.hakikisha.views.seller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.aplusscreators.hakikisha.R;

public class RegisterBuyerDialog extends Dialog {

    private Context context;

    public RegisterBuyerDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register_buyer_details_form);
    }
}
