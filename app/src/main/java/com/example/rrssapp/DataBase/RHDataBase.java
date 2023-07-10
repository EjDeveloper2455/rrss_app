package com.example.rrssapp.DataBase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.rrssapp.DAO.CargoDao;
import com.example.rrssapp.DAO.DepartamentoDao;
import com.example.rrssapp.DAO.EmpleadoDao;
import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.Entities.Empleado;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, exportSchema = false, entities = {Empleado.class, Departamento.class, Cargo.class})
public abstract class RHDataBase extends RoomDatabase {
    public abstract EmpleadoDao empleadoDao();
    public abstract DepartamentoDao departamentoDao();
    public abstract CargoDao cargoDao();

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
                                EmpleadoDao empDao = INSTANCE.empleadoDao();
                                empDao.deleteAll();

                                DepartamentoDao departoDao= INSTANCE.departamentoDao();
                                departoDao.deleteAll();

                                CargoDao cargodao = INSTANCE.cargoDao();

                                //Creacion de datos por defecto en mi base de datos
                                departoDao.insert(new Departamento("Administración",
                                        "Departamento para la administracion empresarial","Activo"));
                                departoDao.insert(new Departamento("Gerencia","Departamento para los gerentes","Activo"));
                                departoDao.insert(new Departamento("IT","Departemento de tecnología","Activo"));
                                departoDao.insert(new Departamento("Caja","Departamento del control de caja","Activo"));
                                departoDao.insert(new Departamento("Bodega","Departamento del control de las bodegas","Activo"));
                                departoDao.insert(new Departamento("Limpieza","Departamento de limpieza","Activo"));
                                departoDao.insert(new Departamento("Contabilidad","Departamento de la contabilidad y finanzas","Activo"));
                                departoDao.insert(new Departamento("Ventas","Departamento del control de ventas","Activo"));

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
