<?php
include 'global/config.php';
include 'carrito.php';
include 'templates/cabecera.php';
?>

<br>
<h3>Lista del carrito</h3>
<?php if(!empty($_SESSION['Carrito'])) {?>
<table border="2">
    <tbody>
        <tr>
        <th width="40%" class="text-center">Descripción</th>
        <th width="15%" class="text-center">Cantidad</th>
        <th width="20%" class="text-center">Precio</th>
        <th width="20%" class="text-center">Total</th>
        <th width="5%" class="text-center" >--</th>
        </tr>
        <?php $total=0; ?>
        <?php foreach($_SESSION['Carrito'] as $indice=>$producto){?>

        <tr>
        <td width="40%"><?php echo $producto['Nombre']?></td>
        <td width="15%" class="text-center"><?php echo $producto['Cantidad']?></td>
        <td width="20%" class="text-center"><?php echo $producto['Precio']?></td>
        <td width="20%" class="text-center"><?php echo number_format($producto['Precio']*$producto['Cantidad'],2);?></td>
        <td width="5%">
         
        <form action="" method="post">
        <input type="hidden" name="id" id="id" value="<?php echo openssl_encrypt($producto['ID'],COD,KEY);?>">            
       <button class="btn btn-danger" type="submit" name="btnAccion" value="Eliminar"> Eliminar </button>   
        </form>
        
        </td> 
        </tr>
        <?php $total=$total+($producto['Precio']*$producto['Cantidad']); ?>
        <?php }?>
        <tr>
            <td colspan="3" aling="right"><h3>Total</h3></td>
            <td aling="right"><h3>$<?php echo number_format($total,2);?></h3></td>
            <td></td>
        </tr>
        
        
    </tbody>
</table>
<?php }else {?>
    <div class="alert alert-success">
    No hay productos en el carrito..    
        
    </div>
    
<?php }?>
<?php include 'templates/pie.php';?>
