package com.nttdata.core;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    private static WebDriver driver;
    private static Scenario scenario;

    public static WebDriver getDriver() {
        return driver;
    }

    @Before(order = 0)
    public void setUp() {
        // Detectar SO
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            // Windows OS
            System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        } else if (os.contains("mac")) {
            // Mac OS
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        } else {
            throw new IllegalStateException("Unsupported operating system: " + os);
        }

        // HTTP Factory
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Before(order = 1)
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @After
    public void quitDriver() {
        if (scenario != null && scenario.isFailed()) {
            try {
                screenShot();
            } catch (Exception ignored) {
            }
        }
        if (driver != null) {
            driver.quit();
        }
    }


    public static void screenShot() {
        if (driver != null && scenario != null) {
            byte[] evidencia = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(evidencia, "image/png", "evidencias");
        }
    }

    // Pausa amigable en segundos para que dé tiempo a narrar en el video
    public static void pausar(int segundos) {
        try {
            Thread.sleep(segundos * 1000L);
        } catch (InterruptedException ignored) {
        }
    }

    public static void scrollDown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
    }

    public static void esperaImplicita(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
