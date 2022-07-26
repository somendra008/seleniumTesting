package sample;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Seven {

	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\kesari\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
		
		WebDriver d = new ChromeDriver();
		
		d.get("https://rahulshettyacademy.com/locatorspractice/");
		d.findElement(By.id("inputUsername")).sendKeys("Somendra");
		d.findElement(By.name("inputPassword")).sendKeys("Kesari");
		d.findElement(By.className("signInBtn")).click();
		System.out.println(d.findElement(By.cssSelector("p.error")).getText());
		
	}
}
