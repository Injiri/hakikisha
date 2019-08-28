package org.aplusscreators.hakikisha.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.utils.ColorTool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Purchase> purchaseList;
    OnTaskClickedListener onTaskClickedListener;

    public PurchasesAdapter(AppCompatActivity context, List<Purchase> purchaseList, OnTaskClickedListener onTaskClickedListener) {
        this.context = context;
        this.purchaseList = purchaseList;
        this.onTaskClickedListener = onTaskClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_layout, parent, false);
        return new ViewHolder(view, onTaskClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final Purchase purchase = purchaseList.get(viewHolder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView taskTimePeriodTextView;
        TextView taskDateTextView;
        TextView purchaseTextView;
        OnTaskClickedListener onTaskClickedListener;

        public ViewHolder(@NonNull View itemView, OnTaskClickedListener onTaskClickedListener) {
            super(itemView);

            this.onTaskClickedListener = onTaskClickedListener;

            purchaseTextView = itemView.findViewById(R.id.item_list_checkbox);
            cardView = itemView.findViewById(R.id.task_parent_card_view);
            taskDateTextView = itemView.findViewById(R.id.task_date_textView);
            taskTimePeriodTextView = itemView.findViewById(R.id.task_event_time);

            purchaseTextView.setOnClickListener(this);

            cardView.setCardBackgroundColor(ColorTool.getRandomDarkColor());

        }

        @Override
        public void onClick(View v) {
            onTaskClickedListener.onTaskClicked(getAdapterPosition(), purchaseTextView);
        }
    }

    public interface OnTaskClickedListener {

        public void onTaskClicked(int position, TextView checkBox);
    }
}
