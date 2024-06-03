package ScenarioPage;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

//import testNG.dataProviders;
import BasePage.Base;
import Utils.Utilsforextentreports;

public class UPSCRegistrationPage extends Base {
	Utilsforextentreports utils = new Utilsforextentreports();
	
	private static final By firstName = By.id("fstname");
	private static final By fatherfirstName = By.id("fatherfstname");
	private static final By motherfirstName = By.id("motherfstname");
	private static final By sexDropDown =By.id("sex");
	private static final By communityDropDown = By.id("community");
	private static final By disability = By.id("handicapp");
	private static final By dateSelector = By.xpath("//*[@id='dobim ']");
	private static final By months = By.name("MonthSelector");
	private static final By years = By.name("YearSelector");
	private static final By days = By.xpath("//span[@id='calBorder']/span/table/tbody/tr/following::tr/following::tr/td/table/tbody//td");
	private static final By place = By.id("pob");
	private static final By state = By.id("sob");
	private static final By nationality = By.id("nationality");
	private static final By mobileNumber = By.id("mobileno");
	private static final By confirmMoblieNumber = By.id("mobileno_confirm");
	private static final By email = By.id("Email");
	private static final By confirmEmail = By.id("confirmEmail");
	private static final By password = By.id("DesiredPassword");
	private static final By confirmPassword = By.id("ConfirmPassword");
	private static final By captcha = By.id("phrase");
	private static final By submitButton = By.name("submit");
	private static final By successPageTitle = By.xpath("//table[@class='front']//strong");

	
	public void extentReport(String testName, String author, String category) {
		try {
			utils.reportDetails(testName, author, category);
		}catch(Exception e) {
			System.out.println("The ExtentReport is not Created, Check the Excel Sheet Path" + e);
		}	
	}
	
	public void fillNames(String Name) throws IOException {
		try {
			typeText(firstName, Name);
			typeText(fatherfirstName, Name);
			typeText(motherfirstName, Name);
			System.out.println("Names Entered Successfully "+firstName+fatherfirstName+motherfirstName);
		} catch (IOException e) {
			
			System.out.println("Names are not entered "+firstName+fatherfirstName+motherfirstName+e);
		}	
	}
	
	public void selectGender(String sex) throws IOException {
		try {
			selectClassByValue(sexDropDown, sex);
			System.out.println("Gender Selected Successfully "+sexDropDown);
		} catch (IOException e) {
			System.out.println("Names are not entered "+sexDropDown+e);
		}
	}
	public void selectCommunity() throws IOException {
		try {
			Random random = new Random();
			int index =random.nextInt(5 - 1) + 1;;
			selectClassByIndex(communityDropDown, index);
			System.out.println("Community Selected Sucessfully "+ communityDropDown);
		} catch (IOException e) {
			System.out.println("Community not Selected  "+ communityDropDown+e);
		}
	}
	public void selectDisability(String disable) throws IOException {
		try {
			selectClassByVisibleText(disability, disable);
			System.out.println("Disability option selected successfully "+disability);
			
		} catch (IOException e) {
			System.out.println("Disability option not selected "+disability+e);
		}
	}
	public void selectDateofBirth(String day,String month,String year) throws IOException {
		try {
			elementClick(dateSelector);
			enterDataByActionClass(month,months);
			enterDataByActionClass(year,years);
			preferenceSelecting(days,day);
			System.out.println("Date selected Successfully "+days+months+years);
		} catch (IOException e) {
			System.out.println("Date not selected "+days+months+years+e);
		}
	
	}
	public void selectPlaceofBirth(String city) throws IOException {
		try {
			typeText(place, city);
			System.out.println("Place selected successfully "+place);
		} catch (IOException e) {
			System.out.println("Place not selected "+place+e);
		}
	}
	public void selectStateofBirth(String stateName) throws IOException {
		try {
			selectClassByValue(state, stateName);
			System.out.println("state selected successfully "+state);
		} catch (IOException e) {
			System.out.println("state not selected  "+state+e);
		}
	}
	public void selectNationality(String nation) throws IOException {
		try {
			selectClassByValue(nationality, nation);
			System.out.println("Nation Selected Successfully "+nationality);
		} catch (IOException e) {
			System.out.println("Nation not Selected "+nationality+e);
		}
	}
	public void entermobileNumber(String mobileno) throws IOException {
		try {
			typeText(mobileNumber, mobileno);
			typeText(confirmMoblieNumber, mobileno);
			System.out.println("Mobile Number entered Successfully "+mobileNumber+confirmMoblieNumber);
		} catch (IOException e) {
			System.out.println("Mobile Number not entered "+mobileNumber+confirmMoblieNumber+e);
		}
	}
	public void enteremail(String emailData) throws IOException {
		try {
			typeText(email, emailData);	
			typeText(confirmEmail, emailData);
			System.out.println("Email Entered Successfully "+email+confirmEmail);
		} catch (IOException e) {
			System.out.println("Email not Entered  "+email+confirmEmail+e);
		}
	}
	public void enterpassword(String PasswordData) throws IOException {
		try {
			typeText(password, PasswordData);
			typeText(confirmPassword, PasswordData);
			System.out.println("Password entered Successfully "+password+confirmPassword);
		} catch (IOException e) {
			System.out.println("Password Not entered "+password+confirmPassword+e);
		}
	}
	public void submit(String expValue) throws IOException {
		try {
			elementClick(submitButton);
			String actValue = getTitle();
			assertEqual(actValue,expValue);
			System.out.println("form Sumitted Successfully "+submitButton);
		} catch (IOException e) {
			System.out.println("form Not Sumitted "+submitButton+e);
		}
	}

	public void enterCaptha() throws InterruptedException, IOException {
		scrollToElement(submitButton);
		Thread.sleep(12000);
		highlightElement(captcha);
		
	}
}
