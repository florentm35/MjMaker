package fr.florent.mjmaker.fragment.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.category.recyclerview.CategoryAdapter;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.service.model.Category;
import fr.florent.mjmaker.service.repository.CategoryRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListCategoryFragment extends AbstractFragment {

    private final CategoryRepositoryService categoryRepositoryService = CategoryRepositoryService.getInstance();

    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_category, container, false);

        RecyclerView listCategory = view.findViewById(R.id.list_category);

        listCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryAdapter = new CategoryAdapter(getContext(), categoryRepositoryService.getAll(), this::onDataAction);

        listCategory.setAdapter(categoryAdapter);

        return view;
    }

    private void onDataAction(CategoryAdapter.EnumAction action, Category category) {
        if (action == CategoryAdapter.EnumAction.EDIT) {
            AndroidLayoutUtil.openModalAskText(getContext(),
                    getLayoutInflater(),
                    "Set the name ?",
                    category.getName(), v -> this.onEdit(v, category));
        } else {
            categoryRepositoryService.delete(category);
            categoryAdapter.removeItem(category);
            AndroidLayoutUtil.showToast(getContext(), "Category removed");
        }

    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label("Add category")
                        .handler(this::showPopup)
                        .icone(R.drawable.material_add)
                        .build()
        );
    }

    public void showPopup(BiFunction<EnumScreen, Object[], Void> redirect) {
        AndroidLayoutUtil.openModalAskText(getContext(),
                getLayoutInflater(),
                "Set the name ?",
                null, this::onCreate);
    }

    // FIXME : Refacto with onCreate
    private boolean onEdit(String value, Category category) {
        if (value == null || value.isEmpty()) {
            AndroidLayoutUtil.showToast(getContext(), "Category name can not be empty");
            return false;
        }

        Category existCategory = categoryRepositoryService.findByName(value);

        if (existCategory != null && !existCategory.getId().equals(category.getId())) {
            AndroidLayoutUtil.showToast(getContext(), "A category with name " + value + " exist");
            return false;
        }

        category.setName(value);

        categoryRepositoryService.update(category);

        categoryAdapter.updateItem(category);

        AndroidLayoutUtil.showToast(getContext(), "Category modified");

        return true;
    }

    private boolean onCreate(String value) {

        if (value == null || value.isEmpty()) {
            AndroidLayoutUtil.showToast(getContext(), "Category name can not be empty");
            return false;
        }

        if (categoryRepositoryService.findByName(value) != null) {
            AndroidLayoutUtil.showToast(getContext(), "A category with name " + value + " exist");
            return false;
        }

        Category category = Category.builder()
                .name(value)
                .build();

        categoryRepositoryService.save(category);

        categoryAdapter.addItem(category);

        AndroidLayoutUtil.showToast(getContext(), "Category created");

        return true;
    }
}
