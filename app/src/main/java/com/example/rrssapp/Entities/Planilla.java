package com.example.rrssapp.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "planilla_table")
public class Planilla implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "planilla_id")
    @NonNull
    private int id;

    @NonNull
    @ColumnInfo(name = "planilla_fecha")
    private String fecha;

    @NonNull
    @ColumnInfo(name = "planilla_concepto")
    private String concepto;

    @NonNull
    @ColumnInfo(name = "planilla_monto")
    private double monto;

    public Planilla(@NonNull String fecha, @NonNull String concepto, double monto) {
        this.fecha = fecha;
        this.concepto = concepto;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull String fecha) {
        this.fecha = fecha;
    }

    @NonNull
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(@NonNull String concepto) {
        this.concepto = concepto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
