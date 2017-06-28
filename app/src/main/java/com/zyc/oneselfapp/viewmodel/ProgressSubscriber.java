package com.zyc.oneselfapp.viewmodel;

import android.content.Context;
import android.widget.Toast;

import com.zyc.oneselfapp.exception.ApiException;
import com.zyc.oneselfapp.view.ApplicationData;

import rx.Subscriber;

/**
 * Created by zyc on 2016/12/7.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;
    private boolean isShow;
    public ProgressSubscriber(Context context,boolean isShow) {
        this.context = context;
        this.isShow=isShow;
        mProgressDialogHandler = new ProgressDialogHandler(false, this.context, this);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null&&isShow) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null&&isShow) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        Toast.makeText(ApplicationData.globalContext, "³É¹¦", Toast.LENGTH_LONG).show();
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            Toast.makeText(ApplicationData.globalContext, "´íÎó" + apiException.getMsg() + apiException.getCode(), Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
