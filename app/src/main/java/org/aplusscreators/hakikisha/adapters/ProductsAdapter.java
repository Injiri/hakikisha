package org.aplusscreators.hakikisha.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Order> orderList;
    ProductsAdapter.OnTaskClickedListener onTaskClickedListener;

    public ProductsAdapter(AppCompatActivity context, List<Order> orderList, ProductsAdapter.OnTaskClickedListener onOrdersClickedListener) {
        this.context = context;
        this.orderList = orderList;
        this.onTaskClickedListener = onOrdersClickedListener;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_form_layout, parent, false);
        return new ProductsAdapter.ViewHolder(view, onTaskClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        ProductsAdapter.ViewHolder viewHolder = (ProductsAdapter.ViewHolder) holder;
        final Order order = orderList.get(viewHolder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ProductsAdapter.OnTaskClickedListener onTaskClickedListener;
        LinearLayout productParentLayout;
        TextView productNameTextView;

        public ViewHolder(@NonNull View itemView, ProductsAdapter.OnTaskClickedListener onTaskClickedListener) {
            super(itemView);

            this.onTaskClickedListener = onTaskClickedListener;
            this.productParentLayout = itemView.findViewById(R.id.item_product_parent_layout);
            this.productNameTextView = itemView.findViewById(R.id.item_form_product_name_text_view);

            this.productParentLayout.setOnClickListener(this);

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
