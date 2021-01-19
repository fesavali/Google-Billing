package com.felixsncodes.billing;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;

import java.util.List;

public abstract class IInAppBillingService implements IInterface {
    @Override
    public IBinder asBinder() {
        return null;
    }

    public abstract Bundle getBuyIntentToReplaceSkus(int i, String packageName, List<String> oldSkus, String sku, String itemType, String extraData);

    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder    {
        private static final java.lang.String DESCRIPTOR = "com.android.vending.billing.IInAppBillingService";
        /** Construct the stub at attach it to the interface. */
        public Stub()
        {
            this.attachInterface(asInterface(this), DESCRIPTOR);
        }
        /**
         * Cast an IBinder object into an com.android.vending.billing.IInAppBillingService interface,
         * generating a proxy if needed.
         * @return
         */
        public static Proxy asInterface(IBinder obj)
        {
            if ((obj==null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null) {
                return (Proxy) iin;
            }
            return new IInAppBillingService.Stub.Proxy(obj);
        }
        @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
        {
            switch (code)
            {
                case INTERFACE_TRANSACTION:
                {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_isBillingSupported:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    int _result = this.isBillingSupported(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                case TRANSACTION_getSkuDetails:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    android.os.Bundle _arg3;
                    if ((0!=data.readInt())) {
                        _arg3 = android.os.Bundle.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg3 = null;
                    }
                    android.os.Bundle _result = this.getSkuDetails(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    if ((_result!=null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_getBuyIntent:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    java.lang.String _arg3;
                    _arg3 = data.readString();
                    java.lang.String _arg4;
                    _arg4 = data.readString();
                    android.os.Bundle _result = this.getBuyIntent(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    if ((_result!=null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_getPurchases:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    java.lang.String _arg3;
                    _arg3 = data.readString();
                    android.os.Bundle _result = this.getPurchases(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    if ((_result!=null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_consumePurchase:
                {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    int _result = this.consumePurchase(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        protected abstract Bundle getSkuDetails(int arg0, String arg1, String arg2, Bundle arg3);

        protected abstract Bundle getBuyIntent(int arg0, String arg1, String arg2, String arg3, String arg4);

        protected abstract Bundle getPurchases(int arg0, String arg1, String arg2, String arg3);

        protected abstract int consumePurchase(int arg0, String arg1, String arg2);

        private int isBillingSupported(int arg0, String arg1, String arg2) {
            return 0;
        }

        private static class Proxy extends IInAppBillingService {
            private android.os.IBinder mRemote;
            Proxy(android.os.IBinder remote)
            {
                mRemote = remote;
            }
            @Override public android.os.IBinder asBinder()
            {
                return mRemote;
            }

            @Override
            public Bundle getBuyIntentToReplaceSkus(int i, String packageName, List<String> oldSkus, String sku, String itemType, String extraData) {
                return null;
            }

            public java.lang.String getInterfaceDescriptor()
            {
                return DESCRIPTOR;
            }
            /**
             * Checks support for the requested billing API version, package and in-app type.
             * Minimum API version supported by this interface is 3.
             * @param apiVersion the billing version which the app is using
             * @param packageName the package name of the calling app
             * @param type type of the in-app item being purchased "inapp" for one-time purchases
             *        and "subs" for subscription.
             * @return RESULT_OK(0) on success, corresponding result code on failures
             */
            @Override public int isBillingSupported(int apiVersion, java.lang.String packageName, java.lang.String type) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    mRemote.transact(Stub.TRANSACTION_isBillingSupported, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readInt();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * Provides details of a list of SKUs
             * Given a list of SKUs of a valid type in the skusBundle, this returns a bundle
             * with a list JSON strings containing the productId, price, title and description.
             * This API can be called with a maximum of 20 SKUs.
             * @param apiVersion billing API version that the Third-party is using
             * @param packageName the package name of the calling app
             * @param skusBundle bundle containing a StringArrayList of SKUs with key "ITEM_ID_LIST"
             * @return Bundle containing the following key-value pairs
             *         "RESPONSE_CODE" with int value, RESULT_OK(0) if success, other response codes on
             *              failure as listed above.
             *         "DETAILS_LIST" with a StringArrayList containing purchase information
             *              in JSON format similar to:
             *              '{ "productId" : "exampleSku", "type" : "inapp", "price" : "$5.00",
             *                 "title : "Example Title", "description" : "This is an example description" }'
             */
            @Override public android.os.Bundle getSkuDetails(int apiVersion, java.lang.String packageName, java.lang.String type, android.os.Bundle skusBundle) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    if ((skusBundle!=null)) {
                        _data.writeInt(1);
                        skusBundle.writeToParcel(_data, 0);
                    }
                    else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(Stub.TRANSACTION_getSkuDetails, _data, _reply, 0);
                    _reply.readException();
                    if ((0!=_reply.readInt())) {
                        _result = android.os.Bundle.CREATOR.createFromParcel(_reply);
                    }
                    else {
                        _result = null;
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * Returns a pending intent to launch the purchase flow for an in-app item by providing a SKU,
             * the type, a unique purchase token and an optional developer payload.
             * @param apiVersion billing API version that the app is using
             * @param packageName package name of the calling app
             * @param sku the SKU of the in-app item as published in the developer console
             * @param type the type of the in-app item ("inapp" for one-time purchases
             *        and "subs" for subscription).
             * @param developerPayload optional argument to be sent back with the purchase information
             * @return Bundle containing the following key-value pairs
             *         "RESPONSE_CODE" with int value, RESULT_OK(0) if success, other response codes on
             *              failure as listed above.
             *         "BUY_INTENT" - PendingIntent to start the purchase flow
             *
             * The Pending intent should be launched with startIntentSenderForResult. When purchase flow
             * has completed, the onActivityResult() will give a resultCode of OK or CANCELED.
             * If the purchase is successful, the result data will contain the following key-value pairs
             *         "RESPONSE_CODE" with int value, RESULT_OK(0) if success, other response codes on
             *              failure as listed above.
             *         "INAPP_PURCHASE_DATA" - String in JSON format similar to
             *              '{"orderId":"12999763169054705758.1371079406387615",
             *                "packageName":"com.example.app",
             *                "productId":"exampleSku",
             *                "purchaseTime":1345678900000,
             *                "purchaseToken" : "122333444455555",
             *                "developerPayload":"example developer payload" }'
             *         "INAPP_DATA_SIGNATURE" - String containing the signature of the purchase data that
             *                                  was signed with the private key of the developer
             *                                  TODO: change this to app-specific keys.
             */
            @Override public android.os.Bundle getBuyIntent(int apiVersion, java.lang.String packageName, java.lang.String sku, java.lang.String type, java.lang.String developerPayload) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(sku);
                    _data.writeString(type);
                    _data.writeString(developerPayload);
                    mRemote.transact(Stub.TRANSACTION_getBuyIntent, _data, _reply, 0);
                    _reply.readException();
                    if ((0!=_reply.readInt())) {
                        _result = android.os.Bundle.CREATOR.createFromParcel(_reply);
                    }
                    else {
                        _result = null;
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * Returns the current SKUs owned by the user of the type and package name specified along with
             * purchase information and a signature of the data to be validated.
             * This will return all SKUs that have been purchased in V3 and managed items purchased using
             * V1 and V2 that have not been consumed.
             * @param apiVersion billing API version that the app is using
             * @param packageName package name of the calling app
             * @param type the type of the in-app items being requested
             *        ("inapp" for one-time purchases and "subs" for subscription).
             * @param continuationToken to be set as null for the first call, if the number of owned
             *        skus are too many, a continuationToken is returned in the response bundle.
             *        This method can be called again with the continuation token to get the next set of
             *        owned skus.
             * @return Bundle containing the following key-value pairs
             *         "RESPONSE_CODE" with int value, RESULT_OK(0) if success, other response codes on
             *              failure as listed above.
             *         "INAPP_PURCHASE_ITEM_LIST" - StringArrayList containing the list of SKUs
             *         "INAPP_PURCHASE_DATA_LIST" - StringArrayList containing the purchase information
             *         "INAPP_DATA_SIGNATURE_LIST"- StringArrayList containing the signatures
             *                                      of the purchase information
             *         "INAPP_CONTINUATION_TOKEN" - String containing a continuation token for the
             *                                      next set of in-app purchases. Only set if the
             *                                      user has more owned skus than the current list.
             */
            @Override public android.os.Bundle getPurchases(int apiVersion, java.lang.String packageName, java.lang.String type, java.lang.String continuationToken) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    _data.writeString(continuationToken);
                    mRemote.transact(Stub.TRANSACTION_getPurchases, _data, _reply, 0);
                    _reply.readException();
                    if ((0!=_reply.readInt())) {
                        _result = android.os.Bundle.CREATOR.createFromParcel(_reply);
                    }
                    else {
                        _result = null;
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            /**
             * Consume the last purchase of the given SKU. This will result in this item being removed
             * from all subsequent responses to getPurchases() and allow re-purchase of this item.
             * @param apiVersion billing API version that the app is using
             * @param packageName package name of the calling app
             * @param purchaseToken token in the purchase information JSON that identifies the purchase
             *        to be consumed
             * @return 0 if consumption succeeded. Appropriate error values for failures.
             */
            @Override public int consumePurchase(int apiVersion, java.lang.String packageName, java.lang.String purchaseToken) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(purchaseToken);
                    mRemote.transact(Stub.TRANSACTION_consumePurchase, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readInt();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }
        static final int TRANSACTION_isBillingSupported = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_getSkuDetails = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_getBuyIntent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_getPurchases = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
        static final int TRANSACTION_consumePurchase = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    }
    /**
     * Checks support for the requested billing API version, package and in-app type.
     * Minimum API version supported by this interface is 3.
     * @param apiVersion the billing version which the app is using
     * @param packageName the package name of the calling app
     * @param type type of the in-app item being purchased "inapp" for one-time purchases
     *        and "subs" for subscription.
     * @return RESULT_OK(0) on success, corresponding result code on failures
     */
    public abstract int isBillingSupported(int apiVersion, java.lang.String packageName, java.lang.String type) throws android.os.RemoteException;
    /**
     * Provides details of a list of SKUs
     * Given a list of SKUs of a valid type in the skusBundle, this returns a bundle
     * with a list JSON strings containing the productId, price, title and description.
     * This API can be called with a maximum of 20 SKUs.
     * @param apiVersion billing API version that the Third-party is using
     * @param packageName the package name of the calling app
     * @param skusBundle bundle containing a StringArrayList of SKUs with key "ITEM_ID_LIST"
     * @return Bundle containing the following key-value pairs
     *         "RESPONSE_CODE" with int value, RESULT_OK(0) if success, other response codes on
     *              failure as listed above.
     *         "DETAILS_LIST" with a StringArrayList containing purchase information
     *              in JSON format similar to:
     *              '{ "productId" : "exampleSku", "type" : "inapp", "price" : "$5.00",
     *                 "title : "Example Title", "description" : "This is an example description" }'
     */
    public abstract android.os.Bundle getSkuDetails(int apiVersion, java.lang.String packageName, java.lang.String type, android.os.Bundle skusBundle) throws android.os.RemoteException;
    /**
     * Returns a pending intent to launch the purchase flow for an in-app item by providing a SKU,
     * the type, a unique purchase token and an optional developer payload.
     * @param apiVersion billing API version that the app is using
     * @param packageName package name of the calling app
     * @param sku the SKU of the in-app item as published in the developer console
     * @param type the type of the in-app item ("inapp" for one-time purchases
     *        and "subs" for subscription).
     * @param developerPayload optional argument to be sent back with the purchase information
     * @return Bundle containing the following key-value pairs
     *         "RESPONSE_CODE" with int value, RESULT_OK(0) if success, other response codes on
     *              failure as listed above.
     *         "BUY_INTENT" - PendingIntent to start the purchase flow
     *
     * The Pending intent should be launched with startIntentSenderForResult. When purchase flow
     * has completed, the onActivityResult() will give a resultCode of OK or CANCELED.
     * If the purchase is successful, the result data will contain the following key-value pairs
     *         "RESPONSE_CODE" with int value, RESULT_OK(0) if success, other response codes on
     *              failure as listed above.
     *         "INAPP_PURCHASE_DATA" - String in JSON format similar to
     *              '{"orderId":"12999763169054705758.1371079406387615",
     *                "packageName":"com.example.app",
     *                "productId":"exampleSku",
     *                "purchaseTime":1345678900000,
     *                "purchaseToken" : "122333444455555",
     *                "developerPayload":"example developer payload" }'
     *         "INAPP_DATA_SIGNATURE" - String containing the signature of the purchase data that
     *                                  was signed with the private key of the developer
     *                                  TODO: change this to app-specific keys.
     */
    public abstract android.os.Bundle getBuyIntent(int apiVersion, java.lang.String packageName, java.lang.String sku, java.lang.String type, java.lang.String developerPayload) throws android.os.RemoteException;
    /**
     * Returns the current SKUs owned by the user of the type and package name specified along with
     * purchase information and a signature of the data to be validated.
     * This will return all SKUs that have been purchased in V3 and managed items purchased using
     * V1 and V2 that have not been consumed.
     * @param apiVersion billing API version that the app is using
     * @param packageName package name of the calling app
     * @param type the type of the in-app items being requested
     *        ("inapp" for one-time purchases and "subs" for subscription).
     * @param continuationToken to be set as null for the first call, if the number of owned
     *        skus are too many, a continuationToken is returned in the response bundle.
     *        This method can be called again with the continuation token to get the next set of
     *        owned skus.
     * @return Bundle containing the following key-value pairs
     *         "RESPONSE_CODE" with int value, RESULT_OK(0) if success, other response codes on
     *              failure as listed above.
     *         "INAPP_PURCHASE_ITEM_LIST" - StringArrayList containing the list of SKUs
     *         "INAPP_PURCHASE_DATA_LIST" - StringArrayList containing the purchase information
     *         "INAPP_DATA_SIGNATURE_LIST"- StringArrayList containing the signatures
     *                                      of the purchase information
     *         "INAPP_CONTINUATION_TOKEN" - String containing a continuation token for the
     *                                      next set of in-app purchases. Only set if the
     *                                      user has more owned skus than the current list.
     */
    public abstract android.os.Bundle getPurchases(int apiVersion, java.lang.String packageName, java.lang.String type, java.lang.String continuationToken) throws android.os.RemoteException;
    /**
     * Consume the last purchase of the given SKU. This will result in this item being removed
     * from all subsequent responses to getPurchases() and allow re-purchase of this item.
     * @param apiVersion billing API version that the app is using
     * @param packageName package name of the calling app
     * @param purchaseToken token in the purchase information JSON that identifies the purchase
     *        to be consumed
     * @return 0 if consumption succeeded. Appropriate error values for failures.
     */
    public abstract int consumePurchase(int apiVersion, java.lang.String packageName, java.lang.String purchaseToken) throws android.os.RemoteException;
}

