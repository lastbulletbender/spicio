package com.tlongdev.spicio;

import android.app.Application;

import com.tlongdev.spicio.component.DaggerNetworkComponent;
import com.tlongdev.spicio.component.DaggerStorageComponent;
import com.tlongdev.spicio.component.NetworkComponent;
import com.tlongdev.spicio.component.StorageComponent;
import com.tlongdev.spicio.module.NetworkModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.module.StorageModule;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public class SpicioApplication extends Application {

    private NetworkComponent mNetworkComponent;

    private StorageComponent mStorageComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        mNetworkComponent = DaggerNetworkComponent.builder()
                .spicioAppModule(new SpicioAppModule(this))
                .networkModule(new NetworkModule())
                .networkRepositoryModule(new NetworkRepositoryModule())
                .build();

        mStorageComponent = DaggerStorageComponent.builder()
                .spicioAppModule(new SpicioAppModule(this))
                .storageModule(new StorageModule())
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public StorageComponent getStorageComponent() {
        return mStorageComponent;
    }

    public void setStorageComponent(StorageComponent storageComponent) {
        this.mStorageComponent = storageComponent;
    }
}
