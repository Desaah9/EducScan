package fr.isen.perigot.educscan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
        private final MutableLiveData<Integer> nombreListPresent = new MutableLiveData<>();

        public LiveData<Integer> getNombreListPresent() {
            return nombreListPresent;
        }

        public void setNombreListPresent(int value) {
            nombreListPresent.setValue(value);
        }

}
