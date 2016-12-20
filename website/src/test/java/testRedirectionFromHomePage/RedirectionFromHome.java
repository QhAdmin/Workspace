package testRedirectionFromHomePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import pages.HomePage;
import utils.Utils;

@Test
public class RedirectionFromHome {

	private WebDriver driver;
	private HomePage homePage;
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

	}

	public void checkRedirection() throws Exception {

		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php");

		homePage.getAboutUsBtn().click();
		Utils.waitForTitle(driver, "Company Overview | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=about-us");

		homePage.getHomeBtn().click();
		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		homePage.getTestingServicesBtn().click();
		Utils.waitForTitle(driver, "By App Type | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=testing-services");

		homePage.getHomeBtn().click();
		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		homePage.getCertificationCoursesBtn().click();
		Utils.waitForTitle(driver, "Courses Overview | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=certification-courses");

		homePage.getHomeBtn().click();
		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		homePage.getOurAproachBtn().click();
		Utils.waitForTitle(driver, "Delivery | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=our-approach");

		homePage.getHomeBtn().click();
		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		homePage.getKnowledgeHubBtn().click();
		Utils.waitForTitle(driver, "Case Studies | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=knowledge-hub");

		homePage.getHomeBtn().click();
		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		homePage.getContactUsBtn().click();
		Utils.waitForTitle(driver, "Contact Us | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=contact-us");

		homePage.getHomeBtn().click();
		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		homePage.getTwitterBtn().click();
		Thread.sleep(5000);
		ArrayList<String> tabs1 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs1.get(1));
		Utils.waitForTitle(driver, "Quality House (@quality__house) | Twitter", 10);
		assertEquals(driver.getCurrentUrl(), "https://twitter.com/quality__house");
		driver.close();
		driver.switchTo().window(tabs1.get(0));
	    Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);

		homePage.getFacebookBtn().click();
		Thread.sleep(5000);
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
		Utils.waitForTitle(driver, "Quality House | Facebook", 10);
		assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/QualityHouseLTD?fref=ts");
		driver.close();
		driver.switchTo().window(tabs2.get(0));
	    Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);

		
		homePage.getLinkedinBtn().click();
		Thread.sleep(5000);
		ArrayList<String> tabs3 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs3.get(1));
	    Utils.waitForTitle(driver, "Quality House | LinkedIn", 10);
		assertEquals(driver.getCurrentUrl(), "https://www.linkedin.com/company/quality-house");
	    driver.close();
	    driver.switchTo().window(tabs3.get(0));
	    Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);

		
		homePage.getSiteMapBtn().click();
		Utils.waitForTitle(driver, "Sitemap | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=sitemap");
		
		homePage.getHomeBtn().click();
		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		homePage.getCourseCalendarBtn().click();
		Utils.waitForTitle(driver, "Calendar | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?page=certification-courses-calendar");
		
		homePage.getHomeBtn().click();
		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		homePage.getSearchInputField().click();
		homePage.getSearchBtn().click();
		Utils.waitForTitle(driver, "Search | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php?q=&page=search");

	}

	@AfterMethod
	public void closeSelenium() {
		driver.quit();
	}

}
