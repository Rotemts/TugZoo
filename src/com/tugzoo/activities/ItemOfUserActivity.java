package com.tugzoo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fedorvlasov.lazylist.ImageLoader;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tugzoo.DataHolder;
import com.tugzoo.R;
import com.tugzoo.types.Item;
import com.tugzoo.types.ItemOfUser;

import org.json.JSONException;

import java.math.BigDecimal;

/**
 * Created by Rotem on 25/07/13.
 */
public class ItemOfUserActivity extends BaseActivity {

    private static final String EMAIL_THAT_GET_PAYED = "maxim.battlehack-facilitator@vekslers.org";
    private static final String TUGZOO_USER_ID_OF_BUYER = "some_tugzoo_user";
    public static final String PAYPAL_CLIENT_ID = "AUDfVxDM2djpKnnPzEuNX9xJIV-KuN3OvNbW4LS5RGJH1ThNgsTn_Kxlwbeu";

    private Item parentItem;
    private ItemOfUser itemOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_of_user);

        itemOfUser = getItemOfUserById(getIntent().getExtras().getString("item_id"));
        setItemData();
    }

    private void setItemData() {
        parentItem = getParentItemById(itemOfUser.getParentItemId());

        ImageLoader imageLoader = new ImageLoader(this);
        imageLoader.displayImage(itemOfUser.getImgUrl(), ((ImageView)findViewById(R.id.item_img)));
        imageLoader.displayImage(itemOfUser.getUserImgUrl(), ((ImageView)findViewById(R.id.user_img)));
        ((TextView)findViewById(R.id.item_desc)).setText(itemOfUser.getDesc());
        ((TextView)findViewById(R.id.parent_item_name)).setText(parentItem.getName() + "");
        ((TextView)findViewById(R.id.user_name)).setText(itemOfUser.getUserName());
        ((TextView)findViewById(R.id.rent_btn)).setText("Borrow for $" + parentItem.getPrice() + "/day");

        findViewById(R.id.rent_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyPressed();
            }
        });

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID) {
            Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
        }
    }

    private void onBuyPressed() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(parentItem.getPrice()), "USD", parentItem.getName());

        Intent intent = new Intent(this, PaymentActivity.class);

        // comment this line out for live or set to PaymentActivity.ENVIRONMENT_SANDBOX for sandbox
        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, PaymentActivity.ENVIRONMENT_SANDBOX);

        // it's important to repeat the clientId here so that the SDK has it if Android restarts your
        // app midway through the payment UI flow.
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, PAYPAL_CLIENT_ID);

        // Provide a payerId that uniquely identifies a user within the scope of your system,
        // such as an email address or user ID.
        intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, TUGZOO_USER_ID_OF_BUYER);

        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, EMAIL_THAT_GET_PAYED);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }

    private ItemOfUser getItemOfUserById(String id) {
        return DataHolder.itemsOfUsers.get(id);
    }

    private Item getParentItemById(int id) {
        return DataHolder.parentItems.get(id);
    }
}
