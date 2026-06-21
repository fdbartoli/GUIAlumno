package persona;

import exceptions.CantidadMateriasInvalidaException;
import exceptions.DniInvalidoException;
import exceptions.EstadoInvalidoException;
import exceptions.FechaInvalidaException;
import exceptions.NombreApellidoInvalidoException;
import exceptions.PromedioInvalidoException;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

public class Alumno extends Persona {

    private static final String EMPTY_STRING = "";
    private static final String SLASH = "/";

    private double promedio;
    private LocalDate fecIng;
    private short cantMatAprob;
    private char estado; // A = Activo, B = Baja

    public Alumno() {
    }

    public Alumno(
            int dni,
            String nombre,
            String apellido,
            LocalDate fecNac,
            double promedio,
            LocalDate fecIng,
            short cantMatAprob,
            char estado)
            throws DniInvalidoException,
                   NombreApellidoInvalidoException,
                   FechaInvalidaException,
                   PromedioInvalidoException,
                   CantidadMateriasInvalidaException,
                   EstadoInvalidoException {

        super(dni, nombre, apellido, fecNac);
        setPromedio(promedio);
        setFecIng(fecIng);
        setCantMatAprob(cantMatAprob);
        setEstado(estado);
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) throws PromedioInvalidoException {
        if (promedio < 0 || promedio > 10) {
            throw new PromedioInvalidoException("El promedio es inválido");
        }

        this.promedio = promedio;
    }

    public LocalDate getFecIng() {
        return fecIng;
    }

    public void setFecIng(LocalDate fecIng) throws FechaInvalidaException {
        if (fecIng == null) {
            throw new FechaInvalidaException("La fecha de ingreso no puede ser nula");
        }

        if (getFecNac() != null && fecIng.isBefore(getFecNac())) {
            throw new FechaInvalidaException(
                    "La fecha de ingreso no puede ser anterior a la fecha de nacimiento");
        }

        if (getFecNac() != null &&
            fecIng.isBefore(getFecNac().plusYears(17))) {

            throw new FechaInvalidaException(
                    "El alumno debe tener al menos 17 años al ingresar");
        }

        this.fecIng = fecIng;
    }

    public String getFecIngStr() {
        if (fecIng == null) {
            return EMPTY_STRING;
        }

        String dayOfMonth = StringUtils.leftPad(
                String.valueOf(fecIng.getDayOfMonth()), 2, "0");

        return EMPTY_STRING
                + dayOfMonth
                + SLASH
                + fecIng.getMonthValue()
                + SLASH
                + fecIng.getYear();
    }

    public short getCantMatAprob() {
        return cantMatAprob;
    }

    public void setCantMatAprob(short cantMatAprob)
            throws CantidadMateriasInvalidaException {

        if (cantMatAprob < 0) {
            throw new CantidadMateriasInvalidaException(
                    "La cantidad de materias aprobadas no puede ser negativa");
        }

        this.cantMatAprob = cantMatAprob;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) throws EstadoInvalidoException {
        if (estado != 'A' && estado != 'B') {
            throw new EstadoInvalidoException("El estado debe ser A (Activo) o B (Baja)");
        }

        this.estado = estado;
    }

    @Override
    public String toString() {
        return super.toString()
                + " Alumno{"
                + "promedio=" + promedio
                + ", fecIng=" + getFecIngStr()
                + ", cantMatAprob=" + cantMatAprob
                + ", estado=" + estado
                + '}';
    }
}
