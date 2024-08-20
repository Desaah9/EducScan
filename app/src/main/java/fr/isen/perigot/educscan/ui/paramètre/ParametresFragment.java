package fr.isen.perigot.educscan.ui.paramètre;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.isen.perigot.educscan.ChangepwdActivity;
import fr.isen.perigot.educscan.LoginActivity;
import fr.isen.perigot.educscan.R;

import fr.isen.perigot.educscan.databinding.FragmentNotificationsBinding;

public class ParametresFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PrametresViewModel parametresViewModel =
                new ViewModelProvider(this).get(PrametresViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String name = getActivity().getIntent().getStringExtra("name");
        String email = getActivity().getIntent().getStringExtra("email");
        String username = getActivity().getIntent().getStringExtra("username");

       DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("user");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //User user = dataSnapshot.getValue(User.class);
                
                binding.nom.setText(name);
                binding.mail.setText(email);
                binding.id.setText(username);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Récupérer la référence du bouton de déconnexion
        ImageButton logoutButton = root.findViewById(R.id.logout_button);

        // Ajouter un écouteur de clic sur le bouton de déconnexion
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Déconnexion avec Firebase Auth
                FirebaseAuth.getInstance().signOut();

                // Rediriger vers l'écran de connexion
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Récupérer la référence du bouton "changer le mot de passe"
        Button changeMdpButton = root.findViewById(R.id.change_mdp);
        changeMdpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirection vers l'activité ChangePasswordActivity
                Intent intent = new Intent(getActivity(), ChangepwdActivity.class);
                startActivity(intent);
            }
        });

        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}