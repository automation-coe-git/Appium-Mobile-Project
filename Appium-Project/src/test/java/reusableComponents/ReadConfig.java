package reusableComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {
	Properties pro;

	public ReadConfig(String path) {
		File src = new File(path);
		try {
			FileInputStream fis = new FileInputStream(src);
			pro = new Properties();
			pro.load(fis);
		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
		}
	}

	public String getValue(String key) {
		String val = null;
		try {
			if (key == "baseURL") {
				val = pro.getProperty("baseURL");
			} else if (key == "excelPath") {
				val = pro.getProperty("excelPath");
			} else if (key == "sheetName") {
				val = pro.getProperty("sheetName");
			} else if (key == "browser") {
				val = pro.getProperty("browser");
			} 
		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
		}
		return val;
	}
	
	public void setValue(String path,String key,String value) throws IOException {
		FileOutputStream fileOutput=new FileOutputStream(path);
		pro.setProperty(key, value);
		pro.store(fileOutput, null);
	    fileOutput.close();
	}
}