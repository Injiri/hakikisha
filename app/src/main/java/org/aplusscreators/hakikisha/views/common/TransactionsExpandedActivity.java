package org.aplusscreators.hakikisha.views.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.viewpager.TransactionsViewPagerAdapter;

public class TransactionsExpandedActivity  extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    TransactionsViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_transactions_view);

        initializeResources();

    }

    private void initializeResources() {
        this.toolbar = findViewById(R.id.transactions_expanded_toolbar);
        this.viewPager = findViewById(R.id.expanded_transactions_view_pager);
        this.tabLayout = findViewById(R.id.expanded_transactions_tab_layout);

        this.viewPagerAdapter = new TransactionsViewPagerAdapter(getSupportFragmentManager());
        this.viewPager.setAdapter(viewPagerAdapter);
        this.tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(this.toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(TransactionsExpandedActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
