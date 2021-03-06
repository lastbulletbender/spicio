package com.tlongdev.spicio.presentation.ui.view.activity;

import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public interface SeriesSearchDetailsView extends BaseView {
    void showDetails(Series series);

    void reportError();

    void onSeriesSaved();

    void showLoading();

    void hideLoading();
}
