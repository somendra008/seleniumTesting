package sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Three {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\kesari\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
		WebDriver w = new ChromeDriver();
		w.get("https://rahulshettyacademy.com/");
		System.out.println(w.getTitle());
		System.out.println(w.getCurrentUrl());
		w.close();
	}

}
