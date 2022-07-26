package sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Five {

	public static void main(String[] args) {
		
		System.setProperty("webdriver.edge.driver", "C:\\Users\\kesari\\Downloads\\edgedriver_win64\\msedgedriver.exe");
		
		WebDriver d = new EdgeDriver();
		
		d.get("https://rahulshettyacademy.com/");
		
		System.out.println(d.getCurrentUrl());

		d.close();
		
		d.quit();
	}

}
