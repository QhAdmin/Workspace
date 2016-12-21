package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Utils;

public class ContactUsPage {

	private WebDriver driver;

	public ContactUsPage(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public WebElement getSendMessageBtn() {
		return Utils.waitForElementPresence(driver, By.xpath("//div[2]//form/div[4]/div/input"), 1);
	}

	public WebElement getNameError() {
		return Utils.waitForElementPresence(driver, By.id("name-error"), 1);
	}

	public WebElement getEmailError() {
		return Utils.waitForElementPresence(driver, By.id("email-error"), 1);
	}

	public WebElement getSubjectError() {
		return Utils.waitForElementPresence(driver, By.id("subject-error"), 1);
	}

	public WebElement getMessageError() {
		return Utils.waitForElementPresence(driver, By.id("message-error"), 1);
	}
	
	public WebElement getInputNameField() {
		return Utils.waitForElementPresence(driver, By.id("name"), 1);
	}
	
	public void setInputNameField(String name) {
		WebElement nameInput = getInputNameField();
		nameInput.clear();
		nameInput.sendKeys(name);
	}
	
	public WebElement getInputEmailField() {
		return Utils.waitForElementPresence(driver, By.id("email"), 1);
	}
	
	public void setInputEmailField(String email) {
		WebElement emailInput = getInputEmailField();
		emailInput.clear();
		emailInput.sendKeys(email);
	}
	
	public WebElement getInputSubjectField() {
		return Utils.waitForElementPresence(driver, By.id("subject"), 1);
	}
	
	public void setInputSubjectField(String subject) {
		WebElement subjectInput = getInputSubjectField();
		subjectInput.clear();
		subjectInput.sendKeys(subject);
	}
	
	public WebElement getInputMessageField() {
		return Utils.waitForElementPresence(driver, By.id("message"), 1);
	}
	
	public void setInputMessageField(String message) {
		WebElement messageInput = getInputMessageField();
		messageInput.clear();
		messageInput.sendKeys(message);
	}
	
	public void fillAllContactFields(String name, String email, String subject, String message) {
		setInputNameField(name);
		setInputEmailField(email);
		setInputSubjectField(subject);
		setInputMessageField(message);
		getSendMessageBtn().click();
	}
	
	public WebElement getSuccessMessage() {
		return Utils.waitForElementPresence(driver, By.className("alert alert-success"), 1);
	}

}
