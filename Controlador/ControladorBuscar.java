
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
 *
 * @author EVADAFNEDILIAN
 */
public class ControladorBuscar implements ActionListener,MouseListener{
    private ModeloBiblioteca modelo;
    private Buscar vistaBuscar;
    private int idLibro;
    private int disponible;
    private int idPrestamo;
    
    public ControladorBuscar(ModeloBiblioteca modelo, Buscar vistaBuscar)
    {
        this.modelo = modelo;
        this.vistaBuscar = vistaBuscar;
        this.vistaBuscar.btnAceptar.addActionListener(this);
        this.vistaBuscar.miLibro.addActionListener(this);
        this.vistaBuscar.miPrestamo.addActionListener(this);
        this.vistaBuscar.botones.add(vistaBuscar.rbtnLibro);
        this.vistaBuscar.botones.add(vistaBuscar.rbtnCliente);
        this.vistaBuscar.btnDevolver.addActionListener(this);
        this.vistaBuscar.tbBuscar.addMouseListener(this);
    }
    
    public void iniciarVista()
    {
        vistaBuscar.setTitle("Buscar");
        vistaBuscar.pack();
        vistaBuscar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaBuscar.setLocationRelativeTo(null);       
        vistaBuscar.tbBuscar.setModel(modelo.consultarPrestamos());

        vistaBuscar.setVisible(true);
    }
    
     public void mouseClicked(MouseEvent e) {
        if(vistaBuscar.tbBuscar == e.getSource())
        {
            int fila = vistaBuscar.tbBuscar.rowAtPoint(e.getPoint());
            if (fila > -1)
            {
                this.idPrestamo = Integer.parseInt(vistaBuscar.tbBuscar.getValueAt(fila, 0).toString());
                 this.idLibro=modelo.idLibro(idPrestamo);
                this.disponible=modelo.disponibilidad(idLibro);
               
                System.out.println("seleccionado: "+this.idLibro+"  disponible: "+this.disponible+"id: "+this.idPrestamo);
            }
        }
        else
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún libro");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaBuscar.miPrestamo) {
            Prestamo vistaPrestamo = new Prestamo();        
            ControladorPrestamo controladorPrestamo = new ControladorPrestamo(modelo, vistaPrestamo);
            controladorPrestamo.iniciarVista();
            vistaBuscar.dispose();
        }
        //Para realizar un préstamo
         if(vistaBuscar.btnDevolver == e.getSource())
        {
           
                //Actualizamos la disponibilidad
             
                modelo.actualizarDisponibilidadDev(this.idLibro,this.disponible,this.idPrestamo);
                
                JOptionPane.showMessageDialog(null, "¡Préstamo devuelto exitosamente!");
                vistaBuscar.tbBuscar.setModel(modelo.consultarPrestamos());
             
            
        }
        
        if(e.getSource() == vistaBuscar.miLibro) {
            NuevoLibro vistaLibro = new NuevoLibro();
            ControladorLibros controladorLibros = new ControladorLibros(modelo, vistaLibro);
            controladorLibros.iniciarVista();
            vistaBuscar.dispose();
        }
        
        if(vistaBuscar.rbtnLibro.isSelected())
        {
            this.vistaBuscar.tbBuscar.setModel(modelo.buscarLibroCliente("nombre_libro",vistaBuscar.txtBuscar.getText()));
        }
        else if(vistaBuscar.rbtnCliente.isSelected())
        {
            this.vistaBuscar.tbBuscar.setModel(modelo.buscarLibroCliente("nombre_cliente",vistaBuscar.txtBuscar.getText()));
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado nada");
        }
        
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
    
}
