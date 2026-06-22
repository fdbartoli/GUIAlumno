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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persona.Alumno;
import utils.DateUtils;
import java.time.LocalDate;
import java.util.ArrayList;

public class AlumnoDAOSQL extends DAO<Alumno, Integer> {

    private final Connection connection;
    private final PreparedStatement insertPrepareStatement;
    private final PreparedStatement readPrepareStatement;

    AlumnoDAOSQL(String user, String password) throws DAOException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/universidad", user, password);
            System.out.println("dao.AlumnoDAOSQL.<init>() OK!!!");

            String insertSql = "INSERT INTO alumnos\n"
                    + "(DNI, NOMBRE, APELLIDO, FECNAC, FECING, PROMEDIO, CANTMATAPROB, ESTADO)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            insertPrepareStatement = connection.prepareStatement(insertSql);

            String readSql = "SELECT DNI, NOMBRE, APELLIDO, FECNAC, FECING, PROMEDIO, CANTMATAPROB, ESTADO FROM alumnos WHERE DNI = ?";
            readPrepareStatement = connection.prepareStatement(readSql);

        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error I/O: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void create(Alumno alumno) throws DAOException {
        try {
            int index = 1;
            insertPrepareStatement.setInt(index++, alumno.getDni());
            insertPrepareStatement.setString(index++, alumno.getNombre());
            insertPrepareStatement.setString(index++, alumno.getApellido());
            insertPrepareStatement.setDate(index++, DateUtils.localDate2SqlDate(LocalDate.of(2002, 5, 15)));
            insertPrepareStatement.setDate(index++, DateUtils.localDate2SqlDate(alumno.getFecIng()));
            insertPrepareStatement.setDouble(index++, alumno.getPromedio());

            insertPrepareStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error AL INSERTAR: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public Alumno read(Integer dni)  
            throws DAOException {
        try {
            readPrepareStatement.setInt(1, dni);
            final ResultSet rs = readPrepareStatement.executeQuery();
            if (rs.next()) {
                Alumno alu = new Alumno();
                alu.setDni(rs.getInt("DNI"));
                alu.setNombre(rs.getString("NOMBRE"));
                alu.setApellido(rs.getString("APELLIDO"));
                alu.setFecIng(DateUtils.sqlDate2LocalDate(rs.getDate("FECING")));
                alu.setFecNac(DateUtils.sqlDate2LocalDate(rs.getDate("FECNAC")));
                alu.setPromedio(rs.getDouble("PROMEDIO"));
                alu.setCantMatAprob(rs.getShort("CANTMATAPROB"));

                String estadoStr = rs.getString("ESTADO");
                alu.setEstado(estadoStr.charAt(0));
                return alu;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error AL LEER: " + ex.getLocalizedMessage());

        } catch (DniInvalidoException |
                 FechaInvalidaException |
                 EstadoInvalidoException |
                 NombreApellidoInvalidoException |
                 CantidadMateriasInvalidaException |
                 PromedioInvalidoException ex) {

            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al setear datos del alumno (DNI="
                    + dni
                    + "): "
                    + ex.getLocalizedMessage());
        }

        return null;
    }

    @Override
    public void update(Alumno entidad) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer dni) throws DAOException {
        String sql = "UPDATE alumnos SET ESTADO = 'B' WHERE DNI = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, dni);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new DAOException("No se encontró el alumno con DNI " + dni);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al eliminar: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public List<Alumno> findAll(boolean all) throws DAOException {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT DNI, NOMBRE, APELLIDO, FECNAC, FECING, PROMEDIO, CANTMATAPROB, ESTADO FROM alumnos";
        if (!all) {
            sql += " WHERE ESTADO = 'A'";
        }
        Integer dniActual = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Alumno alu = new Alumno();
                dniActual=rs.getInt("DNI");
                alu.setDni(rs.getInt("DNI"));
                alu.setNombre(rs.getString("NOMBRE"));
                alu.setApellido(rs.getString("APELLIDO"));
                alu.setFecNac(DateUtils.sqlDate2LocalDate(rs.getDate("FECNAC")));
                alu.setFecIng(DateUtils.sqlDate2LocalDate(rs.getDate("FECING")));
                alu.setPromedio(rs.getDouble("PROMEDIO"));
                alu.setCantMatAprob(rs.getShort("CANTMATAPROB"));
                
                String estadoStr = rs.getString("ESTADO");
                alu.setEstado(estadoStr.charAt(0));
                lista.add(alu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al listar alumnos: " + ex.getLocalizedMessage());
        } catch (DniInvalidoException |
                 FechaInvalidaException |
                 EstadoInvalidoException |
                 NombreApellidoInvalidoException |
                 CantidadMateriasInvalidaException |
                 PromedioInvalidoException ex) {

            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al mapear datos (DNI="
                   + dniActual
                   + "): "
                   + ex.getLocalizedMessage());
        }
        return lista;
    }

    @Override
    public void close() throws DAOException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Erro al cerrar la conexión:" + ex.getLocalizedMessage());
        }

    }

    @Override
    public boolean exist(Integer id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
