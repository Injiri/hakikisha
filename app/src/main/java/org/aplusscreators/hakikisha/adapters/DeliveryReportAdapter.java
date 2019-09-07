package org.aplusscreators.hakikisha.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.DeliveryReport;

import java.text.SimpleDateFormat;
import java.util.List;

public class DeliveryReportAdapter extends RecyclerView.Adapter<DeliveryReportAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<DeliveryReport> deliveryReports;
    OnReportClickedListener onTaskClickedListener;

    public DeliveryReportAdapter(AppCompatActivity context, List<DeliveryReport> deliveryReports, OnReportClickedListener onTaskClickedListener) {
        this.context = context;
        this.deliveryReports = deliveryReports;
        this.onTaskClickedListener = onTaskClickedListener;
    }

    @NonNull
    @Override
    public DeliveryReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery_report_form_layout, parent, false);
        return new DeliveryReportAdapter.ViewHolder(view, onTaskClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryReportAdapter.ViewHolder holder, int position) {
        DeliveryReportAdapter.ViewHolder viewHolder = (DeliveryReportAdapter.ViewHolder) holder;
        final DeliveryReport deliveryReport = deliveryReports.get(viewHolder.getAdapterPosition());
        viewHolder.deliveryStatusTextView.setText(deliveryReport.getReportType());
        viewHolder.deliveryStatusImageView.setImageResource(R.drawable.ic_action_delivery_accepted_light);
        viewHolder.deliveryOrderIdTextView.setText(deliveryReport.getProductId());
    }

    @Override
    public int getItemCount() {
        return deliveryReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnReportClickedListener onTaskClickedListener;
        LinearLayout parentLayout;
        TextView deliveryStatusTextView;
        TextView deliveryOrderIdTextView;
        ImageView deliveryStatusImageView;

        public ViewHolder(@NonNull View itemView, OnReportClickedListener onTaskClickedListener) {
            super(itemView);

            this.parentLayout = itemView.findViewById(R.id.item_delivery_report_parent_layout);
            this.deliveryStatusTextView = itemView.findViewById(R.id.item_delivery_report_status_text_view);
            this.deliveryOrderIdTextView = itemView.findViewById(R.id.item_delivery_report_order_id_text_view);
            this.deliveryStatusImageView = itemView.findViewById(R.id.item_delivery_report_image_view);

            this.onTaskClickedListener = onTaskClickedListener;
        }

        @Override
        public void onClick(View v) {
            onTaskClickedListener.onTaskClicked(getAdapterPosition());
        }
    }

    public interface OnReportClickedListener {

        public void onTaskClicked(int position);
    }
}
