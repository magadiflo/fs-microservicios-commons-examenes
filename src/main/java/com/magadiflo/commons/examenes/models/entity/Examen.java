package com.magadiflo.commons.examenes.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "examenes")
public class Examen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	// @JsonIgnoreProperties(..), Con esto evitamos la relación inversa (evitamos
	// que se genera un ciclo ya que la relación es Bidireccional).
	// allowSetters = true, para evitar posibles errores, lo único que queremos es
	// que suprima el atributo examen de cada pregunta, pero que sí permita asignar
	// mediante el set la relación inversa
	@JsonIgnoreProperties(value = { "examen" }, allowSetters = true)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "examen")
	private List<Pregunta> preguntas;

	@ManyToOne(fetch = FetchType.LAZY)
	private Asignatura asignatura;

	public Examen() {
		this.preguntas = new ArrayList<>();
	}

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas.clear(); // Importante limpiar antes la lista, ya que cada elemento será agregado en
								// addPregunta(..)
		preguntas.forEach(this::addPregunta); // Cada pregunta se pasa al método addPregunta(..)
	}

	public void addPregunta(Pregunta pregunta) {
		this.preguntas.add(pregunta);
		// ...setExamen(this), con esto establecemos la relación inversa,
		// para que guarde en la FK el id del examen
		pregunta.setExamen(this);
	}

	public void removePregunta(Pregunta pregunta) {
		this.preguntas.remove(pregunta);
		// A esa pregunta le quitamos la referencia del examen, quedándo huérfana,
		// de esa manera como está en orphanRemoval=true, se eliminará
		pregunta.setExamen(null);
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Examen other = (Examen) obj;
		return this.id != null && this.id.equals(other.getId());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Examen [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", createAt=");
		builder.append(createAt);
		builder.append("]");
		return builder.toString();
	}
}
