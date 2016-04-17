package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.GetFeedInteractor;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.15.
 */
public class GetFeedInteractorImpl extends AbstractInteractor implements GetFeedInteractor {

    private static final String LOG_TAG = GetFeedInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject SpicioRepository mRepository;

    private long mUserId;
    private Callback mCallback;

    public GetFeedInteractorImpl(SpicioApplication application, long userId, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mUserId = userId;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        List<UserActivity> activities = mRepository.getFeed(mUserId);

        if (activities != null) {
            postFinish(activities);
        } else {
            postFail();
        }
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish(final List<UserActivity> activities) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetFeedFinish(activities);
                }
            });
        }
    }

    private void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetFeedFail();
                }
            });
        }
    }
}