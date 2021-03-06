package com.tlongdev.spicio.network.converter;

import com.tlongdev.spicio.domain.model.Day;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;
import com.tlongdev.spicio.domain.model.Status;
import com.tlongdev.spicio.network.model.TvdbSeries;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Locale;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 02. 28.
 */
@RunWith(MockitoJUnitRunner.class)
public class TvdbModelConverterTest {

    @Mock
    TvdbSeries tvdbSeries;

    @Test
    public void testSeriesConversion() {
        when(tvdbSeries.getActors()).thenReturn("|Emilia Clarke|Peter Dinklage|Kit Harington|Nikolaj Coster-Waldau|");
        when(tvdbSeries.getAirsDayOfWeek()).thenReturn("Sunday");
        when(tvdbSeries.getAirsTime()).thenReturn("9:00 PM");
        when(tvdbSeries.getFirstAired()).thenReturn("2011-04-17");
        when(tvdbSeries.getGenres()).thenReturn("|Adventure|Drama|Fantasy|");
        when(tvdbSeries.getStatus()).thenReturn("Continuing");

        // TODO: 2016. 02. 29. throws a non-critical exception because joda-time is not initiated 
        // Using robolectric doesn't work: https://github.com/dlew/joda-time-android/issues/37
        TvdbSeriesOld series = TvdbModelConverter.convertToDomainModel(tvdbSeries);

        assertNotNull(series);

        assertEquals(4, series.getActors().length);
        assertEquals("Emilia Clarke", series.getActors()[0]);
        assertEquals("Peter Dinklage", series.getActors()[1]);
        assertEquals("Kit Harington", series.getActors()[2]);
        assertEquals("Nikolaj Coster-Waldau", series.getActors()[3]);
        assertEquals(Day.SUNDAY, series.getAirsDayOfWeek());

        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("h:mm a").withLocale(Locale.US);
        assertEquals("9:00 PM", timeFormatter.print(series.getAirsTime()));

        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        assertEquals("2011-04-17", dateFormatter.print(series.getFirstAired()));

        assertEquals(3, series.getGenres().length);
        assertEquals("Adventure", series.getGenres()[0]);
        assertEquals("Drama", series.getGenres()[1]);
        assertEquals("Fantasy", series.getGenres()[2]);
        assertEquals(Status.CONTINUING, series.getStatus());
    }
}
