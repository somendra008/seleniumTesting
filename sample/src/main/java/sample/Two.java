package sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Two {

	public static void main(String[] args) {
		
	System.out.println("Hello Two");
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\kesari\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
	
	WebDriver w = new ChromeDriver();
	w.get("https://www.google.com/");
	//WebDriver driver = new ChromeDriver();
	//driver.get("https://www.google.com/");*/
	}
}
