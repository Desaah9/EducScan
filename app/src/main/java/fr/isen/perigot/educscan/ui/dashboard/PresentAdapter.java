package fr.isen.perigot.educscan.ui.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import fr.isen.perigot.educscan.R;
import fr.isen.perigot.educscan.SharedViewModel;

public class PresentAdapter extends RecyclerView.Adapter<PresentAdapter.ViewHolder> {

    private final SharedViewModel sharedViewModel;
    private List<Presences> mListPresent;
    int nombreListPresent;

    // Constructeur prenant la liste d'interventions présentes
    public PresentAdapter(List<Presences> listPresent ,  SharedViewModel sharedViewModel) {
        mListPresent = listPresent;
        this.sharedViewModel = sharedViewModel;
    }

    // Méthode pour créer une nouvelle vue
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_present, parent, false);
        return new ViewHolder(view);
    }

    // Méthode pour lier les données à la vue
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Presences presences = mListPresent.get(position);

        // Vérifiez si heureArrivee n'est pas null avant de lier
        if (presences.getHeureArrivee() != null) {
            holder.bind(presences);
            nombreListPresent++;
        } else {
            // Si heureArrivee est null, masquez la vue
            holder.hide();
        }
        Log.d("LISTBOUCLE", "getListPresent: " +nombreListPresent);
    }

    // Méthode pour obtenir le nombre total d'éléments dans la liste
    @Override
    public int getItemCount() {
        // Utilisez la méthode stream pour filtrer les éléments avec heureArrivee non égale à null
        return (int) mListPresent.stream().filter(p -> p.getHeureArrivee() != null).count();
    }

    // Classe ViewHolder pour contenir les vues des éléments de la liste
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Déclarez les vues à utiliser dans chaque élément de la liste
        private TextView itemPresent;
        private TextView heureArrive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialisez les vues
            itemPresent = itemView.findViewById(R.id.itemPresent);
            heureArrive = itemView.findViewById(R.id.heure_arrive);
        }

        // Méthode pour lier les données à la vue
        public void bind(Presences presences) {
            itemPresent.setText(presences.getIdApprenant());
            heureArrive.setText(presences.getHeureArrivee());
        }

        // Méthode pour masquer la vue si heureArrivee est null
        public void hide() {
            itemView.setVisibility(View.GONE);
        }
    }

    public void setData(List<Presences> newData) {
        mListPresent = newData;
        notifyDataSetChanged();
    }

    public int getListPresent() {

        // Mettez à jour le ViewModel partagé avec la nouvelle valeur
        sharedViewModel.setNombreListPresent(nombreListPresent);
        Log.d("LIST", "getListPresent: " +nombreListPresent);
        return nombreListPresent;
    }
}

