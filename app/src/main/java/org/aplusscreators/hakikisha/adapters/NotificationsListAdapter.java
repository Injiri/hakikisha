package org.aplusscreators.hakikisha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {

    private List<Notification> notificationList = new ArrayList<>();
    private Context context;

    public NotificationsListAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        Notification notification = notificationList.get(position);
        viewHolder.notificationsTextView.setText(notification.getTitle());
        viewHolder.notificationIconImageView.setImageDrawable(context.getResources().getDrawable(notification.getIcon_drawable()));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View parentCardView;
        ImageView notificationIconImageView;
        TextView notificationsTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.parentCardView = itemView.findViewById(R.id.notification_parent_card_view);
            this.notificationIconImageView = itemView.findViewById(R.id.notification_image_view);
            this.notificationsTextView = itemView.findViewById(R.id.item_notification_message_text_view);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnNotificationClickedListener {
        public void OnNotificationClicked(int position);
    }
}
