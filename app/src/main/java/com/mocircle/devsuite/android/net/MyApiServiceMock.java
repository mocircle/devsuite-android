package com.mocircle.devsuite.android.net;

import com.mocircle.devsuite.android.model.Message;
import com.mocircle.devsuite.android.model.User;
import com.mocircle.devsuite.android.net.model.ErrorResponse;
import com.mocircle.devsuite.android.net.model.LoginResponse;

import java.util.ArrayList;
import java.util.List;

public class MyApiServiceMock implements MyApiService {

    @Override
    public LoginResponse login(String userName, String password) throws ApiException {
        delay();
        if (userName.equals("demo") && password.equals("demo")) {
            LoginResponse response = new LoginResponse();
            response.token = "xxx-token-xxx";
            response.userInfo = new User();
            response.userInfo.modelId = 1;
            response.userInfo.userName = "demo";
            response.userInfo.displayName = "Demo User";
            response.userInfo.email = "demo@email.com";
            return response;
        } else {
            ErrorResponse resp = new ErrorResponse();
            resp.httpCode = 401;
            resp.errMsg = "User name or password is incorrect";
            throw new ApiException(resp);
        }
    }

    @Override
    public void logout() throws ApiException {
        delay();
    }

    @Override
    public List<Message> getMessageList() throws ApiException {
        delay();
        List<Message> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Message item = new Message();
            item.modelId = i;
            item.title = "Message title " + i;
            item.body = "This ia msg body content " + i;
            result.add(item);
        }
        return result;
    }

    private void delay() {
        delay(2);
    }

    private void delay(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
