package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SaveEpisodesInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public class SaveEpisodesInteractorImpl extends AbstractInteractor implements SaveEpisodesInteractor {

    private static final String LOG_TAG = SaveEpisodesInteractorImpl.class.getSimpleName();

    @Inject EpisodeDao mEpisodeDao;
    @Inject Logger mLogger;

    private List<Episode> mEpisodes;
    private Callback mCallback;

    public SaveEpisodesInteractorImpl(SpicioApplication app, List<Episode> episodes, Callback callback) {
        super(app.getInteractorComponent());
        app.getInteractorComponent().inject(this);
        mEpisodes = episodes;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.verbose(LOG_TAG, "started");

        int episodesInserted = mEpisodeDao.insertAllEpisodes(mEpisodes);
        if (episodesInserted != mEpisodes.size()) {
            mLogger.warn(LOG_TAG, "episodes to insert: " + mEpisodes.size() + ", actually inserted: " + episodesInserted);
        }

        postFinish();
        mLogger.verbose(LOG_TAG, "ended");
    }

    private void postFinish() {
        if (mCallback != null) {
            mCallback.onSaveEpisodesFinish();
        }
    }
}