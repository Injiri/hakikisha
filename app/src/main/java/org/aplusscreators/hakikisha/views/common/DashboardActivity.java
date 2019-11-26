package org.aplusscreators.hakikisha.views.common;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import org.aplusscreators.hakikisha.settings.HakikishaPreference;
import org.aplusscreators.hakikisha.utils.HakikishaUtils;
import org.aplusscreators.hakikisha.views.buyer.GoodsReceiptActivity;
import org.aplusscreators.hakikisha.views.buyer.RegisterPurchaseForm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DashboardActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    private static final String TAG = "DashboardActivity";
    RecyclerView purchasesRecyclerView;
    PurchasesAdapter purchasesAdapter;
    List<Purchase> purchaseList = new ArrayList<>();
    Toolbar toolbar;
    View noDataView;
    View dataView;

    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;
    private RapidFloatingActionLayout rfaLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        purchasesRecyclerView = findViewById(R.id.purchases_recycler_view);
        toolbar = findViewById(R.id.buyer_dashboard_toolbar);
        dataView = findViewById(R.id.buyer_dashboard_data_view);
        rfaBtn = findViewById(R.id.activity_buyer_dashboard_rfab);
        rfaLayout = findViewById(R.id.activity_buyer_dashboard_rfal);
        noDataView = findViewById(R.id.buyer_dashboard_no_data_layout);

        purchasesAdapter = new PurchasesAdapter(DashboardActivity.this, purchaseList, new PurchasesAdapter.OnPurchaseClickedListener() {
            @Override
            public void onPurchaseClicked(int position) {
                //todo design response.
            }
        });

        purchasesRecyclerView.setAdapter(purchasesAdapter);
        purchasesRecyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, RecyclerView.VERTICAL, false));


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }

        intializeExpandableFab();

        sendEmailSmsIfNeccessary();

        hakikishaPermissionsRequest();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (HakikishaPreference.getAccountUuidPrefs(DashboardActivity.this) == null)
            HakikishaPreference.setAccountUuidPrefs(DashboardActivity.this, UUID.randomUUID().toString());

        downloadPurchaseDataIfPossible();
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

    private void intializeExpandableFab() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(DashboardActivity.this);
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
                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(DashboardActivity.this, 4)))
                .setWrapper(1)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(DashboardActivity.this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(DashboardActivity.this, 5));

        rfabHelper = new RapidFloatingActionHelper(
                DashboardActivity.this,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();
    }

    private void downloadPurchaseDataIfPossible() {
        if (HakikishaPreference.getAccountUuidPrefs(DashboardActivity.this) == null) return;

        purchaseList.clear();
        purchasesAdapter.notifyDataSetChanged();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hakikisha")
                .child("buyer")
                .child(HakikishaPreference.getAccountUuidPrefs(DashboardActivity.this))
                .child("purchases");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Purchase purchase = child.getValue(Purchase.class);
                    purchaseList.add(purchase);

                    Log.e(TAG, "onDataChange: Purchase" + purchase);
                }

                purchasesAdapter.notifyDataSetChanged();

                if (!purchaseList.isEmpty()) {
                    noDataView.setVisibility(View.GONE);
                    dataView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
