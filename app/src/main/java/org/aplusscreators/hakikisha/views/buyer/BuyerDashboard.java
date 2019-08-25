package org.aplusscreators.hakikisha.views.buyer;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.PurchasesAdapter;
import org.aplusscreators.hakikisha.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class BuyerDashboard extends AppCompatActivity {

    RecyclerView purchasesRecyclerView;
    PurchasesAdapter purchasesAdapter;
    List<Purchase> purchaseList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        purchasesRecyclerView = findViewById(R.id.purchases_recycler_view);

        purchasesAdapter = new PurchasesAdapter(BuyerDashboard.this,purchaseList,null);
        purchasesRecyclerView.setAdapter(purchasesAdapter);
        purchasesRecyclerView.setLayoutManager(new LinearLayoutManager(BuyerDashboard.this, RecyclerView.VERTICAL, false));

    }

    private void populatePurchaseList(){
        Purchase purchase = new Purchase();
        purchaseList.add(purchase);
        purchasesAdapter.notifyDataSetChanged();
    }
}
