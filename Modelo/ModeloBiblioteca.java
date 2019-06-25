//COSAS POR HACER :D
//Validar correos (opcional)

/*LISTOOOOO :DD*/
//Buscar por nombre en Prestamo y Buscar
//Radio button en Buscar
//Inner join en tabla de Buscar
//El mouse clicked en la tabla de libros de Prestamo y disponibilidad
//Combobox en Sucursal - Conexion
//Transacciones

package Modelo;

import Vista.Sucursal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 * @author EVADAFNEDILIAN
 */
public class ModeloBiblioteca {
    private Conexion conexion = new Conexion();
    Connection con = null;
    
    
    //AGREGAR UN NUEVO LIBRO
    public boolean insertarLibro(String nombre, int cantidad) throws SQLException
    {       
        try
        {
            //Para abrir una conexion
            con = conexion.getConnection();
            con.setAutoCommit(false);
            
            //Para detectar la sucursal donde se está dando de alta
            Sucursal vistaSucursal = new Sucursal();
            int seleccionado;
            seleccionado = vistaSucursal.cmbSucursal.getSelectedIndex();
            char sucursal='Z';
        
            switch(seleccionado)
            {
                case 0:
                    sucursal='A';
                    break;
                case 1:
                    sucursal='B';
                    break;
                case 2:
                    sucursal='C';
                    break;
            }            
            

            //Para ejecutar la consulta
            Statement s = con.createStatement();

            //Insertar en la tabla Libros       
            int registro = s.executeUpdate("INSERT INTO libros(nombre_libro,cantidad,disponible,sucursal) "
                    + "VALUES('"+nombre+"',"+cantidad+","+cantidad+",'"+sucursal+"');");
            
            con.commit();
            conexion.cerrarConexion(con);            
            return true;
        }
        catch(SQLException e)
        {
            con.rollback();
            return false;
        }             
    }
    
    //Realizar un nuevo prestamo a un usuario
    public boolean nuevoPrestamo(String nombre, String telefono, String correo, int idLibro) throws SQLException
    {       
        try
        {
            //Para abrir una conexion
            con = conexion.getConnection();
            con.setAutoCommit(false);

            //Para ejecutar la consulta
            Statement s = con.createStatement();
            //Insertar en la tabla Prestamos
            int registro = s.executeUpdate("INSERT INTO prestamos(nombre_cliente,telefono,correo,idLibro) VALUES('"+nombre+"','"+telefono+"','"+correo+"',"+idLibro+");");
            
            con.commit();
            conexion.cerrarConexion(con);           
            return true;
        }
        catch(SQLException e)
        {
            con.rollback();
            return false;
        }             
    }
    
    //Update que modifica la disponibilidad de un libro al prestarse
    public boolean actualizarDisponibilidad(int id, int disponible) throws SQLException
    {      
        disponible=disponible-1;
        
        try 
        {
            con = conexion.getConnection();
            con.setAutoCommit(false);
            
            Statement s = con.createStatement();
            int registro = s.executeUpdate("UPDATE libros SET disponible = "+disponible+" WHERE id = "+id+";");
            
            con.commit();
            conexion.cerrarConexion(con);            
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false;
        
        }
    }
    
    //HAcer devolucion
    public boolean actualizarDisponibilidadDev(int id, int disponible,int idPrestamo) throws SQLException
    {
        disponible=disponible+1;
        
        try 
        {
            con = conexion.getConnection();
            con.setAutoCommit(false);
            
            Statement s = con.createStatement();
            int registro = s.executeUpdate("UPDATE libros SET disponible = "+disponible+" WHERE id = "+id+";");
            registro = s.executeUpdate("UPDATE prestamos SET status = 0 WHERE id = "+idPrestamo+";");
            
            con.commit();
            conexion.cerrarConexion(con);            
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false;
        
        }
    }
    
    //COnsultar disponibilidad
    public int disponibilidad(int id)
    {
        int disponible = 0;
        
        String SQL="SELECT disponible FROM libros WHERE id="+id;
        
        try
        {            
            con = conexion.getConnection();           
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if(rs.next())
            {
                disponible = rs.getInt("disponible");
            }
            
            conexion.cerrarConexion(con);
        }
        catch(SQLException e)
        {
        }
        
        return disponible;
    }
    
    //COnsultar disponibilidad
    public int idLibro(int idPrestamo)
    {
        int disponible = 0;        
        String SQL="SELECT libros.id FROM prestamos inner join libros on libros.id=prestamos.idLibro WHERE prestamos.id="+idPrestamo;
        
        try
        {
            con = conexion.getConnection();
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if(rs.next())
            {
                disponible = rs.getInt("disponible");
            }
            
            conexion.cerrarConexion(con);
        }
        catch(SQLException e)
        {
        }
        
        return disponible;
    }
   
    //Consultar toda la tabla libros
    public DefaultTableModel consultarLibros() throws SQLException {
        try{
            //Para abrir conexión a la BD
            con = conexion.getConnection();
            
            //Para generar la consulta
            Statement s = con.createStatement();
            DefaultTableModel dtm;
            //Para establecer el modelo al JTable
            try (   //Ejecutamos la consulta                  
                    ResultSet rs = s.executeQuery("select id as Id,nombre_libro as LIBRO,cantidad as CANTIDAD, disponible as DISPONIBLE, sucursal as SUCURSAL  from libros;");
                ) 
            {
                //Para establecer el modelo al JTable
                dtm = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column) {
                        return true;
                    }
                };                

                //Obteniendo la informacion de las columnas que estan siendo consultadas
                ResultSetMetaData rsMd = rs.getMetaData();
                //La cantidad de columnas que tiene la consulta
                int cantidadColumnas = rsMd.getColumnCount();
                //Establecer como cabeceras el nombre de las columnas
                for (int i = 1; i <= cantidadColumnas; i++) {
                    dtm.addColumn(rsMd.getColumnLabel(i));
                }   //Creando las filas para el JTable
                while (rs.next()) {
                    Object[] fila = new Object[cantidadColumnas];
                    for (int i = 0; i < cantidadColumnas; i++) {
                        fila[i]=rs.getObject(i+1);
                    }
                    dtm.addRow(fila);
                }
                //Cerrar objeto de ResultSet
            }

