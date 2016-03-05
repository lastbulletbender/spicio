package com.tlongdev.spicio.module;

import android.app.Application;
import android.content.ContentResolver;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@Module
public class StorageModule {

    @Provides
    ContentResolver provideContentResolver(Application application) {
        return application.getContentResolver();
    }
}