package com.tlongdev.spicio.ui.fragment;

import com.tlongdev.spicio.network.model.SeriesApi;
import com.tlongdev.spicio.ui.BaseView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 02. 24.
 */
public interface SearchSeriesView extends BaseView {
    void showSearchResult(List<SeriesApi> series);
    void showErrorMessage();
}