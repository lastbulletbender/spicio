package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.LoadAllSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadAllSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.AbstractPresenter;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SeriesView;
import com.tlongdev.spicio.threading.MainThread;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 06.
 */
public class SeriesPresenter extends AbstractPresenter implements Presenter<SeriesView>,LoadAllSeriesInteractor.Callback {

    private SeriesView mView;

    public SeriesPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
    }

    @Override
    public void attachView(SeriesView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadSeries() {
        LoadAllSeriesInteractor interactor = new LoadAllSeriesInteractorImpl(
                mExecutor,
                mMainThread,
                mView.getSpicioApplication(),
                this
        );
        interactor.execute();
    }

    @Override
    public void onLoadAllSeriesFinish(List<Series> series) {
        if (mView != null) {
            mView.showSeries(series);
        }
    }

    @Override
    public void onLoadAllSeriesFail() {
        // TODO: 2016. 03. 07.
    }
}
