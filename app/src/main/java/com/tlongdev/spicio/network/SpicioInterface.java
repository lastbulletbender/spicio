package com.tlongdev.spicio.network;

import com.tlongdev.spicio.network.model.spicio.request.SpicioEpisodeBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioSeriesBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioUserBody;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserFullResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author longi
 * @since 2016.03.28.
 */
public interface SpicioInterface {

    String BASE_URL = "http://spicio-tlongdev.rhcloud.com/api/v1/";

    @GET("users")
    Call<List<SpicioUserResponse>> searchUsers(@Query("query") String query);

    @POST("users")
    Call<Void> login(@Body SpicioUserBody user);

    @GET("users/{id}")
    Call<SpicioUserFullResponse> getUser(@Path("id") long id, @Query("full") boolean full);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") long id);

    @POST("users/{id}/series")
    Call<Void> addSeries(@Path("id") long id, @Body SpicioSeriesBody series);

    @POST("users/{id}/series/{seriesId}/episodes/checks")
    Call<Void> checkEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Body SpicioEpisodeBody episode);

    @POST("users/{id}/series/{seriesId}/episodes/skips")
    Call<Void> skipEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Body SpicioEpisodeBody episode);

    @POST("users/{id}/series/{seriesId}/episodes/likes")
    Call<Void> likeEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Body SpicioEpisodeBody episode);

    @DELETE("users/{id}/series/{seriesId}/episodes/checks/{episodeId}")
    Call<Void> unCheckEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Path("episodeId") int episodeId);

    @DELETE("users/{id}/series/{seriesId}/episodes/skips/{episodeId}")
    Call<Void> unSkipEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Path("episodeId") int episodeId);

    @DELETE("users/{id}/series/{seriesId}/episodes/likes/{episodeId}")
    Call<Void> unLikeEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Path("episodeId") int episodeId);
}
