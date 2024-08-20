package fr.isen.perigot.educscan.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.isen.perigot.educscan.ApiClient;
import fr.isen.perigot.educscan.ApiService;
import fr.isen.perigot.educscan.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AbsentFragment extends Fragment {
    private RecyclerView recyclerViewAbsent;
    private AbsentAdapter absentAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_absent, container, false);

        recyclerViewAbsent = view.findViewById(R.id.recyclerViewAbsent);
        recyclerViewAbsent.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter with an empty list initially
        absentAdapter = new AbsentAdapter(new ArrayList<>());

        // Attach the adapter to the RecyclerView
        recyclerViewAbsent.setAdapter(absentAdapter);

        // Call fetchTableData() to fetch data and display it in your RecyclerView
        fetchTableData();

        return view;
    }

    public void fetchTableData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Presences>> call = apiService.getPresences();
        call.enqueue(new Callback<List<Presences>>() {
            @Override
            public void onFailure(Call<List<Presences>> call, Throwable t) {
                Log.e("ATTENTION", "onResponse: PAS DE CO AVEC BDD" + t.getMessage());
            }

            @Override
            public void onResponse(Call<List<Presences>> call, Response<List<Presences>> response) {
                if (response.isSuccessful()) {
                    List<Presences> presences = response.body();
                    if (presences != null && !presences.isEmpty()) {
                        // Filtrer les données pour n'inclure que celles avec heureArrivee null
                        List<Presences> filteredPresences = presences.stream()
                                .filter(p -> p.getHeureArrivee() == null)
                                .collect(Collectors.toList());

                        // Mise à jour de la liste des données et notification de l'adaptateur
                        updateAdapterData(filteredPresences);

                        // Attachement de l'adaptateur après la récupération des données
                        recyclerViewAbsent.setAdapter(absentAdapter);

                        for (Presences presence : presences) {
                            Log.e("ATTENTION", "idApprenant: " + presence.getIdApprenant());
                            Log.e("ATTENTION", "HEURE: " + presence.getHeureArrivee());
                        }
                    }
                } else {
                    Log.e("ATTENTION", "onResponse: Échec de la requête, code: " + response.code());
                }
            }
        });
    }

    // Méthode pour mettre à jour la liste des données de l'adaptateur et notifier les changements
    private void updateAdapterData(List<Presences> newData) {
        getActivity().runOnUiThread(() -> {
            // Filtrer la liste pour inclure uniquement les éléments avec heureArrivee non null
            List<Presences> filteredList = newData.stream()
                    .filter(p -> p.getHeureArrivee() == null)
                    .collect(Collectors.toList());

            // Set data to the adapter and notify changes
            absentAdapter.setData(filteredList);
            absentAdapter.notifyDataSetChanged();

            // Make sure to attach the adapter after updating data
            recyclerViewAbsent.setAdapter(absentAdapter);
        });
    }
}