package com.tlongdev.spicio.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.adapter.SearchSeriesAdapter;
import com.tlongdev.spicio.network.model.SeriesApi;
import com.tlongdev.spicio.presenter.SearchSeriesPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesFragment extends Fragment implements SearchSeriesView {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.search) EditText mSearchText;

    private SearchSeriesPresenter presenter;

    private SearchSeriesAdapter adapter;

    public SearchSeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchSeriesPresenter();
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_series, container, false);
        ButterKnife.bind(this, rootView);

        //Set the toolbar to the main activity's action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) rootView.findViewById(R.id.toolbar));

        adapter = new SearchSeriesAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    presenter.searchForSeries(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showSearchResult(List<SeriesApi> series) {
        adapter.setDataSet(series);
    }

    @Override
    public void showErrorMessage() {
        adapter.setDataSet(null);
    }
}