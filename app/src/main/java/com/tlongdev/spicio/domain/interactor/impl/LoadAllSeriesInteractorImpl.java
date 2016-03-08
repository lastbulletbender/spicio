package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.LoadAllSeriesInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
public class LoadAllSeriesInteractorImpl extends AbstractInteractor implements LoadAllSeriesInteractor {

    private static final String LOG_TAG = LoadAllSeriesInteractorImpl.class.getSimpleName();

    @Inject SeriesDao mSeriesDao;
    @Inject Logger logger;

    private Callback mCallback;

    public LoadAllSeriesInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                       SpicioApplication app, Callback callback) {
        super(threadExecutor, mainThread);
        app.getInteractorComponent().inject(this);
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "querying alll series");
        List<Series> seriesList = mSeriesDao.getAllSeries();

        logger.debug(LOG_TAG, "ended");
        postFinish(seriesList);
    }

    private void postFinish(final List<Series> seriesList) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFinish(seriesList);
            }
        });
    }

    private void postError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFail();
            }
        });
    }
}