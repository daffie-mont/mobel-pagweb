<?php
session_start();
$mensaje="";

if(isset($_POST['btnAccion'])){
    
    switch($_POST['btnAccion']){
            
        case 'Agregar':
            
            if(is_numeric( openssl_decrypt($_POST['id'],COD,KEY ))){
                $ID=openssl_decrypt($_POST['id'],COD,KEY );
                $mensaje.="OK ID correcto".$ID."<br/>";
                
            }else{
                $mensaje.="Upss.. ID incorrecto".$ID."<br/>"; 
            }
            
            if(is_string( openssl_decrypt($_POST['nombre'],COD,KEY ))){
                $Nombre=openssl_decrypt($_POST['nombre'],COD,KEY );
                $mensaje.="OK Nombre".$Nombre."<br/>";
            }else{ 
                $mensaje.="Upss.. nombre incorrecto"."<br/>";
                break;
            }
            
            if(is_numeric( openssl_decrypt($_POST['cantidad'],COD,KEY ))){
                $Cantidad=openssl_decrypt($_POST['cantidad'],COD,KEY );
                $mensaje.="OK Cantidad correcto".$Cantidad."<br/>";
            }else{
                $mensaje.="Upss.. cantidad incorrecto".$Cantidad."<br/>"; 
                break;
            }
            
            if(is_numeric( openssl_decrypt($_POST['precio'],COD,KEY ))){
                $Precio=openssl_decrypt($_POST['precio'],COD,KEY );
                $mensaje.="OK precio correcto".$Precio."<br/>";
            }else{
                $mensaje.="Upss.. Precio incorrecto".$Precio."<br/>"; 
                break;
            }
            
            if(!isset($_SESSION['Carrito'])){
                $producto=array(
                    'ID'=>$ID,
                    'Nombre'=>$Nombre,
                    'Cantidad'=>$Cantidad,
                    'Precio'=>$Precio,
                    );
                $_SESSION['Carrito'][0]=$producto;
                $mensaje="Producto agregado al carrito";
                
            }
            else{
                $idProductos=array_column($_SESSION['Carrito'],"ID");
                if(in_array($ID,$idProductos)){
                    echo "<script>alert('El producto ya a sido seleccionado...')
                    </script>";
                    $mensaje="Producto agregado al carrito";
                }else{
                $numeroProductos=count($_SESSION['Carrito']);
                $producto=array(    
                    'ID'=>$ID,
                    'Nombre'=>$Nombre,
                    'Cantidad'=>$Cantidad,
                    'Precio'=>$Precio,
                    );
                $_SESSION['Carrito'][$numeroProductos]=$producto;
                $mensaje="Producto agregado al carrito";
                    }
            }
            //$mensaje=print_r($_SESSION,true);
            break;
        case "Eliminar":
            if(is_numeric( openssl_decrypt($_POST['id'],COD,KEY ))){
                $ID=openssl_decrypt($_POST['id'],COD,KEY );

                foreach($_SESSION['Carrito'] as $indice=>$producto){
                    if($producto['ID']==$ID){
                        unset($_SESSION['Carrito'][$indice]);
                        echo "<script>alert('Elemento borrado...')</script>";
                    }
                    
                }
            }else{
                $mensaje.="Upss.. ID incorrecto".$ID."<br/>"; 
            }                
            break;
}
}
?>