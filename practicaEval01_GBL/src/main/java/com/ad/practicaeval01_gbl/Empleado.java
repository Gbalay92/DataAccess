package com.ad.practicaeval01_gbl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Empleado {
	@Id
	private int codigo;
	@Column
	private String nombre;
	@Column
	private String apellido;
	@Column
	private String puesto;
	@Column
	private int salario;
	@Column
	private int codDepartamento;
	public Empleado(int codigo, String nombre, String apellido, String puesto, int salario, int codDepartamento) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.puesto = puesto;
		this.salario = salario;
		this.codDepartamento = codDepartamento;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public int getSalario() {
		return salario;
	}
	public void setSalario(int salario) {
		this.salario = salario;
	}
	public int getCodDepartamento() {
		return codDepartamento;
	}
	public void setCodDepartamento(int codDepartamento) {
		this.codDepartamento = codDepartamento;
	}
	@Override
	public String toString() {
		return "Empleado [codigo=" + codigo + ", nombre=" + nombre + ", apellido=" + apellido + ", puesto=" + puesto
				+ ", salario=" + salario + ", codDepartamento=" + codDepartamento + "]";
	}
	
	
}
