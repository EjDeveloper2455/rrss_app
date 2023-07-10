package com.example.rrssapp.DAO;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rrssapp.DataBase.RHDataBase;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Empleado;

import java.util.List;

@Dao
public interface DepartamentoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Departamento departamento);

    @Update
    void update(Departamento departamento);

    @Query("DELETE FROM departamento_table")
    void deleteAll();

    @Query("SELECT * FROM departamento_table ORDER BY departamento_id ASC")
    LiveData<List<Departamento>> getDepartamentos();

}
