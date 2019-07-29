package com.example.movieslistingmvvm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.movieslistingmvvm.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<Movie> mMovies;
    private Context context;
    private final ClickListener mClickListener;

    public RecyclerViewAdapter(Context context , List<Movie> movies ,ClickListener clickListener) {

        this.mMovies = movies;
        this.context = context;
        this.mClickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent,false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String title = mMovies.get(position).getTitle();
        String poster_path = "https://image.tmdb.org/t/p/original"+mMovies.get(position).getPhotoPath();

        if(mMovies.get(position).isFavourite()){
            holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            Log.d("hhhh",mMovies.get(position).getTitle()+"    "+mMovies.get(position).isFavourite());
        }
        else if(!mMovies.get(position).isFavourite()){
            holder.fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            Log.d("kkkkk",mMovies.get(position).getTitle()+"    "+mMovies.get(position).isFavourite());
        }

        holder.movieTitle.setText(title);


        Glide.with(holder.moviePoster.getContext())
                .load(poster_path)
                .placeholder(R.drawable.default_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView movieTitle;
        ImageView moviePoster;
        FrameLayout frameLayout;
        ImageButton fav;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            movieTitle = itemView.findViewById(R.id.movieName);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            frameLayout = itemView.findViewById(R.id.frame_layout);
            fav = itemView.findViewById(R.id.favButton);

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean Fav = mMovies.get(getAdapterPosition()).isFavourite();
                    Log.d("adapter",Fav+"");

                    if(Fav) mMovies.get(getAdapterPosition()).setFavourite(false);
                    else mMovies.get(getAdapterPosition()).setFavourite(true);

                    notifyItemChanged(getAdapterPosition());

                    mClickListener.clickListen(mMovies.get(getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}