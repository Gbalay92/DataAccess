package com.ad.practicaeval01_gbl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Utilities {
	
	
	public static void readCsv(ArrayList<Departamento> departamentos, ArrayList<Empleado> empleados) {
		Path origen1 = Paths.get("path");
		Path origen2 = Paths.get("path");
    	 BufferedReader br;
		try {
			br = Files.newBufferedReader(origen1);
			
			
			
			String linea ="";
			while ((linea = br.readLine()) != null) {
				String datos[]=linea.split(",");
				Departamento dep = new Departamento(Integer.parseInt(datos[0]), datos[1], datos[2]);
				departamentos.add(dep);
			}
			
			br = Files.newBufferedReader(origen2);
			linea ="";
			while ((linea = br.readLine()) != null) {
				String datos[]=linea.split(",");
				Empleado emp= new Empleado(Integer.parseInt(datos[0]), datos[1], datos[2], datos[3], Integer.parseInt(datos[4]), Integer.parseInt(datos[5]));
				empleados.add(emp);
			}
			
		} catch (IOException e) {
			System.out.println("Error al leer el fichero");
		}
	}
	public static Connection conexionPostgres() {
		Connection conexion= null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion=DriverManager.getConnection("urlPostgres","user", "password");
			System.out.println("??");
			return conexion;
		} catch (ClassNotFoundException e) {
			System.out.println("Error al conectar con bbdd postgres, no encuentra driver");
		} catch (SQLException e) {
			System.out.println("Error al conectar con bbdd postgres");
	}
		return null;
		
}
	

	

	public static void crearTablas(Connection conexion) {
		try {
		
			Statement creacionEmp=conexion.createStatement();
			Statement creacionDep=conexion.createStatement();
			System.out.println(creacionDep.getUpdateCount());
			String consulta="CREATE TABLE IF NOT EXISTS Departamento (codigo INT,"
					+ "nombre VARCHAR(255),"
					+ "comunidad VARCHAR(255),"
					+ "PRIMARY KEY(codigo))";
			creacionDep.executeUpdate(consulta);
			
			consulta="CREATE TABLE IF NOT EXISTS Empleado "
					+ "(codigo INT,"
					+ "nombre VARCHAR(255),"
					+ "apellido VARCHAR(255),"
					+ "puesto VARCHAR(255),"
					+ "salario INT,"
					+ "codDepartamento INT,"
					+ "PRIMARY KEY(codigo),"
					+ "FOREIGN KEY(codDepartamento) REFERENCES Departamento(codigo))";
			int filas=creacionEmp.executeUpdate(consulta);
			System.out.println(filas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void inserts(Connection conexion, ArrayList<Departamento> departamentos,
			ArrayList<Empleado> empleados) {
		PreparedStatement ps=null;
		try {
			ps = conexion.prepareStatement("INSERT INTO Departamento VALUES(?,?,?)");
			for(Departamento d : departamentos) {
				ps.setInt(1, d.getCodigo());
				ps.setString(2, d.getNombre());
				ps.setString(3, d.getComunidad());
				int filas = ps.executeUpdate();
			}
			ps= conexion.prepareStatement("INSERT INTO Empleado VALUES(?,?,?,?,?,?)");
			for(Empleado e : empleados) {
				ps.setInt(1, e.getCodigo());
				ps.setString(2, e.getNombre());
				ps.setString(3, e.getApellido());
				ps.setString(4, e.getPuesto());
				ps.setInt(5, e.getSalario());
				ps.setInt(6, e.getCodDepartamento());
				int filas = ps.executeUpdate();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void consultas(Connection conexion) {
			
		try {
			Path destino = Paths.get("path");
			BufferedWriter bw = Files.newBufferedWriter(destino);

			Statement consulta = conexion.createStatement();
			ResultSet result = consulta.executeQuery("SELECT nombre FROM Departamento");
			Statement consulta2 = conexion.createStatement();
			ResultSet result2 = consulta2.executeQuery("SELECT * FROM empleado WHERE codDepartamento=20");
			while(result.next()) {
				System.out.println(result.getString(1));
				bw.write(result.getString(1) + "\n");
			}
			bw.write("********************************************\n");
			while(result2.next()) {
				System.out.println(result2.getInt(1) + " " + result2.getString(2)+ " "
						+ result2.getString(3) + " " + result2.getString(4)+ " " + result2.getInt(5) + " " + result2.getInt(6));
				bw.write(result2.getInt(1) + " " + result2.getString(2)+ " "
						+ result2.getString(3) + " " + result2.getString(4)+ " " + result2.getInt(5) + " " + result2.getInt(6)+"\n");
			}
			bw.flush();
			
			
		} catch (SQLException e) {
			System.out.println("error al lanzar consulta");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error al escribir el fichero txt");
		}
	}
	public static void generarXml(ArrayList<Departamento> departamentos, ArrayList<Empleado> empleados) {
		Document documento=null;
		File file = new File("path");
		
			
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = factoria.newDocumentBuilder();
			documento = db.newDocument();
			Element empresa = documento.createElement("empresa");
			documento.appendChild(empresa);
			Element deps = documento.createElement("departamentos");
			empresa.appendChild(deps);
			for(Departamento d : departamentos) {
				Element departamento = documento.createElement("departamento");
				deps.appendChild(departamento);
				Element codigo = documento.createElement("codigo");
				codigo.setTextContent(String.valueOf(d.getCodigo()));
				departamento.appendChild(codigo);
				Element nombre = documento.createElement("nombre");
				nombre.setTextContent(d.getNombre());
				departamento.appendChild(nombre);
				Element comunidad=documento.createElement("comunidad");
				comunidad.setTextContent(d.getComunidad());
				departamento.appendChild(comunidad);
			}
			Element emps = documento.createElement("empleados");
			empresa.appendChild(emps);
			for(Empleado e : empleados) {
				Element empleado = documento.createElement("empleado");
				emps.appendChild(empleado);
				Element codigo = documento.createElement("codigo");
				codigo.setTextContent(String.valueOf(e.getCodigo()));
				empleado.appendChild(codigo);
				Element nombre = documento.createElement("nombre");
				nombre.setTextContent(e.getNombre());
				empleado.appendChild(nombre);
				Element apellido = documento.createElement("apellido");
				apellido.setTextContent(e.getApellido());
				empleado.appendChild(apellido);
				Element puesto =  documento.createElement("puesto");
				puesto.setTextContent(e.getPuesto());
				empleado.appendChild(puesto);
				Element salario = documento.createElement("salario");
				salario.setTextContent(String.valueOf(e.getSalario()));
				empleado.appendChild(salario);
				Element codDep = documento.createElement("cod_departamento");
				codDep.setTextContent(String.valueOf(e.getCodDepartamento()));
				empleado.appendChild(codDep);
				
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
		
			transformer = tf.newTransformer();
			DOMSource dom = new DOMSource(documento);
			StreamResult sr = new StreamResult(file);
			transformer.transform(dom, sr);
		} catch (TransformerConfigurationException e) {
			System.out.println("error al crear xml");
		} catch (TransformerException e) {
			System.out.println("error al crear xml");
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
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
