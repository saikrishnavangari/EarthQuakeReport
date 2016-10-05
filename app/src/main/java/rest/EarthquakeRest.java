package rest;


import model.EarthQuakeJsonAPI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by krrish on 2/10/2016.
 */
public interface EarthquakeRest {
    @GET("/fdsnws/event/1/query")
    Call<EarthQuakeJsonAPI> loadEartquakes(@Query("format") String format,
                                           @Query("orderby") String orderby,
                                           @Query("minmagnitude") String minmagnitude);
}
