package com.example.wallpaperapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpaperapp.R;
import com.example.wallpaperapp.model.Image;
import com.example.wallpaperapp.UI.setwallpaper;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context CONTEXT;
    ArrayList<Image> wallpaper;

    public Adapter(Context CONTEXT, ArrayList<Image> wallpaper) {
        this.CONTEXT = CONTEXT;
        this.wallpaper = wallpaper;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(CONTEXT).inflate(R.layout.item_layout, null, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.textView.setMovementMethod(LinkMovementMethod.getInstance());
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent Browser = new Intent(Intent.ACTION_VIEW);
                        Browser.setData(Uri.parse("https://www.pexels.com/api/"));
                        Browser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        CONTEXT.startActivity(Browser);
                    }
                });
            }
        });
        holder.likes.setText(String.valueOf(wallpaper.get(position).getLikes()));
        holder.textView.setText(String.valueOf(wallpaper.get(position).getComments()));
        Glide.with(CONTEXT)
                .load(wallpaper
                .get(position)
                .getWebformatURL())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CONTEXT, setwallpaper.class);
                intent.putExtra("image", wallpaper.get(holder.getAdapterPosition()).getLargeImageURL());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CONTEXT.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wallpaper.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView likes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            likes = itemView.findViewById(R.id.likes);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
