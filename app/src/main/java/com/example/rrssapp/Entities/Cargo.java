package com.example.rrssapp.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cargo_table")
public class Cargo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "cargo_id")
    private int idCargo;
    @NonNull
    @ColumnInfo(name = "cargo_nombre")
    private String nombreCargo;
    @NonNull
    @ColumnInfo(name = "cargo_descripcion")
    private String descripcionCargo;
    @NonNull
    @ColumnInfo(name = "cargo_sueldo")
    private double sueldoCargo;
    @NonNull
    @ColumnInfo(name = "cargo_departamento")
    private int departamento;

    public Cargo(@NonNull String nombreCargo, @NonNull String descripcionCargo, double sueldoCargo, int departamento) {
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
        this.sueldoCargo = sueldoCargo;
        this.departamento = departamento;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    @NonNull
    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(@NonNull int idCargo) {
        this.idCargo = idCargo;
    }
    @NonNull
    public String getNombreCargo() {
        return nombreCargo;
    }


    public void setNombreCargo(@NonNull String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    @NonNull
    public String getDescripcionCargo() {
        return descripcionCargo;
    }


    public void setDescripcionCargo(@NonNull String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    @NonNull
    public double getSueldoCargo() {
        return sueldoCargo;
    }


    public void setSueldoCargo(@NonNull double sueldoCargo) {
        this.sueldoCargo = sueldoCargo;
    }




}
