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
    private final Random random = new Random();
    private GameActivityViewModel gameActivityViewModel;
    private final int min = 35, max = 50;
    private int position1 = random.nextInt((max - min)) + 1 + min;
    private int position2 = random.nextInt((max - min)) + 1 + min;
    private int position3 = random.nextInt((max - min)) + 1 + min;

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

        binding.recyclerView1.smoothScrollToPosition(position1);
        binding.recyclerView2.smoothScrollToPosition(position2);
        binding.recyclerView3.smoothScrollToPosition(position3);



        binding.buttonSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position1 = changePosition(position1);
                position2 = changePosition(position2);
                position3 = changePosition(position3);


                binding.recyclerView1.smoothScrollToPosition(position1);
                binding.recyclerView2.smoothScrollToPosition(position2);
                binding.recyclerView3.smoothScrollToPosition(position3);


                int index1 = getCurrentItem(binding.recyclerView1) % 5;
                int index2 = getCurrentItem(binding.recyclerView2) % 5;
                int index3 = getCurrentItem(binding.recyclerView3) % 5;

                gameActivityViewModel.balanceCalculation(index1, index2, index3);
            }
        });
    }

    public int changePosition(int position) {
        return position + random.nextInt((max - min) + 1 + min);
    }


    public int getCurrentItem(RecyclerView recyclerView) {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
    }
}