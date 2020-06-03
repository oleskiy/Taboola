package com.oleskiy.taboola.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oleskiy.taboola.R;
import com.oleskiy.taboola.view.api.ViewModel;
import com.oleskiy.taboola.view.model.Item;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private ViewModel viewModel;
    private ArrayList<Item> models = new ArrayList<>();
    private Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModel(getActivity().getApplication());
        return inflater.inflate(R.layout.main_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_taboola);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(
                        getContext(),
                        DividerItemDecoration.VERTICAL
                )
        );
         adapter = new Adapter(getContext(), models);
         recyclerView.setAdapter(adapter);

        viewModel.getData().observe(this.getViewLifecycleOwner(), new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> itemModels) {
               adapter.updateList(itemModels);
                adapter.notifyDataSetChanged();
            }
        });
    }



}
