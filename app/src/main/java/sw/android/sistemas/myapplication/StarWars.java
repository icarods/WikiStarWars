package sw.android.sistemas.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sw.android.sistemas.myapplication.models.People;
import sw.android.sistemas.myapplication.models.SWAPIList;

public interface StarWars {

    @GET("people/")
    public Call<SWAPIList<People>> getAllPeople(@Query("page") int page);

}
