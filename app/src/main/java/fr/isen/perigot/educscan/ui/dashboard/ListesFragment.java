package fr.isen.perigot.educscan.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import fr.isen.perigot.educscan.R;
import fr.isen.perigot.educscan.databinding.FragmentListesBinding;


public class ListesFragment extends Fragment {

    private FragmentListesBinding binding;
    private ViewPager listesViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListesViewModel listesViewModel =
                new ViewModelProvider(this).get(ListesViewModel.class);

        binding = FragmentListesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ViewPager listesViewPager = root.findViewById(R.id.listesViewPager);
        ListesPagerAdapter adapter = new ListesPagerAdapter(getChildFragmentManager());

        String presentTitle = getString(R.string.present);
        String absentTitle = getString(R.string.absents);

        // Ajoutez les fragments et les titres ici
        adapter.addFragment(new PresentFragment(), presentTitle);
        adapter.addFragment(new AbsentFragment(), absentTitle);


        listesViewPager.setAdapter(adapter);

        TabLayout tabLayout = root.findViewById(R.id.listesTabLayout);
        tabLayout.setupWithViewPager(listesViewPager);

        final TextView textView = binding.textListes;
        listesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // Méthode pour créer une nouvelle instance de ListesFragment avec les valeurs de l'intervention
   /* public static ListesFragment newInstance(Intervention intervention) {
        ListesFragment fragment = new ListesFragment();
        Bundle args = new Bundle();
        args.putString("idApprenant", intervention.getId_apprenant());
        args.putString("idAbsent", intervention.getId_absent());
        fragment.setArguments(args);
        return fragment;
    } */
}