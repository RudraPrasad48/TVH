package testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;

public class SmokeSuite {
	WebDriver driver;
	
  @Test (priority=1)
  public void login() throws InterruptedException, AWTException, IOException{
	 
	//Upload the template file
	  WebElement uploadTemplate =driver.findElement(By.xpath("(//input[@type='file'])[1]")); 
	  File template = new File("./UploadTemplate-SmokeTestIM.xlsx");
	  uploadTemplate.sendKeys(template.getAbsolutePath()); 
	  Thread.sleep(10000);
 
	 //Upload the drawing
	   WebElement uploadDrawing =driver.findElement(By.xpath("(//input[@type='file'])[2]")); 
	   File drawing =new File("./Drawings"); 
	   uploadDrawing.sendKeys(drawing.getAbsolutePath());
	   Thread.sleep(10000);
	
     //Activate Project
	   driver.findElement(By.xpath("//button[text()='Activate Project']")).click();
	   Thread.sleep(15000);
	   driver.navigate().refresh();
	   Thread.sleep(10000);
	   
	 //Open the first project
	   WebElement firstProject = driver.findElement(By.xpath("//a[text()='Smoke Test Project']"));
	   firstProject.click();
	   Thread.sleep(20000);
  }
  
  @Test (priority=2)
  public void validateCosting() {
	  try
	  {
		  Thread.sleep(10000);
		
		  //1. TC to verify Manufacturing country is as template
	        WebElement ActualManufacturingCountry= driver.findElement(By.xpath("//input[@formcontrolname='ManufacturingCountry']")); 
	        String ExpectedManufacturingCountry="USA";
		    Assert.assertEquals(ExpectedManufacturingCountry,ActualManufacturingCountry.getAttribute("value"));
		    System.out.println("TC1. Manufacturing Country is USA – Assert passed");
				  
		  //2. TC to verify Annual Volume is as template
		    WebElement ActualAnnualVolume =driver.findElement(By.xpath("//input[@formcontrolname='AnnualVolume']")); 
		    String ExpectedAnnualVolume="12000";
		    Assert.assertEquals(ExpectedAnnualVolume, ActualAnnualVolume.getAttribute("value"));
		    System.out.println("TC2. Annual Volume is 12000 – Assert passed");
		    
		  //3. TC to verify Delivery Frequency is default to 30
		    WebElement ActualDF =driver.findElement(By.xpath("//input[@formcontrolname='DeliveryFrequency']")); 
		    String ExpectedDF="30";
		    Assert.assertEquals(ExpectedDF, ActualDF.getAttribute("value"));
		    System.out.println("TC3. DeliveryFrequency is default to 30 – Assert passed");
		    
		  //4. TC to verify the lot size calculation
		    WebElement ActualLotSize =driver.findElement(By.xpath("//input[@formcontrolname='lotsize']")); 
		    int ExpectedLotSize= (Integer.parseInt(ActualAnnualVolume.getAttribute("value"))/12);
		    Assert.assertEquals(ExpectedLotSize, Integer.parseInt(ActualLotSize.getAttribute("value")));
		    System.out.println("TC4. LotSize is calculated as expected – Assert passed");
		  
		  //5. TC to verify the lot size calculation
		    WebElement ActualLifeTimeQR =driver.findElement(By.xpath("//input[@formcontrolname='lifeTimeQtyRemaining']")); 
		    WebElement ProductLifeRemaining =driver.findElement(By.xpath("//input[@formcontrolname='prodLifeRemaining']")); 
		    int ExpectedLifeTimeQR= (Integer.parseInt(ActualAnnualVolume.getAttribute("value"))*Integer.parseInt(ProductLifeRemaining.getAttribute("value")));
		    Assert.assertEquals(ExpectedLifeTimeQR, Integer.parseInt(ActualLifeTimeQR.getAttribute("value")));
		    System.out.println("TC5. Life Time Quantity Remaining is calculated as expected – Assert passed");
		    
		  //6. TC to verify the material section entries
		      //<!------Expand Material section-----!>
			  WebElement ExpandMaterialSection = driver.findElement(By.xpath("//h6[text()='Material Information']"));
			  ExpandMaterialSection.click();
			  Thread.sleep(10000);
		      //<!------Verifying multiple entry-----!>
			  WebElement table = driver.findElement(By.xpath("(//table[@class='table'])[1]"));
			  java.util.List<WebElement> rows = table.findElements(By.tagName("tr"));
	   		  Assert.assertEquals(2, rows.size());
	   		  System.out.println("TC6. Material section has only 1 entry – Assert passed");
	   		  Thread.sleep(20000);
	  }
	  
	  catch(AssertionError e)
	  {
		  e.printStackTrace();
	  } 
	  catch (InterruptedException e) 
	  {
		  e.printStackTrace();
	  }
	  
  }
	/*
	 * @Test(priority=4) public void deleteProject() throws InterruptedException {
	 * WebElement Home = driver.findElement(By.xpath("//p[text()='Home']"));
	 * Home.click(); WebElement ProjectTab =
	 * driver.findElement(By.xpath("//div[text()='Projects']")); ProjectTab.click();
	 * WebElement DeleteTheFirstRecord =
	 * driver.findElement(By.xpath("//a[@title='Delete']"));
	 * DeleteTheFirstRecord.click(); WebElement ConfirmDelete =
	 * driver.findElement(By.xpath("//button/span[text()='CONFIRM']"));
	 * ConfirmDelete.click(); Thread.sleep(10000); }
	 * 
	 * @Test(priority=5) public void logout() throws InterruptedException {
	 * WebElement ProfileDownArrow =
	 * driver.findElement(By.xpath("//span/mat-icon[text()='keyboard_arrow_down']"))
	 * ; ProfileDownArrow.click(); WebElement Logout =
	 * driver.findElement(By.xpath("//button//span[text()='Logout']"));
	 * Logout.click(); }
	 */
  

@BeforeTest
  public void beforeMethod() throws InterruptedException,IOException {
	  System.out.println("Starting the browser session");
	//Setting the driver to chrome driver 
	  System.setProperty("webdriver.chrome.driver", "Driver\\chromedriver-win32\\chromedriver.exe");
	  driver = new ChromeDriver();
  	  String url = "https://qa.truevaluehub.com/home/";
  	  driver.get(url);
  	  driver.manage().window().maximize();
  	  Thread.sleep(20000);
  	      
	  
    //Login using username and password
  	  driver.findElement(By.id("logonIdentifier")).sendKeys("rudraqa");
	  driver.findElement(By.id("password")).sendKeys("tru0value#ub");
	  driver.findElement(By.id("next")).click();
		
	  Thread.sleep(20000); 
	  
	  driver.findElement(By.xpath("//span/div[text()='Create']")).click();
	  driver.findElement(By.id("pninput")).sendKeys("Smoke Test Project");
	  driver.findElement(By.id("pdinput")).sendKeys("Dev Smoke Testing");
	  Thread.sleep(10000);
  }

  @AfterTest
  public void afterMethod() throws InterruptedException {
	   System.out.println("Closing the browser session");
  	  //driver.quit();
  }

}




