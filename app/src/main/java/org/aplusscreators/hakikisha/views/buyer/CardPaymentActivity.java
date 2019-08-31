package org.aplusscreators.hakikisha.views.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import org.aplusscreators.hakikisha.R;
import org.aplusscreators.hakikisha.model.Purchase;
import org.aplusscreators.hakikisha.utils.Sound;

public class CardPaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    CardMultilineWidget cardMultilineWidget;
    Button submitButton;
    String purchaseDataSerialized;
    TextView cardErrorInfoTextView;
    Purchase purchaseData = new Purchase();
    Stripe stripe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info_layout);

        toolbar = findViewById(R.id.card_payment_toolbar);
        cardMultilineWidget = findViewById(R.id.card_widget);
        submitButton = findViewById(R.id.submit_card_payment_button);
        cardErrorInfoTextView = findViewById(R.id.card_info_error_text_view);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Payments");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        stripe = new Stripe(CardPaymentActivity.this,"publishableKey","stripAccountId");
        stripe.authenticateSetup(CardPaymentActivity.this,"client secret");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardPaymentActivity.this,BuyerDashboard.class);
                startActivity(intent);

                Toast.makeText(CardPaymentActivity.this,"Payment was successfull",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onCardSaved() {
        final Card cardToSave = cardMultilineWidget.getCard();
        if (cardToSave != null) {
            tokenizeCard(cardToSave);
        }
    }

    private void tokenizeCard(Card cardToSave) {
        final Card card = Card.create(cardToSave.getNumber(), cardToSave.getExpMonth(), cardToSave.getExpYear(), cardToSave.getCVC());
        if (!card.validateCard()) {
            // Do not continue token creation.
            cardErrorInfoTextView.setVisibility(View.VISIBLE);
            Sound.vibrateDevice(CardPaymentActivity.this);
        } else {
            stripe.createToken(
                    card,
                    new ApiResultCallback<Token>() {
                        public void onSuccess(@NonNull Token token) {
                            // send token ID to your server, you'll create a charge next
                        }

                        @Override
                        public void onError(@NonNull Exception e) {

                        }
                    }
            );
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent data = getIntent();
        purchaseDataSerialized = data.getStringExtra(RegisterPurchaseForm.PURCHASE_SERIALIZED_KEY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(CardPaymentActivity.this,MakePaymentActivity.class);
                intent.putExtra(RegisterPurchaseForm.PURCHASE_SERIALIZED_KEY,purchaseDataSerialized);
                startActivity(intent);
                break;
        }

        return true;
    }
}
