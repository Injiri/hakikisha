package org.aplusscreators.hakikisha.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.OrdersAdapter;
import org.aplusscreators.hakikisha.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExpenseFragment extends Fragment {

    private OrdersAdapter adapter;
    private RecyclerView orderRecyclerView;

    private List<Order> orderList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses_layout,container,false);

        initializeResources(view);
        fetchExpenseOrders();

        return view;
    }

    private void fetchExpenseOrders() {
        List<Order> orders = new ArrayList<>();
        Order olxOrder = new Order();
        olxOrder.setOrder_id(UUID.randomUUID().toString());
        olxOrder.setTransactionId(123456789123465L);
        olxOrder.setTitle("Purchase Jumia Kenya");
        olxOrder.setOrderDate(new Date());
        olxOrder.setAmount(11_445);
        olxOrder.setDrawableResourceId(R.drawable.jumia);

        orders.add(olxOrder);
        orders.add(olxOrder);
        orders.add(olxOrder);
        orders.add(olxOrder);

        orderList.addAll(orders);
        adapter.notifyDataSetChanged();

    }

    private void initializeResources(View view) {
        this.orderRecyclerView = view.findViewById(R.id.expenses_recycler_view);
        this.adapter = new OrdersAdapter(getActivity(), orderList, new OrdersAdapter.OnOrderClickedListener() {
            @Override
            public void onOrderClicked(int position) {

            }
        });

        this.orderRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        this.orderRecyclerView.setAdapter(adapter);
    }
}
