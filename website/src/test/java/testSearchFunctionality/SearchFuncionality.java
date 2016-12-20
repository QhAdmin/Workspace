package testSearchFunctionality;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;
import java.util.concurrent.TimeUnit;

import pages.HomePage;
import pages.SearchPage;
import utils.Utils;

@Test
public class SearchFuncionality {

	private WebDriver driver;
	private HomePage homePage;
	private SearchPage searchPage;
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
		searchPage = new SearchPage(driver);

	}

	public void searchExactWordFuncionality() throws Exception {

		Utils.waitForTitle(driver, "Home | Quality House Ltd.", 10);
		assertEquals(driver.getCurrentUrl(), "https://qualityhouse.com/index.php");
		homePage.setSearchInputField("Quality");
		homePage.getSearchBtn().click();

		Utils.waitForTitle(driver, "Search | Quality House Ltd.", 10);
		assertEquals(searchPage.getFirstText().getText(), "Why Should We Hire Quality House?" + '\n'
				+ "Quality House is a client-focused company. What we mean by this is that we strive to build a solid partnership and design a bespoke testing solution around our cli...");

	}

	@AfterMethod
	public void closeSelenium() {
		driver.quit();
	}

}
