# language: es
@test
Característica: Product - Store

  Esquema del escenario: Validación del precio de un producto
    Dado estoy en la página de la tienda
    Y me logueo con mi usuario "<usuario>" y clave "<clave>"
    Cuando navego a la categoria "<categoria>" y subcategoria "<subcategoria>"
    Y agrego <cantidad> unidades del primer producto al carrito
    Entonces valido en el popup la confirmación del producto agregado
    Y valido en el popup que el monto total sea calculado correctamente
    Cuando finalizo la compra
    Entonces valido el título de la página del carrito
    Y vuelvo a validar el cálculo de precios en el carrito

    Ejemplos:
      | usuario                   | clave        | categoria | subcategoria | cantidad |
      | mcastroascencio@gmail.com | Uu20161b517  | Clothes   | Men          | 2        |
      | usuario_invalido@test.com | ClaveFalsa12 | Clothes   | Men          | 2        |
      | mcastroascencio@gmail.com | Uu20161b517  | Autos     | Sedan        | 2        |
