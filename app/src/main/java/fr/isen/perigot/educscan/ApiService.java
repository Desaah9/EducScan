package fr.isen.perigot.educscan;

import java.util.List;

import fr.isen.perigot.educscan.ui.dashboard.Presences;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("presences")
    Call<List<Presences>> getPresences();

}