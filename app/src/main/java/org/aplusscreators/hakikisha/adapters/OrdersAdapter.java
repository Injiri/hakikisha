package org.aplusscreators.hakikisha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    Context context;
    List<Order> orderList;
    OrdersAdapter.OnOrderClickedListener onOrderClickedListener;

    public OrdersAdapter(Context context, List<Order> orderList, OrdersAdapter.OnOrderClickedListener onOrderClickedListener) {
        this.context = context;
        this.orderList = orderList;
        this.onOrderClickedListener = onOrderClickedListener;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_layout, parent, false);
        return new OrdersAdapter.ViewHolder(view, onOrderClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        OrdersAdapter.ViewHolder viewHolder = (OrdersAdapter.ViewHolder) holder;
        final Order order = orderList.get(viewHolder.getAdapterPosition());
        if (order == null)return;
        viewHolder.orderTitleTextView.setText(order.getOrderTitle());
        viewHolder.orderTransactionTextView.setText(String.format(Locale.getDefault(),"%d",order.getTransactionId()));
        viewHolder.orderImageView.setImageDrawable(context.getResources().getDrawable(order.getDrawableResourceId()));
        viewHolder.amountTextView.setText(String.format(Locale.getDefault(),"%d",order.getAmount()));
        viewHolder.orderDateTextView.setText(simpleDateFormat.format(order.getDate()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View container;
        TextView orderTitleTextView;
        TextView orderTransactionTextView;
        ImageView orderImageView;
        TextView amountTextView;
        TextView orderDateTextView;

        OrdersAdapter.OnOrderClickedListener onOrderClickedListener;

        public ViewHolder(@NonNull View itemView, OrdersAdapter.OnOrderClickedListener onOrderClickedListener) {
            super(itemView);

            this.onOrderClickedListener = onOrderClickedListener;

            container = itemView.findViewById(R.id.item_order_parent_container);
            orderTitleTextView = itemView.findViewById(R.id.item_order_title_text_view);
            orderTransactionTextView = itemView.findViewById(R.id.item_order_transaction_id_text_view);
            orderImageView = itemView.findViewById(R.id.order_item_image_view);
            amountTextView = itemView.findViewById(R.id.item_order_amount_text_view);
            orderDateTextView = itemView.findViewById(R.id.item_order_transaction_date_text_view);

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
