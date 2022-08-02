package com.example.gallerymanager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.viewHolder> {
    public GalleryAdapter(Context context, List<String> imagesList, PhotoListener photoListener) {
        this.context = context;
        this.imagesList = imagesList;
        this.photoListener = photoListener;
    }

    private Context context;
    private List<String> imagesList;
    protected PhotoListener photoListener;

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new viewHolder(
                LayoutInflater.from(context).inflate(R.layout.gallery_item, parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final String image = imagesList.get(position);
        final String imageText = imagesList.get(position).toString();

        Glide.with(context).load(image).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoListener.onPhotoClick(image);

                showDialogBox(image, imageText);

            }
        });


    }
    public void showDialogBox(final String img_id, String imageText){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        ImageView image =  dialog.findViewById(R.id.img);
        Button btnClose = dialog.findViewById(R.id.btn_close);
        Button btnFull = dialog.findViewById(R.id.btn_full);
        TextView title = dialog.findViewById(R.id.txt_image_name);

        int indexOfSlash = imageText.lastIndexOf("/");

        title.setText(imageText.substring(indexOfSlash + 1));

        Glide.with(context)
                .load(img_id)
                .into(image);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FullView.class);
                intent.putExtra("img_id", img_id);
                context.startActivity(intent);

            }
        });
        dialog.show();

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
        }
    }

    public interface PhotoListener{
        void onPhotoClick(String path);
    }

}
