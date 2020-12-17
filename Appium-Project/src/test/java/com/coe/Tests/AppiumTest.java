package com.coe.Tests;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import com.coe.pages.GuineaPigPage;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumTest {

	private AndroidDriver driver;
	private String applicationName;
	public static final String URL = "https://Autoamtioncoe:f35aca5f-c3e8-4309-9479-8a98e5fe8f6f@ondemand.us-west-1.saucelabs.com:443/wd/hub";

	@Factory(dataProvider = "parallelDp")
	public AppiumTest(String applicationName) {
		this.applicationName = applicationName;
	}

	@DataProvider(name = "parallelDp")
	public static Object[][] parallelDp() {
		return new Object[][] { { "Samsung S4" }, { "Samsung S5" } };
	}

	@BeforeClass
	public void setup() throws MalformedURLException {
		String app = "https://github.com/saucelabs-sample-test-frameworks/GuineaPig-Sample-App/blob/master/android/GuineaPigApp-debug.apk?raw=true";
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
		capabilities.setCapability("name", "Demo Appium Parallel Test");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability("applicationName", this.applicationName);
		capabilities.setCapability(MobileCapabilityType.APP, app);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
		capabilities.setCapability("automationName", "uiautomator2");
		URL url = new URL(URL);
		driver = new AndroidDriver(url, capabilities);
	}
	@Test
	 public void launchTest() throws InterruptedException {
	   System.err.println("Thread id: " + Thread.currentThread().getId());
	   Thread.sleep(10000);
	 }
	@Test(dependsOnMethods = {"launchTest"})
	public void verifyLinkTest() {
		GuineaPigPage page = new GuineaPigPage(driver);

		page.followLink();

		Assert.assertFalse(page.isOnPage());
		System.out.println("verifyLinkTest Done");
	}

	@AfterClass
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
}