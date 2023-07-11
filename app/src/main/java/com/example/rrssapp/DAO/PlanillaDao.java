package com.example.rrssapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Entities.Planilla;

import java.util.List;

@Dao
public interface PlanillaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Planilla planilla);

    @Update
    void update(Planilla planilla);
    @Delete
    void delete(Planilla planilla);

    @Query("DELETE FROM planilla_table")
    void deleteAll();

    @Query("SELECT * FROM planilla_table ORDER BY planilla_fecha ASC")
    LiveData<List<Planilla>> getPlanilla();
}
