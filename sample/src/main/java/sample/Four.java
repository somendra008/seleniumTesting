package sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Four{
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\kesari\\Downloads\\geckodriver-v0.31.0-win64\\geckodriver.exe");
		WebDriver d = new FirefoxDriver();
		
		d.get("https://rahulshettyacademy.com/");
		System.out.println(d.getCurrentUrl());
		d.close();
		d.quit();
	}
	
}