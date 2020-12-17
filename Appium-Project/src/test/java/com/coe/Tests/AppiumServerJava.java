package com.coe.Tests;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServerJava {
	public static final String URL = "http://127.0.0.1:4723/wd/hub";
	public static String apkUkl = "D:\\rcworksp\\Appium-SaucelabProject\\WikipediaSample.apk";
	AndroidDriver<AndroidElement> driver;
	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;
	
	
	public void startServer() {
		//Set Capabilities
		cap = new DesiredCapabilities();
		cap.setCapability("noReset", "false");
		
		//Build the Appium service
		builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		builder.withCapabilities(cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		
		//Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
	}
	
	public void stopServer() {
		service.stop();
	}

	public boolean checkIfServerIsRunnning(int port) {
		
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			//If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}	

/*	public static void main(String[] args) {
		System.setProperty("ANDROID_HOME","C:\\Users\\91734\\AppData\\Local\\Android\\Sdk") ;
		System.setProperty("ANDROID_SDK_HOME","C:\\Users\\91734\\.android") ;
		AppiumServerJava appiumServer = new AppiumServerJava();
		
		int port = 4723;
		if (!appiumServer.checkIfServerIsRunnning(port)) {
			appiumServer.startServer();
			appiumServer.stopServer();
		} else {
			System.out.println("Appium Server already running on Port - " + port);
		}
	}
	*/
	public static void main(String[] args) {

	    ProcessBuilder pb = new ProcessBuilder("C:\\Users\\91734\\AppData\\Local\\Android\\Sdk"); // or any other program you want to run

	    Map<String, String> envMap = pb.environment();

	    envMap.put("MY_ENV_VAR", "1");
	    Set<String> keys = envMap.keySet();
	    for(String key:keys){
	        System.out.println(key+" ==> "+envMap.get(key));
	    }

	}
	
	public  void setEnv(String key, String value) {
	    try {
	        Map<String, String> env = System.getenv();
	        Class<?> cl = env.getClass();
	        Field field = cl.getDeclaredField("m");
	        field.setAccessible(true);
	        Map<String, String> writableEnv = (Map<String, String>) field.get(env);
	        writableEnv.put(key, value);
	        System.out.println( writableEnv.put(key, value));
	    } catch (Exception e) {
	        throw new IllegalStateException("Failed to set environment variable", e);
	    }
	}
	@BeforeSuite
	public void setup() throws Exception {
		AppiumServerJava appiumServer =new AppiumServerJava();

		int port = 4723;
		if (!appiumServer.checkIfServerIsRunnning(port)) {
			appiumServer.startServer();
			
		} else {
			System.out.println("Appium Server already running on Port - " + port);
		}

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
	@Test
	public void launchWikipediaApp() throws InterruptedException {
		AndroidElement searchElement = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Search Wikipedia")));
		searchElement.click();
		AndroidElement insertTextElement = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(MobileBy.id("org.wikipedia.alpha:id/search_src_text")));
		insertTextElement.sendKeys("Appium");
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
}