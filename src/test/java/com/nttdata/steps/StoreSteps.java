package com.nttdata.steps;

import com.nttdata.core.DriverManager;
import com.nttdata.page.StorePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StoreSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private int cantidadComprada = 1;

    // Constructor que inicializa el navegador y la espera explícita
    public StoreSteps(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 1. Abrir la página web de la tienda
    public void abrirPaginaTienda(String url) {
        driver.get(url);
        DriverManager.screenShot();
    }

    // 2. Iniciar sesión con usuario y clave
    public void iniciarSesion(String usuario, String clave) {
        // Hacemos clic en el enlace para ir al formulario de login
        WebElement enlaceLogin = wait.until(ExpectedConditions.elementToBeClickable(StorePage.loginLink));
        enlaceLogin.click();

        // Escribimos el correo electrónico
        WebElement campoEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(StorePage.emailInput));
        campoEmail.clear();
        campoEmail.sendKeys(usuario);

        // Escribimos la contraseña
        WebElement campoClave = driver.findElement(StorePage.passwordInput);
        campoClave.clear();
        campoClave.sendKeys(clave);

        DriverManager.screenShot();

        // Hacemos clic en el botón de iniciar sesión
        driver.findElement(StorePage.loginButton).click();

        // Validamos la autenticación: revisamos si logramos entrar a la pantalla principal
        validarAutenticacion();
    }

    // Validar que el inicio de sesión haya sido exitoso
    public void validarAutenticacion() {
        boolean sesionIniciada = false;

        try {
            // Si el login fue bueno, debe aparecer el botón de Cerrar sesión o el nombre de usuario
            WebDriverWait esperaCorta = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement elementoCuenta = esperaCorta.until(ExpectedConditions.visibilityOfElementLocated(StorePage.logoutLink));
            sesionIniciada = elementoCuenta.isDisplayed();
        } catch (Exception e) {
            sesionIniciada = false;
        }

        // Si falló el login, tomamos captura del error y lanzamos la alerta
        DriverManager.screenShot();

        Assertions.assertTrue(sesionIniciada, 
                "Error de autenticación: Las credenciales son inválidas o no se pudo llegar a la pantalla principal.");
    }

    // 3. Navegar a la categoría y subcategoría
    public void navegarCategoriaYSubcategoria(String categoria, String subcategoria) {
        // Buscamos si la categoría existe en el menú
        By categoriaLocator = StorePage.getCategoryLink(categoria);
        List<WebElement> categoriasEncontradas = driver.findElements(categoriaLocator);

        // Si la lista está vacía, tomamos captura y fallamos como se espera
        if (categoriasEncontradas.isEmpty()) {
            DriverManager.screenShot();
            Assertions.fail("Error de catálogo: La categoría '" + categoria + "' no existe en la tienda.");
        }

        WebElement elemCategoria = wait.until(ExpectedConditions.visibilityOfElementLocated(categoriaLocator));

        // Pasamos el ratón por encima de la categoría para que se despliegue el menú
        Actions actions = new Actions(driver);
        actions.moveToElement(elemCategoria).perform();

        // Buscamos y hacemos clic en la subcategoría
        By subcatLocator = StorePage.getSubcategoryLink(subcategoria);
        WebElement elemSubcat = wait.until(ExpectedConditions.elementToBeClickable(subcatLocator));
        elemSubcat.click();

        DriverManager.screenShot();
    }

    // 4. Agregar cantidad de unidades del primer producto al carrito
    public void agregarPrimerProducto(int cantidad) {
        this.cantidadComprada = cantidad;

        // Clic en el primer producto de la lista
        WebElement primerProducto = wait.until(ExpectedConditions.elementToBeClickable(StorePage.firstProduct));
        primerProducto.click();

        // Cambiamos la cantidad en la cajita
        WebElement campoCantidad = wait.until(ExpectedConditions.visibilityOfElementLocated(StorePage.quantityInput));
        campoCantidad.sendKeys(Keys.CONTROL + "a");
        campoCantidad.sendKeys(Keys.BACK_SPACE);
        campoCantidad.sendKeys(String.valueOf(cantidad));

        DriverManager.screenShot();

        // Clic en el botón Añadir al carrito
        WebElement botonAgregar = wait.until(ExpectedConditions.elementToBeClickable(StorePage.addToCartButton));
        botonAgregar.click();
    }

    // 5. Validar la confirmación del producto agregado en el popup
    public void validarConfirmacionPopup() {
        WebElement tituloModal = wait.until(ExpectedConditions.visibilityOfElementLocated(StorePage.modalTitle));
        String textoTitulo = tituloModal.getText().trim();

        DriverManager.screenShot();

        Assertions.assertTrue(textoTitulo.contains("Producto añadido correctamente"),
                "El mensaje de confirmación en el popup no es el esperado. Se obtuvo: " + textoTitulo);
    }

    // 6. Validar que el monto total en el popup esté bien calculado
    public void validarCalculoMontoPopup() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(StorePage.modalPopup));

        // Obtenemos el precio unitario y el monto total
        String textoPrecioUnitario = wait.until(ExpectedConditions.visibilityOfElementLocated(StorePage.modalUnitPrice)).getText();
        String textoTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(StorePage.modalTotal)).getText();

        double precioUnitario = extraerNumero(textoPrecioUnitario);
        double totalPopup = extraerNumero(textoTotal);
        double totalCalculado = precioUnitario * this.cantidadComprada;

        DriverManager.screenShot();

        // Verificamos que cantidad * precioUnitario sea igual al total mostrado
        Assertions.assertEquals(totalCalculado, totalPopup, 0.01,
                "El monto total en el popup (" + totalPopup + ") no coincide con el cálculo esperado (" + totalCalculado + ")");
    }

    // 7. Finalizar la compra desde el popup
    public void finalizarCompra() {
        DriverManager.screenShot();
        WebElement botonFinalizar = wait.until(ExpectedConditions.elementToBeClickable(StorePage.modalCheckoutButton));
        botonFinalizar.click();
    }

    // 8. Validar el título de la página del carrito
    public void validarTituloCarrito() {
        WebElement tituloCarrito = wait.until(ExpectedConditions.visibilityOfElementLocated(StorePage.cartTitle));
        String texto = tituloCarrito.getText().trim();

        DriverManager.screenShot();

        Assertions.assertTrue(texto.equalsIgnoreCase("CARRITO") || driver.getTitle().contains("Carrito"),
                "El título de la página del carrito no es el esperado. Se obtuvo: " + texto);
    }

    // 9. Validar el cálculo de precios en el carrito
    public void validarCalculoCarrito() {
        String textoPrecioUnitario = wait.until(ExpectedConditions.visibilityOfElementLocated(StorePage.cartUnitPrice)).getText();
        String textoTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(StorePage.cartTotal)).getText();

        double precioUnitario = extraerNumero(textoPrecioUnitario);
        double totalCarrito = extraerNumero(textoTotal);
        double totalCalculado = precioUnitario * this.cantidadComprada;

        DriverManager.screenShot();

        Assertions.assertEquals(totalCalculado, totalCarrito, 0.01,
                "El monto total en el carrito (" + totalCarrito + ") no coincide con el cálculo esperado (" + totalCalculado + ")");
    }

    // Método auxiliar muy simple para convertir textos como 'S/ 19.12' en números como 19.12
    private double extraerNumero(String texto) {
        String limpio = texto.replaceAll("[^0-9.]", "").trim();
        return Double.parseDouble(limpio);
    }
}
