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
import com.example.rrssapp.DAO.PlanillaDao;
import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.Entities.Planilla;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, exportSchema = false, entities = {Empleado.class, Departamento.class, Cargo.class, Planilla.class})
public abstract class RHDataBase extends RoomDatabase {
    public abstract EmpleadoDao empleadoDao();
    public abstract DepartamentoDao departamentoDao();
    public abstract CargoDao cargoDao();
    public abstract PlanillaDao planillaDao();

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

                                empDao.insert(new Empleado("0000000000001","ELMER JOHEL MEJIA",1,1,"Masculino",20000.0,"Activo"));
                                empDao.insert(new Empleado("0000000000002","CRISTINA MARLENY VALLECILLO",2,2,"Femenino",20000.0,"Activo"));
                                empDao.insert(new Empleado("0000000000003","ERICK ADRIAN JIMENEZ",2,2,"Masculino",20000.0,"Activo"));/*
                                empDao.insert(new Empleado("0000000000004","MARITZA ESTER ACOSTA",3,3,"Femenino",20000.0,"Activo"));
                                empDao.insert(new Empleado("0000000000005","DANNY JOSUE MEJIA",4,4,"Masculino",20000.0,"Activo"));
                                empDao.insert(new Empleado("0000000000006","KATTY RACHEL LEIVA",5,5,"Femenino",20000.0,"Activo"));
                                empDao.insert(new Empleado("0000000000007","NOE ERIKA CASTILLO",2,2,"Femenino",20000.0,"Inactivo"));
                                /*empDao.insert(new Empleado("0000000000008","SCARLETH SAMANTHA TORRES",3,3,"Femenino",20000.0,"Activo"));
                                empDao.insert(new Empleado("0000000000009","KEVIN JOSE VILLAFRANCA",5,5,"Masculino",20000.0,"Activo"));
                                empDao.insert(new Empleado("0000000000010","JOSE FRANCISCO MARTINEZ",2,2,"Masculino",20000.0,"Activo"));
                                empDao.insert(new Empleado("0000000000011","ENRRIQUE FERNANDO REYES",4,4,"Masculino",20000.0,"Inactivo"));
                                empDao.insert(new Empleado("0000000000012","JUAN CARLOS SANDOVAL",3,2,"Masculino",20000.0,"Activo"));*/


                                DepartamentoDao departoDao= INSTANCE.departamentoDao();
                                departoDao.deleteAll();


                                //Creacion de datos por defecto en mi base de datos
                                departoDao.insert(new Departamento("ADMINISTRACIÓN",
                                        "Departamento para la administracion empresarial","Activo"));
                                departoDao.insert(new Departamento("GERENCIA","Departamento para los gerentes","Activo"));
                                departoDao.insert(new Departamento("IT","Departemento de tecnología","Activo"));
                                departoDao.insert(new Departamento("CAJA","Departamento del control de caja","Activo"));
                                departoDao.insert(new Departamento("BODEGA","Departamento del control de las bodegas","Activo"));
                                departoDao.insert(new Departamento("LIMPIEZA","Departamento de limpieza","Activo"));
                                departoDao.insert(new Departamento("CONTABILIDAD","Departamento de la contabilidad y finanzas","Activo"));
                                departoDao.insert(new Departamento("VENTAS","Departamento del control de ventas","Activo"));

                                CargoDao cargodao = INSTANCE.cargoDao();
                                cargodao.deleteAll();
                                cargodao.insert(new Cargo("ADMINISTRADOR","Encargado de la administracion total",25000,1));
                                cargodao.insert(new Cargo("GERENTE DE AREA","Gerente encargado de su departamento",20000,2));
                                cargodao.insert(new Cargo("GERENTE DE VENTAS","Gerente encargado de las ventas",20000,2));
                                cargodao.insert(new Cargo("DESARROLLADOR","Persona encargado del desarrollo de software y mantenimiento",25000,3));
                                cargodao.insert(new Cargo("CAJERO","Encargado del control de las cajas",12000,4));
                                cargodao.insert(new Cargo("BODEGUERO","Encargado del control de bodega",15000,5));
                                cargodao.insert(new Cargo("LIMPIEZA","Encargado de la limpieza y el orden",1000,6));
                                cargodao.insert(new Cargo("CONTADOR","Encargado de las finanzas",20000,7));
                                cargodao.insert(new Cargo("VENDEDOR","Encargado de las ventas",12000,8));

                                PlanillaDao planillaDao = INSTANCE.planillaDao();
                                planillaDao.deleteAll();

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
