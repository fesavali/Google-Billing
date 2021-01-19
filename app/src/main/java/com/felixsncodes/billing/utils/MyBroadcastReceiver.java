package com.felixsncodes.billing.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyBroadcastReceiver extends BroadcastReceiver {
    /**
     * Listener interface for received broadcast messages.
     */
    public interface MyBroadcastListener {
        void receivedBroadcast();
    }

    /**
     * The Intent action that this Receiver should filter for.
     */
    public static final String ACTION = "com.android.vending.billing.PURCHASES_UPDATED";

    private final MyBroadcastListener mListener;

    public MyBroadcastReceiver(MyBroadcastListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mListener != null) {
            mListener.receivedBroadcast();
        }
    }
}
