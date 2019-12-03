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

import java.text.SimpleDateFormat;

public class PendingOrderNoDataAdapter extends RecyclerView.Adapter<PendingOrderNoDataAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;

    public PendingOrderNoDataAdapter(AppCompatActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PendingOrderNoDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_orders_no_data_layout, parent, false);
        return new PendingOrderNoDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrderNoDataAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView orderTitleTextView;
        TextView orderTransactionTextView;
        TextView orderImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.item_orders_container_layout);
            orderTitleTextView = itemView.findViewById(R.id.item_order_title_text_view);
            orderTransactionTextView = itemView.findViewById(R.id.item_order_transaction_id_text_view);
            orderImageView = itemView.findViewById(R.id.item_no_order_transaction_image_view);

        }
    }
}
