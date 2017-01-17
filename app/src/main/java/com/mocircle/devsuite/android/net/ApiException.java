package com.mocircle.devsuite.android.net;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mocircle.devsuite.android.logging.MyLog;
import com.mocircle.devsuite.android.net.model.ErrorResponse;

import java.io.IOException;

import okhttp3.ResponseBody;

public class ApiException extends Exception {

    private static final String TAG = "ApiException";
    private static Gson gson = new Gson();

    private IOException ioException;
    private int statusCode;
    private String statusMsg;
    private ErrorResponse errorObj;

    public ApiException() {
    }

    public ApiException(IOException ex) {
        this.ioException = ex;
    }

    public ApiException(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public ApiException(int statusCode, ResponseBody responseBody) {
        this.statusCode = statusCode;
        this.errorObj = getErrorObj(responseBody);
    }

    public ApiException(ErrorResponse resp) {
        if (resp != null) {
            this.errorObj = resp;
            this.statusCode = resp.httpCode;
            this.statusMsg = resp.httpMsg;
        }
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

    public ErrorResponse getErrorResponse() {
        return errorObj;
    }

    private ErrorResponse getErrorObj(ResponseBody body) {
        try {
            ErrorResponse response = gson.fromJson(body.string(), ErrorResponse.class);
            return response;
        } catch (JsonParseException e) {
            MyLog.w(TAG, e);
        } catch (IOException e) {
            MyLog.w(TAG, e);
        }
        return null;
    }

}
