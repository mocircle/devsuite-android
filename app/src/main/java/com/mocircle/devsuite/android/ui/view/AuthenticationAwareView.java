package com.mocircle.devsuite.android.ui.view;

public interface AuthenticationAwareView {

    enum ViewType {
        UnknownView,
        UnauthenticatedView,
        AuthenticatedView
    }

    ViewType getCurrentViewType();

    void switchToUnauthenticatedView();

    void switchToAuthenticatedView();
}
