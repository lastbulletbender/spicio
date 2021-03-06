package com.tlongdev.spicio.presentation.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.fragment.SeriesPresenter;
import com.tlongdev.spicio.presentation.ui.activity.SeriesActivity;
import com.tlongdev.spicio.presentation.ui.adapter.SeriesAdapter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SeriesView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment implements SeriesView, SeriesAdapter.OnItemSelectedListener {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    private SeriesPresenter presenter;

    private SeriesAdapter adapter;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SeriesPresenter();
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_series, container, false);
        ButterKnife.bind(this, rootView);

        //Set the toolbar to the main activity's action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) rootView.findViewById(R.id.toolbar));

        adapter = new SeriesAdapter(getActivity());
        adapter.setListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadSeries();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public SpicioApplication getSpicioApplication() {
        return (SpicioApplication) getActivity().getApplication();
    }

    @Override
    public void showSeries(List<Series> series) {
        adapter.setDataSet(series);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(Series series) {
        Intent intent = new Intent(getActivity(), SeriesActivity.class);
        intent.putExtra(SeriesActivity.EXTRA_SERIES_ID, series.getTraktId());
        intent.putExtra(SeriesActivity.EXTRA_SERIES_TITLE, series.getTitle());
        startActivity(intent);
    }
}
