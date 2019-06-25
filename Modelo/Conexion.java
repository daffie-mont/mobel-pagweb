
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Vista.Sucursal;
import javax.swing.JOptionPane;

/**
 * @author Daf
 */
public class Conexion 
{
    
    public Connection getConnection()
    {       
        Sucursal vistaSucursal = new Sucursal();
        //int seleccionado;
        String direccion="";
        
        if(vistaSucursal.cmbSucursal.getSelectedIndex() == 0)
            direccion = "192.168.43.125";
        else if(vistaSucursal.cmbSucursal.getSelectedIndex() == 1)
            direccion = "192.168.43.133";
        else if(vistaSucursal.cmbSucursal.getSelectedIndex() == 2)
            direccion = "192.168.43.170";
        
        /*seleccionado = vistaSucursal.cmbSucursal.getSelectedIndex();
        
        switch(seleccionado)
        {
            case 0:
                direccion = "192.168.43.133";
                break;
            case 1:
                direccion = "192.168.43.125";
                break;
            case 2:
                direccion = "192.168.43.125";
                break;
        }*/
        
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://"+direccion+":3306/biblioteca";
        String usuario = "root";
        String pass = "dafnem15";
        
        try {
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error con el driver");
            e.printStackTrace();
        }            
        
        try {
            return DriverManager.getConnection(url,usuario,pass);
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error con la conexion");
            e.printStackTrace();
        }
        return null;
    }           
    
    public void cerrarConexion(Connection c) throws SQLException
    {        
        try
        {
            if(!c.isClosed())
            {
                c.close();
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error al cerrar la conexión");
        }        
    }
}
