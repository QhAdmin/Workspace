package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Utils;

public class SearchPage {
	
	private WebDriver driver;

	public SearchPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public WebElement getFirstText() {
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='pages']/li[1]/ul"), 2);
	}

}
