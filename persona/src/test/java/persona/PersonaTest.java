/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persona;

import exceptions.DniInvalidoException;
import exceptions.FechaInvalidaException;
import exceptions.NombreApellidoInvalidoException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author Grupo 6
 */
public class PersonaTest {

    @Test
    public void crearPersonaValida() throws Exception {
        Persona persona = new Persona(
                12345678,
                "Juan",
                "Perez",
                LocalDate.of(1990, 1, 1)
        );

        assertEquals(12345678, persona.getDni());
        assertEquals("Juan", persona.getNombre());
        assertEquals("Perez", persona.getApellido());
        assertEquals(LocalDate.of(1990, 1, 1), persona.getFecNac());
        assertTrue(persona.getEdad() > 0);
    }

    @Test
    public void dniInvalidoLanzaException() {
        assertThrows(DniInvalidoException.class, () -> {
            new Persona(0, "Juan", "Perez", LocalDate.of(1990, 1, 1));
        });
    }

    @Test
    public void nombreVacioLanzaException() {
        assertThrows(NombreApellidoInvalidoException.class, () -> {
            new Persona(12345678, "   ", "Perez", LocalDate.of(1990, 1, 1));
        });
    }

    @Test
    public void apellidoVacioLanzaException() {
        assertThrows(NombreApellidoInvalidoException.class, () -> {
            new Persona(12345678, "Juan", "", LocalDate.of(1990, 1, 1));
        });
    }

    @Test
    public void fechaNacimientoFuturaLanzaException() {
        assertThrows(FechaInvalidaException.class, () -> {
            new Persona(12345678, "Juan", "Perez", LocalDate.now().plusDays(1));
        });
    }
    
    @Test
    public void nombreNuloLanzaException() {
        assertThrows(NombreApellidoInvalidoException.class, () -> {
            new Persona(12345678, null, "Perez", LocalDate.of(1990, 1, 1));
        });
    }

    @Test
    public void apellidoNuloLanzaException() {
        assertThrows(NombreApellidoInvalidoException.class, () -> {
            new Persona(12345678, "Juan", null, LocalDate.of(1990, 1, 1));
        });
    }

    @Test
    public void fechaNacimientoNulaLanzaException() {
        assertThrows(FechaInvalidaException.class, () -> {
            new Persona(12345678, "Juan", "Perez", null);
        });
    }
}