package com.kids2pull.android.loaders;


import com.kids2pull.android.api.parser.ApiException;

public class LoaderResponse {
    public static final int ERROR_CODE_OK = 0;
    public static final int ERROR_CODE_IO = 1;
    public static final int ERROR_CODE_PARSING = 2;
    public static final int ERROR_CODE_GENERAL = 3;
    public static final int SIGNATURE_ERROR = 503;

    private Object data;
    private int errorCode;
    private int httpCode;
    private ApiException throwable;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiException getThrowable() {
        return throwable;
    }

    public void setThrowable(ApiException throwable) {
        this.throwable = throwable;
    }

    public boolean isSignatureError() {
        return httpCode == SIGNATURE_ERROR;
    }
}
