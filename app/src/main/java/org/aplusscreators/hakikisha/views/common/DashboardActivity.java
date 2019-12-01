package org.aplusscreators.hakikisha.views.common;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.PendingOrdersAdapter;
import org.aplusscreators.hakikisha.adapters.viewpager.TransactionsViewPagerAdapter;
import org.aplusscreators.hakikisha.model.Order;
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.HakikishaUtils;
import org.aplusscreators.hakikisha.views.buyer.GoodsReceiptActivity;
import org.aplusscreators.hakikisha.views.buyer.RegisterPurchaseForm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    private static final String TAG = "DashboardActivity";

    private CircleImageView userProfileImageView;
    private TextView welcomeMessageTExtView;
    private FloatingActionButton actionsFab;
    private RecyclerView pendingTransactionsRecyclerView;
    private ViewPager transactionsViewPager;
    private TabLayout transactionsTabLayout;
    private View expandTransactionsView;
    private View bottomSheetsView;
    private View bottomSheetsFog;
    private View bottomSheetMakePaymentView;
    private View bottomSheetRequestPaymentView;
    private View bottomSheetsRequestDeliveryView;

    private PendingOrdersAdapter pendingOrdersAdapter;
    private TransactionsViewPagerAdapter transactionsViewPagerAdapter;
    private BottomSheetBehavior bottomSheetBehavior;

    private List<Order> orderList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_container_layout);

        initializeResources();
        fetchPendingOrders();

    }

    private void fetchPendingOrders() {
        List<Order> orders = new ArrayList<>();
        Order olxOrder = new Order();
        olxOrder.setOrder_id(UUID.randomUUID().toString());
        olxOrder.setTransactionId(123456789123465L);
        olxOrder.setTitle("Jumia Kenya");
        olxOrder.setDrawableResourceId(R.drawable.jumia);

        orders.add(olxOrder);
        orders.add(olxOrder);
        orders.add(olxOrder);
        orders.add(olxOrder);

        orderList.addAll(orders);
        pendingOrdersAdapter.notifyDataSetChanged();

    }

    private void initializeResources() {
        this.userProfileImageView = findViewById(R.id.dashboard_user_profile_image_view);
        this.welcomeMessageTExtView = findViewById(R.id.dashboard_user_welcome_text_view);
        this.actionsFab = findViewById(R.id.dashboard_actions_fab);
        this.pendingTransactionsRecyclerView = findViewById(R.id.pending_transactions_recycler_view);
        this.transactionsViewPager = findViewById(R.id.dashboard_transaction_view_page);
        this.transactionsTabLayout = findViewById(R.id.dashboard_transaction_tablayout_view);
        this.expandTransactionsView = findViewById(R.id.dashboard_expand_transactions_view);
        this.bottomSheetsView = findViewById(R.id.activity_dashboard_bottom_sheets);
        this.bottomSheetsFog = findViewById(R.id.dashboard_fog_view);
        this.bottomSheetMakePaymentView = findViewById(R.id.make_payment_action_view);
        this.bottomSheetRequestPaymentView = findViewById(R.id.request_payment_actions_view);
        this.bottomSheetsRequestDeliveryView = findViewById(R.id.request_delivery_action_view);

        this.pendingOrdersAdapter = new PendingOrdersAdapter(DashboardActivity.this, orderList, new PendingOrdersAdapter.OnOrderClickedListener() {
            @Override
            public void onOrderClicked(int position) {

            }
        });
        this.pendingTransactionsRecyclerView.setLayoutManager( new LinearLayoutManager(DashboardActivity.this,RecyclerView.HORIZONTAL,false));
        this.pendingTransactionsRecyclerView.setAdapter(pendingOrdersAdapter);

        this.transactionsViewPagerAdapter = new TransactionsViewPagerAdapter(getSupportFragmentManager());
        this.transactionsTabLayout.setupWithViewPager(transactionsViewPager);
        this.transactionsViewPager.setAdapter(transactionsViewPagerAdapter);

        this.expandTransactionsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,TransactionsExpandedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetsView);
        this.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        this.actionsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottomSheetsFog.setVisibility(View.VISIBLE);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    bottomSheetsFog.setVisibility(View.GONE);
                }
            }
        });

        this.bottomSheetsFog.setVisibility(View.GONE);
        this.bottomSheetsFog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetsFog.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        hakikishaPermissionsRequest();
        sendEmailSmsIfNeccessary();
        if (HakikishaPreference.getAccountUuidPrefs(DashboardActivity.this) == null)
            HakikishaPreference.setAccountUuidPrefs(DashboardActivity.this, UUID.randomUUID().toString());

    }

    private void hakikishaPermissionsRequest() {
        if (ContextCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1111);
        }
    }


    private void sendEmailSmsIfNeccessary() {

        PendingIntent pendingIntent = PendingIntent.getBroadcast(DashboardActivity.this,0,new Intent("Filter"),0);

        Intent data = getIntent();
        String sellerPhone = data.getStringExtra("send_purchase_sms");
        String sellerEmail = data.getStringExtra("send_purchase_email");

        if (sellerPhone != null && !sellerPhone.isEmpty())
            HakikishaUtils.sendSms(DashboardActivity.this, "Sms Message", sellerPhone,pendingIntent);
        else if (sellerEmail != null && !sellerEmail.isEmpty())
            HakikishaUtils.sendEmail(DashboardActivity.this, "from@gmail", sellerEmail, "PURCHASE EMAIL", "Purchase message...");

    }



    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (position) {
            case 0:
                Intent launchPurchaseIntent = new Intent(DashboardActivity.this, RegisterPurchaseForm.class);
                startActivity(launchPurchaseIntent);
                finish();
                break;
            case 1:
                Intent intent = new Intent(DashboardActivity.this, GoodsReceiptActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        switch (position) {
            case 0:
                Intent launchPurchaseIntent = new Intent(DashboardActivity.this, RegisterPurchaseForm.class);
                startActivity(launchPurchaseIntent);
                finish();
                break;
            case 1:
                Intent intent = new Intent(DashboardActivity.this, GoodsReceiptActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

}
