package com.example.movieslistingmvvm.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieslistingmvvm.ClickListener;
import com.example.movieslistingmvvm.R;
import com.example.movieslistingmvvm.RecyclerViewAdapter;
import com.example.movieslistingmvvm.model.Movie;
import com.example.movieslistingmvvm.model.movieList;
import com.example.movieslistingmvvm.viewModel.NowPlayingViewModel;
import com.example.movieslistingmvvm.viewModel.TopMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class TopMoviesFragment extends Fragment implements ClickListener{

    private TopMoviesViewModel mTopMoviesViewModel = new TopMoviesViewModel();

    private RecyclerView recyclerView;

    private List<Movie> moMvieList = new ArrayList<>();

    private int page ;

    private RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(getActivity(), moMvieList, this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_movies, container, false);

        page = 1;
        recyclerView = view.findViewById(R.id.top_movies_recycleerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        mTopMoviesViewModel = ViewModelProviders.of(this).get(TopMoviesViewModel.class);

        mTopMoviesViewModel.getTopMovies(page).observe(this, new Observer<movieList>() {
            @Override
            public void onChanged(movieList movieList) {
                moMvieList.addAll(movieList.getMovies());
                mAdapter.notifyDataSetChanged();
            }
        });

        listeningToRecyclerViewScroll();

        return view;
    }

    private void listeningToRecyclerViewScroll(){

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(getActivity(), "Last", Toast.LENGTH_LONG).show();
                    mTopMoviesViewModel.getTopMovies(++page);

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        moMvieList.clear();
}

    @Override
    public void clickListen(Movie movie) {
        if(movie.isFavourite())
            mTopMoviesViewModel.insertFav(movie);

        else if(!movie.isFavourite())
            mTopMoviesViewModel.deleteFav(movie.getTitle());
    }


}
