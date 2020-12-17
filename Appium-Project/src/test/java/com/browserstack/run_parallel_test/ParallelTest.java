package com.browserstack.run_parallel_test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidElement;
import reusableComponents.ExcelUtils;
import reusableComponents.ExtentManager;
import reusableComponents.ReadConfig;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import java.util.List;

public class ParallelTest extends BrowserStackTestNGTest {

    @Test
    public void launchAndSearchWikipediaApp_BrowserStackAndriod() throws Exception {
    	ReadConfig readConfig=new ReadConfig("./Configuration/config.properties");
    	String exlpath= readConfig.getValue("excelPath");
		String shName= readConfig.getValue("sheetName");
		ExcelUtils exObj=new ExcelUtils(exlpath,shName);
    	ExtentTest extentTest = ExtentManager.extent.createTest("launchAndSearchWikipediaApp_BrowserStackAndriod");
    	AndroidElement searchElement = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Search Wikipedia")));
		searchElement.click();
		AndroidElement insertTextElement = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.id("org.wikipedia.alpha:id/search_src_text")));
		insertTextElement.sendKeys("Browser stack");
		Thread.sleep(5000);

		List<AndroidElement> allsearchTerm = driver.findElementsByClassName("android.widget.TextView");
		assert (allsearchTerm.size() > 0);
		extentTest.pass("Search Term  "+exObj.getDataBasedOnTCandAttribute("SearchTerm", "TC-1")+" and  Found Results.."+allsearchTerm.size());
		//testflight app- ios
		//hockey -app -apk
    }
}
