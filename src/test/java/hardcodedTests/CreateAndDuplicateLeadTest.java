package hardcodedTests;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericLibraries.AutoConstantPath;
import genericLibraries.ExcelFileUtility;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;

public class CreateAndDuplicateLeadTest {

	public static void main(String[] args) {
		
		ExcelFileUtility excel = new ExcelFileUtility();
		JavaUtility javaUtility = new JavaUtility();
		PropertyFileUtility property = new PropertyFileUtility();
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
		if(logo.isDisplayed())
			System.out.println("Pass: Login page displayed");
		else
			System.out.println("Fail: Login page not found");
		
		String username = property.getDataFromPropertyFile("username");
		String password = property.getDataFromPropertyFile("password");
		
		driver.findElement(By.name("user_name")).sendKeys(username);
		driver.findElement(By.name("user_password")).sendKeys(password);
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
		
		Map<String,String> map = excel.fetchDataFromExcel("Create Lead", "TestData");
		WebElement firstNameSalutationDropdown = driver.findElement(By.name("salutationtype"));
		webdriver.dropdown(map.get("First Name Salutation"), firstNameSalutationDropdown);
//		Select salutation = new Select(firstNameSalutationDropdown);
//		salutation.selectByValue("Mrs.");

		String lastName = map.get("Last Name")+javaUtility.generateRandomNumber(100);
		driver.findElement(By.name("lastname")).sendKeys(lastName);
		driver.findElement(By.name("company")).sendKeys(map.get("Company"));

		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();

		String newLeadInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (newLeadInfo.contains(lastName))
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
		String newLastName = map.get("New Last Name")+javaUtility.generateRandomNumber(100);
		lastNameTextField.sendKeys(newLastName);
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();

		String duplicatedLeadInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (duplicatedLeadInfo.contains(newLastName))
			System.out.println("Pass: New Lead Info page displayed");
		else
			System.out.println("Fail: New Lead Info not found");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		WebElement newOrganization = driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr[last()]/td[3]"));
		if (newOrganization.getText().contains(newLastName)) {
			System.out.println("Test case passed");
			excel.writeDataIntoExcel("Create Lead", "Pass", AutoConstantPath.EXCEL_FILE_PATH, "TestData");
		}
		else {
			System.out.println("Test case failed");
			excel.writeDataIntoExcel("Create Lead", "Fail", AutoConstantPath.EXCEL_FILE_PATH, "TestData");
		}

		WebElement administratorImage = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		webdriver.mouseHoverToElement(administratorImage);
		
//		Actions a = new Actions(driver);
//		a.moveToElement(administratorImage).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		
		excel.closeWorkbook();
		webdriver.closeBrowser();

		//driver.quit();
		
		

	}

}
