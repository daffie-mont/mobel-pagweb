
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.*;
import Vista.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author EVADAFNEDILIAN
 */
public class ControladorLibros implements ActionListener{
    private ModeloBiblioteca modelo;
    private NuevoLibro vistaLibros;      
    
    public ControladorLibros(ModeloBiblioteca modelo, NuevoLibro vistaLibros)
    {
        this.modelo = modelo;
        this.vistaLibros = vistaLibros;
        this.vistaLibros.btnAceptar.addActionListener(this);
        this.vistaLibros.miBuscar.addActionListener(this);
        this.vistaLibros.miPrestamo.addActionListener(this);
    }
    
    public void iniciarVista()
    {
        vistaLibros.setTitle("Agregar un nuevo libro");
        vistaLibros.pack();
        vistaLibros.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaLibros.setLocationRelativeTo(null);
        vistaLibros.setVisible(true);
    }
    
    public void limpiarCajasTexto()
    {
        vistaLibros.txtLibro.setText("");
        vistaLibros.txtCantidad.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(vistaLibros.btnAceptar == e.getSource())
        {
            if(modelo.insertarLibro(vistaLibros.txtLibro.getText(),
                    Integer.parseInt(vistaLibros.txtCantidad.getText())))
            {
                JOptionPane.showMessageDialog(null, "¡Libro registrado exitosamente!");
                limpiarCajasTexto();
            }
        }
        if(e.getSource() == vistaLibros.miPrestamo) {
            Prestamo vistaPrestamo = new Prestamo();        
            ControladorPrestamo controladorPrestamo = new ControladorPrestamo(modelo, vistaPrestamo);
            controladorPrestamo.iniciarVista();
            vistaLibros.dispose();
        }
        if(e.getSource() == vistaLibros.miBuscar) {
            Buscar vistaBuscar = new Buscar();
            ControladorBuscar controladorBuscar = new ControladorBuscar(this.modelo, vistaBuscar);
            controladorBuscar.iniciarVista();
            vistaLibros.dispose();
        }      
    }
    
}
