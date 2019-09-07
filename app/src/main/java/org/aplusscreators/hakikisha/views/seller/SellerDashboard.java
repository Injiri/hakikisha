package org.aplusscreators.hakikisha.views.seller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.OrdersAdapter;
import org.aplusscreators.hakikisha.fab.ABShape;
import org.aplusscreators.hakikisha.fab.ABTextUtil;
import org.aplusscreators.hakikisha.model.Order;

import java.util.ArrayList;
import java.util.List;

public class SellerDashboard extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    RecyclerView ordersRecyclerView;
    OrdersAdapter ordersAdapter;
    List<Order> ordersList = new ArrayList<>();
    Toolbar toolbar;
    View sellerNoDataLayout;
    View sellerDataView;

    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;
    private RapidFloatingActionLayout rfaLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        ordersRecyclerView = findViewById(R.id.orders_recycler_view);
        toolbar = findViewById(R.id.seller_dashboard_toolbar);
        sellerDataView = findViewById(R.id.seller_dashboad_data_view);
        sellerNoDataLayout = findViewById(R.id.buyer_dashboard_no_data_layout);

        rfaBtn = findViewById(R.id.activity_seller_dashboard_rfab);
        rfaLayout = findViewById(R.id.activity_seller_dashboard_rfal);

        ordersAdapter = new OrdersAdapter(SellerDashboard.this, ordersList, null);
        ordersRecyclerView.setAdapter(ordersAdapter);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(SellerDashboard.this, RecyclerView.VERTICAL, false));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }

        populateOrdersList();

        intializeExpandableFab();

        if (ordersList.isEmpty()){
            sellerNoDataLayout.setVisibility(View.VISIBLE);
            sellerDataView.setVisibility(View.GONE);
        }
    }

    private void intializeExpandableFab() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(SellerDashboard.this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);

        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>()
                .setLabel("Register Product")
                .setResId(R.drawable.ic_action_add_product_light)
                .setLabelColor(Color.BLACK)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelSizeSp(14)
                .setWrapper(0)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("New Order")
                .setResId(R.drawable.ic_action_order_light)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.WHITE)
                .setLabelSizeSp(14)
                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(SellerDashboard.this, 4)))
                .setWrapper(1)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(SellerDashboard.this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(SellerDashboard.this, 5));

        rfabHelper = new RapidFloatingActionHelper(
                SellerDashboard.this,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();
    }

    private void populateOrdersList() {
        Order order = new Order();
        //ordersList.add(order);
        ordersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        rfabHelper.toggleContent();
        switch (position) {
            case 0:
                Intent intent = new Intent(SellerDashboard.this,RegisterProductActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:
                Intent nextIntent = new Intent(SellerDashboard.this,RegisterOrderActivity.class);
                startActivity(nextIntent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        rfabHelper.toggleContent();
        switch (position) {
            case 0:
                Intent intent = new Intent(SellerDashboard.this,RegisterProductActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:
                Intent nextIntent = new Intent(SellerDashboard.this,RegisterOrderActivity.class);
                startActivity(nextIntent);
                finish();
                break;
            default:
                break;
        }
    }
}
