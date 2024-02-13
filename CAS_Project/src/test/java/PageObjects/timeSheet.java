package PageObjects;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import Utilitis.ExcelUtilis;

public class timeSheet extends basePage{

	public timeSheet(WebDriver driver) 
	{
		super(driver);
		
	}
	int rowNo = 0, appRowNo=0,curRowno=0,ovrRowNo=0,sumRowNo=0,revrowNo=0,savrowNo=0,penrowNo=0,parrowNo=0;
	
	String websiteHeader = "Timesheets", firstWeekDate, dateConcat, startDatestr, endDatestr;
	String[] resultDate; 
	
	@FindBy(xpath="//*[@id='PT_PAGETITLElbl']")
	WebElement pageTitle;
	
	@FindBy(xpath="//*[@id='win0groupletPTNUI_LAND_REC_GROUPLET$0']")
	WebElement timesheet2;
	
	@FindBy(xpath="//a[contains(@id,'CTS_TS_LAND_PER_DESCR30$') and contains(@class,'ps-link')]")
	List<WebElement> timesheetDates;
	
	@FindBy(xpath="//span[contains(@id,'CTS_TS_LAND_PER_CTS_TS_STATUS_LAND$')]")
	List<WebElement> timesheetDatesStatus;
	
	@FindBy(xpath="//*[@id='CTS_TS_LAND_WRK_CTS_TS_SEARCH']")
	WebElement timesheetSearchBy;
	
	@FindBy(xpath="//*[@id='CTS_TS_LAND_WRK_DATE$prompt']")
	WebElement timesheetCalendar;
	
	@FindBy(xpath="//a[@id='curdate']")
	WebElement timesheetCurrentDate;
	
	@FindBy(xpath="//a[@id='CTS_TS_LAND_WRK_SEARCH']")
	WebElement timesheetSearch;
	
	@FindBy(xpath="//*[@id='CTS_TS_LAND_PER_DESCR30$0']")
	WebElement currentWeek;
	
	@FindBy(xpath="//select[@id='CTS_TS_LAND_WRK_CTS_TS_LAND_STATUS']")
	WebElement timesheetStatus;
	
	@FindBy(xpath="//*[@id='msgcontainer']")
	WebElement errorMsg;
	
	@FindBy(xpath="//*[@id='#ICOK']")
	WebElement okButton;
	
	public void headerValidation()
	{
		
		System.out.println("");
		if (pageTitle.getText().equalsIgnoreCase(websiteHeader))
		{
			System.out.println("Website header for TimeSheet is verified.\n \nOpening Timesheet.....");
		}
		else
		{
			System.out.println("Website header is not verified.\n \nOpening Timesheet......");
			timesheet2.click();
			
		}
		System.out.println("");
	}
	
	public void threeWeeksTimesheet() throws IOException 
	{
		
		System.out.println(" ");
		System.out.println("--------- Dates of Last Three Weeks ---------");
		for (int date =0;date<3;date++)
		{ 	
			firstWeekDate =timesheetDates.get(0).getText();
			dateConcat = timesheetDates.get(date).getText();
			System.out.println(dateConcat);
			
			ExcelUtilis.write("Dates of Last Three Weeks", rowNo,0, dateConcat);
			rowNo++;
			
			resultDate = firstWeekDate.split(" To ");
		}
		System.out.println("---------------------------------------------");
		System.out.println(" ");
	}
	
	public void currentWeek() throws InterruptedException, IOException
	{
		
		Select dropdown = new Select(timesheetSearchBy);
		dropdown.selectByVisibleText("Date");
		
		Thread.sleep(3000);
		timesheetCalendar.click();
		timesheetCurrentDate.click();
		timesheetSearch.click();
		
		System.out.println(" ");
		System.out.println("------------- The Current Week --------------");
		System.out.println(currentWeek.getText());
		System.out.println("---------------------------------------------");
		System.out.println(" ");
		ExcelUtilis.write("The Current Week", rowNo,0, currentWeek.getText());
		
	}
	
	public void dateValidationTimesheet() throws IOException 
	{
		
		startDatestr = resultDate[0];
		endDatestr = resultDate[1];
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
		try 
		{
			Date startDate=dateFormat.parse(startDatestr);
			Date endDate=dateFormat.parse(endDatestr);
			
			System.out.println(" ");
			System.out.println("--------- Date Validation for Current Week ---------");
			if(isSaturday(startDate)&& isFriday(endDate)) 
			{
				
				System.out.println("Timesheet is valid : "+startDatestr+"is Saturday and\n                     "+endDatestr+"is Friday");
				
				String starts= "Timesheet is valid : "+startDatestr+"is Saturday and";
				String ends= endDatestr+"is Friday";
				
				ExcelUtilis.write("Date Validation-Current Week", curRowno,0, starts);
				curRowno++;
				
				ExcelUtilis.write("Date Validation-Current Week", curRowno,0, ends);
			}
			else 
			{
				System.out.println("The Week given in the Timesheet is invalid");
				ExcelUtilis.write("Date Validation for Current Week", curRowno,0,"The Week given in the Timesheet is invalid" );
				
			}
			System.out.println("---------------------------------------------");
			System.out.println(" ");
		}
		catch(ParseException e) 
		{
			e.printStackTrace();
		}
	}
 
