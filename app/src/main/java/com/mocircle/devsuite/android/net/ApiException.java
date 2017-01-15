package com.mocircle.devsuite.android.net;

import com.mocircle.devsuite.android.net.model.ErrorResponse;

import java.io.IOException;

import okhttp3.ResponseBody;

public class ApiException extends Exception {

    private IOException ioException;
    private int statusCode;
    private String statusMsg;
    private ErrorResponse errorBody;

    public ApiException() {
    }

    public ApiException(IOException ex) {
        this.ioException = ex;
    }

    public ApiException(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public ApiException(int httpStatus, ResponseBody errorResponse) {
        this.statusCode = httpStatus;
        this.errorBody = getErrorBody(errorResponse);
    }

    public boolean isIOException() {
        return ioException != null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public ErrorResponse getErrorBody() {
        return errorBody;
    }

    private ErrorResponse getErrorBody(ResponseBody body) {
        throw new UnsupportedOperationException();
    }

}
