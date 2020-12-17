
package com.nativeapp.demo;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import reusableComponents.ExcelUtils;
import reusableComponents.ExtentManager;
import reusableComponents.ReadConfig;

public class AppiumNativeAppDemo  extends BaseTest {
	public static final String URL = "http://127.0.0.1:4723/wd/hub";
	public static String apkUkl = "D:\\rcworksp\\Appium-Project\\WikipediaSample.apk";
	@BeforeSuite
	public void setup() throws Exception {

		DesiredCapabilities caps = new DesiredCapabilities();

		// Specify device and os_version for testing
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "7.0");

		// Set other BrowserStack capabilities
		caps.setCapability("build", "Java Android");
		caps.setCapability("deviceName", "emulator-5554");
		caps.setCapability("automationName", "UiAutomator2");
		caps.setCapability("noSign", "true");

		// Set URL of the application under test
		caps.setCapability("app", apkUkl);

		URL url = new URL(URL);

		driver = new AndroidDriver<AndroidElement>(url, caps);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void launchAndSearchWikipediaNativeApp() throws InterruptedException, IOException {
		ExtentTest extentTest = ExtentManager.extent.createTest("launchAndSearchWikipediaNativeApp");
		ReadConfig readConfig=new ReadConfig("./Configuration/config.properties");
		String exlpath= readConfig.getValue("excelPath");
		String shName= readConfig.getValue("sheetName");
		ExcelUtils exObj=new ExcelUtils(exlpath,shName);
		AndroidElement searchElement = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Search Wikipedia")));
		searchElement.click();
		AndroidElement insertTextElement = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.id("org.wikipedia.alpha:id/search_src_text")));
		insertTextElement.sendKeys(exObj.getDataBasedOnTCandAttribute("SearchTerm", "TC-1"));
		Thread.sleep(5000);
		System.out.println("Entering Search term " +exObj.getDataBasedOnTCandAttribute("SearchTerm", "TC-1"));
	
		List<AndroidElement> allsearchTerm = driver.findElementsByClassName("android.widget.TextView");
		assert (allsearchTerm.size() > 0);
		System.out.println("Search Results "+allsearchTerm.size());
		extentTest.pass("Search Term  "+exObj.getDataBasedOnTCandAttribute("SearchTerm", "TC-1")+" and  Found Results.."+allsearchTerm.size());
		Thread.sleep(5000);
		insertTextElement.sendKeys(exObj.getDataBasedOnTCandAttribute("SearchTerm", "TC-2"));
		Thread.sleep(5000);
		System.out.println("Entering Search term " +exObj.getDataBasedOnTCandAttribute("SearchTerm", "TC-2"));
		
		List<AndroidElement> allsearchTerm2 = driver.findElementsByClassName("android.widget.TextView");
		assert (allsearchTerm2.size() > 0);
		System.out.println("Search Results "+allsearchTerm2.size());
		extentTest.pass("Search Term  "+exObj.getDataBasedOnTCandAttribute("SearchTerm", "TC-2")+" and  Found Results.."+allsearchTerm2.size());
		Thread.sleep(5000);
	}

	@AfterSuite

	public void teardown() {
		if (driver != null) {
			driver.quit();
			System.out.println("Done");
		}
	}

	

}
