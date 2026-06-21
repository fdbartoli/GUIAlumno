package gui.alumnogui;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import persona.Alumno;

/**
 * Renderizador personalizado para pintar las filas de la tabla de alumnos.
 * Los alumnos inactivos (estado 'I') se muestran con fondo rojo tenue.
 */
public class AlumnoTableRenderer extends DefaultTableCellRenderer {
    
    private List<Alumno> alumnos;
    
    /**
     * Constructor que recibe la lista de alumnos.
     * @param alumnos Lista de alumnos para consultar el estado de cada fila.
     */
    public AlumnoTableRenderer(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
    
    /**
     * Actualiza la lista de alumnos (cuando se recargan los datos).
     * @param alumnos Nueva lista de alumnos.
     */
    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Obtener el componente por defecto (JLabel)
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Solo aplicar colores si la fila no está seleccionada y hay datos
        if (!isSelected && alumnos != null && row < alumnos.size()) {
            Alumno alu = alumnos.get(row);
            
            // Si el estado es 'I' (inactivo) -> fondo rojo tenue
            if (alu.getEstado() == 'I') {
                c.setBackground(new Color(255, 200, 200)); // rojo suave
            } else {
                c.setBackground(Color.WHITE); // fondo blanco para activos
            }
        }
        
        return c;
    }
}