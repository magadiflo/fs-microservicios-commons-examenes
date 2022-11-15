# Sección 5: Backend: Microservicio Exámenes
37. Creando librería commons para reutilizar clases entity de exámenes

# Registros como ejemplo a usar en tabla asignaturas
```
-- Serán las asignaturas Padre (la base)
INSERT INTO asignaturas(id, nombre) VALUES(1, 'Matemática');
INSERT INTO asignaturas(id, nombre) VALUES(2, 'Lenguaje');
INSERT INTO asignaturas(id, nombre) VALUES(3, 'Inglés');
INSERT INTO asignaturas(id, nombre) VALUES(4, 'Ciencias Naturales');
INSERT INTO asignaturas(id, nombre) VALUES(5, 'Ciencias Sociales e Historia');
INSERT INTO asignaturas(id, nombre) VALUES(6, 'Música');
INSERT INTO asignaturas(id, nombre) VALUES(7, 'Artes');

-- Insertando asignaturas Hijas
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(8, 'Álgebra', 1);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(9, 'Aritmética', 1);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(10, 'Trigonometría', 1);

INSERT INTO asignaturas(id, nombre, padre_id) VALUES(11, 'Lectura y comprensión', 2);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(12, 'Verbos', 2);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(13, 'Gramática', 2);

INSERT INTO asignaturas(id, nombre, padre_id) VALUES(14, 'Inglés', 3);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(15, 'Gramática', 3);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(16, 'Verbos', 3);

INSERT INTO asignaturas(id, nombre, padre_id) VALUES(17, 'Ciencias Naturales', 4);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(18, 'Biología', 4);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(19, 'Física', 4);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(20, 'Química', 4);

INSERT INTO asignaturas(id, nombre, padre_id) VALUES(21, 'Historia', 5);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(22, 'Ciencias Sociales', 5);
INSERT INTO asignaturas(id, nombre, padre_id) VALUES(23, 'Filosofía', 5);

INSERT INTO asignaturas(id, nombre, padre_id) VALUES(24, 'Música', 6);

INSERT INTO asignaturas(id, nombre, padre_id) VALUES(25, 'Arte', 7);
```
