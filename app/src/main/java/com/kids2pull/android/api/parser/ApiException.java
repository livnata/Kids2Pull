package com.kids2pull.android.api.parser;

public class ApiException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1362758917408869362L;
    private int _errorCode = 0;
    private Object _data = null;
    public static final int SIGNATURE_ERROR = 503;

    public ApiException(int errorCode, String message, Object data) {
        super(message);
        _errorCode = errorCode;
        _data = data;
    }

    public Object getData() {
        return _data;
    }

    public void setData(Object data) {
        _data = data;
    }

    public int getErrorCode() {
        return _errorCode;
    }

    public void setErrorCode(int errorCode) {
        _errorCode = errorCode;
    }

    public boolean isSignatureError() {
        return _errorCode == SIGNATURE_ERROR;
    }
}
