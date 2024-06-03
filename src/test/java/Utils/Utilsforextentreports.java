package Utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter; 


public  class Utilsforextentreports {
	public static ExtentHtmlReporter exthtml; 
	public static ExtentTest exttest; 
	public static ExtentReports report; 
	public static ExtentTest logger; 
	public WebDriver driver; 
	public static ExtentTest log ; 
	
	public void reportDetails(String testName,String author,String category) {
		exthtml = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\reports\\ExtentReport.html"); 
		report = new ExtentReports(); 
		report.attachReporter(exthtml);
		System.out.println("------------");
		System.out.println(testName+ author+ category);
//		logger=report.createTest(testName).assignAuthor(author).assignCategory(category); 
		if (report != null) {
	        logger = report.createTest(testName).assignAuthor(author).assignCategory(category);
	    } else {
	        // Handle the case where the report object is null
	        System.out.println("Error creating ExtentReports instance");
	    }
	}
	public void reportPass(String report) { 
		logger.log(Status.PASS, report); 
	} 		  
	public void endReport() { 
		report.flush(); 
	} 
	public void reportFail(String report) { 
		logger.log(Status.FAIL, report); 
	}   
	public void screenshotForExtentReports(WebDriver driver) throws IOException {
		String path=getScreenshot(driver); 
		logger.addScreenCaptureFromPath(path); 
	}
	public static String getScreenshot(WebDriver driver) throws IOException
	{
		TakesScreenshot screen=(TakesScreenshot) driver; 
		File srcpath=screen.getScreenshotAs(OutputType.FILE); 
		String image=System.getProperty("user.dir")+"/reportscreenshots/"+System.currentTimeMillis()+".png"; 
		File desti=new File(image); 
		FileUtils.copyFile(srcpath, desti); 
		return image;
	}	
	
}
