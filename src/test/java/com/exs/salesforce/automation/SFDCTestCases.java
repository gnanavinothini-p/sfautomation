package com.exs.salesforce.automation;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class SFDCTestCases extends ReusableMethods{

	
	public static void TC32() throws InterruptedException, IOException {
		logger = extent.createTest("TC32");
		IntializeDriver("firefox");
		OpenBrowser();
		String testDataPath = "src/test/resources/TestdataExceutionScripts.xls";
		String[][] recdata = ReusableMethods.readSheet(testDataPath, "TC32");
		String username = recdata[1][0];
		WebElement un = driver.findElement(By.xpath("//input[@id='username']"));
		Text(un, username, "userName");	
		String password = recdata[1][1];
		WebElement pwd = driver.findElement(By.xpath("//input[@id='password']"));
		Text(pwd, password, "Password");	
		WebElement login = driver.findElement(By.xpath("//input[@id='Login']"));
		click(login, "Login Button");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement contactstab;
		contactstab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Contacts Tab']")));
		click(contactstab, "Contacts tab");
		Thread.sleep(4000);
		WebElement newBtn = driver.findElement(By.xpath("//input[@title='New']"));
		click(newBtn, "New Button");
		String lastName = recdata[1][2];
		WebElement lastname = driver.findElement(By.xpath("//input[@id='name_lastcon2']"));
		Text(lastname, lastName, "Last Name");
		String accountName = recdata[1][3];
		WebElement accName = driver.findElement(By.xpath("//input[@id='con4']"));
		Text(accName, accountName, "Account Name");	
		WebElement saveAndNew = driver.findElement(By.xpath("//td[@id='topButtonRow']//input[@title='Save & New']"));
		click(saveAndNew, "Save & New Button");
		WebElement title = driver.findElement(By.xpath("//h1[@class='pageType']"));
		String expected = recdata[1][4];
		verifyText(title, "error message", expected);
		logger.log(Status.PASS, MarkupHelper.createLabel("New Contact creation page is displayed", ExtentColor.GREEN));		
		closeBrowser();
	}
}
