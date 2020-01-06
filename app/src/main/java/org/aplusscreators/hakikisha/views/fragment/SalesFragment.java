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
import java.util.List;

public class SalesFragment extends Fragment {

    private OrdersAdapter adapter;
    private RecyclerView recyclerView;
    private View noDataView;

    private List<Order> salesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_sales_layout,container,false);
        initializeResources(view);
        return view;
    }

    private void initializeResources(View view) {
        this.recyclerView = view.findViewById(R.id.sales_recycler_view);
        this.noDataView = view.findViewById(R.id.transaction_no_data_layout_view);
        this.adapter = new OrdersAdapter(getActivity(), salesList, new OrdersAdapter.OnOrderClickedListener() {
            @Override
            public void onOrderClicked(int position) {

            }
        });

        this.recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(adapter);
    }
}
