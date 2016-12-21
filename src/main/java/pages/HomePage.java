package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Utils;

public class HomePage {
	
	private WebDriver driver;

	public HomePage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public WebElement getStartedNowBtn() {
		return Utils.waitForElementPresence(driver, By.linkText("Get Started Now!"), 2);
	}
	
	public WebElement getHomeBtn() {
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='header']//div[1]/div/a/img"), 2);
	}
	
	
	public WebElement getAboutUsBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='mainNav']/li[1]/a"), 2);
	}
	
	public WebElement getTestingServicesBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='mainNav']/li[2]/a"), 2);
	}
	
	public WebElement getCertificationCoursesBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='mainNav']/li[3]/a"), 2);
	}
	
	public WebElement getOurAproachBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='mainNav']/li[4]/a"), 2);
	}
	
	public WebElement getKnowledgeHubBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='mainNav']/li[5]/a"), 2);
	}
	
	public WebElement getContactUsBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='mainNav']/li[6]/a"), 2);
	}
	
	public WebElement getTwitterBtn(){
		return Utils.waitForElementPresence(driver, By.className("social-icons-twitter"), 2);
	}
	
	public WebElement getFacebookBtn(){
		return Utils.waitForElementPresence(driver, By.className("social-icons-facebook"), 2);
	}
	
	public WebElement getLinkedinBtn(){
		return Utils.waitForElementPresence(driver, By.className("social-icons-linkedin"), 2);
	}
	
	public WebElement getUpperHomeBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='header']//div[2]/div[1]/nav/ul/li[1]/a"), 2);
	}
	
	public WebElement getSiteMapBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='header']//div[2]/div[1]/nav/ul/li[2]/a"), 2);
	}
	
	public WebElement getCourseCalendarBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='header']//div[2]/div[1]/nav/ul/li[3]/a"), 2);
	}
	
	public WebElement getQualityHouseEmailBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='header']//div[2]/div[1]/nav/ul/li[4]/a"), 2);
	}
	
	public WebElement getSearchInputField(){
		return Utils.waitForElementPresence(driver, By.id("q"), 2);
	}
	
	public void setSearchInputField(String search) {
		WebElement searchInput = getSearchInputField();
		searchInput.clear();
		searchInput.sendKeys(search);
	}
	
	public WebElement getSearchBtn(){
		return Utils.waitForElementPresence(driver, By.xpath("//*[@id='searchForm']/div/span/button/i"), 2);
	}

}
