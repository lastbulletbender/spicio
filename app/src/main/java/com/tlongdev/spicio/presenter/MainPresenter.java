package com.tlongdev.spicio.presenter;

import com.tlongdev.spicio.ui.activity.MainView;

/**
 * Middle layer, Presenter.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public class MainPresenter implements Presenter<MainView> {

    private MainView view;

    @Override
    public void attachView(MainView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
