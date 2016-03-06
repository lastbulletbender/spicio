package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.threading.MainThread;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public class SaveSeriesInteractorImpl extends AbstractInteractor implements SaveSeriesInteractor {

    @Inject SeriesDao mSeriesDao;
    private Series mSeries;
    private Callback mCallback;

    public SaveSeriesInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    SpicioApplication app, Series series,
                                    Callback callback) {
        super(threadExecutor, mainThread);
        app.getStorageComponent().inject(this);

        mSeries = series;
        mCallback = callback;
    }

    @Override
    public void run() {
        // TODO: 2016. 03. 05. send to server, don't insert on failure
        // TODO: 2016. 03. 05. get image links and staffs
        mSeriesDao.insertSeries(mSeries);
        mCallback.onFinish();
    }
}
