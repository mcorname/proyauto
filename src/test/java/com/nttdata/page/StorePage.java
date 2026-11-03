package com.nttdata.page;

import org.openqa.selenium.By;

public class StorePage {

    // --- Elementos de Inicio de Sesión y Autenticación ---
    public static By loginLink = By.cssSelector(".user-info a");
    public static By emailInput = By.id("field-email");
    public static By passwordInput = By.id("field-password");
    public static By loginButton = By.id("submit-login");
    public static By userAccount = By.cssSelector(".user-info a.account");
    public static By logoutLink = By.cssSelector(".user-info a.logout");
    public static By authAlert = By.cssSelector(".alert-danger");

    // Buscar categoría por texto
    public static By getCategoryLink(String categoria) {
        return By.xpath("//ul[@id='top-menu']/li/a[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), '" + categoria.toUpperCase() + "')]");
    }

    // Buscar subcategoría por texto
    public static By getSubcategoryLink(String subcategoria) {
        return By.xpath("//ul[@id='top-menu']//a[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), '" + subcategoria.toUpperCase() + "')]");
    }

    // Elementos de la Página de Producto
    public static By firstProduct = By.cssSelector(".product-miniature:first-child a.product-thumbnail, .product-miniature a.product-thumbnail");
    public static By quantityInput = By.id("quantity_wanted");
    public static By addToCartButton = By.cssSelector("button.add-to-cart");

    // Elementos de la Ventana Emergente (Popup / Modal)
    public static By modalPopup = By.id("blockcart-modal");
    public static By modalTitle = By.id("myModalLabel");
    public static By modalUnitPrice = By.cssSelector("#blockcart-modal p.product-price");
    public static By modalQuantity = By.cssSelector("#blockcart-modal .product-quantity strong");
    public static By modalTotal = By.cssSelector("#blockcart-modal .subtotal.value");
    public static By modalCheckoutButton = By.cssSelector("#blockcart-modal .cart-content-btn a.btn-primary");

    // Elementos de la Página del Carrito
    public static By cartTitle = By.cssSelector("h1, .cart-grid-body h1");
    public static By cartUnitPrice = By.cssSelector(".cart-item .current-price .price, .cart-item .price");
    public static By cartTotal = By.cssSelector("#cart-subtotal-products .value, .cart-total .value");
}
