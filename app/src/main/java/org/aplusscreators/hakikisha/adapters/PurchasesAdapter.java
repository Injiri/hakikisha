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
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.utils.ColorTool;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Purchase> purchaseList;
    OnPurchaseClickedListener onPurchaseClickedListener;

    public PurchasesAdapter(AppCompatActivity context, List<Purchase> purchaseList, OnPurchaseClickedListener onPurchaseClickedListener) {
        this.context = context;
        this.purchaseList = purchaseList;
        this.onPurchaseClickedListener = onPurchaseClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_layout, parent, false);
        return new ViewHolder(view, onPurchaseClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final Purchase purchase = purchaseList.get(viewHolder.getAdapterPosition());
        viewHolder.productNameTextView.setText(purchase.getName());
        viewHolder.purchaseStatusTextView.setText(purchase.getStatus());
        viewHolder.costTextView.setText(String.format(Locale.ENGLISH,"Ksh. %.2f",purchase.getCost()));
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView productNameTextView;
        TextView costTextView;
        TextView purchaseStatusTextView;
        OnPurchaseClickedListener onPurchaseClickedListener;

        public ViewHolder(@NonNull View itemView, OnPurchaseClickedListener onPurchaseClickedListener) {
            super(itemView);

            this.onPurchaseClickedListener = onPurchaseClickedListener;

            purchaseStatusTextView = itemView.findViewById(R.id.item_purchase_status_text_view);
            cardView = itemView.findViewById(R.id.task_parent_card_view);
            costTextView = itemView.findViewById(R.id.item_purchase_cost_text_view);
            productNameTextView = itemView.findViewById(R.id.item_purchase_name_text_view);

            cardView.setOnClickListener(this);

            cardView.setCardBackgroundColor(ColorTool.getRandomDarkColor());

        }

        @Override
        public void onClick(View v) {
            onPurchaseClickedListener.onPurchaseClicked(getAdapterPosition());
        }
    }

    public interface OnPurchaseClickedListener {

        public void onPurchaseClicked(int position);
    }
}
