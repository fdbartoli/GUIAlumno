/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.alumnogui;

import exceptions.CantidadMateriasInvalidaException;
import exceptions.DniInvalidoException;
import exceptions.EstadoInvalidoException;
import exceptions.FechaInvalidaException;
import exceptions.NombreApellidoInvalidoException;
import exceptions.PromedioInvalidoException;
import org.apache.commons.lang3.StringUtils;
import persona.Alumno;

public final class AlumnoMapper {

    private AlumnoMapper() {
    }

    public static AlumnoDTO entity2Dto(Alumno alu) {
        AlumnoDTO dto = new AlumnoDTO();
        dto.setDni(String.valueOf(alu.getDni()));
        dto.setNombre(StringUtils.defaultString(alu.getNombre()).trim());
        dto.setApellido(StringUtils.defaultString(alu.getApellido()).trim());
        dto.setFecNac(alu.getFecNac());
        dto.setFecIng(alu.getFecIng());
        dto.setEstado(alu.getEstado());
        dto.setPromedio(alu.getPromedio());
        dto.setCantMatAprob(alu.getCantMatAprob());
        return dto;
    }

    public static Alumno dto2Entity(AlumnoDTO dto)
            throws DniInvalidoException,
                   NombreApellidoInvalidoException,
                   FechaInvalidaException,
                   PromedioInvalidoException,
                   CantidadMateriasInvalidaException,
                   EstadoInvalidoException {

        Alumno alu = new Alumno();

        alu.setDni(Integer.valueOf(dto.getDni()));
        alu.setNombre(dto.getNombre());
        alu.setApellido(dto.getApellido());
        alu.setFecNac(dto.getFecNac());
        alu.setFecIng(dto.getFecIng());
        alu.setEstado(dto.getEstado());
        alu.setPromedio(dto.getPromedio());
        alu.setCantMatAprob(dto.getCantMatAprob());

        return alu;
    }
}
