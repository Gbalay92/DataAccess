package com.ad.practicaeval01_gbl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class UtilitiesHiber {
		
	public static Session createSession() {
		StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build(); 
		SessionFactory sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
		Session session=sf.openSession();
		return session;
	}
	public static void insertHibernate(ArrayList<Departamento> departamentos, Session session) {
		//creacion de la transaccion
		session.getTransaction().begin();
		
		for(Departamento d :departamentos) {
			session.persist(d);
		}
		session.getTransaction().commit();
		
		
	}
	public static void consultaHibernate(Session session) {
		Query<Object[]> query = session.createQuery("SELECT d.nombre FROM Departamento d", Object[].class);
				 List<Object[]> lista = (List<Object[]>) query.getResultList();
				 for (Object[] objeto: lista) {
				 System.out.println((String)objeto[0]);
				
				 	
	}
	}
}
