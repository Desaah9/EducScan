package fr.isen.perigot.educscan.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ListesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is listes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}