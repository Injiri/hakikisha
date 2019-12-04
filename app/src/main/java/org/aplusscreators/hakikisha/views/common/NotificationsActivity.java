package org.aplusscreators.hakikisha.views.common;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import org.aplusscreators.hakikisha.R;

public class NotificationsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView notificationsRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_layout);

        initializeResources();
    }

    private void initializeResources() {
        this.toolbar = findViewById(R.id.notifications_activity_toolbar);
        this.notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        this.progressBar = findViewById(R.id.notifications_progress_bar);
    }
}
