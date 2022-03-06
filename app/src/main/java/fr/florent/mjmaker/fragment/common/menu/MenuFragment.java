package fr.florent.mjmaker.fragment.common.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import fr.florent.mjmaker.R;

/**
 * Fragment for the side menu
 */
public class MenuFragment extends Fragment {

    /**
     * Callback interface when click on menu item
     */
    public interface IClickMenuEvent{
        void action(EnumScreen btn);
    }

    /**
     * Event handler when click on menu item
     */
    private IClickMenuEvent handler;

    /**
     * Constructor
     *
     * @param handler Event handler when click on menu item
     */
    public MenuFragment(IClickMenuEvent handler) {
        this.handler = handler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.side_menu, container, false);

        // Add event handler for all button
        LinearLayout layout = view.findViewById(R.id.parentMenu);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);

            if(child instanceof Button) {
                child.setOnClickListener(v -> handler.action(EnumScreen.valueOf(child.getId())));
            }

        }

        return view;
    }

}
