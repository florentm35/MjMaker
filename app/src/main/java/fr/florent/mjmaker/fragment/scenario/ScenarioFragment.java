package fr.florent.mjmaker.fragment.scenario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.service.model.Scenario;

public class ScenarioFragment extends AbstractFragment {

    private enum EnumState {
        CREATE, EDIT;
    }

    private Scenario scenario;
    private EnumState state;

    public ScenarioFragment(Object[] param) {
        super();
        if(param != null && param.length > 0 && param[0] != null) {
            scenario = (Scenario) param[0];
            state = EnumState.EDIT;
        } else {
            scenario = new Scenario();
            state = EnumState.CREATE;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_scenario, container, false);


        return view;
    }



}
