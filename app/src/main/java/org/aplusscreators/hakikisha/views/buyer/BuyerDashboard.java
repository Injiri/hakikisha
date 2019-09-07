package org.aplusscreators.hakikisha.views.buyer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.adapters.PurchasesAdapter;
import org.aplusscreators.hakikisha.fab.ABShape;
import org.aplusscreators.hakikisha.fab.ABTextUtil;
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.utils.HakikishaUtils;
import org.aplusscreators.hakikisha.views.seller.SellerDashboard;

import java.util.ArrayList;
import java.util.List;

public class BuyerDashboard extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    RecyclerView purchasesRecyclerView;
    PurchasesAdapter purchasesAdapter;
    List<Purchase> purchaseList = new ArrayList<>();
    Toolbar toolbar;

    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;
    private RapidFloatingActionLayout rfaLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        purchasesRecyclerView = findViewById(R.id.purchases_recycler_view);
        toolbar = findViewById(R.id.buyer_dashboard_toolbar);
        rfaBtn = findViewById(R.id.activity_buyer_dashboard_rfab);
        rfaLayout = findViewById(R.id.activity_buyer_dashboard_rfal);

        purchasesAdapter = new PurchasesAdapter(BuyerDashboard.this, purchaseList, null);
        purchasesRecyclerView.setAdapter(purchasesAdapter);
        purchasesRecyclerView.setLayoutManager(new LinearLayoutManager(BuyerDashboard.this, RecyclerView.VERTICAL, false));


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }

        populatePurchaseList();

        intializeExpandableFab();

        sendEmailSmsIfNeccessary();

        hakikishaPermissionsRequest();
    }

    private void hakikishaPermissionsRequest() {
        if (ContextCompat.checkSelfPermission(BuyerDashboard.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(BuyerDashboard.this,new String[]{Manifest.permission.SEND_SMS},1111);
        }
    }


    private void sendEmailSmsIfNeccessary() {
        Intent data = getIntent();
        String sellerPhone = data.getStringExtra("send_purchase_sms");
        String sellerEmail = data.getStringExtra("send_purchase_email");

        if (sellerPhone != null && !sellerPhone.isEmpty())
            HakikishaUtils.sendSms(BuyerDashboard.this, "Sms Message", sellerPhone);
        else if (sellerEmail != null && !sellerEmail.isEmpty())
            HakikishaUtils.sendEmail(BuyerDashboard.this, "from@gmail", sellerEmail, "PURCHASE EMAIL", "Purchase message...");

    }

    private void intializeExpandableFab() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(BuyerDashboard.this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);

        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>()
                .setLabel("Register Purchase")
                .setResId(R.drawable.ic_action_add_product_light)
                .setLabelColor(Color.BLACK)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelSizeSp(14)
                .setWrapper(0)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("Delivery")
                .setResId(R.drawable.ic_action_order_light)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.WHITE)
                .setLabelSizeSp(14)
                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(BuyerDashboard.this, 4)))
                .setWrapper(1)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(BuyerDashboard.this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(BuyerDashboard.this, 5));

        rfabHelper = new RapidFloatingActionHelper(
                BuyerDashboard.this,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();
    }

    private void populatePurchaseList() {
        Purchase purchase = new Purchase();
        purchaseList.add(purchase);
        purchasesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        //todo add system exit code here
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (position) {
            case 0:
                Intent launchPurchaseIntent = new Intent(BuyerDashboard.this, RegisterPurchaseForm.class);
                startActivity(launchPurchaseIntent);
                finish();
                break;
            case 1:
                Intent intent = new Intent(BuyerDashboard.this, GoodsReceiptActivity.class);
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
                Intent launchPurchaseIntent = new Intent(BuyerDashboard.this, RegisterPurchaseForm.class);
                startActivity(launchPurchaseIntent);
                finish();
                break;
            case 1:
                Intent intent = new Intent(BuyerDashboard.this, GoodsReceiptActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
