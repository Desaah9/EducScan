package fr.isen.perigot.educscan.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.isen.perigot.educscan.R;

public class AbsentAdapter extends RecyclerView.Adapter<AbsentAdapter.ViewHolder> {

    private List<Presences> mListAbsent;

    public AbsentAdapter(List<Presences> listAbsent) {
        mListAbsent = listAbsent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Presences presences = mListAbsent.get(position);

        // Vérifiez si heureArrivee est null avant de lier
        if (presences.getHeureArrivee() == null) {
            holder.bind(presences);
        } else {
            // Si heureArrivee n'est pas null, masquez la vue
            holder.hide();
        }
    }

    @Override
    public int getItemCount() {
        return mListAbsent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemAbsent;
        private TextView heureArrive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemAbsent = itemView.findViewById(R.id.itemAbsent);
            heureArrive = itemView.findViewById(R.id.heure_arrive);
        }

        public void bind(Presences presences) {
            itemAbsent.setText(presences.getIdApprenant());
            heureArrive.setText(presences.getHeureArrivee());
        }

        public void hide() {
            itemView.setVisibility(View.GONE);
        }
    }

    public void setData(List<Presences> newData) {
        mListAbsent = newData;
        notifyDataSetChanged();
    }

    public List<Presences> getListAbsent() {
        return mListAbsent;
    }
}

