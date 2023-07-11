package com.example.rrssapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rrssapp.Entities.Empleado;

import java.util.List;
@Dao
public interface EmpleadoDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Empleado empleado);

    @Update
    void update(Empleado empleado);
    @Delete
    void delete(Empleado empleado);

    @Query("DELETE FROM empleado_table")
    void deleteAll();

    @Query("SELECT * FROM empleado_table ORDER BY empleado_nombre ASC")
    LiveData<List<Empleado>> getEmpleados();
    @Query("SELECT * FROM empleado_table where empleado_estado = :estado ORDER BY empleado_nombre ASC")
    LiveData<List<Empleado>> getEmpleadosEstado(String estado);

    @Query("SELECT SUM(empleado_salario) FROM empleado_table where empleado_estado = 'Activo'")
    LiveData<Double> getMontoTotal();
}
