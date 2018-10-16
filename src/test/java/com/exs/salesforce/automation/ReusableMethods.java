package com.exs.salesforce.automation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ReusableMethods extends BaseClass {

	static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent = null;
	static ExtentTest logger = null;

	public static void OpenBrowser() throws IOException {

		Properties pro = new Properties();
		BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/Configuration.properties"));
		pro.load(reader);
		driver.get(pro.getProperty("SalesforceURL"));
		logger.log(Status.INFO, "salesforce page opened");

	}

	public static void Text(WebElement obj, String value, String objName) {
		if (obj.isDisplayed()) {
			obj.sendKeys(value);
			logger.log(Status.PASS,
					MarkupHelper.createLabel(value + " is entered in " + objName + " field", ExtentColor.GREEN));
		} else {
			logger.log(Status.FAIL, MarkupHelper
					.createLabel(objName + " field does not exist, please check your application", ExtentColor.RED));
		}

	}

	public static void verifyText(WebElement element, String elementName, String expectedText) throws IOException {
		if (element.isDisplayed()) {
			if (element.getText().equals(expectedText))
				logger.log(Status.PASS,
						MarkupHelper.createLabel(elementName + " is displayed as expected", ExtentColor.GREEN));
			else {
				logger.log(Status.FAIL, MarkupHelper.createLabel(elementName + "is NOT as expected", ExtentColor.RED));
				String imagePath = takeScreenShot();
				logger.addScreenCaptureFromPath(imagePath);
			}
		} else {
			logger.log(Status.FAIL, MarkupHelper.createLabel(elementName + " not found", ExtentColor.RED));
		}
	}

	public static void click(WebElement obj, String objName) {
		if (obj.isDisplayed()) {
			obj.click();
			logger.log(Status.PASS, MarkupHelper.createLabel(objName + " is clicked", ExtentColor.GREEN));
		} else {
			logger.log(Status.FAIL, MarkupHelper
					.createLabel(objName + " field does not exist, please check your application", ExtentColor.RED));
		}

	}

	public static void dropdownSelection(WebElement obj, String option, String optionName) {

		Select select = new Select(obj);
		select.selectByValue(option);
		logger.log(Status.PASS,
				MarkupHelper.createLabel(optionName + " is selected from the dropdown", ExtentColor.GREEN));

	}

	public static String takeScreenShot() throws IOException {
		String reportPath = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String destination = "Test Reports/ScreenShots/" + reportPath + "image.PNG";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(destination), true);
		return destination;
	}

	public static void closeBrowser() {
		if (!driver.toString().contains("null"))
			;
		{
			driver.close();
			logger.log(Status.PASS, MarkupHelper.createLabel("Browser is closed", ExtentColor.GREEN));

		}
	}

	public static void executionReport(String reportName) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timeNow = dtf.format(now);
		timeNow = timeNow.replace(":", "_");
		timeNow = timeNow.replace(" ", "_");
		timeNow = timeNow.replace("/", "_");

		System.out.println(reportName + "_" + timeNow + ".html");

		htmlReporter = new ExtentHtmlReporter("Test Reports/Reports/" + reportName + "_" + timeNow + ".html");

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

	}

	public static String[][] readSheet(String dt_Path, String sheetName) throws IOException {

		/* Step 1: Get the XL Path */
		File xlFile = new File(dt_Path);

		/* Step2: Access the Xl File */
		FileInputStream xlDoc = new FileInputStream(xlFile);

		/* Step3: Access the work book */
		HSSFWorkbook wb = new HSSFWorkbook(xlDoc);

		/* Step4: Access the Sheet */
		HSSFSheet sheet = wb.getSheet(sheetName);

		int iRowCount = sheet.getLastRowNum() + 1;
		int iColCount = sheet.getRow(0).getLastCellNum();

		String[][] xlData = new String[iRowCount][iColCount];

		for (int i = 0; i < iRowCount; i++) {
			for (int j = 0; j < iColCount; j++) {

				xlData[i][j] = sheet.getRow(i).getCell(j).getStringCellValue();
			}

		}

		return xlData;
	}
}
