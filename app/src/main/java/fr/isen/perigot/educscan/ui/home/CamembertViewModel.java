package fr.isen.perigot.educscan.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CamembertViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public CamembertViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is camembert fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
