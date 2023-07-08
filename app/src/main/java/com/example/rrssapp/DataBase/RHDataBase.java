package com.example.rrssapp.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.rrssapp.DAO.EmpleadoDao;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.Entities.Empleado;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, exportSchema = false, entities = {Empleado.class})
public abstract class RHDataBase extends RoomDatabase {
    public abstract EmpleadoDao empleadoDao();

    private static volatile RHDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Generando una sola instancia con el patron singleton
    public static RHDataBase getDataBase(final Context context){
        if(INSTANCE == null){
            synchronized (RHDataBase.class){
                if (INSTANCE == null){
                    Callback callback = new Callback(){
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db){
                            super.onCreate(db);
                            databaseWriteExecutor.execute(() -> {
                                EmpleadoDao dao = INSTANCE.empleadoDao();
                                dao.deleteAll();

                                //Creacion de datos por defecto en mi base de datos
                                dao.insert(new Empleado("0412578555","Elmer Johel Mejia Leiva",1,1,"Masculino",20000,"Activo"));
                                dao.insert(new Empleado("0412574765","Danny Josue Mejia Leiva",1,1,"Masculino",20000,"Activo"));
                                dao.insert(new Empleado("041257412365","Genesis Rubi Espinal Leiva",1,1,"Femenino",20000,"Activo"));
                                dao.insert(new Empleado("0412574000","Kathia Michelle Alvarez Leiva",1,1,"Femenino",20000,"Inactivo"));
                                dao.insert(new Empleado("04125433765","Eddie Nahum Mejia Leiva",1,1,"Masculino",20000,"Activo"));
                                dao.insert(new Empleado("147855555","Fernando David Mejia Leiva",1,1,"Masculino",20000,"Inactivo"));
                                dao.insert(new Empleado("1478555332","Katty Rachel Mejia Leiva",1,1,"Femenino",20000,"Activo"));
                                dao.insert(new Empleado("547896631","Fernanda Elizabeth Santos Munguia",1,1,"Femenino",20000,"Activo"));
                                dao.insert(new Empleado("4587965233","Emilio Sandoval",1,1,"Masculino",20000,"Activo"));
                                dao.insert(new Empleado("5412300558","Ruben Adalberto Castillo Norales",1,1,"Masculino",20000,"Activo"));
                                dao.insert(new Empleado("5412300547","Luis Enrique Varela Fernandez",1,1,"Masculino",20000,"Inactivo"));
                                dao.insert(new Empleado("5412300452","Victoria Isabel Santos Munguia",1,1,"Femenino",20000,"Activo"));
                            });
                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),RHDataBase.class,"db_recursos_humanos").addCallback(callback).build();

                }
            }
        }
        return INSTANCE;
    }

}
