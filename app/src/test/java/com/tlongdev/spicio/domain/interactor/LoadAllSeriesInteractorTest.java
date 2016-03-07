package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.LoadAllSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadAllSeriesInteractorTest {

    @Mock
    private SeriesDao mSeriesDao;

    @Mock
    private Executor mExecutor;

    @Mock
    private LoadAllSeriesInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();

        FakeDaoModule storageModule = new FakeDaoModule();
        storageModule.setSeriesDao(mSeriesDao);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .daoModule(storageModule)
                .networkRepositoryModule(mock(NetworkRepositoryModule.class))
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testLoad() {

        List<Series> series = new LinkedList<>();
        when(mSeriesDao.getAllSeries()).thenReturn(series);

        LoadAllSeriesInteractorImpl interactor = new LoadAllSeriesInteractorImpl(
                mExecutor, mMainThread, mApp, mMockedCallback
        );
        interactor.run();

        verify(mSeriesDao).getAllSeries();
        verifyNoMoreInteractions(mSeriesDao);
        verify(mMockedCallback).onFinish(series);
    }
}
