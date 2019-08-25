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
import org.aplusscreators.hakikisha.model.Order;
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.utils.ColorTool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Order> orderList;
    PurchasesAdapter.OnTaskClickedListener onTaskClickedListener;

    public OrdersAdapter(AppCompatActivity context, List<Order> orderList, PurchasesAdapter.OnTaskClickedListener onTaskClickedListener) {
        this.context = context;
        this.orderList = orderList;
        this.onTaskClickedListener = onTaskClickedListener;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_layout, parent, false);
        return new OrdersAdapter.ViewHolder(view, onTaskClickedListener);
    }

    private String getTodayAM_PM(){
        String today = "";

        String AM_PM = "";
        Calendar calendar = Calendar.getInstance();
        boolean isAm = calendar.get(Calendar.AM_PM) == Calendar.AM;
        boolean isPm = calendar.get(Calendar.AM_PM) == Calendar.PM;

        AM_PM = isAm ? "AM" : "PM";

        String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(calendar.get(Calendar.MINUTE));

        today = String.format(context.getResources().getConfiguration().locale,"%s:%s %s",hour,minute,AM_PM);

        return today;

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
        CheckBox checkBox;
        PurchasesAdapter.OnTaskClickedListener onTaskClickedListener;

        public ViewHolder(@NonNull View itemView, PurchasesAdapter.OnTaskClickedListener onTaskClickedListener) {
            super(itemView);

            this.onTaskClickedListener = onTaskClickedListener;

            checkBox = itemView.findViewById(R.id.item_list_checkbox);
            cardView = itemView.findViewById(R.id.task_parent_card_view);
            taskDateTextView = itemView.findViewById(R.id.task_date_textView);
            taskTimePeriodTextView = itemView.findViewById(R.id.task_event_time);

            checkBox.setOnClickListener(this);

            cardView.setCardBackgroundColor(ColorTool.getRandomDarkColor());

        }

        @Override
        public void onClick(View v) {
            onTaskClickedListener.onTaskClicked(getAdapterPosition(), checkBox);
        }
    }

    public interface OnTaskClickedListener {

        public void onTaskClicked(int position, CheckBox checkBox);
    }
}
