package com.ad.practicaeval01_gbl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Departamento {
	@Id
	private int codigo;
	@Column
	private String nombre;
	@Column
	private String comunidad;
	
	public Departamento(int codigo, String nombre, String comunidad) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.comunidad = comunidad;
		
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getComunidad() {
		return comunidad;
	}

	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	@Override
	public String toString() {
		return "Departamento [codigo=" + codigo + ", nombre=" + nombre + ", comunidad=" + comunidad + "]";
	}
	
}
