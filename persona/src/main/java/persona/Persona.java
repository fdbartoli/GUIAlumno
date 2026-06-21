/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persona;

import exceptions.DniInvalidoException;
import exceptions.FechaInvalidaException;
import exceptions.NombreApellidoInvalidoException;
import java.time.LocalDate;
import java.time.Period;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author g.guzman - collaborators Grupo 6
 */

public class Persona {

    private int dni;
    private String nombre;
    private String apellido;
    private LocalDate fecNac;

    public Persona() {
    }

    public Persona(int dni, String nombre, String apellido, LocalDate fecNac)
            throws DniInvalidoException,
                   NombreApellidoInvalidoException,
                   FechaInvalidaException {

        setDni(dni);
        setNombre(nombre);
        setApellido(apellido);
        setFecNac(fecNac);
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) throws DniInvalidoException {
        if (dni <= 0) {
            throw new DniInvalidoException("El DNI debe ser mayor a cero");
        }
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws NombreApellidoInvalidoException {
        if (StringUtils.isBlank(nombre)) {
            throw new NombreApellidoInvalidoException("El nombre es inválido");
        }
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) throws NombreApellidoInvalidoException {
        if (StringUtils.isBlank(apellido)) {
            throw new NombreApellidoInvalidoException("El apellido es inválido");
        }
        this.apellido = apellido.trim();
    }

    public LocalDate getFecNac() {
        return fecNac;
    }

    public void setFecNac(LocalDate fecNac) throws FechaInvalidaException {
        if (fecNac == null) {
            throw new FechaInvalidaException("La fecha de nacimiento no puede ser nula");
        }

        if (fecNac.isAfter(LocalDate.now())) {
            throw new FechaInvalidaException("La fecha de nacimiento no puede ser futura");
        }

        this.fecNac = fecNac;
    }

    public int getEdad() {
        if (fecNac == null) {
            return 0;
        }

        return Period.between(fecNac, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Persona{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fecNac=" + fecNac +
                ", edad=" + getEdad() +
                '}';
    }
}
