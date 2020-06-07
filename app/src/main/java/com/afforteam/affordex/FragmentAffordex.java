package com.afforteam.affordex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAffordex extends Fragment {

    //Charge le layout fragment_affordex.xml au démarrage de l'application
    //ATTENTION, je crois que les methodes doivent etre ecrites dans MainActivity.java,
    //FragMentAffordex,java servant uniquement à charger le layout fragment_affordex.xml au démarrage de l'app
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_affordex, container, false);
    }
}
