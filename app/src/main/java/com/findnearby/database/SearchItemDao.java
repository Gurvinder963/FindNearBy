package com.findnearby.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SearchItemDao {

    @Query("SELECT * FROM SearchItem ORDER BY uid DESC LIMIT 3")
    List<SearchItem> getAll();

    @Insert
    void insertAll(SearchItem... users);

    @Delete
    void delete(SearchItem user);
}
