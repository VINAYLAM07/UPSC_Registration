package TestPages;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import BasePage.Base;
import BasePage.runnerClass;
import ScenarioPage.UPSCRegistrationPage;
public class UPSCRegistrationTest extends runnerClass {
	
	Base basepage = new Base();
	UPSCRegistrationPage register = new UPSCRegistrationPage();
	
	
	@BeforeTest
	public void data() {
		dataSheet="Upsc_RegistrationData";
	}
	@Test(dataProvider="testData")
	public void register(String firstName,String sex,String disability,String day,String month,String year,String city,String state,String nationality,String mobileNo,String email,String password,String Title, String testName, String author,String category) throws IOException, InterruptedException {
		System.out.print("------------");
		register.extentReport(testName, author, category);
		register.fillNames(firstName);
		register.selectGender(sex);
		register.selectCommunity();
		register.selectDisability(disability);
		register.selectDateofBirth(day,month,year);
		register.selectPlaceofBirth(city);
		register.selectStateofBirth(state);
		register.selectNationality(nationality);
		register.entermobileNumber(mobileNo);
		register.enteremail(email);
		register.enterpassword(password);
		register.enterCaptha();
		register.submit(Title);
	}


}
