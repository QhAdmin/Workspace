package testContactUsForm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;
import java.util.concurrent.TimeUnit;

import pages.HomePage;
import pages.ContactUsPage;
import utils.Utils;

@Test
public class ContactUsFormTesting {

	private WebDriver driver;
	private HomePage homePage;
	private ContactUsPage contactUsPage;
	private String baseUrl;

	@BeforeMethod
	public void setupSelenium() {
		baseUrl = "https://qualityhouse.com/index.php";
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.navigate().to(baseUrl);

		homePage = new HomePage(driver);
		contactUsPage = new ContactUsPage(driver);

	}

	public void fillOutContactFormEmpty() throws Exception {

		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php");

		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 2);
		assertTrue(homePage.getStartedNowBtn().isDisplayed());
		homePage.getStartedNowBtn().click();

		Utils.waitForTitle(driver, "Contact Us | Quality House Ltd.", 2);
		assertTrue(contactUsPage.getSendMessageBtn().isDisplayed());
		contactUsPage.getSendMessageBtn().click();

		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=contact-us");

		assertEquals(contactUsPage.getNameError().getText(), "Please enter your name.");
		assertEquals(contactUsPage.getEmailError().getText(), "Please enter your E-mail address.");
		assertEquals(contactUsPage.getSubjectError().getText(), "Please enter the subject.");
		assertEquals(contactUsPage.getMessageError().getText(), "Please enter your message.");

	}

	public void fillOutNameWithNumbers() throws Exception {

		homePage.getContactUsBtn().click();
		Utils.waitForTitle(driver, "Contact Us | Quality House Ltd.", 2);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=contact-us");

		contactUsPage.fillAllContactFields("1234", "danepinku@gmail.com", "proba", "proba");
		assertEquals(contactUsPage.getNameError().getText(), "Please enter valid characters.");

	}

	public void fillOutEmailWithNumbers() throws Exception {

		homePage.getContactUsBtn().click();
		Utils.waitForTitle(driver, "Contact Us | Quality House Ltd.", 2);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=contact-us");

		contactUsPage.fillAllContactFields("Daniel", "123", "proba", "proba");
		assertEquals(contactUsPage.getEmailError().getText(), "Please enter a valid E-mail address.");

	}


//	public void fillOutMessageFieldWith10001Char() throws Exception {
//
//		homePage.getContactUsBtn().click();
//		Utils.waitForTitle(driver, "Contact Us | Quality House Ltd.", 2);
//		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=contact-us");
//
//		contactUsPage.setInputNameField("Daniel");
//		contactUsPage.setInputEmailField("danepinku@gmail.com");
//		contactUsPage.setInputSubjectField("10.000 Characters");
//		contactUsPage.setInputMessageField(Utils.get10001Characters(10001));
//		contactUsPage.getSendMessageBtn().click();
//		assertEquals(contactUsPage.getNameError().getText(), "Please enter valid characters.");
//		Utils.takeSnapShot(driver, "test.png");
//
// 		Milorad comment
//	}

	@AfterMethod
	public void closeSelenium() {
		driver.quit();
	}

}
