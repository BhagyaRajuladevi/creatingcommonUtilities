package genericLibraries;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverUtility {
	
	private WebDriver driver;
	
	public WebDriver openBrowserAndApplication(String url, long time) {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
		return driver;
	}

	public void mouseHoverToElement(WebElement element) {
		Actions a = new Actions(driver);
		a.moveToElement(element).perform();
	}
	
	public void dropdown(WebElement element, String text) {
		Select s = new Select(element);
		s.selectByVisibleText(text);
	}
	
	public void dropdown(String value, WebElement element) {
		Select s = new Select(element);
		s.selectByValue(value);
	}
	
	public void switchToWindow(String windowID) {
		driver.switchTo().window(windowID);
	}
	
	public String getParentWindow() {
		return driver.getWindowHandle();
	}
	
	public void handleChildBrowserPopup(String expectedTitle) {
		Set<String> windowTitles = driver.getWindowHandles();
		for(String windowID : windowTitles) {
			driver.switchTo().window(windowID);
			String actualTitle = driver.getTitle();
			if (actualTitle.contains(expectedTitle))
				break;
		}
	}
	public void closeBrowser() {
		driver.quit();
	}
}
