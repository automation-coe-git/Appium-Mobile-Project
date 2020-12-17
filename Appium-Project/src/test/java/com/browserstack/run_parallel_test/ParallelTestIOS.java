package com.browserstack.run_parallel_test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import reusableComponents.ExcelUtils;
import reusableComponents.ExtentManager;
import reusableComponents.ReadConfig;

public class ParallelTestIOS extends BrowserStackTestNGTestIOS {

	@Test
	public void launchAndSearchWikipediaApp_BrowserStackIOS() throws Exception {
		IOSElement textButton = (IOSElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Text Button")));
		textButton.click();
		IOSElement textInput = (IOSElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Text Input")));
		textInput.sendKeys("hello@browserstack.com" + "\n");

		Thread.sleep(5000);

		IOSElement textOutput = (IOSElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Text Output")));

		Assert.assertEquals(textOutput.getText(), "hello@browserstack.com");
	}
}
