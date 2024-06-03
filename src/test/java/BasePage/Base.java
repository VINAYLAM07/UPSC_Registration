package BasePage;

import java.awt.AWTException;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Utils.Utilsforextentreports;
//import io.github.bonigarcia.wdm.WebDriverManager;

public class Base extends Utilsforextentreports {
	public static Properties prop;
	public static WebDriver driver;
	private int delaytime;
	public static Robot robot = null;
	public static String Elementtext;
	public static String applicationUrl;
	public static TreeSet listOfElements = new TreeSet();
	public static TreeSet revlistOfElements = new TreeSet();
	public static String alertText = null;
	public static boolean elementdisplay;
	public static int flag = 0;
	public static String BrowserName;


	public void browserSetup() throws IOException {
		prop = new Properties();
		try {

			prop.load(new FileInputStream("./src/test/resources/ConfigurationPage/configurefile.properties"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {String browser=prop.getProperty("browserName");
		System.out.println(browser);

		if (prop.getProperty("browserName").matches("chrome")) {
//			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver();
//			ChromeOptions options = new ChromeOptions();
//			options.addArguments("disable-notifications");
//			basePage bp = new basePage();
//			driver = bp.browserSetup();
			ChromeOptions chromeOptions = new ChromeOptions();
			 chromeOptions.addArguments("--remote-allow-origins=*");
			 driver = new ChromeDriver(chromeOptions);

		}
		
		else if (browser.equalsIgnoreCase("edge")) {
//			WebDriverManager.edgedriver().setup();
			driver= new EdgeDriver();
			System.out.println("njinkpm;ko");
			EdgeOptions EdgeOptions = new EdgeOptions();
			 EdgeOptions.addArguments("--remote-allow-origins=*");
			 driver = new EdgeDriver(EdgeOptions);
		}
		
		/*
		 * WebDriverManager.chromedriver().setup(); //for private connection
		 * DesiredCapabilities caps = new DesiredCapabilities();
		 * caps.setAcceptInsecureCerts(true); ChromeOptions options = new
		 * ChromeOptions(); //for incognito options.addArguments("--incognito"); //for
		 * disablimg notifications options.addArguments("disable-notifications");
		 * options.merge(caps); WebDriver driver = new ChromeDriver(options);
		 * driver.get("https://google.com");
		 */
		driver.manage().window().maximize();
		implicitWait(40);
		System.out.println("The driver is invoked successfully");
			
		}catch(Exception e) {
			System.out.println("The driver is not invoked"+e);
		}
		
		

	}

	

	public void invokingUrl() throws IOException {

		try {
			applicationUrl = prop.getProperty("url");
			driver.get(applicationUrl);
			implicitWait(40);

		} catch (Exception e) {
			System.out.println("The driver is not invoked"+e);
		}
	}

	public void elementClick(By locator) throws IOException {
		try {
			highlightElement(locator);
			driver.findElement(locator).click();
			implicitWait(40);
			System.out.println("Element Clicked  " + locator);
			captureScreenShot();
			reportPass("Element with " + locator + " is clicked successfully");
			screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The Element is not Clicked"+e);
			reportFail("Element with " + locator + " is not clicked successfully");
			screenshotForExtentReports(driver);
			captureScreenShot();
		}
	}
	public void elementClickforAlerts(By locator) throws IOException {
		try {
			//highlightElement(locator);
			driver.findElement(locator).click();
			implicitWait(40);
			System.out.println("Element Clicked  " + locator);
			//captureScreenShot();
			reportPass("Element with " + locator + " is clicked successfully");
			//screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The Element is not Clicked"+e);
			reportFail("Element with " + locator + " is not clicked successfully");
			screenshotForExtentReports(driver);
			captureScreenShot();
		}
	}

	public String gettingText(By locator) throws IOException {

		try {
			if (driver.findElement(locator).isDisplayed()) {
				Elementtext = driver.findElement(locator).getText();
				highlightElement(locator);
				reportPass("Element with " + locator + " is successfully extracted");
				screenshotForExtentReports(driver);
				System.out.println("GettingText  " + Elementtext);
				captureScreenShot();

			}
		} catch (Exception e) {
			System.out.println("The Text Extraction got Failed "+e);
			reportFail("Element with " + locator + " is not extracted");
			screenshotForExtentReports(driver);
			captureScreenShot();
		}
		return Elementtext;
	}

	public boolean isElementPresent(By locator) throws IOException {
		try {
			elementdisplay = driver.findElement(locator).isDisplayed();
			System.out.println("The Element is Present");
			captureScreenShot();
			highlightElement(locator);
			reportPass("The Element with locator" + locator + "is Present");
			screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The Element is not present"+e);
			captureScreenShot();
			reportFail("The Element with locator " + locator + "is not Present");
			screenshotForExtentReports(driver);
		}
		return elementdisplay;
	}

	public void assertEqual(String actualvalue, String expectedvalue) throws IOException {
		try {
			Assert.assertEquals(actualvalue, expectedvalue);
			System.out.println("The AssertionEquals is passed");
			captureScreenShot();
			flag = 1;
			reportPass("The actual value is equal to the expected value");
			screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The AssertionEquals is not invoked"+e);
			reportFail("The Assertion equals is not invoked");
			screenshotForExtentReports(driver);
			captureScreenShot();
		}
	}

	public void assertNotEquals(String actualvalue, String expectedvalue) throws IOException {
		try {
			Assert.assertNotEquals(actualvalue, expectedvalue);
			captureScreenShot();
			System.out.println("The AssertionNotEquals is passed");
			reportPass("The actual value is not equal to the expected value");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The AssertionNotEquals is not invoked"+e);
			reportFail("The Assertion not equals is not invoked");
			screenshotForExtentReports(driver);
			captureScreenShot();
		}
	}

	public void assertTrue(boolean condition, String message) throws IOException {
		try {
			Assert.assertTrue(condition, message);
			captureScreenShot();
			System.out.println("The Asserttrue is passed");
			System.out.println("The AssertionNotEquals is passed");
			reportPass("The Assert true is passed");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The Asserttrue is not invoked"+e);
			reportFail("The Assertion not equals is not invoked");
			screenshotForExtentReports(driver);
			captureScreenShot();
		}
	}

	public void assertFalse(boolean condition, String message) throws IOException {
		try {
			Assert.assertFalse(condition, message);
			captureScreenShot();
			System.out.println("The Assertfalse is passed");
			reportPass("The Assert false is passed");
			screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The Assertfalse is not invoked"+e);
			captureScreenShot();
			reportFail("The Assert false is not invoked");
			screenshotForExtentReports(driver);

		}
	}

	public void softAssertionForEquals(boolean actualvalue, boolean expectedvalue) throws IOException {
		SoftAssert softAssert = new SoftAssert();
		try {
			softAssert.assertEquals(actualvalue, expectedvalue);
			captureScreenShot();
			System.out.println("The SoftAssertion is passed");
			reportPass("SoftAssertion equals is passed");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The softassertionforequals is not invoked"+e);
			captureScreenShot();
			reportFail("SoftAssertion equals is not invoked");
			screenshotForExtentReports(driver);
		}
	}

	public void softAssertionForNotEquals(boolean actualvalue, boolean expectedvalue) throws IOException {
		SoftAssert softAssert = new SoftAssert();
		try {
			softAssert.assertNotEquals(actualvalue, expectedvalue);
			captureScreenShot();
			System.out.println("The softassertionfornotequals is passed");
			reportPass("SoftAssertion for not equals is passed");
			screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The softassertionfornotequals is not invoked"+e);
			captureScreenShot();
			reportFail("SoftAssertion for not equals is passed");
			screenshotForExtentReports(driver);
		}
	}

	public void validateUrl(String currenturl, String url) throws IOException {
		try {
			Assert.assertEquals(currenturl, url);
			captureScreenShot();
			System.out.println("The Validation of the CurrentUrl is passed");
			reportPass("Validation of URL" + currenturl + " is passed");
			screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The Validation of the CurrentUrl is not invoked"+e);
			reportFail("Validation of URL" + currenturl + " is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
		}
	}

	public void validateTitle(String currenttitle, String title) throws IOException {
		try {
			Assert.assertEquals(currenttitle, title);
			captureScreenShot();
			System.out.println("The Validation of the Title is passed");
			reportPass("Validation of currenturl" + currenttitle + " is passed");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The Validation of the Title is not invoked"+e);
			reportFail("Validation of currenturl" + currenttitle + " is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
		}
	}

	public void validateCheckbox(By locator) {
		try {
			boolean status = driver.findElement(locator).isEnabled();
			if (status) {
				System.out.println("The checkbox is enabled");
				captureScreenShot();
				highlightElement(locator);
				reportPass("Validation of checkbox is passed");
				screenshotForExtentReports(driver);
			} else {
				System.out.println("The checkbox is not enabled");
				reportFail("Validation of checkbox is failed");
				screenshotForExtentReports(driver);
				captureScreenShot();
			}
		} catch (Exception e) {
			System.out.println("Validation of Checkbox is not invoked"+e);
		}
	}

	public void validateRadiobutton(By locator) {
		try {
			boolean status = driver.findElement(locator).isSelected();
			if (status) {
				System.out.println("The radiobutton is enabled");
				captureScreenShot();
				highlightElement(locator);
				reportPass("Validation of RadioButton is passed");
				screenshotForExtentReports(driver);
			} else {
				System.out.println("The radtiobutton is not enabled");
				reportFail("Validation of RadioButton is failed");
				screenshotForExtentReports(driver);
				captureScreenShot();
			}
		} catch (Exception e) {
			System.out.println("Validation of Radiobutton is not invoked"+e);
		}
	}

	public void validateDropdown(By locator) {
		try {
			Select select = new Select(driver.findElement(locator));
			boolean status = select.isMultiple();
			if (status) {
				System.out.println("Allow multiple selection is enabled");
				captureScreenShot();
				highlightElement(locator);
				reportPass("Validation of DropDown is passed");
				screenshotForExtentReports(driver);
			} else {
				System.out.println("Allow multiple selection is not enabled");
				captureScreenShot();
				reportFail("Validation of DropDown is failed");
				screenshotForExtentReports(driver);
			}
		} catch (Exception e) {
			System.out.println("Validation of Dropdown is not invoked"+e);
		}
	}

	public void typeText(By locator, String keys) throws IOException {
		try {
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(keys);
			System.out.println("Locator for SendingKeys  " + locator);
			System.out.println("Inputs send  " + keys);
			captureScreenShot();
			reportPass("The Type Text is successful");
			screenshotForExtentReports(driver);
			highlightElement(locator);
		} catch (Exception e) {
			System.out.println("The Text is not entered"+e);
			captureScreenShot();
			reportFail("The Type Text is failed");
			screenshotForExtentReports(driver);
		}
	}

	public void javascriptExecutorByElement(By locator) throws IOException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement((locator));
			js.executeScript("arguments[0].value=", element);
			reportPass("The Type Text by JavaScript by element is successful");
			screenshotForExtentReports(driver);
			System.out.println("The Text is entered using JavaScript Executor Element");

		} catch (Exception e) {
			reportFail("The Type Text by JavaScript by element is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The Text cannot be entered using JavaScriptExecutor by Element"+e);

		}

	}

	public void typeTextbyjs(By locator, String keys) throws IOException {
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			// je.executeScript("arguments[0].value='12344'",locator);
			je.executeScript("arguments[0].value=arguments[1]", locator, keys);
			captureScreenShot();
			highlightElement(locator);
			reportPass("The Type Text by JavaScript is successful");
			screenshotForExtentReports(driver);
			System.out.println("The Text is entered using JavaScript Executor");

		} catch (Exception e) {
			reportFail("The Type Text by JavaScript is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The Text cannot be entered using JavaScriptExecutor"+e);
		}

	}

	public void clearText(By locator) throws IOException {
		try {
			driver.findElement(locator).clear();
			System.out.println("Text cleared from field" + locator);
			captureScreenShot();
			reportPass("The Text is Cleared successfully");
			screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The Text is not Cleared"+e);
			captureScreenShot();
			reportFail("The Text is not Cleared");
			screenshotForExtentReports(driver);
		}
	}

	public void closeBrowser() throws IOException {
		try {
			driver.close();
			System.out.println("The Browser is closed Successfully");
			captureScreenShot();
			reportPass("Closing the browser is Successful");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("Closing of the Browser is not invoked"+e);
			captureScreenShot();
			reportFail("Closing the browser is Failed");
			screenshotForExtentReports(driver);
		}
	}

	public void quitDriver() throws IOException {
		try {
			//implicitWait(40);
			System.out.println("The Quitting of the Browser is Successful");
			reportPass("The Execution is Passed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			endReport();
			driver.quit();

		} catch (Exception e) {
			System.out.println("Quitting of the driver is failed "+e);
			captureScreenShot();
			reportFail("The Execution is failed");
			screenshotForExtentReports(driver);
		}
	}

	public void selectClassByVisibleText(By locator, String ElementText) throws IOException {
		try {
			Select select = new Select(driver.findElement(locator));
			System.out.println("The Select method is invoked");
			select.selectByVisibleText(ElementText);
			highlightElement(locator);
			captureScreenShot();
			reportPass("Selecting by Visible Text is done");
			screenshotForExtentReports(driver);
			System.out.println("The element is Selected by visible Text");

		} catch (Exception e) {
			reportFail("Selecting by Visible Text is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The SelectClassByVisibleText is not invoked"+e);

		}
	}

	public void selectClassByValue(By locator, String value) throws IOException {
		try {
			Select select = new Select(driver.findElement(locator));
			System.out.println("The Select method is invoked");
			captureScreenShot();
			select.selectByValue(value);
			highlightElement(locator);
			reportPass("Selecting by value is done");
			screenshotForExtentReports(driver);
			System.out.println("The element is Selected by value");

		} catch (Exception e) {
			reportFail("Selecting by value is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The SelectClassById is not invoked"+e);

		}
	}

	public void selectClassByIndex(By locator, int index) throws IOException {
		try {
			Select select = new Select(driver.findElement(locator));
			System.out.println("The Select method is invoked");
			select.selectByIndex(index);
			highlightElement(locator);
			reportPass("Selecting by Index is done");
			System.out.println("The element is Selected by index value");
			} 
		catch (Exception e) {
			reportFail("Selecting by Class is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The SelectClassByindex is not invoked"+e);
			}
		}

	public void sendKeysUsingActionClass() throws IOException {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ENTER).build().perform();
			captureScreenShot();
			reportPass("Moving to Element is done");
			screenshotForExtentReports(driver);
			System.out.println("Element is Clicked using action Class");
		} catch (Exception e) {
			reportFail("Clicking an Element is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("Element is not Clicked using action Class"+e);
		}
	}

	public void moveToElement(WebElement locator) throws IOException {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(locator).build().perform();
			captureScreenShot();
			reportPass("Moving to Element is done");
			screenshotForExtentReports(driver);
			System.out.println("moved to the particular element");
		} catch (Exception e) {
			reportFail("Moving to Element is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The element is not moved to the particular"+e);
		}
	}

	public void dragAndDrop(WebElement source, WebElement destination) throws IOException {
		try {
			Actions action = new Actions(driver);
			action.dragAndDrop(source, destination).build().perform();
			captureScreenShot();
			reportPass("Dragging and Dropping is done");
			screenshotForExtentReports(driver);
			System.out.println("The element is dragged and dropped successfully");
		} catch (Exception e) {
			reportFail("Dragging and Dropping is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("Dragging and Dropping of the Element is failed"+e);
		}
	}

	public void dragAndDropUsingSliders(By locator, int x_pixels, int y_pixels) throws IOException {
		try {
			WebElement slider = driver.findElement(locator);
			implicitWait(40);
			Actions action = new Actions(driver);
			action.dragAndDropBy(slider, x_pixels, y_pixels).perform();
			captureScreenShot();
			reportPass("Dragging and Dropping using slider is done");
			screenshotForExtentReports(driver);
			System.out.println("The element is dragged and dropped using slider is successfully");
		} catch (Exception e) {
			reportFail("Dragging and Dropping is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("Dragging and Dropping of the Element using slider is failed"+e);
		}
	}

	public void scrollDown(int pixels) throws IOException {
		try {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("window.scrollBy(0,'pixels')", "");
			implicitWait(40);
			captureScreenShot();
			reportPass("The page is scrolled successfully");
			screenshotForExtentReports(driver);
			System.out.printf("The Page is scrolled ");
		} catch (Exception e) {
			reportFail("The page is scrolling is Failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The Page Scrolling is Failed"+e);
		}
	}

	public void scrollToElement(By locator) throws IOException {
		try {
			WebElement ele = driver.findElement(locator);
			Actions action = new Actions(driver);
			action.scrollToElement(ele);
			action.perform();
			captureScreenShot();
			highlightElement(locator);
			reportPass("Scrolling to Element is successfully");
			screenshotForExtentReports(driver);
			System.out.println("The Scroll to element is invoked");
		} catch (Exception e) {
			reportFail("Scrolling to Element is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The Scroll to element method is not invoked"+e);
		}
	}

	public void isDisplayed(By locator) {
		try {
			Boolean display = driver.findElement(locator).isDisplayed();
			if (display = true) {
				captureScreenShot();
				highlightElement(locator);
				reportPass("The element is Displayed");
				screenshotForExtentReports(driver);
				System.out.println("The located Element is displayed " + locator);
			} else {
				reportFail("The element is not Displayed");
				screenshotForExtentReports(driver);
				captureScreenShot();
				System.out.println("The located Element is not displayed " + locator);
			}
		} catch (Exception e) {
			System.out.println("The driver is not invoked"+e);
		}
	}

	public void uploadFile(String type, By locator, String path, int time) throws AWTException, IOException {
		// String path = prop.getProperty("buttonpath");
		try {
			if (type == "TypeText") {
				WebElement element = driver.findElement(locator);
				captureScreenShot();
				highlightElement(locator);
				reportPass("Uploading of the File using SendKeys");
				screenshotForExtentReports(driver);
				implicitWait(time);
				element.sendKeys(path);
				System.out.println("The File is uploaded Successfully using SendKeys");
				// element.sendKeys("C:\\Users\\2158566\\eclipse-workspace\\TestNGFramework\\images\\img.png");
			} else if (type == "UsingRobot") {
				Robot robot = new Robot();
				StringSelection stringselect = new StringSelection(path);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselect, null);
				Actions action = new Actions(driver);
				WebElement upload = driver.findElement(locator);
				System.out.println("The Element locator is clicked");
				action.moveToElement(upload).click().build().perform();
				System.out.println("The element to be uploaded is clicked");
				robot.delay(2000);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_V);
				robotDelay(delaytime);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				captureScreenShot();
				highlightElement(locator);
				reportPass("Uploading of the File using Robot Class");
				screenshotForExtentReports(driver);
				System.out.println("The file is uploaded using Robot Class");

			}
		} catch (Exception e) {
			reportFail("Uploading of the File is Failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("File Uploading is Failed"+e);
		}

	}

	public void typeTextUsingRobot(By locator, String keys) throws AWTException, IOException {
		try {
			Robot robot = new Robot();
			typeText(locator, keys);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			captureScreenShot();
			highlightElement(locator);
			reportPass("The Keys are send using Robot Class");
			screenshotForExtentReports(driver);
			System.out.println("The text is sent Successfully using Robot Class");

		} catch (Exception e) {
			reportFail("The Keys are send using Robot Class is Failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("Sending the text is Failed using Robot Class"+e);
		}
	}

	public void mouseScrollingUsingRobot(int srollbypixels) throws AWTException, IOException {
		try {
			Robot robot = new Robot();
			robot.mouseWheel(srollbypixels);
			reportPass("Mouse Scrolling using Robot Class is Successful");
			screenshotForExtentReports(driver);
			System.out.printf("Mouse Scrolling is done by", srollbypixels);
		} catch (Exception e) {
			reportFail("Mouse Scrolling using Robot Class is Failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("Mouse Scrolling is Failed using Robot Class"+e);
		}
	}

	public void robotDelay(int delaytime) throws AWTException, IOException {
		try {
			Robot robot = new Robot();
			robot.delay(delaytime);
			reportPass("The delay is started");
			screenshotForExtentReports(driver);
			System.out.printf("Delay time started wait until", delaytime);
		} catch (Exception e) {
			reportFail("The delay is not invoked");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The delay by Robot class is failed"+e);
		}
	}

	public void highlightElement(By locator) throws IOException {
		try {
			WebElement ele = driver.findElement(locator);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
			System.out.println("Highlighting of the Element by locator is done");
			captureScreenShot();
			reportPass("HighLighting of the Element by" + locator + "is Successful");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The Element is not Highlighted"+e);
			captureScreenShot();
			reportFail("HighLighting of the Element by" + locator + "is Failed");
			screenshotForExtentReports(driver);
		}
	}

	private void highlightElements(By locator) throws IOException {
		try {
			List<WebElement> list = driver.findElements(locator);

			for (int i = 0; i < list.size(); i++) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
						list.get(i));
			}
			System.out.println("Highlighting of the Elements by locator is done");
			captureScreenShot();
			reportPass("HighLighting of the Element by " + locator + " is Successful");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The Elements are not Highlighted"+e);
			captureScreenShot();
			reportFail("HighLighting of the Element by " + locator + " is Failed");
			screenshotForExtentReports(driver);
		}
	}

	public void actionClass(WebElement locator) throws IOException {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(locator).build().perform();
			captureScreenShot();
			System.out.println("The Action Class is invoked");
			reportPass("Action Class is invoked and moving to the Element" + locator + "is Successful");
			screenshotForExtentReports(driver);
		} catch (Exception e) {
			reportFail("Action Class is invoked and moving to the Element" + locator + "is Failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The Action Class is not invoked"+e);
		}
	}

	public void implicitWait(int wait) throws IOException {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(wait));
			//captureScreenShot();
			System.out.println("Implicit wait has been invoked");
			//logger.log(Status.PASS, "Implicit wait has been invoked");
			//screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("Implicit wait has not invoked"+e);
			captureScreenShot();
			//reportFail("The Implicit wait invoking is Failed");
			//screenshotForExtentReports(driver);
		}
	}

	public void webdriverwait(By locator, Duration i) throws IOException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, i);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			System.out.println("The WedDriver wait is invoked");
			highlightElement(locator);
			captureScreenShot();
			reportPass("The Webdriver is invoked");
			screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The WedDriver wait is not invoked"+e);
			captureScreenShot();
			reportFail("The Webdriver is not invoked");
			screenshotForExtentReports(driver);
		}
	}

	public void acceptAlerts(int wait) throws IOException {
		try {
			implicitWait(wait);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			captureScreenShot();
			reportPass("The Alert is accepted");
			screenshotForExtentReports(driver);
			System.out.println("The Alert is accepted successfully");
		} catch (Exception e) {
			captureScreenShot();
			reportFail("The Alert is not accepted");
			screenshotForExtentReports(driver);
			System.out.println("The CheckAlert is not invoked"+e);
		}
	}

	public void dismissAlerts(int wait) throws IOException {
		try {
			implicitWait(wait);
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			captureScreenShot();
			System.out.println("The Alert is dismissed successfully");
			reportPass("The Alert got dismissed successfully");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The DismissAlerts is not invoked"+e);
			captureScreenShot();
			reportFail("The Alert got dismissing is Failed");
			screenshotForExtentReports(driver);
		}
	}

	public void alertText() throws IOException {
		try {
		String alertText = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();

			//captureScreenShot();
			System.out.println("The text is fetched from the Alert "+ alertText);
			reportPass("The Alert text is extracted successfully");
			//screenshotForExtentReports(driver);
		} catch (Exception e) {
			System.out.println("The text is not fetched from the Alert"+e);
			captureScreenShot();
			reportFail("The Alert text is not extracted");
			screenshotForExtentReports(driver);
		}
	}
	public void alertTypeText(Duration timeOut, String keys) throws IOException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().sendKeys(keys);
			captureScreenShot();
			reportPass("The text is sent into Alert successfully");
			screenshotForExtentReports(driver);
			System.out.println("The keys are successfully entered");

		} catch (Exception e) {
			reportFail("The text is not sent into Alert successfully");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The keys are not entered"+e);
		}
	}

	public void windowHandles() throws IOException {
		try {
			Set<String> parentWindow = driver.getWindowHandles();
			Iterator<String> iterator = parentWindow.iterator();
			while (iterator.hasNext()) {
				String childWindow = iterator.next();
				if (!parentWindow.equals(childWindow)) {
					driver.switchTo().window(childWindow);
				}
			}
			captureScreenShot();
			reportPass("The Scrolling is invoked and got success");
			screenshotForExtentReports(driver);
			System.out.println("The Window Handles are now in ChildWindow");
		} catch (Exception e) {
			reportFail("The Windows handling is Failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The Window Handles is not Successfull"+e);
		}
	}

	public void captureScreenShot() throws IOException {
		File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		System.out.println("The Screenshot is taken Successfully");
		try {
			String dest = ".//Screenshots/";
			FileUtils.copyFile(screenShot, new File(".//Screenshots/" + System.currentTimeMillis() + ".png"));
			reportPass("The ScreenShot is Captured Successfully");
			screenshotForExtentReports(driver);
			System.out.println("The Screenshot is saved Successfully");
		} catch (IOException e) {
			reportFail("The ScreenShot is not Captured Successfully");
			screenshotForExtentReports(driver);
			System.out.println("The Screenshot is not taken Successfully"+e);
		}
	}

	public void frames(By locator) throws IOException {
		try {
			driver.switchTo().frame(driver.findElement(locator));
			captureScreenShot();
			highlightElement(locator);
			reportPass("The frames by locator" + locator + " is invoked successfully");
			screenshotForExtentReports(driver);
			System.out.println("The Frames are switched by the locator");

		} catch (Exception e) {
			reportFail("The frames by locator" + locator + " is not invoked successfully");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The frames switching by locator is not invoked"+e);
		}
	}

	public void switchToFramesusingId(String frameid) throws IOException {
		try {
			driver.switchTo().frame(frameid);
			captureScreenShot();
			reportPass("The frames by ID" + frameid + " is invoked successfully");
			screenshotForExtentReports(driver);
			System.out.println("The Frames are switched by the ID");

		} catch (Exception e) {
			reportFail("The frames by ID" + frameid + " is not invoked successfully");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The frames switching by ID is not invoked"+e);
		}
	}

	public void switchToFramesusingname(String framename) throws IOException {
		try {
			driver.switchTo().frame(framename);
			captureScreenShot();
			reportPass("The frames by ID" + framename + " is invoked successfully");
			screenshotForExtentReports(driver);
			System.out.println("The Frames are switched by the Framename");
		} catch (Exception e) {
			reportFail("\"The frames by Name\"+framename+\" is not invoked successfully");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The frames switching by Framename is not invoked"+e);
		}
	}

	public void switchtoframesusinglocator(String frameElement) throws IOException {
		try {
			driver.switchTo().frame(frameElement);
			captureScreenShot();
			reportPass("The frames by Locator" + frameElement + " is invoked successfully");
			screenshotForExtentReports(driver);
			System.out.println("The Frames are switched by the FrameElement");

		} catch (Exception e) {
			reportFail("\"The frames by Locator" + frameElement + " is not invoked");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("The frames switching by FrameElement is not invoked"+e);
		}
	}

	public void switchtoframesusingindex(int index) throws IOException {
		try {
			driver.switchTo().frame(index);
			System.out.println("The Frames are switched by the index");
			captureScreenShot();
			reportPass("The frames by index" + index + " is invoked successfully");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The frames switching by index is not invoked"+e);
			reportFail("\"The frames by index" + index + " is not invoked successfully");
			screenshotForExtentReports(driver);
			captureScreenShot();
		}
	}

	public void switchToDefaultcontent() throws IOException {
		try {
			driver.switchTo().defaultContent();
			captureScreenShot();
			System.out.println("The Frames are switched by the defaultContent");
			reportPass("The frames by default content is invoked successfully");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The frames switching by defaultContent is not invoked"+e);
			captureScreenShot();
			reportFail("The frames by default content is not invoked successfully");
			screenshotForExtentReports(driver);
		}

	}

	public TreeSet listOfElements(By locator, int size) throws IOException {
		try {
			List<WebElement> list = driver.findElements(locator);
			for (int i = 0; i < list.size(); i++) {
				listOfElements.add(list.get(i).getText());
			}
			System.out.println("The Elements are stored in the TreeSet");
			captureScreenShot();
			highlightElement(locator);
			reportPass("The List of Elements from locator" + locator + "are extracted successfully");
			screenshotForExtentReports(driver);

		} catch (Exception e) {
			System.out.println("The ListofElements method is not invoked"+e);
			captureScreenShot();
			reportFail("\"The List of Elements from locator " + locator + "are not extracted successfully");
			screenshotForExtentReports(driver);
		}
		return listOfElements;
	}

	public TreeSet revListOfElements(By locator) throws IOException {
		try {
			List<WebElement> list = driver.findElements(locator);
			highlightElements(locator);
			for (int i = 0; i < list.size(); i++) {
				revlistOfElements.add(list.get(i).getText());
			}
			TreeSet<Object> descendingOrder = (TreeSet<Object>) revlistOfElements.descendingSet();

			System.out.println("The Reverse Elements are stored in the TreeSet");
			System.out.println("The Reverse Elements are " + descendingOrder);
			captureScreenShot();

			reportPass("The List of Elements from locator" + locator + "are extracted successfully");
			screenshotForExtentReports(driver);
			descendingOrder.clear();

		} catch (Exception e) {
			System.out.println("The ListofElements method is not invoked"+e);
			captureScreenShot();
			reportFail("\"The List of Elements from locator " + locator + "are not extracted successfully");
			screenshotForExtentReports(driver);
		}
		return revlistOfElements;

	}

	public void preferenceSelecting(By locator, String value) throws IOException {
		try {
			List<WebElement> preference = driver.findElements(locator);
			for (WebElement preferences : preference) {
				if (preferences.getText().equals(value)) {
					preferences.click();
					break;
				}
			}
			captureScreenShot();
			highlightElement(locator);
			reportPass("The Preference Selection by locator " + locator + "is successful");
			screenshotForExtentReports(driver);
			System.out.println("The Preference Selection is done");
		} catch (Exception e) {
			System.out.println("The Preference Selection is not done"+e);
			captureScreenShot();
			reportFail("The Preference Selection by locator " + locator + "is not successful");
			screenshotForExtentReports(driver);
		}

	}
	
	public void enterDataByActionClass(String value, By dateMonthyear) throws IOException {
		try {
			Actions action = new Actions(driver);
			WebElement dayMonthYearSel = driver.findElement(dateMonthyear);
			action.moveToElement(dayMonthYearSel).click().sendKeys(value).perform();
			captureScreenShot();
			reportPass("Moving to Element is done");
			screenshotForExtentReports(driver);
			System.out.println("Element is Clicked using action Class");
 
		} catch (Exception e) {
			reportFail("Clicking an Element is failed");
			screenshotForExtentReports(driver);
			captureScreenShot();
			System.out.println("Element is not Clicked using action Class" + e);
		}
	}
	
	public String getTitle() {
		return driver.getTitle();
 
	}

	
}