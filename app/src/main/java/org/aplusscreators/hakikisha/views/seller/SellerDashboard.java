package org.aplusscreators.hakikisha.views.seller;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.OrdersAdapter;
import org.aplusscreators.hakikisha.model.Order;

import java.util.ArrayList;
import java.util.List;

public class SellerDashboard extends AppCompatActivity {

    RecyclerView ordersRecyclerView;
    OrdersAdapter ordersAdapter;
    List<Order> ordersList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        ordersRecyclerView = findViewById(R.id.orders_recycler_view);

        ordersAdapter = new OrdersAdapter(SellerDashboard.this, ordersList,null);
        ordersRecyclerView.setAdapter(ordersAdapter);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(SellerDashboard.this, RecyclerView.VERTICAL, false));

        populateOrdersList();

    }

    private void populateOrdersList(){
        Order order = new Order();
        ordersList.add(order);
        ordersAdapter.notifyDataSetChanged();
    }
}
