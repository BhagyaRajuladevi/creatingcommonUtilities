package hardcodedTests;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateContactWithExistingOrganizationTest {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement logo = driver.findElement(By.xpath("//img[@alt='logo']"));
		if (logo.isDisplayed())
			System.out.println("Pass: Login page displayed");
		else
			System.out.println("Fail: Login page not found");

		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();
		String homePageHeader = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if (homePageHeader.contains("Home"))
			System.out.println("Pass: Home page is displayed");
		else
			System.out.println("Fail: Home page not found");

		driver.findElement(By.xpath("//a[.='Contacts']")).click();
		String organizationsPageHeader = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if (organizationsPageHeader.contains("Contacts"))
			System.out.println("Pass: Contacts page displayed");
		else
			System.out.println("Fail: Contacts page not found");

		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		String createOrganizationPageHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if (createOrganizationPageHeader.contains("Creating new Contact"))
			System.out.println("Pass: Create New Contact page is displayed");
		else
			System.out.println("Fail: Create new Contact page not found");

		WebElement firstNameSalutationDropdown = driver.findElement(By.name("salutationtype"));
		Select salutation = new Select(firstNameSalutationDropdown);
		salutation.selectByValue("Mrs.");

		driver.findElement(By.name("lastname")).sendKeys("Sri_02");
		driver.findElement(By.xpath("//img[contains(@onclick,'Accounts&action=Popup')]")).click();
		String parentWindowID = driver.getWindowHandle();
		Set<String> windowIDs = driver.getWindowHandles();

		for (String windowID : windowIDs) {
			driver.switchTo().window(windowID);
		}

		driver.findElement(By.xpath("//a[.='Wipro']")).click();
		driver.switchTo().window(parentWindowID);
		driver.findElement(By.name("imagename"))
				.sendKeys("C:\\Users\\QPS-Basavanagudi\\Pictures\\Camera Roll\\WIN_20221106_14_02_38_Pro.jpg");
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();

		String newOrganizationInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (newOrganizationInfo.contains("Sri_02"))
			System.out.println("Pass: New Contact Info page displayed");
		else
			System.out.println("Fail: New Contact Info not found");

		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		WebElement newOrganization = driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr[last()]/td[4]"));
		if (newOrganization.getText().contains("Sri_02"))
			System.out.println("Test case passed");
		else
			System.out.println("Test case failed");

		WebElement administratorImage = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions a = new Actions(driver);
		a.moveToElement(administratorImage).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();

		driver.quit();

	}

}
