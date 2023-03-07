package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name="libros")
public class Libro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Double precio;
	private String nombre;
	private String autor;
	public Long getId() {
		return id;
	}
	public Libro(String nombre, String autor, Double precio, Date createAt,String foto) {
		this.nombre=nombre;
		this.autor=autor;
		this.precio=precio;
		this.createAt=createAt;
		this.foto=foto;
	}
	
	public Libro() {}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createAt;
	
	@PrePersist
	public void prePersit() {
		createAt= new Date();
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	private String foto;

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
