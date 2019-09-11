package org.aplusscreators.hakikisha.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Order;
import org.aplusscreators.hakikisha.utils.ColorTool;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Order> orderList;
    PurchasesAdapter.OnPurchaseClickedListener onPurchaseClickedListener;

    public OrdersAdapter(AppCompatActivity context, List<Order> orderList, PurchasesAdapter.OnPurchaseClickedListener onPurchaseClickedListener) {
        this.context = context;
        this.orderList = orderList;
        this.onPurchaseClickedListener = onPurchaseClickedListener;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_layout, parent, false);
        return new OrdersAdapter.ViewHolder(view, onPurchaseClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        OrdersAdapter.ViewHolder viewHolder = (OrdersAdapter.ViewHolder) holder;
        final Order order = orderList.get(viewHolder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView taskTimePeriodTextView;
        TextView taskDateTextView;
        TextView checkBox;
        PurchasesAdapter.OnPurchaseClickedListener onPurchaseClickedListener;

        public ViewHolder(@NonNull View itemView, PurchasesAdapter.OnPurchaseClickedListener onPurchaseClickedListener) {
            super(itemView);

            this.onPurchaseClickedListener = onPurchaseClickedListener;

            checkBox = itemView.findViewById(R.id.item_purchase_name_text_view);
            cardView = itemView.findViewById(R.id.task_parent_card_view);
            taskDateTextView = itemView.findViewById(R.id.item_purchase_status_text_view);
            taskTimePeriodTextView = itemView.findViewById(R.id.item_purchase_cost_text_view);

            checkBox.setOnClickListener(this);

            cardView.setCardBackgroundColor(ColorTool.getRandomDarkColor());

        }

        @Override
        public void onClick(View v) {
            onPurchaseClickedListener.onPurchaseClicked(getAdapterPosition());
        }
    }

    public interface OnTaskClickedListener {

        public void onTaskClicked(int position, TextView checkBox);
    }
}
