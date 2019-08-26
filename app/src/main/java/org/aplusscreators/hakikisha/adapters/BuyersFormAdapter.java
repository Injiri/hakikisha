package org.aplusscreators.hakikisha.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Buyer;

import java.text.SimpleDateFormat;
import java.util.List;

public class BuyersFormAdapter extends RecyclerView.Adapter<BuyersFormAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Buyer> buyerList;
    OnBuyerClickedListener onBuyerClickedListener;

    public BuyersFormAdapter(AppCompatActivity context, List<Buyer> buyerList, OnBuyerClickedListener onOrdersClickedListener) {
        this.context = context;
        this.buyerList = buyerList;
        this.onBuyerClickedListener = onOrdersClickedListener;
    }

    @NonNull
    @Override
    public BuyersFormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buyers_form_layout, parent, false);
        return new BuyersFormAdapter.ViewHolder(view, onBuyerClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyersFormAdapter.ViewHolder holder, int position) {
        BuyersFormAdapter.ViewHolder viewHolder = (BuyersFormAdapter.ViewHolder) holder;
        final Buyer buyer = buyerList.get(viewHolder.getAdapterPosition());
        viewHolder.buyerNameTextView.setText(String.format("%s %s",buyer.getFirstName(),buyer.getLastName()));
    }

    @Override
    public int getItemCount() {
        return buyerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView buyerNameTextView;
        OnBuyerClickedListener onBuyerClickedListener;

        public ViewHolder(@NonNull View itemView, OnBuyerClickedListener onBuyerClickedListener) {
            super(itemView);

            this.onBuyerClickedListener = onBuyerClickedListener;
            this.buyerNameTextView = itemView.findViewById(R.id.item_buyers_form_buyer_name_text_view);
        }

        @Override
        public void onClick(View v) {
            onBuyerClickedListener.onTaskClicked(getAdapterPosition());
        }
    }

    public interface OnBuyerClickedListener {

        public void onTaskClicked(int position);
    }
}
