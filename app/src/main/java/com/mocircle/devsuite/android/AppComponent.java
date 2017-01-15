package com.mocircle.devsuite.android;

import com.mocircle.devsuite.android.data.DataModule;
import com.mocircle.devsuite.android.net.NetModule;
import com.mocircle.devsuite.android.service.AuthenticationService;
import com.mocircle.devsuite.android.service.MessageService;

import dagger.Component;

@Component(modules = {
        AppModule.class,
        NetModule.class,
        DataModule.class,
})
public interface AppComponent {

    void inject(AuthenticationService service);

    void inject(MessageService service);
}
