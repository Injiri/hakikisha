package org.aplusscreators.hakikisha.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PendingOrdersAdapter extends RecyclerView.Adapter<PendingOrdersAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Order> orderList;
    OnOrderClickedListener onOrderClickedListener;

    public PendingOrdersAdapter(AppCompatActivity context, List<Order> orderList, OnOrderClickedListener onOrderClickedListener) {
        this.context = context;
        this.orderList = orderList;
        this.onOrderClickedListener = onOrderClickedListener;
    }

    @NonNull
    @Override
    public PendingOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_orders_layout, parent, false);
        return new ViewHolder(view, onOrderClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrdersAdapter.ViewHolder holder, int position) {
        PendingOrdersAdapter.ViewHolder viewHolder = (ViewHolder) holder;
        final Order order = orderList.get(viewHolder.getAdapterPosition());
        if (order == null)return;
        viewHolder.orderTitleTextView.setText(order.getOrderTitle());
        viewHolder.orderTransactionTextView.setText(String.format(Locale.getDefault(),"%d",order.getTransactionId()));
        viewHolder.orderImageView.setImageDrawable(context.getResources().getDrawable(order.getDrawableResourceId()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView orderTitleTextView;
        TextView orderTransactionTextView;
        ImageView orderImageView;

        OnOrderClickedListener onOrderClickedListener;

        public ViewHolder(@NonNull View itemView, OnOrderClickedListener onOrderClickedListener) {
            super(itemView);

            this.onOrderClickedListener = onOrderClickedListener;

            cardView = itemView.findViewById(R.id.item_orders_container_layout);
            orderTitleTextView = itemView.findViewById(R.id.item_order_title_text_view);
            orderTransactionTextView = itemView.findViewById(R.id.item_order_transaction_id_text_view);
            orderImageView = itemView.findViewById(R.id.item_order_transaction_image_view);

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
