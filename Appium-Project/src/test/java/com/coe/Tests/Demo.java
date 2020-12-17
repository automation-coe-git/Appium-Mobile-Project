package com.coe.Tests;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.util.UUID;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coe.pages.GuineaPigPage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Demo{
	public static final String URL = "https://Autoamtioncoe:f35aca5f-c3e8-4309-9479-8a98e5fe8f6f@ondemand.us-west-1.saucelabs.com:443/wd/hub";
	AppiumDriver driver;

	@BeforeMethod
	public void start() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String app = "https://github.com/saucelabs-sample-test-frameworks/GuineaPig-Sample-App/blob/master/android/GuineaPigApp-debug.apk?raw=true";

		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
		capabilities.setCapability("name", "DemoAppiumTest");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.APP, app);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");

		URL url = new URL(URL);
		driver = new AndroidDriver(url, capabilities);
	}

	@Test
	public void verifyLinkTest() {
		GuineaPigPage page = new GuineaPigPage(driver);

		page.followLink();

		Assert.assertFalse(page.isOnPage());
		System.out.println("verifyLinkTest Done");
	}

	

	// @Test
	public void verifyCommentInputTest() throws InterruptedException {
		String commentInputText = UUID.randomUUID().toString();

		GuineaPigPage page = new GuineaPigPage(driver);

		page.submitComment(commentInputText);
		Thread.sleep(5000);

		Assert.assertTrue(page.getSubmittedCommentText().contains(commentInputText));
		System.out.println(" verifyCommentInputTest Done");
	}

	@AfterMethod
	public void tearDown() throws Exception {

		// Gets browser logs if available.
		driver.quit();
	}
}