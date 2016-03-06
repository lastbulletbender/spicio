package com.tlongdev.spicio.module;

import android.app.Application;

import com.tlongdev.spicio.util.AndroidLogger;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 02. 25.
 */
@Module
public class SpicioAppModule {

    private Application mApplication;

    public SpicioAppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Logger provideLogger() {
        return new AndroidLogger();
    }
}
