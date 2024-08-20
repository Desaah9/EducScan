package fr.isen.perigot.educscan.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import java.util.ArrayList;
import java.util.List;

import fr.isen.perigot.educscan.R;
import fr.isen.perigot.educscan.SharedViewModel;
import fr.isen.perigot.educscan.databinding.FragmentCamembertBinding;
import fr.isen.perigot.educscan.ui.dashboard.AbsentAdapter;
import fr.isen.perigot.educscan.ui.dashboard.Presences;
import fr.isen.perigot.educscan.ui.dashboard.PresentAdapter;


public class CamembertFragment extends Fragment {

    private FragmentCamembertBinding binding;

    private final PresentAdapter presentAdapter = new PresentAdapter(new ArrayList<Presences>(), new SharedViewModel());
    private final AbsentAdapter absentAdapter = new AbsentAdapter(new ArrayList<Presences>());
    PieChart camembert;
    Segment s1, s2;
    private List<Presences> mListPresent;
    int i, nombreListP, nombreListA;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CamembertViewModel camembertViewModel =
                new ViewModelProvider(this).get(CamembertViewModel.class);

        binding = FragmentCamembertBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Récupérer le nombre d'éléments présents dans l'adaptateur
        int nombreListPresent = presentAdapter.getListPresent();
        Log.d("LISTBOUCLE", "getListPresent: " + nombreListPresent);

        // Initialiser la variable nombrePresent après avoir obtenu l'adaptateur
        // long nombrePresent = presentFragment.getNombrePresent();

        camembert = root.findViewById(R.id.PieChart);

        s1 = new Segment("Présents", 8);
        s2 = new Segment("Absents", 7);

        SegmentFormatter sf1 = new SegmentFormatter(getResources().getColor(R.color.bordeau));
        SegmentFormatter sf2 = new SegmentFormatter(getResources().getColor(R.color.black));

        camembert.addSegment(s1, sf1);
        camembert.addSegment(s2, sf2);


        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


