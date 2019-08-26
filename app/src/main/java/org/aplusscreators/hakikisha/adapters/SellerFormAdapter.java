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
import org.aplusscreators.hakikisha.model.Seller;

import java.text.SimpleDateFormat;
import java.util.List;

public class SellerFormAdapter extends RecyclerView.Adapter<SellerFormAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Seller> sellerList;
    OnSellerClickedListener onSellerClickedListener;

    public SellerFormAdapter(AppCompatActivity context, List<Seller> sellerList, OnSellerClickedListener onSellerClickedListener) {
        this.context = context;
        this.sellerList = sellerList;
        this.onSellerClickedListener = onSellerClickedListener;
    }

    @NonNull
    @Override
    public SellerFormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_form_layout, parent, false);
        return new SellerFormAdapter.ViewHolder(view, onSellerClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerFormAdapter.ViewHolder holder, int position) {
        SellerFormAdapter.ViewHolder viewHolder = (SellerFormAdapter.ViewHolder) holder;
        final Seller seller = sellerList.get(viewHolder.getAdapterPosition());
        viewHolder.sellerNameTextView.setText(String.format("%s %s",seller.getFirstName(),seller.getLastName()));
    }

    @Override
    public int getItemCount() {
        return sellerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout parentLayout;
        TextView sellerNameTextView;
        OnSellerClickedListener onSellerClickedListener;

        public ViewHolder(@NonNull View itemView, OnSellerClickedListener onSellerClickedListener) {
            super(itemView);

            this.onSellerClickedListener = onSellerClickedListener;
            this.parentLayout = itemView.findViewById(R.id.item_seller_parent_layout);
            this.sellerNameTextView = itemView.findViewById(R.id.item_seller_names_text_view);

            this.parentLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSellerClickedListener.onSellerClicked(getAdapterPosition());
        }
    }

    public interface OnSellerClickedListener {

        public void onSellerClicked(int position);
    }
}
