package org.example.tests;

import org.example.Pages.HomePage;
import org.example.Hooks.Hooks;
import org.example.Utilities.DateConverter;
import org.example.Utilities.ExcelDataProvider;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class HotelsSearchTest extends Hooks {

    @Test
    public void testSearch() {
        HomePage homePage = new HomePage(driver);
        String excelFilePath = "src/test/resources/booking.xlsx";
        String sheetName = "Sheet1";
        int rowNumber = 1;

        Map<String, String> rowData = ExcelDataProvider.getRowData(excelFilePath, sheetName, rowNumber);

        String location = rowData.get("Location");
        String checkInDate = rowData.get("checkInDate");
        String checkOutDate = rowData.get("checkOutDate");

        homePage.closeDialog();
        homePage.searchFor(location);
        homePage.selectDate(checkInDate);
        homePage.selectDate(checkOutDate);
        homePage.submitSearch();

        List<String> hotelNames = homePage.getFirstThreeHotelNames();
        Assert.assertTrue(hotelNames.contains("Tolip Hotel Alexandria"), "Tolip Hotel Alexandria is not found among the first three hotels.");

        homePage.clickSeeAvailabilityForHotel("Tolip Hotel Alexandria");
        homePage.selectBedType("1");
        homePage.selectRoomOption("1");

        // Assert that the chosen check-in and check-out dates are displayed correctly
        String actualCheckInDate = homePage.bookingCheckInDate.getText();
        String expectedCheckInDate = DateConverter.convertDateFormat(checkInDate);
        Assert.assertEquals(actualCheckInDate, expectedCheckInDate, "The check-in date is not as expected.");

        String actualCheckOutDate = homePage.bookingCheckOutDate.getText();
        String expectedCheckOutDate = DateConverter.convertDateFormat(checkOutDate);
        Assert.assertEquals(actualCheckOutDate, expectedCheckOutDate, "The check-out date is not as expected.");

        // Assert that the hotel name is displayed correctly on the details page
        String actualHotelName = driver.findElement(By.xpath("//div[@class='bui-grid']//div/h1")).getText();
        String expectedHotelName = "Tolip Hotel Alexandria";
        Assert.assertEquals(actualHotelName, expectedHotelName, "The hotel name is not as expected.");
    }
}
