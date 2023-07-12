package com.example.testtraffbraza;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtraffbraza.databinding.ScrollItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ScrollViewsAdapter extends RecyclerView.Adapter<ScrollViewsAdapter.ScrollViewHolder>{

    private final List<Integer> scrollImagesList = new ArrayList<>();

    public void setItem(List<Integer> scrollImagesList) {
        this.scrollImagesList.addAll(scrollImagesList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ScrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        final ScrollItemBinding binding = ScrollItemBinding.inflate(inflater, parent, false);
        return new ScrollViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollViewHolder holder, int position) {
        int dataPosition = position % scrollImagesList.size();
        holder.bind(scrollImagesList.get(dataPosition));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public static class ScrollViewHolder extends RecyclerView.ViewHolder {
        private final ScrollItemBinding binding;

        private ScrollViewHolder(ScrollItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Integer image) {
            binding.imageView.setImageResource(image);
        }
    }
}
