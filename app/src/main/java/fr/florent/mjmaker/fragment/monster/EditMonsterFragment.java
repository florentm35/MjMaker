package fr.florent.mjmaker.fragment.monster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.service.monstrer.Monster;
import fr.florent.mjmaker.service.monstrer.MonsterRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class EditMonsterFragment extends AbstractFragment {

    private static final String TAG = EditMonsterFragment.class.getName();

    private MonsterRepositoryService monsterRepositoryService = MonsterRepositoryService.getInstance();


    private Monster monster;

    public EditMonsterFragment(Object... params) {
        if (params != null && params.length > 0) {
            monster = monsterRepositoryService.findBydId((Integer) params[0]);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_monster, container, false);

        ImageButton saveButton = view.findViewById(R.id.valide);

        if (monster == null) {
            saveButton.setImageResource(R.drawable.material_add);
        } else {
            saveButton.setImageResource(R.drawable.material_done);
        }

        saveButton.setOnClickListener(this::save);

        return view;
    }


    private void save(View view) {

        if (monster == null) {
            monster = new Monster();
        }

        monster.setName(AndroidLayoutUtil.getTextFromEditText(getView(), R.id.et_name));

        String message;

        if (monster.getId() == null) {
            monsterRepositoryService.save(monster);
            message = "Monster create";
        } else {
            monsterRepositoryService.update(monster);
            message = "Monster update";
        }

        AndroidLayoutUtil.showToast(view.getContext(), message);

        backHandler.back();
    }

}
