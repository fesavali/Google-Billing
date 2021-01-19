package com.felixsncodes.billing;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.billingclient.api.Purchase;
import com.felixsncodes.billing.utils.IabHelper;
import com.felixsncodes.billing.utils.IabResult;
import com.felixsncodes.billing.utils.Inventory;
import com.felixsncodes.billing.utils.MyBroadcastReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.felixsncodes.billing.constants.SUBS_ONE;
import static com.felixsncodes.billing.constants.base64EncodedPublicKey;

public class MainActivity extends AppCompatActivity implements MyBroadcastReceiver.MyBroadcastListener,
        DialogInterface.OnClickListener {
    //init sub button
    public Button sub;

    // Debug tag, for logging
    static final String TAG = "MainActivity";

    // Does the user have an active subscription to the delaroy plan?
    boolean mSubscribed = false;

    // Will the subscription auto-renew?
    boolean mAutoRenewEnabled = false;

    // Tracks the currently owned subscription, and the options in the Manage dialog
    String mDelaroySku = "";
    String mFirstChoiceSku = "";

    // Used to select between subscribing on a monthly, three month, six month or yearly basis
    String mSelectedSubscriptionPeriod = "";

    // SKU for our subscription

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;


    // The helper object
    IabHelper mHelper;

    // Provides purchase notification while this app is running
    MyBroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You Need to Accept Billing",Toast.LENGTH_SHORT).show();
            }
        });

        sub = findViewById(R.id.sub_bt);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSubscription();
            }
        });

        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                mBroadcastReceiver = new MyBroadcastReceiver(MainActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(MyBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");


            // First find out which subscription is auto renewing
            Purchase premium_monthly = inventory.getPurchase(SUBS_ONE);

            if (premium_monthly != null && premium_monthly.isAutoRenewing()) {
                mDelaroySku = SUBS_ONE;
                mAutoRenewEnabled = true;
            } else {
                mDelaroySku = "";
                mAutoRenewEnabled = false;
            }

            // The user is subscribed if either subscription exists, even if neither is auto
            // renewing
            mSubscribed = (premium_monthly != null && verifyDeveloperPayload(premium_monthly));
            Log.d(TAG, "User " + (mSubscribed ? "HAS" : "DOES NOT HAVE")
                    + " infinite gas subscription.");

            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    private void initSubscription() {
        // "Subscribe to delaroy" button clicked. Explain to user, then start purchase
        // flow for subscription.
        if (!mHelper.subscriptionsSupported()) {
            complain("Subscriptions not supported on your device yet. Sorry!");
            return;
        }

        CharSequence[] options;
        if (!mSubscribed || !mAutoRenewEnabled) {
            // Both subscription options should be available
            options = new CharSequence[0];
            options[0] = getString(R.string.premium_subscription);

            mFirstChoiceSku = SUBS_ONE;
        } else {
            // This is the subscription upgrade/downgrade path, so only one option is valid
            options = new CharSequence[3];
            if (mDelaroySku.equals(SUBS_ONE)) {
                // Give the option to upgrade below
                mFirstChoiceSku = SUBS_ONE;
            }
            int titleResId;
            if (!mSubscribed) {
                titleResId = R.string.cancel;
            } else if (!mAutoRenewEnabled) {
                titleResId = R.string.cancel;
            } else {
                titleResId = R.string.cancel;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(titleResId)
                    .setSingleChoiceItems(options, 0 /* checkedItem */, this)
                    .setPositiveButton(R.string.stop, this)
                    .setNegativeButton(R.string.cancel, this);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int id) {
        if (id == 0 /* First choice item */) {
            mSelectedSubscriptionPeriod = mFirstChoiceSku;
        } else if (id == DialogInterface.BUTTON_POSITIVE /* continue button */) {

            String payload = "";

            if (TextUtils.isEmpty(mSelectedSubscriptionPeriod)) {
                // The user has not changed from the default selection
                mSelectedSubscriptionPeriod = mFirstChoiceSku;
            }

            List<String> oldSkus = null;
            if (!TextUtils.isEmpty(mDelaroySku)
                    && !mDelaroySku.equals(mSelectedSubscriptionPeriod)) {
                // The user currently has a valid subscription, any purchase action is going to
                // replace that subscription
                oldSkus = new ArrayList<String>();
                oldSkus.add(mDelaroySku);
            }

            setWaitScreen(true);
            try {
                mHelper.launchPurchaseFlow(this, mSelectedSubscriptionPeriod, IabHelper.ITEM_TYPE_SUBS,
                        oldSkus, RC_REQUEST, mPurchaseFinishedListener, payload);
            } catch (IabHelper.IabAsyncInProgressException e) {
                complain("Error launching purchase flow. Another async operation in progress.");
                setWaitScreen(false);
            }
            // Reset the dialog options
            mSelectedSubscriptionPeriod = "";
            mFirstChoiceSku = "";
        } else if (id != DialogInterface.BUTTON_NEGATIVE) {
            // There are only four buttons, this should not happen
            Log.e(TAG, "Unknown button clicked in subscription dialog: " + id);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();


        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, com.felixsncodes.billing.utils.Purchase info) {

            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + info);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(info)) {
                complain("Error purchasing. Authenticity verification failed.");
                setWaitScreen(false);
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (info.getSku().equals(SUBS_ONE)){
                // bought the subscription
                Log.d(TAG, "subscription purchased.");
                alert("Thank you for subscribing!");
                mSubscribed = true;
                mAutoRenewEnabled = info.isAutoRenewing();
                mDelaroySku = info.getSku();
                updateUi();
                setWaitScreen(false);
            }
        }
    };

    private boolean verifyDeveloperPayload(com.felixsncodes.billing.utils.Purchase info) {
        return false;
    }

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(com.felixsncodes.billing.utils.Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "End consumption flow.");
        }
    };

    @Override
    public void receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error querying inventory. Another async operation in progress.");
        }
    }
// We're being destroyed. It's important to dispose of the helper here!

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    // updates UI to reflect model
    public void updateUi() {
        if (mSubscribed) {
            Intent intent = new Intent(this, Premium.class);
            startActivity(intent);
            finish();
        } else {
            // The user does not subscription"
           Toast.makeText(this, "You Dont have an Active Subscription",Toast.LENGTH_SHORT).show();
        }


    }

    // Enables or disables the "please wait" screen.
    void setWaitScreen(boolean set) {

    }

    void complain(String message) {
        Log.e(TAG, "**** Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
}