	static boolean isSaturday(Date date) 
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY;
	}
	
	static boolean isFriday(Date date) 
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY;
	}
	
	public void tsStatusApproved() throws InterruptedException, IOException
	{
		
		Select dropdown = new Select(timesheetSearchBy);
		dropdown.selectByVisibleText("Status");
		Thread.sleep(10000);
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Approved");
		timesheetSearch.click();
		
		
		Thread.sleep(10000);
		
		System.out.println(" ");
		System.out.println("------------- The Approved Week --------------");
		for(int a=0;a<timesheetDates.size();a++)
		{
			
			System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			ExcelUtilis.write("The Approved Week", appRowNo,0, timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
		
			appRowNo++;
		}
		System.out.println("----------------------------------------------");
		System.out.println(" ");
	}
	
	public void tsStatusOverdue() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Overdue");
		timesheetSearch.click();
		
	
		Thread.sleep(10000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Overdue Week --------------");
			System.out.println("No results found. ");
			ExcelUtilis.write("The Overdue Week", ovrRowNo,0,"No results found. " );
			System.out.println("---------------------------------------------");
			System.out.println(" ");
			okButton.click();
		}
		
		else
		{
			System.out.println(" ");
			System.out.println("------------- The Overdue Week --------------");
			for(int a =0;a<timesheetDates.size();a++)
			{
				System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDates.get(a).getText()+"'");
		
				ExcelUtilis.write("The Overdue Week", ovrRowNo,0,timesheetDates.get(a).getText()+" - '"+timesheetDates.get(a).getText()+"'");
				ovrRowNo++;
			}
			System.out.println("---------------------------------------------");
			System.out.println(" ");
		}
	}
	
	public void tsStatusPartiallyApproved() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Overdue");
		timesheetSearch.click();
		
		Thread.sleep(10000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Partially Approved Week --------------");
			System.out.println("No results found. ");
			ExcelUtilis.write("The Partially Approved Week", parrowNo,0,"No results found. ");
			
			System.out.println("---------------------------------------------------------");
			System.out.println(" ");
			okButton.click();
		}
		
		else
		{
			System.out.println(" ");
			System.out.println("------------- The Partially Approved Week --------------");
			for(int a =0;a<timesheetDates.size();a++)
			{
				System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDates.get(a).getText()+"'");
				
				ExcelUtilis.write("The Partially Approved Week", parrowNo,0,timesheetDates.get(a).getText()+" - '"+timesheetDates.get(a).getText()+"'");
				parrowNo++;
			}
			System.out.println("---------------------------------------------------------");
			System.out.println(" ");
		}
	}
	
	public void tsStatusPending() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Pending");
		timesheetSearch.click();
		
		Thread.sleep(10000);
		
		System.out.println(" ");
		System.out.println("------------- The Pending Week --------------");
		for(int a =0;a<timesheetDates.size();a++)
		{
			System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			ExcelUtilis.write("The Pending Week", penrowNo,0,timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			penrowNo++;
		}
		System.out.println("----------------------------------------------");
		System.out.println(" ");
	}
	
	public void tsStatusSaved() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Saved");
		timesheetSearch.click();
		
		Thread.sleep(10000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Saved Week --------------");
			System.out.println("No results found. ");
			
			ExcelUtilis.write("The Saved Week", savrowNo,0,"No results found. ");
			
			System.out.println("-------------------------------------------");
			
			System.out.println(" ");
			okButton.click();
		}
		
		else
		{
			System.out.println(" ");
			System.out.println("------------- The Saved Week --------------");
			for(int a =0;a<timesheetDates.size();a++)
			{
				System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
				
				
				ExcelUtilis.write("The Saved Week", savrowNo,0,timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
				savrowNo++;
			}
			System.out.println("--------------------------------------------");
			System.out.println(" ");
		}	
	}
	
	
	public void tsStatusSentBackforRevision() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Sent Back for Revision");
		timesheetSearch.click();
		
		Thread.sleep(10000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Sent Back for Revision Week --------------");
			System.out.println("No results found. ");
			
			ExcelUtilis.write("The Sent Back for Revision Week", revrowNo,0,"No results found. ");
			System.out.println("------------------------------------------------------------");
			System.out.println(" ");
			okButton.click();
		}
		
		else
		{
			System.out.println(" ");
			System.out.println("------------- The Sent Back for Revision Week --------------");
			for(int a =0;a<timesheetDates.size();a++)
			{
				System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDates.get(a).getText()+"'");
				
				ExcelUtilis.write("The Sent Back for Revision Week", revrowNo,0,timesheetDates.get(a).getText()+" - '"+timesheetDates.get(a).getText()+"'");
				revrowNo++;
			}
			System.out.println("------------------------------------------------------------");
			System.out.println(" ");
		}
		
	}
	
	
	public void tsStatusSubmittedforApproval() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Submitted for Approval");
		timesheetSearch.click();
		
		Thread.sleep(10000);
		{
		System.out.println(" ");
		System.out.println("------------- The Submitted for Approval Week --------------");
		for(int a =0;a<timesheetDates.size();a++)
		{
			System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			
			ExcelUtilis.write("The Submitted for Approval Week", sumRowNo,0,timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			
			sumRowNo++;
		}
		System.out.println("------------------------------------------------------------");
		System.out.println(" ");
		}
	}
	
}
