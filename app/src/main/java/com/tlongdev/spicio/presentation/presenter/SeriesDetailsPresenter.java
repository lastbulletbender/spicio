package com.tlongdev.spicio.presentation.presenter;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.TraktSeriesDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.presentation.ui.view.activity.SeriesDetailsView;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.threading.MainThread;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class SeriesDetailsPresenter extends AbstractPresenter implements Presenter<SeriesDetailsView>,TraktSeriesDetailsInteractor.Callback, SaveSeriesInteractor.Callback {

    private SeriesDetailsView mView;

    private TraktRepository mRepository;
    private SeriesDao mSeriesDao;

    public SeriesDetailsPresenter(Executor executor, MainThread mainThread,
                                  TraktRepository repository, SeriesDao seriesDao) {
        super(executor, mainThread);
        mRepository = repository;
        mSeriesDao = seriesDao;
    }

    @Override
    public void attachView(SeriesDetailsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadDetails(int traktId) {
        TraktSeriesDetailsInteractor interactor = new TraktSeriesDetailsInteractorImpl(
                mExecutor, mMainThread, traktId, mRepository, this
        );

        interactor.execute();
    }

    @Override
    public void onResult(Series series) {
        mView.showDetails(series);
    }

    @Override
    public void onFail() {
        mView.reportError();
    }

    public void saveSeries(Series series) {
        SaveSeriesInteractor interactor = new SaveSeriesInteractorImpl(
                mExecutor, mMainThread, series, mSeriesDao, this
        );

        interactor.execute();
    }

    @Override
    public void onFinish() {
        mView.onSeriesSaved();
    }
}