            conexion.cerrarConexion(con);
            return dtm;        
        }
        catch(SQLException e){
            return null;
        }
    }
    
    //Consultar toda la informacion de los prestamos
    public DefaultTableModel consultarPrestamos(){
        try{
            //Para abrir conexión a la BD
            con = conexion.getConnection();            
            //Para generar la consulta
            Statement s = con.createStatement();
            DefaultTableModel dtm;
            //Para establecer el modelo al JTable
            try (   //Ejecutamos la consulta                  
                    ResultSet rs = s.executeQuery("SELECT prestamos.`id` AS ID, libros.nombre_libro AS LIBRO,nombre_cliente AS cliente,correo,telefono,sucursal FROM libros INNER JOIN prestamos ON libros.`id`=prestamos.`idLibro` WHERE STATUS=1;");
              
                    ) 
            {
                //Para establecer el modelo al JTable
                dtm = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column) {
                        return true;
                    }
                };                

                //Obteniendo la informacion de las columnas que estan siendo consultadas
                ResultSetMetaData rsMd = rs.getMetaData();
                //La cantidad de columnas que tiene la consulta
                int cantidadColumnas = rsMd.getColumnCount();
                //Establecer como cabeceras el nombre de las columnas
                for (int i = 1; i <= cantidadColumnas; i++) {
                    dtm.addColumn(rsMd.getColumnLabel(i));
                }   //Creando las filas para el JTable
                while (rs.next()) {
                    Object[] fila = new Object[cantidadColumnas];
                    for (int i = 0; i < cantidadColumnas; i++) {
                        fila[i]=rs.getObject(i+1);
                    }
                    dtm.addRow(fila);
                }
                //Cerrar objeto de ResultSet
            }            
            conexion.cerrarConexion(con);
            return dtm;        
        }
        catch(SQLException e){
            return null;
        }
    }
    
    
    //Buscar un libro en específico por su nombre
    public DefaultTableModel buscarLibro(String nombreLibro){
        try{
            //Para abrir conexión a la BD
            con = conexion.getConnection();            
            //Para generar la consulta
            Statement s = con.createStatement();            
            DefaultTableModel dtm;
            //Para establecer el modelo al JTable
            try (   //Ejecutamos la consulta                  
                    ResultSet rs = s.executeQuery("SELECT * FROM libros WHERE nombre_libro='"+nombreLibro+"';");
                ) 
            {
                //Para establecer el modelo al JTable
                dtm = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column) {
                        return true;
                    }
                };                

                //Obteniendo la informacion de las columnas que estan siendo consultadas
                ResultSetMetaData rsMd = rs.getMetaData();
                //La cantidad de columnas que tiene la consulta
                int cantidadColumnas = rsMd.getColumnCount();
                //Establecer como cabeceras el nombre de las columnas
                for (int i = 1; i <= cantidadColumnas; i++) {
                    dtm.addColumn(rsMd.getColumnLabel(i));
                }   //Creando las filas para el JTable
                while (rs.next()) {
                    Object[] fila = new Object[cantidadColumnas];
                    for (int i = 0; i < cantidadColumnas; i++) {
                        fila[i]=rs.getObject(i+1);
                    }
                    dtm.addRow(fila);
                }
                //Cerrar objeto de ResultSet
            }            
            conexion.cerrarConexion(con);
            return dtm;        
        }
        catch(SQLException e){
            return null;
        }
    }
    
    //Buscar qué libros tienen determinados clientes
    //O qué clientes tienen algún libro
    public DefaultTableModel buscarLibroCliente(String criterio,String nombre){
        try{
            //Para abrir conexión a la BD
            con = conexion.getConnection();            
            //Para generar la consulta
            Statement s = con.createStatement();            
            DefaultTableModel dtm;
            //Para establecer el modelo al JTable
            try (   //Ejecutamos la consulta                  
                    ResultSet rs = s.executeQuery(
                            "SELECT libros.id AS ID,nombre_libro as LIBRO, nombre_cliente AS CLIENTE,correo as CORREO,telefono AS TELEFONO,sucursal AS SUCURSAL FROM libros INNER JOIN prestamos ON libros.id=prestamos.idLibro WHERE "+criterio+"='"+nombre+"';");
                ) 
            {
                //Para establecer el modelo al JTable
                dtm = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column) {
                        return true;
                    }
                };                

                //Obteniendo la informacion de las columnas que estan siendo consultadas
                ResultSetMetaData rsMd = rs.getMetaData();
                //La cantidad de columnas que tiene la consulta
                int cantidadColumnas = rsMd.getColumnCount();
                //Establecer como cabeceras el nombre de las columnas
                for (int i = 1; i <= cantidadColumnas; i++) {
                    dtm.addColumn(rsMd.getColumnLabel(i));
                }   //Creando las filas para el JTable
                while (rs.next()) {
                    Object[] fila = new Object[cantidadColumnas];
                    for (int i = 0; i < cantidadColumnas; i++) {
                        fila[i]=rs.getObject(i+1);
                    }
                    dtm.addRow(fila);
                }
                //Cerrar objeto de ResultSet
            }            
            conexion.cerrarConexion(con);
            return dtm;        
        }
        catch(SQLException e){
            return null;
        }
    }
    
}
