package com.magadiflo.commons.examenes.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "asignaturas")
public class Asignatura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	/**
	 * Como la relación entre padre e hijo (Asignatura) son del tipo FetchType.LAZY
	 * (carga perezosa) en la relación ManyToOne - OneToMany, por detrás de escena
	 * se genera un proxy, por lo tanto cuando el padre llama a los hijos con el
	 * get... y lo mismo cuando el hijo llama al padre con el get..., ahí recién se
	 * hace la consulta y trae los hijos o el padre, pero es un proxy. Este proxy
	 * también maneja atributos que al final se traducen en el JSON y estos
	 * atributos a veces provocan errores y es importante tener que deshabilitarlos,
	 * sobre todo cuando la relación es Bidireccional, como en este caso Padre <->
	 * Hijo.
	 * 
	 * Esos atributos a omitir son: "handler", "hibernateLazyInitializer"
	 */

	// Muchas asignaturas hijas asociadas a un padre
	@JsonIgnoreProperties(value = { "hijos", "handler", "hibernateLazyInitializer" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Asignatura padre;

	// Una asignatura, muchos hijos
	@JsonIgnoreProperties(value = { "padre", "handler", "hibernateLazyInitializer" }, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "padre", cascade = CascadeType.ALL)
	private List<Asignatura> hijos;

	public Asignatura() {
		this.hijos = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Asignatura getPadre() {
		return padre;
	}

	public void setPadre(Asignatura padre) {
		this.padre = padre;
	}

	public List<Asignatura> getHijos() {
		return hijos;
	}

	public void setHijos(List<Asignatura> hijos) {
		this.hijos = hijos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Asignatura [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append("]");
		return builder.toString();
	}

}
