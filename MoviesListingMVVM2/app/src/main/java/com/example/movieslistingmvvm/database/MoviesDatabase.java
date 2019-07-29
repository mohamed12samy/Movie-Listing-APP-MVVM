package com.example.movieslistingmvvm.database;

import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieslistingmvvm.MyApplication;
import com.example.movieslistingmvvm.model.Movie;

@Database(entities = Movie.class,version = 1)
public abstract class MoviesDatabase extends RoomDatabase{


        private static MoviesDatabase INSTANCE;

        public abstract MoviesDao dao();

        public static MoviesDatabase getInstance()
        {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(MyApplication.getInstance().getApplicationContext(),MoviesDatabase.class,
                        "movie_database").allowMainThreadQueries().build();
            }
            return INSTANCE;
        }


}
