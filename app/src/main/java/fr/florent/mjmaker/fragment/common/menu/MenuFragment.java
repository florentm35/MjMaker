package fr.florent.mjmaker.fragment.common.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import fr.florent.mjmaker.R;

public class MenuFragment extends Fragment {

    public interface IClickMenuEvent{
        void action(EnumMenu btn);
    }

    private IClickMenuEvent handler;

    public MenuFragment(IClickMenuEvent handler) {
        this.handler = handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.side_menu, container, false);

        LinearLayout layout = view.findViewById(R.id.parentMenu);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);

            if(child instanceof Button) {
                child.setOnClickListener(v -> handler.action(EnumMenu.valueOf(child.getId())));
            }

        }

        return view;
    }

}
