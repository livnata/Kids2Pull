package com.kids2pull.android.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.kids2pull.android.loggers.LogTags;
import com.kids2pull.android.loggers.Logger;


public abstract class BaseAsyncTaskLoader extends AsyncTaskLoader<LoaderResponse> {

    LoaderResponse mResult;
    public static final int RESULT_OK = 0;
    public static final int INVALID_CONFIRMATION_CODE = 1;
    public static final int ROUMING_REDIRECT = 12;

    public static final int RESULT_FAIL = 500;
    public static final int RESULT_JSON_PARSE_ERROR = 501;
    public static final int RESULT_NO_NETWORK = 502;

    public static final int RESULT_OVER_QUERY_LIMIT = 666;

    private int overQueryRetry;

    public LoaderResponse checkOverQueryLimit(LoaderResponse response, String type) {
        if (overQueryRetry < 3
                && response != null
                && response.getThrowable() != null
                && response.getThrowable().getErrorCode() == RESULT_OVER_QUERY_LIMIT) {
            try {
                Logger.d(LogTags.NetworkTag, response.getThrowable().getMessage());
                overQueryRetry++;
                Thread.sleep(1000);
                return loadInBackground();
            } catch (Exception e) {
            }
        }

        return response;
    }

    public BaseAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public abstract LoaderResponse loadInBackground();

    @Override
    public void onCanceled(LoaderResponse data) {
        super.onCanceled(data);
        mResult = data;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Logger.d("BaseAsyncTaskLoader", "on start loading");
        if (mResult != null) {
            deliverResult(mResult);
        }

        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }

    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        Logger.d("BaseAsyncTaskLoader", "on Reset");
        stopLoading();
    }

    @Override
    public void deliverResult(LoaderResponse data) {
        Logger.d("BaseAsyncTaskLoader", "on Deliver called");
        if (isStarted()) {
            super.deliverResult(data);
            mResult = null;
        }
    }
}