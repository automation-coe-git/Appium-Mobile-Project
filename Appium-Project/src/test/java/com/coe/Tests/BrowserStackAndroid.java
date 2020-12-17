package com.coe.Tests;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class BrowserStackAndroid {
	public static final String URL = "https://automationcoe1:tbS2wQVDs9wUoNWJTwKf@hub-cloud.browserstack.com/wd/hub";
	public static String userName = "automationcoe1";
	public static String accessKey = "tbS2wQVDs9wUoNWJTwKf";
	public static String apkUkl = "D:\\rcworksp\\Appium-Project\\WikipediaSample.apk";
	AndroidDriver<AndroidElement> driver;

	@BeforeSuite
	public void setup() throws Exception {
		DesiredCapabilities caps = new DesiredCapabilities();

		// Specify device and os_version for testing
		caps.setCapability("device", "Google Pixel 3");
		caps.setCapability("os_version", "9.0");

		// Set other BrowserStack capabilities
		caps.setCapability("project", "First Appium Mobile Project");
		caps.setCapability("build", "Java Android");
		caps.setCapability("name", "Bstack-[Java-Appium] Sample Test");

		// Set URL of the application under test
		caps.setCapability("app", uploadApp());

		URL url = new URL(URL);

		driver = new AndroidDriver<AndroidElement>(url, caps);
	}

	@Test
	public void launchWikipediaApp() throws InterruptedException {
		AndroidElement searchElement = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Search Wikipedia")));
		searchElement.click();
		AndroidElement insertTextElement = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.id("org.wikipedia.alpha:id/search_src_text")));
		insertTextElement.sendKeys("Slk Software");
		Thread.sleep(5000);

		List<AndroidElement> allProductsName = driver.findElementsByClassName("android.widget.TextView");
		assert (allProductsName.size() > 0);
	}

	@AfterSuite

	public void teardown() {
		if (driver != null) {
			driver.quit();
			System.out.println("Done");
		}
	}

	public String uploadApp() throws Exception {

		String urlS = "https://" + userName + ":" + accessKey + "@api-cloud.browserstack.com/app-automate/upload";

		HttpClient client = new DefaultHttpClient();
		URI url = new URI(urlS);
		HttpPost post = new HttpPost(url);

		MultipartEntity nameValuePairs = new MultipartEntity();
		// Select the file you want to upload .ipa or .apk and pass the path
		// accordingly.
		nameValuePairs.addPart("file", new FileBody(new File(apkUkl)));// Path
																		// to
																		// the
																		// file
		post.setEntity(nameValuePairs);

		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		System.out.println(responseString);
		final JSONObject obj = new JSONObject(responseString);
		String app_url = (String) obj.get("app_url");
		System.out.println("reading App id From json " + app_url);
		return app_url;

	}

}
