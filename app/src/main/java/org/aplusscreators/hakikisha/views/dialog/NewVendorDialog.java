package org.aplusscreators.hakikisha.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aplusscreators.hakikisha.R;

public class NewVendorDialog extends Dialog {

    private FloatingActionButton addVendorFab;
    private FloatingActionButton submitNewVendorFab;
    private Spinner vendorsSpinner;

    public NewVendorDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_vendor_dialog);
    }
}
