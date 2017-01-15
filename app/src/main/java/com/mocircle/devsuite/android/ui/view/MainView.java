package com.mocircle.devsuite.android.ui.view;

import com.mocircle.devsuite.android.model.User;

public interface MainView extends BaseView, AuthenticationAwareView {

    void showSummaryUi();

    void showMessagesUi();

    void showSettingsUi();

    void setupNavigationHeader(User user);

}
