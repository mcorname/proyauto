package com.nttdata.stepsdefinitions;

import com.nttdata.steps.StoreSteps;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.openqa.selenium.WebDriver;

import static com.nttdata.core.DriverManager.getDriver;

public class StoreStepsDef {

    private StoreSteps storeSteps;

    private StoreSteps getStoreSteps() {
        if (storeSteps == null) {
            WebDriver driver = getDriver();
            storeSteps = new StoreSteps(driver);
        }
        return storeSteps;
    }

    @Dado("estoy en la página de la tienda")
    public void estoy_en_la_pagina_de_la_tienda() {
        getStoreSteps().abrirPaginaTienda("https://qalab.bensg.com/store");
    }

    @Y("me logueo con mi usuario {string} y clave {string}")
    public void me_logueo_con_mi_usuario_y_clave(String usuario, String clave) {
        getStoreSteps().iniciarSesion(usuario, clave);
    }

    @Cuando("navego a la categoria {string} y subcategoria {string}")
    public void navego_a_la_categoria_y_subcategoria(String categoria, String subcategoria) {
        getStoreSteps().navegarCategoriaYSubcategoria(categoria, subcategoria);
    }

    @Y("agrego {int} unidades del primer producto al carrito")
    public void agrego_unidades_del_primer_producto_al_carrito(int cantidad) {
        getStoreSteps().agregarPrimerProducto(cantidad);
    }

    @Entonces("valido en el popup la confirmación del producto agregado")
    public void valido_en_el_popup_la_confirmacion_del_producto_agregado() {
        getStoreSteps().validarConfirmacionPopup();
    }

    @Y("valido en el popup que el monto total sea calculado correctamente")
    public void valido_en_el_popup_que_el_monto_total_sea_calculado_correctamente() {
        getStoreSteps().validarCalculoMontoPopup();
    }

    @Cuando("finalizo la compra")
    public void finalizo_la_compra() {
        getStoreSteps().finalizarCompra();
    }

    @Entonces("valido el título de la página del carrito")
    public void valido_el_titulo_de_la_pagina_del_carrito() {
        getStoreSteps().validarTituloCarrito();
    }

    @Y("vuelvo a validar el cálculo de precios en el carrito")
    public void vuelvo_a_validar_el_calculo_de_precios_en_el_carrito() {
        getStoreSteps().validarCalculoCarrito();
    }
}
