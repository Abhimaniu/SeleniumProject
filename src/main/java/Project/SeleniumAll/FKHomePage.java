package Project.SeleniumAll;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FKHomePage {
	WebDriver driver;

	public FKHomePage(WebDriver driver) {
		this.driver = driver;
	}

	public void enterSearchItem(String item) {
		try {
			WebElement element = driver.findElement(By
					.xpath("//input[@name='q']"));
			element.sendKeys(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clickSearchButton() {
		try {
			WebElement element = driver.findElement(By
					.xpath("//input[@value='SEARCH']"));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clickMenSection() {
		try {
			WebElement element = driver.findElement(By
					.xpath("//span[text()='Men']"));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
