package com.example.movieslistingmvvm.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
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

import java.util.ArrayList;
import java.util.List;

public class NowPlayingFragment extends Fragment implements ClickListener  {

    private NowPlayingViewModel mNowPlayingViewModel = new NowPlayingViewModel();

    private RecyclerView recyclerView;

    private List<Movie> moMvieList = new ArrayList<>();

    private int page ;

    private RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(getActivity(), moMvieList,this);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        page = 1;
        recyclerView = view.findViewById(R.id.now_playing_recycleerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        mNowPlayingViewModel.getNowPlayingMovies(page).observe(this, new Observer<movieList>() {
            @Override
            public void onChanged(movieList movieList) {
                Log.d("rrrr","zehe2t--  "+page+" + "+movieList.getMovies().get(0).getTitle()+" ++  "+movieList.getMovies().get(0).isFavourite());

                moMvieList.addAll(movieList.getMovies());
                mAdapter.notifyDataSetChanged();
            }
        });

        listeningToRecyclerViewScroll();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        mNowPlayingViewModel = ViewModelProviders.of(this).get(NowPlayingViewModel.class);
        super.onCreate(savedInstanceState);

    }

/*

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("is_null","not NULL");

        super.onSaveInstanceState(outState);
    }
*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //mNowPlayingViewModel.getNowPlayingMovies(page).removeObserver(mObserver);

    }

    private void listeningToRecyclerViewScroll(){

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
//                    Toast.makeText(getActivity(), "Last", Toast.LENGTH_LONG).show();
                    mNowPlayingViewModel.getNowPlayingMovies(++page);

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        }


    @Override
    public void clickListen(Movie movie) {

        if(movie.isFavourite())
            mNowPlayingViewModel.insertFav(movie);

        else if(!movie.isFavourite())
            mNowPlayingViewModel.deleteFav(movie.getTitle());
    }
}
