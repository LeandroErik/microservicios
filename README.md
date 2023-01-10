# microservicios
Implementando micro servicios en un sistema de venta de productos personalizados

## ¿Como ejecutarlo?

Primero se debe correr el servnombres,encargado de exponer las ips en el eureka ,asi poder usar las conexiones,status,y demas datos.

Luego el gateway,quien es el encaragdo de redistribuir la caraga de las conexiones entre los microservicios.

## Luego corremos los microservicios:

1- el ms de porductobase

  Quien es el encargado de crear producto base,asignarle un vendedor,y asignarles areas y tipos de personalizacion
  
2- el ms de producto personalizado

  Encargado agregarle una personalizacion a un producto base y calcular su precio
  
3- el ms de ventas

  Encargado de crear y agregar items (de productos personalizados) al carrito de compras,Tambien de generar la orden de compra para su furuta gestion.
