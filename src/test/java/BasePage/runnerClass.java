package BasePage;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import BasePage.Base;
import Utils.Utilsforextentreports;
import Utils.readexcel;

public class runnerClass extends Base{
	Base basepage=new Base();
	public  String dataSheet;
	public String SheetName;
	
	
	
	@BeforeSuite
	public void beforesuit() throws IOException {
		//System.out.println(browser);
		browserSetup();
		
	}
	@BeforeMethod
	public void beforemethod() throws IOException {
		invokingUrl();
	}
	
	@AfterMethod 
	public void afterMethod() { 
		endReport();
	} 
	@AfterSuite 
	public void afterSuite() throws IOException { 
		quitDriver(); 
		
	}

	@DataProvider(name = "testData")
	public Object[][] getData() throws IOException {
		return readexcel.readData(dataSheet);
	}
	
}
