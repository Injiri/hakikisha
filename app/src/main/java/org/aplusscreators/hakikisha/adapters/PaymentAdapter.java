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
import org.aplusscreators.hakikisha.model.Payment;

import java.text.SimpleDateFormat;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");

    AppCompatActivity context;
    List<Payment> paymentList;
    OnPaymentClickedListener onPaymentClickedListener;

    public PaymentAdapter(AppCompatActivity context, List<Payment> paymentList, OnPaymentClickedListener onOrdersClickedListener) {
        this.context = context;
        this.paymentList = paymentList;
        this.onPaymentClickedListener = onOrdersClickedListener;
    }

    @NonNull
    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_form_layout, parent, false);
        return new PaymentAdapter.ViewHolder(view, onPaymentClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.ViewHolder holder, int position) {
        PaymentAdapter.ViewHolder viewHolder = (PaymentAdapter.ViewHolder) holder;
        final Payment payment = paymentList.get(viewHolder.getAdapterPosition());
        viewHolder.amountPaidTextView.setText(payment.getAmount());
        viewHolder.orderIdTextView.setText(payment.getOrder_id());
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnPaymentClickedListener onPaymentClickedListener;
        LinearLayout parentLayout;
        TextView orderIdTextView;
        TextView amountPaidTextView;

        public ViewHolder(@NonNull View itemView, OnPaymentClickedListener onPaymentClickedListener) {
            super(itemView);

            this.onPaymentClickedListener = onPaymentClickedListener;

            this.parentLayout = itemView.findViewById(R.id.item_payment_parent_layout);
            this.orderIdTextView = itemView.findViewById(R.id.item_payment_order_id_text_view);
            this.amountPaidTextView = itemView.findViewById(R.id.item_payment_amount_textview);

            this.parentLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onPaymentClickedListener.onTaskClicked(getAdapterPosition());
        }
    }

    public interface OnPaymentClickedListener {

        public void onTaskClicked(int position);
    }
}
