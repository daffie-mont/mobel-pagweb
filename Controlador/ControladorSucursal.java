
package Controlador;

import Vista.Sucursal;
import Vista.Prestamo;
import Modelo.ModeloBiblioteca;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author EVADAFNEDILIAN
 */
public class ControladorSucursal implements ActionListener {
    private Sucursal vistaSucursal;
    
    ModeloBiblioteca modelo = new ModeloBiblioteca();
    Prestamo vistaPrestamo = new Prestamo();                            
    ControladorPrestamo cp = new ControladorPrestamo(modelo, vistaPrestamo);
    
    public ControladorSucursal(Sucursal vistaSucursal)
    {
        this.vistaSucursal = vistaSucursal;
        this.vistaSucursal.btnAceptar.addActionListener(this);
    }
    
    public void iniciarVista()
    {
        vistaSucursal.setTitle("Seleccionar sucursal");
        vistaSucursal.pack();
        vistaSucursal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaSucursal.setLocationRelativeTo(null);        
        vistaSucursal.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(vistaSucursal.btnAceptar == e.getSource())
        {
            cp.iniciarVista();
            this.vistaSucursal.dispose();
        }
    }
}
