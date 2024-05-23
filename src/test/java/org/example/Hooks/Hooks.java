package org.example.Hooks;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.Utilities.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public class Hooks {
    public static WebDriver driver;

    @BeforeClass
    public static void openBrowser() {
        WebDriverManager.safaridriver().setup();
        driver = new SafariDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String baseUrl = ConfigReader.getProperty("base.url");
        driver.get(baseUrl);
    }

    @AfterClass
    public static void quit() {
        driver.close();
    }
}
