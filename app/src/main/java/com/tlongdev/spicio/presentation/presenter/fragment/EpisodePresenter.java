package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.storage.CheckEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.storage.LikeEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.storage.LoadEpisodeDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.CheckEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LikeEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadEpisodeDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.EpisodeView;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public class EpisodePresenter implements Presenter<EpisodeView>,
        LoadEpisodeDetailsInteractor.Callback, CheckEpisodeInteractor.Callback,
        LikeEpisodeInteractor.Callback {

    private EpisodeView mView;

    private Episode mEpisode;

    private SpicioApplication mApplication;

    public EpisodePresenter(SpicioApplication application) {
        mApplication = application;
    }

    @Override
    public void attachView(EpisodeView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onLoadEpisodeDetailsFinish(Episode episode) {
        mEpisode = episode;
        if (mView != null) {
            mView.showEpisodeDetails(episode);
        }
    }

    @Override
    public void onLoadEpisodeDetailsFail() {
        mEpisode = null;
        if (mView != null) {
            mView.showError();
        }
    }

    public void loadEpisode(int episodeId) {
        LoadEpisodeDetailsInteractor interactor = new LoadEpisodeDetailsInteractorImpl(
                mApplication, episodeId, this
        );
        interactor.execute();
    }

    public void checkEpisode() {
        boolean watched = !mEpisode.isWatched();

        mEpisode.setWatched(watched);
        CheckEpisodeInteractor interactor = new CheckEpisodeInteractorImpl(
                mApplication, mEpisode.getSeriesId(), mEpisode.getTraktId(), true, this
        );
        interactor.execute();
    }

    public void likeEpisode() {
        LikeEpisodeInteractor interactor = new LikeEpisodeInteractorImpl(
                mApplication, mEpisode.getSeriesId(), mEpisode.getTraktId(), !mEpisode.isLiked(), this
        );
        interactor.execute();
    }

    public void skipEpisode() {
        boolean watched = !mEpisode.isSkipped();

        /*mEpisode.setWatched(watched);
        CheckEpisodeInteractor interactor = new CheckEpisodeInteractorImpl(
                mApplication, mEpisode.getSeriesId(), mEpisode.getTraktId(), false, this
        );
        interactor.execute();*/
    }

    @Override
    public void onEpisodeCheckFinish() {
        if (mView != null) {
            mView.updateCheckButton(mEpisode.isWatched());
        }
    }

    @Override
    public void onEpisodeCheckFail() {

    }

    @Override
    public void onEpisodeLikeFinish() {
        mEpisode.setLiked(!mEpisode.isLiked());
        if (mView != null) {
            mView.updateLikeButton(mEpisode.isLiked());
        }
    }

    @Override
    public void onEpisodeLikeFail() {

    }
}
