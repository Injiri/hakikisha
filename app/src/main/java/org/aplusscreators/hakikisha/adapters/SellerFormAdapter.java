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
    SellerFormAdapter.OnTaskClickedListener onTaskClickedListener;

    public SellerFormAdapter(AppCompatActivity context, List<Seller> sellerList, SellerFormAdapter.OnTaskClickedListener onTaskClickedListener) {
        this.context = context;
        this.sellerList = sellerList;
        this.onTaskClickedListener = onTaskClickedListener;
    }

    @NonNull
    @Override
    public SellerFormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_form_layout, parent, false);
        return new SellerFormAdapter.ViewHolder(view, onTaskClickedListener);
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
        SellerFormAdapter.OnTaskClickedListener onTaskClickedListener;

        public ViewHolder(@NonNull View itemView, SellerFormAdapter.OnTaskClickedListener onTaskClickedListener) {
            super(itemView);

            this.onTaskClickedListener = onTaskClickedListener;
            this.parentLayout = itemView.findViewById(R.id.item_seller_parent_layout);
            this.sellerNameTextView = itemView.findViewById(R.id.item_seller_names_text_view);

            this.parentLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTaskClickedListener.onTaskClicked(getAdapterPosition());
        }
    }

    public interface OnTaskClickedListener {

        public void onTaskClicked(int position);
    }
}
