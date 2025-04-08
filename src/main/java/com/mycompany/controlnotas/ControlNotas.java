/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.controlnotas;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Denisse Joya
 */
public class ControlNotas extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JTabbedPane tabbedPane;
    private JTable estudiantesTable, materiasTable, gruposTable, notasTable;
    private DefaultTableModel estudiantesModel, materiasModel, gruposModel, notasModel;
    private JTextField estudianteField, grupoField, materiaField;
    private JButton addEstudianteButton, addMateriaButton, addGrupoButton, addNotaButton, guardarButton;
    private JComboBox<String> estudianteCombo, grupoCombo;

    public ControlNotas() {
        setTitle("Sistema de Control de Notas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Estudiantes", createEstudiantesPanel());
        tabbedPane.addTab("Materias", createMateriasPanel());
        tabbedPane.addTab("Grupos", createGruposPanel());
        tabbedPane.addTab("Control de Notas", createNotasPanel());

        add(tabbedPane);
    }

    private JPanel createEstudiantesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        estudianteField = new JTextField();
        addEstudianteButton = new JButton("Agregar Estudiante");

        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(estudianteField);
        inputPanel.add(addEstudianteButton);

        String[] columnNames = {"Nombre", "Eliminar"};
        estudiantesModel = new DefaultTableModel(columnNames, 0);
        estudiantesTable = new JTable(estudiantesModel);

        addEstudianteButton.addActionListener(e -> {
            agregarElemento(estudianteField, estudiantesModel);
            actualizarComboBox(estudiantesModel, estudianteCombo);
        });

        agregarEventoEliminar(estudiantesTable, estudiantesModel);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(estudiantesTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMateriasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        materiaField = new JTextField();
        addMateriaButton = new JButton("Agregar Materia");

        inputPanel.add(new JLabel("Materia:"));
        inputPanel.add(materiaField);
        inputPanel.add(addMateriaButton);

        String[] columnNames = {"Materia", "Eliminar"};
        materiasModel = new DefaultTableModel(columnNames, 0);
        materiasTable = new JTable(materiasModel);

        addMateriaButton.addActionListener(e -> {
            agregarElemento(materiaField, materiasModel);
        });

        agregarEventoEliminar(materiasTable, materiasModel);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(materiasTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGruposPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        grupoField = new JTextField();
        addGrupoButton = new JButton("Agregar Grupo");

        inputPanel.add(new JLabel("Grupo:"));
        inputPanel.add(grupoField);
        inputPanel.add(addGrupoButton);

        String[] columnNames = {"Grupo", "Eliminar"};
        gruposModel = new DefaultTableModel(columnNames, 0);
        gruposTable = new JTable(gruposModel);

        addGrupoButton.addActionListener(e -> {
            agregarElemento(grupoField, gruposModel);
            actualizarComboBox(gruposModel, grupoCombo);
        });

        agregarEventoEliminar(gruposTable, gruposModel);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(gruposTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createNotasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        estudianteCombo = new JComboBox<>();
        grupoCombo = new JComboBox<>();
        JTextField notaField = new JTextField();
        addNotaButton = new JButton("Agregar Nota");
        guardarButton = new JButton("Guardar Datos");

        inputPanel.add(new JLabel("Estudiante:"));
        inputPanel.add(estudianteCombo);
        inputPanel.add(new JLabel("Grupo:"));
        inputPanel.add(grupoCombo);
        inputPanel.add(new JLabel("Nota:"));
        inputPanel.add(notaField);
        inputPanel.add(addNotaButton);
        inputPanel.add(guardarButton);

        String[] columnNames = {"Estudiante", "Grupo", "Nota", "Eliminar"};
        notasModel = new DefaultTableModel(columnNames, 0);
        notasTable = new JTable(notasModel);

        addNotaButton.addActionListener(e -> agregarNota(estudianteCombo, grupoCombo, notaField));
        guardarButton.addActionListener(e -> guardarDatosEnArchivos());

        agregarEventoEliminar(notasTable, notasModel);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(notasTable), BorderLayout.CENTER);
        return panel;
    }

    private void agregarElemento(JTextField field, DefaultTableModel model) {
        String text = field.getText().trim();
        if (!text.isEmpty()) {
            model.addRow(new Object[]{text, "Eliminar"});
            field.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un valor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarComboBox(DefaultTableModel model, JComboBox<String> comboBox) {
        if (comboBox == null) return;
        comboBox.removeAllItems();
        for (int i = 0; i < model.getRowCount(); i++) {
            comboBox.addItem((String) model.getValueAt(i, 0));
        }
    }

    private void agregarNota(JComboBox<String> estudianteCombo, JComboBox<String> grupoCombo, JTextField notaField) {
        String estudiante = (String) estudianteCombo.getSelectedItem();
        String grupo = (String) grupoCombo.getSelectedItem();
        String nota = notaField.getText().trim();

        if (estudiante != null && grupo != null && !nota.isEmpty()) {
            notasModel.addRow(new Object[]{estudiante, grupo, nota, "Eliminar"});
            notaField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar estudiante, grupo y asignar una nota.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarEventoEliminar(JTable table, DefaultTableModel model) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (row != -1 && column == table.getColumnCount() - 1) {
                    int confirm = JOptionPane.showConfirmDialog(
                            ControlNotas.this,
                            "¿Deseas eliminar esta fila?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        model.removeRow(row);

                        // Actualizar combos si corresponde
                        if (table == estudiantesTable) {
                            actualizarComboBox(estudiantesModel, estudianteCombo);
                        } else if (table == gruposTable) {
                            actualizarComboBox(gruposModel, grupoCombo);
                        }
                    }
                }
            }
        });
    }

    private void guardarDatosEnArchivos() {
        guardarTablaEnArchivo(estudiantesModel, "estudiantes.txt");
        guardarTablaEnArchivo(materiasModel, "materias.txt");
        guardarTablaEnArchivo(gruposModel, "grupos.txt");
        guardarTablaEnArchivo(notasModel, "notas.txt");
        JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
    }

    private void guardarTablaEnArchivo(DefaultTableModel model, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            int columnCount = model.getColumnCount();
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < columnCount - 1; j++) { // Excluir la columna "Eliminar"
                    writer.write(model.getValueAt(i, j).toString());
                    if (j < columnCount - 2) {
                        writer.write(", ");
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar archivo: " + nombreArchivo);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControlNotas app = new ControlNotas();
            app.setVisible(true);
        });
    }
}
