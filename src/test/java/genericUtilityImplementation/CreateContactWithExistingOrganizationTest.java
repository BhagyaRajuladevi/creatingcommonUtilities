package genericUtilityImplementation;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericLibraries.AutoConstantPath;
import genericLibraries.ExcelFileUtility;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;

public class CreateContactWithExistingOrganizationTest {

	public static void main(String[] args) {
		
		ExcelFileUtility excel = new ExcelFileUtility();
		PropertyFileUtility property = new PropertyFileUtility();
		JavaUtility javaUtility = new JavaUtility();
		WebDriverUtility webdriver = new WebDriverUtility();
		
		property.propertyFileInitialization(AutoConstantPath.PROPERTY_FILE_PATH);
		excel.excelFileInitialization(AutoConstantPath.EXCEL_FILE_PATH);
		
		String url = property.getDataFromPropertyFile("url");
		String time = property.getDataFromPropertyFile("timeouts");
		
		WebDriver driver = webdriver.openBrowserAndApplication(url, Long.parseLong(time));
		
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver = new ChromeDriver();
//		driver.manage().window().maximize();
//		driver.get("http://localhost:8888/");
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement logo = driver.findElement(By.xpath("//img[@alt='logo']"));
		if (logo.isDisplayed())
			System.out.println("Pass: Login page displayed");
		else
			System.out.println("Fail: Login page not found");

		String username = property.getDataFromPropertyFile("username");
		String password = property.getDataFromPropertyFile("password");
		
		driver.findElement(By.name("user_name")).sendKeys(username);
		driver.findElement(By.name("user_password")).sendKeys(password);
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
		webdriver.dropdown("First Name Salutation", firstNameSalutationDropdown);
//		Select salutation = new Select(firstNameSalutationDropdown);
//		salutation.selectByValue("Mrs.");

		Map<String,String> map = excel.fetchDataFromExcel("Create Contact", "TestData");
		String lastName = map.get("Last Name")+javaUtility.generateRandomNumber(100);
		driver.findElement(By.name("lastname")).sendKeys(lastName);
		driver.findElement(By.xpath("//img[contains(@onclick,'Accounts&action=Popup')]")).click();
		
		String parentWindowID = webdriver.getParentWindow();
		//String parentWindowID = driver.getWindowHandle();
		
		webdriver.handleChildBrowserPopup("Organizations");
//		Set<String> windowIDs = driver.getWindowHandles();
//
//		for (String windowID : windowIDs) {
//			driver.switchTo().window(windowID);
//		}
		
		String organizationName = map.get("Organization Name");
		driver.findElement(By.xpath("//a[.='"+organizationName+"']")).click();
		webdriver.switchToWindow(parentWindowID);
		//driver.switchTo().window(parentWindowID);
		driver.findElement(By.name("imagename"))
				.sendKeys(map.get("Contact Image"));
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();

		String newOrganizationInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (newOrganizationInfo.contains(lastName))
			System.out.println("Pass: New Contact Info page displayed");
		else
			System.out.println("Fail: New Contact Info not found");

		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		WebElement newOrganization = driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr[last()]/td[4]"));
		if (newOrganization.getText().contains(lastName)) {
			System.out.println("Test case passed");
			excel.writeDataIntoExcel("Create Contact", "Pass", AutoConstantPath.EXCEL_FILE_PATH,"TestData");
		}
		else {
			System.out.println("Test case failed");
			excel.writeDataIntoExcel("Create Contact", "Fail", AutoConstantPath.EXCEL_FILE_PATH,"TestData");
		}
			

		WebElement administratorImage = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		webdriver.mouseHoverToElement(administratorImage);
//		Actions a = new Actions(driver);
//		a.moveToElement(administratorImage).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		excel.closeWorkbook();
		webdriver.closeBrowser();
//		driver.quit();

	}

}
