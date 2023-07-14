package com.example.testtraffbraza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.testtraffbraza.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private ActivityGameBinding binding;
    private final List<Integer> scrollImagesList = new ArrayList<>();
    private final ScrollViewsAdapter scrollViewsAdapter1 = new ScrollViewsAdapter();
    private final ScrollViewsAdapter scrollViewsAdapter2 = new ScrollViewsAdapter();
    private final ScrollViewsAdapter scrollViewsAdapter3 = new ScrollViewsAdapter();
    private GameActivityViewModel gameActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gameActivityViewModel = new ViewModelProvider(this).get(GameActivityViewModel.class);

        binding.recyclerView1.setLayoutManager(new GridLayoutManager(this.getBaseContext(), 1));
        binding.recyclerView1.setAdapter(scrollViewsAdapter1);

        binding.recyclerView2.setLayoutManager(new GridLayoutManager(this.getBaseContext(), 1));
        binding.recyclerView2.setAdapter(scrollViewsAdapter2);

        binding.recyclerView3.setLayoutManager(new GridLayoutManager(this.getBaseContext(), 1));
        binding.recyclerView3.setAdapter(scrollViewsAdapter3);

        scrollImagesList.add(R.drawable.roll_image_1);
        scrollImagesList.add(R.drawable.roll_image_2);
        scrollImagesList.add(R.drawable.roll_image_3);
        scrollImagesList.add(R.drawable.roll_image_4);
        scrollImagesList.add(R.drawable.roll_image_5);

        scrollViewsAdapter1.setItem(scrollImagesList);
        scrollViewsAdapter2.setItem(scrollImagesList);
        scrollViewsAdapter3.setItem(scrollImagesList);

        gameActivityViewModel.getBalanceForUI().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.balanceG1.setText(String.valueOf(integer));
            }
        });

        binding.recyclerView1.smoothScrollToPosition(gameActivityViewModel.firstPosition());
        binding.recyclerView2.smoothScrollToPosition(gameActivityViewModel.firstPosition());
        binding.recyclerView3.smoothScrollToPosition(gameActivityViewModel.firstPosition());

        binding.buttonSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.recyclerView1.smoothScrollToPosition(gameActivityViewModel.nextPosition(getCurrentItem(binding.recyclerView1)));
                binding.recyclerView2.smoothScrollToPosition(gameActivityViewModel.nextPosition(getCurrentItem(binding.recyclerView2)));
                binding.recyclerView3.smoothScrollToPosition(gameActivityViewModel.nextPosition(getCurrentItem(binding.recyclerView3)));

                gameActivityViewModel.balanceCalculation(getCurrentItem(binding.recyclerView1) % 5, getCurrentItem(binding.recyclerView2) % 5, getCurrentItem(binding.recyclerView3) % 5);
            }
        });
    }

    public int getCurrentItem(RecyclerView recyclerView) {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
    }
}