package org.aplusscreators.hakikisha.views.common;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.NotificationsListAdapter;
import org.aplusscreators.hakikisha.model.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotificationsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView notificationsRecyclerView;
    private ProgressBar progressBar;
    private NotificationsListAdapter notificationsListAdapter;

    private List<Notification> notificationList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_layout);

        initializeResources();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchNotifications();
    }

    public void fetchNotifications(){
        Notification notification = new Notification();
        notification.setUuid(UUID.randomUUID().toString());
        notification.setTitle("Thank you for joining hakikisha, check your email inbox to verify your account");
        notification.setIcon_drawable(R.drawable.ic_action_notification_red);

        notificationList.add(notification);
        notificationsListAdapter.notifyDataSetChanged();
    }

    private void initializeResources() {
        this.toolbar = findViewById(R.id.notifications_activity_toolbar);
        this.notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        this.progressBar = findViewById(R.id.notifications_progress_bar);

        this.notificationsListAdapter = new NotificationsListAdapter(notificationList,NotificationsActivity.this);
        this.notificationsRecyclerView.setLayoutManager( new LinearLayoutManager(NotificationsActivity.this));
        this.notificationsRecyclerView.setAdapter(notificationsListAdapter);

    }
}
