package com.example.rrssapp.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "departamento_table")
public class Departamento {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "departamento_id")
    @NonNull
    private int id;

    @NonNull
    @ColumnInfo(name = "departamento_nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "departamento_descripcion")
    private String descripcion;

    @NonNull
    @ColumnInfo(name = "departamento_estado")
    private String estado;

    public Departamento(@NonNull String nombre, @NonNull String descripcion, @NonNull String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }

    @NonNull
    public String getEstado() {
        return estado;
    }

    public void setEstado(@NonNull String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
