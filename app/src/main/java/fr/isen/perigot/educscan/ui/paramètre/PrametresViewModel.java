package fr.isen.perigot.educscan.ui.paramètre;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrametresViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PrametresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Paramètres");
    }

    public LiveData<String> getText() {
        return mText;
    }
}