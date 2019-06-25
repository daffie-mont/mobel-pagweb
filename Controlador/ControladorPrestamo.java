
package Controlador;

import Modelo.ModeloBiblioteca;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author EVADAFNEDILIAN
 */
public class ControladorPrestamo implements ActionListener,MouseListener {
    private ModeloBiblioteca modelo;
    private Prestamo vistaPrestamo;
    private int idLibroPrestar;
    private int disponibilidadActual;
    
    
    public ControladorPrestamo(ModeloBiblioteca modelo, Prestamo vistaPrestamo)
    {
        this.modelo = modelo;
        this.vistaPrestamo = vistaPrestamo;
        this.vistaPrestamo.btnAceptar.addActionListener(this);
        this.vistaPrestamo.btnBuscar.addActionListener(this);
        this.vistaPrestamo.miBuscar.addActionListener(this);
        this.vistaPrestamo.miLibro.addActionListener(this);
        this.vistaPrestamo.tbLibros.addMouseListener(this);
    }
    
    public void iniciarVista()
    {
        vistaPrestamo.setTitle("Préstamo de libros");
        vistaPrestamo.pack();
        vistaPrestamo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaPrestamo.setLocationRelativeTo(null);
        vistaPrestamo.tbLibros.setModel(modelo.consultarLibros());
        vistaPrestamo.setVisible(true);
    }
    
    public void limpiarCajasTexto()
    {
        vistaPrestamo.txtBuscar.setText("");
        vistaPrestamo.txtNombre.setText("");
        vistaPrestamo.txtTelefono.setText("");
        vistaPrestamo.txtCorreo.setText("");
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(vistaPrestamo.tbLibros == e.getSource())
        {
            int fila = vistaPrestamo.tbLibros.rowAtPoint(e.getPoint());
            if (fila > -1)
            {
                this.idLibroPrestar = Integer.parseInt(vistaPrestamo.tbLibros.getValueAt(fila, 0).toString());
                this.disponibilidadActual = Integer.parseInt(String.valueOf(vistaPrestamo.tbLibros.getValueAt(fila, 3)));
                JOptionPane.showMessageDialog(null, "ID libro: "+this.idLibroPrestar+"\nDisponibles: "+this.disponibilidadActual);
            }
        }
        else
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún libro");
    }

    @Override
    public void mousePressed(MouseEvent e) {        
    }

    @Override
    public void mouseReleased(MouseEvent e) {        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {       
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Busca el título de algún libro en la base de datos
        if(vistaPrestamo.btnBuscar == e.getSource())
        {
            vistaPrestamo.tbLibros.setModel(modelo.buscarLibro(vistaPrestamo.txtBuscar.getText()));
        }
        //Para realizar un préstamo
        else if(vistaPrestamo.btnAceptar == e.getSource())
        {
            if(modelo.nuevoPrestamo(vistaPrestamo.txtNombre.getText(),
                    vistaPrestamo.txtTelefono.getText(),vistaPrestamo.txtCorreo.getText(),this.idLibroPrestar))
            {
                //Actualizamos la disponibilidad
                if(disponibilidadActual>=1){
                modelo.actualizarDisponibilidad(this.idLibroPrestar,this.disponibilidadActual);
                
                JOptionPane.showMessageDialog(null, "¡Préstamo realizado exitosamente!");
                limpiarCajasTexto();
                vistaPrestamo.tbLibros.setModel(modelo.consultarLibros());
                }else{
                    JOptionPane.showMessageDialog(null, "No hay libros disponibles");
                }
            }
        }
        
        if(e.getSource() == this.vistaPrestamo.miLibro) {
            NuevoLibro vistaLibro = new NuevoLibro();
            ControladorLibros controladorLibros = new ControladorLibros(this.modelo, vistaLibro);
            controladorLibros.iniciarVista();
            this.vistaPrestamo.dispose();
        }
        if(e.getSource() == this.vistaPrestamo.miBuscar) {
            Buscar vistaBuscar = new Buscar();
            ControladorBuscar controladorBuscar = new ControladorBuscar(this.modelo, vistaBuscar);
            controladorBuscar.iniciarVista();
            this.vistaPrestamo.dispose();
        }      
    }
}
