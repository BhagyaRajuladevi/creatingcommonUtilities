package hardcodedTests;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateOrganizationTest {

	public static void main(String[] args) throws InterruptedException {
		Random random = new Random();
		int randomNum = random.nextInt(100);
		
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
		
		driver.findElement(By.xpath("//a[.='Organizations']")).click();
		String organizationsPageHeader = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(organizationsPageHeader.contains("Organizations"))
			System.out.println("Pass: Organizations page displayed");
		else
			System.out.println("Fail: Organizations page not found");
		
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		String createOrganizationPageHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createOrganizationPageHeader.contains("Creating new Organization"))
			System.out.println("Pass: Create New Organization page is displayed");
		else
			System.out.println("Fail: Create new organization page not found");
		
		String accountName = "Infosys"+randomNum;
		driver.findElement(By.name("accountname")).sendKeys(accountName);
		WebElement industryDropdown = driver.findElement(By.name("industry"));
		Select industry = new Select(industryDropdown);
		industry.selectByVisibleText("Electronics");
		driver.findElement(By.xpath("//input[@value='T']")).click();
		WebElement assignedToDropdown = driver.findElement(By.xpath("//select[@name='assigned_group_id']"));
		
		Select assignedTo = new Select(assignedToDropdown);
		assignedTo.selectByVisibleText("Support Group");
		
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		
		String newOrganizationInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(newOrganizationInfo.contains(accountName))
			System.out.println("Pass: New Orgaization Info page displayed");
		else
			System.out.println("Fail: New Organization Info not found");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		WebElement newOrganization = driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr[last()]/td[3]"));
		if(newOrganization.getText().contains(accountName))
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
