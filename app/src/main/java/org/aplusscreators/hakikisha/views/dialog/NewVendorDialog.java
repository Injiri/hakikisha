package org.aplusscreators.hakikisha.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.views.common.VendorFormActivity;

public class NewVendorDialog extends Dialog {

    private FloatingActionButton addVendorFab;
    private FloatingActionButton submitVendorFab;
    private Spinner vendorsSpinner;
    private ArrayAdapter<String> vendorsAdapter;
    private String[] vendors = {
            "Olx","Jumia","Alibaba"
    };
    
    public NewVendorDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_vendor_dialog);

        this.addVendorFab = findViewById(R.id.activity_new_vendor_fab);
        this.submitVendorFab = findViewById(R.id.submit_vendor_button);
        this.vendorsSpinner = findViewById(R.id.vendor_options_spinner);
        this.vendorsAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,vendors);
        this.vendorsSpinner.setAdapter(vendorsAdapter);

        this.addVendorFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VendorFormActivity.class);
                getContext().startActivity(intent);
            }
        });

        this.submitVendorFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getContext(),"Vendor added successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}
