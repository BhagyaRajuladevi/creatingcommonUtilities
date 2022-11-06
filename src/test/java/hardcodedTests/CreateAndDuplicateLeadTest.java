package hardcodedTests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateAndDuplicateLeadTest {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebElement logo = driver.findElement(By.xpath("//img[@alt='logo']"));
		if(logo.isDisplayed())
			System.out.println("Pass: Login page displayed");
		else
			System.out.println("Fail: Login page not found");
		
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();
		String homePageHeader = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(homePageHeader.contains("Home"))
			System.out.println("Pass: Home page is displayed");
		else
			System.out.println("Fail: Home page not found");
		
		driver.findElement(By.xpath("//a[.='Leads']")).click();
		String organizationsPageHeader = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(organizationsPageHeader.contains("Leads"))
			System.out.println("Pass: Organizations page displayed");
		else
			System.out.println("Fail: Organizations page not found");
		
		driver.findElement(By.xpath("//img[@title='Create Lead...']")).click();
		String createOrganizationPageHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createOrganizationPageHeader.contains("Creating New Lead"))
			System.out.println("Pass: Create New Lead page is displayed");
		else
			System.out.println("Fail: Create new Lead page not found");
		
		WebElement firstNameSalutationDropdown = driver.findElement(By.name("salutationtype"));
		Select salutation = new Select(firstNameSalutationDropdown);
		salutation.selectByValue("Mrs.");

		driver.findElement(By.name("lastname")).sendKeys("Valli_02");
		driver.findElement(By.name("company")).sendKeys("Fedex");

		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();

		String newLeadInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (newLeadInfo.contains("Valli_02"))
			System.out.println("Pass: New Lead Info page displayed");
		else
			System.out.println("Fail: New Lead Info not found");
		driver.findElement(By.name("Duplicate")).click();
		String duplicatingPageHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(duplicatingPageHeader.contains("Duplicating"))
			System.out.println("Pass: Duplicating Lead page is displayed");
		else
			System.out.println("Fail: Duplicating Lead page not found");
		WebElement lastNameTextField = driver.findElement(By.name("lastname"));
		lastNameTextField.clear();
		lastNameTextField.sendKeys("ABC_02");
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();

		String duplicatedLeadInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (duplicatedLeadInfo.contains("ABC_02"))
			System.out.println("Pass: New Lead Info page displayed");
		else
			System.out.println("Fail: New Lead Info not found");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		WebElement newOrganization = driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr[last()]/td[3]"));
		if (newOrganization.getText().contains("ABC_02"))
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
