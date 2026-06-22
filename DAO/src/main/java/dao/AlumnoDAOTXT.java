/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exceptions.CantidadMateriasInvalidaException;
import exceptions.DniInvalidoException;
import exceptions.EstadoInvalidoException;
import exceptions.FechaInvalidaException;
import exceptions.NombreApellidoInvalidoException;
import exceptions.PromedioInvalidoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persona.Alumno;
import utils.AlumnoUtils;

public class AlumnoDAOTXT extends DAO<Alumno, Integer> {

    private RandomAccessFile raf;

    AlumnoDAOTXT(String pathfile) throws DAOException {
        try {
            raf = new RandomAccessFile(pathfile, "rws");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error I/O: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void create(Alumno alumno) throws DAOException {
        try {
            if (exist(alumno.getDni())) {
                throw new DAOException("El alumno con DNI " + alumno.getDni() + " ya existe");
            }
            raf.seek(raf.length()); // Se posiciona al final del archivo

            final String alumno2String = AlumnoUtils.alumno2String(alumno);
            raf.writeBytes(alumno2String + System.lineSeparator());
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error I/O: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public Alumno read(Integer dni) 
            throws DAOException {
        try {
            raf.seek(0); // Se posiciona al comienzo
            String linea;
            while ((linea = raf.readLine()) != null) {
                String dniTxt = linea.substring(0, 8);
                if (Integer.valueOf(dniTxt).equals(dni)) {
                    return AlumnoUtils.string2Alumno(linea);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getLocalizedMessage());
        } catch (DniInvalidoException |
                 FechaInvalidaException |
                 EstadoInvalidoException |
                 NombreApellidoInvalidoException |
                 CantidadMateriasInvalidaException |
                 PromedioInvalidoException ex) {

            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al mapear datos (DNI="
                   + dni
                   + "): "
                   + ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void update(Alumno alu) throws DAOException {
        // raf.getFilePointer() para poder posicionarse al inicio del alumno a 
        // actualizar
    }

    @Override
    public void delete(Integer dni) throws DAOException {
        Alumno alu2Delete = read(dni);
        if (alu2Delete == null) {
            throw new DAOException("El alumno con DNI " + dni + " no existe en el archivo.");
        }
        try {
            alu2Delete.setEstado('B');
            update(alu2Delete);
        } catch (EstadoInvalidoException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("TXT - Error al dar de baja (DNI="
                   + dni
                   + "): "
                   + ex.getLocalizedMessage());
        }
    }

    @Override
    public List<Alumno> findAll(boolean all) throws DAOException {
        List<Alumno> alumnos = new ArrayList<>();
        try {
            raf.seek(0);
            String linea;
            while ((linea = raf.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Alumno alumno = AlumnoUtils.string2Alumno(linea);
                if (all || alumno.getEstado() != 'B') {
                    alumnos.add(alumno);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error I/O: " + ex.getLocalizedMessage());
        } catch (DniInvalidoException |
                 FechaInvalidaException |
                 EstadoInvalidoException |
                 NombreApellidoInvalidoException |
                 CantidadMateriasInvalidaException |
                 PromedioInvalidoException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al parsear línea: " + ex.getLocalizedMessage());
        }
        return alumnos;
    }

    @Override
    public void close() throws DAOException {
        try {
            raf.close();
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getLocalizedMessage());
        }
    }

    @Override
    public boolean exist(Integer dni) throws DAOException {
        try {
            raf.seek(0); // Se posiciona al comienzo
            String linea;
            while ((linea = raf.readLine()) != null) {
                String dniTxt = linea.substring(0, 8);
                if (Integer.valueOf(dniTxt).equals(dni)) {
                    return true;
                }
            }
            return false;
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getLocalizedMessage());
        }
    }

}
