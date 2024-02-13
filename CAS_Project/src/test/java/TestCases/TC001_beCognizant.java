package TestCases;

import java.io.IOException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageObjects.beCognizant;
import PageObjects.oneCognizant;
import PageObjects.timeSheet;
import TestBase.baseClass;

public class TC001_beCognizant extends baseClass
{


	@Test(priority=1)

	public void userVerification() throws InterruptedException, IOException 
	{
		logger.info("***** starting TC001_beCognizant *****");
		
		
		beCognizant bec = new beCognizant(driver);
		bec.clickProfile();
		logger.info("clicked on profile");
		Thread.sleep(1000);
		System.out.println("Account verified");
		logger.info("verified the account");
		bec.getProfile();
		captureScreen("img_userprofile");
		bec.closeProfile();
		Thread.sleep(10000);
		bec.openOneCognizant(driver);
		logger.info("opening the OneCognizant");
		Thread.sleep(10000);
		bec.windowHandlesOneCog(driver);
		logger.info("handled the OneCognizant window");
		captureScreen("img_onecognizant");
		
		
		}
		
	
	@Test(priority=2)
	@Parameters("browser")
	
	public void oneCognizantFunctions(String br) throws InterruptedException
	{
		logger.info("Navigated to OneCognizant");
		oneCognizant one = new oneCognizant(driver); 
		Thread.sleep(1000);
		
		//one.clickSearchIcon();
		logger.info("clicked on search Icon");
		one.inputSearchBar(br);
		
		//one.timeSheetIcon();
		logger.info("input timesheet in search bar");
		captureScreen("img_timesheetIcon");
		
		Thread.sleep(10000);
		one.windowHandelsTimesheet(driver);
		logger.info("handled the timesheet window");
	}
	
	@Test(priority=3)
	public void timeSheetNavigation() throws InterruptedException, IOException
	{
		logger.info("Navigated to timesheet");
		timeSheet ts = new timeSheet(driver);
		
		ts.headerValidation();
		captureScreen("img_timesheet");
		logger.info("validating the header");
		
		Thread.sleep(1000);
		ts.threeWeeksTimesheet();
		logger.info("three weeks of timesheet");
		captureScreen("img_firstThreeWeeks");
		
		Thread.sleep(1000);
		ts.currentWeek();
		logger.info("Displayed current week");
		captureScreen("img_currentWeek");
		ts.dateValidationTimesheet();
		
		Thread.sleep(5000);
		ts.tsStatusApproved();
		logger.info("displayed  tsStatusApproved");
		captureScreen("img_StatusApproved");
		
		Thread.sleep(5000);
		ts.tsStatusOverdue();
		logger.info("displayed tsStatusOverdue");
		captureScreen("img_StatusOverdue");
		
		Thread.sleep(5000);
		ts.tsStatusPartiallyApproved();
		logger.info("displayed tsStatusPartiallyApproved");
		captureScreen("img_StatusPartiallyApproved");
		
		Thread.sleep(5000);
		ts.tsStatusPending();
		logger.info("displayed tsStatusPending ");
		captureScreen("img_StatusPending");
	
		Thread.sleep(5000);
		ts.tsStatusSaved();
		logger.info("displayed tsStatusSaved");
		captureScreen("img_StatusSaved");
		
		Thread.sleep(5000);
		ts.tsStatusSentBackforRevision();
		logger.info("displayed tsStatusSentBackforRevision");
		captureScreen("img_StatusSentBackforRevision");
		
		Thread.sleep(5000);
		ts.tsStatusSubmittedforApproval();
		logger.info("displayed tsStatusSubmittedforApproval");
		captureScreen("img_StatusSubmittedforApproval");
		
		logger.info("***** closing the browser *****");	
	}
		
		
		
}
