package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.LoadEpisodeDetailsInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public class LoadEpisodeDetailsInteractorImpl extends AbstractInteractor implements LoadEpisodeDetailsInteractor {

    private static final String LOG_TAG = LoadEpisodeDetailsInteractorImpl.class.getSimpleName();

    @Inject EpisodeDao mEpisodeDao;
    @Inject Logger logger;

    private int mSeriesId;
    private int mSeason;
    private int mEpisode;
    private Callback mCallback;

    public LoadEpisodeDetailsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                            SpicioApplication application, int seriesId, int season,
                                            int episode, Callback callback) {
        super(threadExecutor, mainThread);
        application.getInteractorComponent().inject(this);

        mSeriesId = seriesId;
        mSeason = season;
        mEpisode = episode;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        Episode episode = mEpisodeDao.getEpisode(mSeriesId, mSeason, mEpisode);
        if (episode == null) {
            logger.debug(LOG_TAG, "EpisodeDao.getEpisode returned null");
            postError();
            return;
        }

        postFinish(episode);
        logger.debug(LOG_TAG, "ended");
    }

    private void postError() {
        if (mCallback == null) {
            return;
        }
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFail();
            }
        });
    }

    private void postFinish(final Episode episode) {
        if (mCallback == null) {
            return;
        }
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFinish(episode);
            }
        });
    }
}