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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "examenes")
public class Examen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nombre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	/**
	 * @Transient, le especifica que esta propidad no es persistente, no está
	 * mapeado a un campo en la tabla de la BD. Y eso es lo que queremos, queremos
	 * que este atributo no se encuentre en la BD, no esté mapeado a ningún campo, y
	 * ¿por qué?, porque si lo mapeamos a la tabla y campo de examenes, si lo
	 * marcamos como true, se aplicará a todos los alumnos, y es probable que
	 * algunos alumnos no hayan respondiendo este examen, y si agregamos este campo
	 * en la BD, se agregará para todos y la idea es que sea solo para un alumno en
	 * particular
	 */
	@Transient
	private boolean respondido;

	// @JsonIgnoreProperties(..), Con esto evitamos la relación inversa (evitamos
	// que se genera un ciclo ya que la relación es Bidireccional).
	// allowSetters = true, para evitar posibles errores, lo único que queremos es
	// que suprima el atributo examen de cada pregunta, pero que sí permita asignar
	// mediante el set la relación inversa
	@JsonIgnoreProperties(value = { "examen" }, allowSetters = true)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "examen")
	private List<Pregunta> preguntas;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Asignatura asignaturaPadre;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Asignatura asignaturaHija;

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

	public boolean isRespondido() {
		return respondido;
	}

	public void setRespondido(boolean respondido) {
		this.respondido = respondido;
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

	public Asignatura getAsignaturaPadre() {
		return asignaturaPadre;
	}

	public void setAsignaturaPadre(Asignatura asignaturaPadre) {
		this.asignaturaPadre = asignaturaPadre;
	}

	public Asignatura getAsignaturaHija() {
		return asignaturaHija;
	}

	public void setAsignaturaHija(Asignatura asignaturaHija) {
		this.asignaturaHija = asignaturaHija;
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
		builder.append(", respondido=");
		builder.append(respondido);
		builder.append("]");
		return builder.toString();
	}
}
