package org.aplusscreators.hakikisha.views.buyer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.aplusscreators.hakikisha.R;

public class ExitPurchaseFormDialog extends Dialog {

    View discardActionView;
    View keepEditingView;
    Context context;
    Class backStackActivity;
    AppCompatActivity activity;

    public ExitPurchaseFormDialog(@NonNull Context context,AppCompatActivity activity,Class backStackActivity) {
        super(context);
        this.context = context;
        this.backStackActivity = backStackActivity;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancel_purchase);

        keepEditingView = findViewById(R.id.keep_editiong_action_view);
        discardActionView = findViewById(R.id.cancel_purchase_action_view);

        discardActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, backStackActivity);
                context.startActivity(intent);
                activity.finish();
            }
        });

        keepEditingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
