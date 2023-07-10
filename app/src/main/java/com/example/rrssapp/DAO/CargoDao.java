package com.example.rrssapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rrssapp.Entities.Cargo;

import java.util.List;

@Dao
public interface CargoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cargo cargo);

    @Update
    void update(Cargo cargo);
    @Delete
    void delete(Cargo cargo);

    @Query("DELETE FROM cargo_table")
    void deleteAll();

    @Query("SELECT * FROM cargo_table ORDER BY cargo_nombre ASC")
    LiveData<List<Cargo>> getCargo();

}
