package com.exs.salesforce.automation;

import java.io.IOException;
import java.util.Properties;

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

	
	public static void TC32() throws Exception {
		logger = extent.createTest("TC32");
		IntializeDriver("firefox");
		OpenBrowser();
		
		String objectRepositoryPath = "src/test/resources/ObjectRepository.properties";
		Properties objPro = accessPropertyFile(objectRepositoryPath);
		
		String testDataPath = "src/test/resources/TestdataExceutionScripts.xls";
		String[][] recdata = ReusableMethods.readSheet(testDataPath, "TC32");
		String username = recdata[1][0];
		
		WebElement un = driver.findElement(getLocator("login.username", objPro));
		Text(un, username, "userName");	
		String password = recdata[1][1];
		WebElement pwd = driver.findElement(getLocator("login.password", objPro));
		Text(pwd, password, "Password");	
		WebElement login = driver.findElement(getLocator("login", objPro));
		click(login, "Login Button");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement contactstab;
		contactstab = wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator("contactstab", objPro)));
		click(contactstab, "Contacts tab");
		Thread.sleep(4000);
		WebElement newBtn = driver.findElement(getLocator("contacts.new", objPro));
		click(newBtn, "New Button");
		String lastName = recdata[1][2];
		WebElement lastname = driver.findElement(getLocator("contacts.new.lastname", objPro));
		Text(lastname, lastName, "Last Name");
		String accountName = recdata[1][3];
		WebElement accName = driver.findElement(getLocator("contacts.new.accname", objPro));
		Text(accName, accountName, "Account Name");	
		WebElement saveAndNew = driver.findElement(getLocator("contacts.saveandnew", objPro));
		click(saveAndNew, "Save & New Button");
		WebElement title = driver.findElement(getLocator("contacts.pagetitle", objPro));
		String expected = recdata[1][4];
		verifyText(title, "error message", expected);
		logger.log(Status.PASS, MarkupHelper.createLabel("New Contact creation page is displayed", ExtentColor.GREEN));		
		closeBrowser();
	}
}
