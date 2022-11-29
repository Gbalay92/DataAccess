package com.ad.practicaeval01_gbl;


import java.sql.Connection;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class App {
	
	private static ArrayList<Departamento> departamentos= new ArrayList<Departamento>();
	private static ArrayList<Empleado> empleados= new ArrayList<Empleado>();

    public static void main( String[] args ){
   
    	Utilities.readCsv(departamentos, empleados);
    	Connection conexion= Utilities.conexionPostgres();
    	Utilities.crearTablas(conexion);
    	//Utilities.inserts(conexion, departamentos, empleados);
    	Utilities.consultas(conexion);
    	Utilities.generarXml(departamentos, empleados);
    	
    	
    	//crear conexion a hibernate
    	StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build(); 
		SessionFactory sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
		Session session=sf.openSession();
    	
    	Utilities.insertHibernate(departamentos, session);
    	Utilities.consultaHibernate(session);
    	
    	
    	//cerrar conexiones
    	session.close();
    	sf.close();
    	sr.close();
    }

	
}
