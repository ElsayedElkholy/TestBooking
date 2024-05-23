package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[contains(@role,'dialog')]//button[contains(@type,'button')]")
    private WebElement closeDialogButton;

    @FindBy(xpath = "//div[@id='indexsearch']//input[contains(@name,'ss')]")
    private WebElement searchBox;

    @FindBy(xpath = "//div[@id='indexsearch']//button[contains(@type,'submit')]")
    private WebElement searchButton;

    @FindBy(xpath = "//button[@data-testid='date-display-field-start']")
    private WebElement dateBox;

    @FindBy(xpath = "//button[contains(@aria-label, 'Next month')]")
    private WebElement clickOnNextMonth;

    @FindBy(xpath = "//time/span[(@class='bui-date__title')][1]")
    public WebElement bookingCheckInDate;

    @FindBy(xpath = "//time/span[(@class='bui-date__title')][2]")
    public WebElement bookingCheckOutDate;

    public void closeDialog() {
        closeDialogButton.click();
    }

    public void searchFor(String location) {
        searchBox.click();
        searchBox.sendKeys(location);
    }

    public void selectDate(String date) {
        String dynamicXPath = String.format("//span[contains(@aria-label,'%s')]", date);
        boolean elementFound = false;

        for (int i = 0; i < 5; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
                WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dynamicXPath)));
                dateElement.click();
                elementFound = true;
                break;
            } catch (Exception e) {
                clickOnNextMonth.click();
            }
        }

        if (!elementFound) {
            throw new RuntimeException("Element not found after 5 iterations");
        }
    }

    public void submitSearch() {
        searchButton.click();
    }

    public List<String> getFirstThreeHotelNames() {
        List<String> hotelNames = new ArrayList<>();
        List<WebElement> hotelNameElements = driver.findElements(By.xpath("//div[contains(@data-testid,'title')]"));

        for (int i = 0; i < Math.min(3, hotelNameElements.size()); i++) {
            hotelNames.add(hotelNameElements.get(i).getText());
        }
        return hotelNames;
    }

    public void clickSeeAvailabilityForHotel(String hotelName) {
        String xpath = String.format("//div[contains(@data-testid, 'title') and contains(text(),'%s')]/parent::div//a[@data-testid='availability-cta-btn']", hotelName);
        WebElement seeAvailabilityButton = driver.findElement(By.xpath(xpath));
        seeAvailabilityButton.click();
    }

    public void selectBedType(String bedTypeValue) {
        String radioButtonXPath = String.format("//label//input[@type='radio' and @value='%s']", bedTypeValue);
        WebElement radioButton = driver.findElement(By.xpath(radioButtonXPath));
        radioButton.click();
    }

    public void selectRoomOption(String optionValue) {
        WebElement dropdown = driver.findElement(By.xpath("//select[@data-testid='select-room-trigger']"));
        Select select = new Select(dropdown);
        select.selectByValue(optionValue);
    }
}
