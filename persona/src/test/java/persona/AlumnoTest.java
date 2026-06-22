/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persona;

import exceptions.CantidadMateriasInvalidaException;
import exceptions.EstadoInvalidoException;
import exceptions.FechaInvalidaException;
import exceptions.PromedioInvalidoException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Grupo 6
 */

public class AlumnoTest {

    @Test
    public void crearAlumnoValido() throws Exception {
        Alumno alumno = new Alumno(
                12345678,
                "Ana",
                "Gomez",
                LocalDate.of(2000, 5, 10),
                8.5,
                LocalDate.of(2020, 3, 1),
                (short) 12,
                'A'
        );

        assertEquals(8.5, alumno.getPromedio());
        assertEquals((short) 12, alumno.getCantMatAprob());
        assertEquals('A', alumno.getEstado());
        assertEquals(LocalDate.of(2020, 3, 1), alumno.getFecIng());
    }

    @Test
    public void promedioMenorACeroLanzaException() {
        assertThrows(PromedioInvalidoException.class, () -> {
            new Alumno(
                    12345678,
                    "Ana",
                    "Gomez",
                    LocalDate.of(2000, 5, 10),
                    -1,
                    LocalDate.of(2020, 3, 1),
                    (short) 12,
                    'A'
            );
        });
    }

    @Test
    public void promedioMayorADiezLanzaException() {
        assertThrows(PromedioInvalidoException.class, () -> {
            new Alumno(
                    12345678,
                    "Ana",
                    "Gomez",
                    LocalDate.of(2000, 5, 10),
                    11,
                    LocalDate.of(2020, 3, 1),
                    (short) 12,
                    'A'
            );
        });
    }

    @Test
    public void cantidadMateriasNegativaLanzaException() {
        assertThrows(CantidadMateriasInvalidaException.class, () -> {
            new Alumno(
                    12345678,
                    "Ana",
                    "Gomez",
                    LocalDate.of(2000, 5, 10),
                    8,
                    LocalDate.of(2020, 3, 1),
                    (short) -1,
                    'A'
            );
        });
    }

    @Test
    public void estadoInvalidoLanzaException() {
        assertThrows(EstadoInvalidoException.class, () -> {
            new Alumno(
                    12345678,
                    "Ana",
                    "Gomez",
                    LocalDate.of(2000, 5, 10),
                    8,
                    LocalDate.of(2020, 3, 1),
                    (short) 12,
                    'X'
            );
        });
    }

    @Test
    public void fechaIngresoAnteriorANacimientoLanzaException() {
        assertThrows(FechaInvalidaException.class, () -> {
            new Alumno(
                    12345678,
                    "Ana",
                    "Gomez",
                    LocalDate.of(2000, 5, 10),
                    8,
                    LocalDate.of(1999, 3, 1),
                    (short) 12,
                    'A'
            );
        });
    }

    @Test
    public void alumnoMenorDe17AniosAlIngresarLanzaException() {
        assertThrows(FechaInvalidaException.class, () -> {

            new Alumno(
                    12345678,
                    "Ana",
                    "Gomez",
                    LocalDate.of(2010, 1, 1),
                    8,
                    LocalDate.of(2025, 1, 1),
                    (short) 10,
                    'A'
            );

        });
    }
}