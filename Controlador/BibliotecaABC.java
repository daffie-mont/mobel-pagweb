package Controlador;
import Vista.Sucursal;
import java.net.InetAddress;

/**
 * @author Luis
 */
public class BibliotecaABC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            InetAddress direccion = InetAddress.getLocalHost();
            String nombreDelHost = direccion.getHostName();//nombre host
            String IP_local = direccion.getHostAddress();//ip como String
            System.out.println("La IP de la maquina local es : " + IP_local);
            System.out.println("El nombre del host local es : " + nombreDelHost);
         
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        
        Sucursal vistaSucursal = new Sucursal();
        ControladorSucursal controladorSucursal = new ControladorSucursal(vistaSucursal);
        
        controladorSucursal.iniciarVista();
        
    }
    
}
