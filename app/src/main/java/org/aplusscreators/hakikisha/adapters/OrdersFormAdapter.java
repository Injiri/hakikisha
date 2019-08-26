package org.aplusscreators.hakikisha.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Order;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrdersFormAdapter extends RecyclerView.Adapter<OrdersFormAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Order> orderList;
    OnOrderClickedListener onOrderClickedListener;

    public OrdersFormAdapter(AppCompatActivity context, List<Order> orderList, OnOrderClickedListener onOrdersClickedListener) {
        this.context = context;
        this.orderList = orderList;
        this.onOrderClickedListener = onOrdersClickedListener;
    }

    @NonNull
    @Override
    public OrdersFormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_form_layout, parent, false);
        return new OrdersFormAdapter.ViewHolder(view, onOrderClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersFormAdapter.ViewHolder holder, int position) {
        OrdersFormAdapter.ViewHolder viewHolder = (OrdersFormAdapter.ViewHolder) holder;
        final Order order = orderList.get(viewHolder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnOrderClickedListener onOrderClickedListener;
        TextView productNameTextView;
        TextView orderDateTimeTextView;
        TextView buyerNamesTextView;
        LinearLayout parentView;

        public ViewHolder(@NonNull View itemView, OnOrderClickedListener onOrderClickedListener) {
            super(itemView);

            this.productNameTextView = itemView.findViewById(R.id.item_order_form_product_name_text_view);
            this.orderDateTimeTextView = itemView.findViewById(R.id.item_order_form_order_date_time);
            this.buyerNamesTextView = itemView.findViewById(R.id.item_order_form_buyer_names_text_view);
            this.parentView = itemView.findViewById(R.id.item_orders_form_parent_layout);


            this.onOrderClickedListener = onOrderClickedListener;

        }

        @Override
        public void onClick(View v) {
            onOrderClickedListener.onOrderClicked(getAdapterPosition());
        }
    }

    public interface OnOrderClickedListener {

        public void onOrderClicked(int position);
    }
}
