package testing;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.Select;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;

public class RemoveClaims {
	public static final String URL = "https://dev.ri-claims.bc.smartrecycling.ca/";
	public static WebDriver driver;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFCell cell;
	public static int row = 1;
	
	public static void main(String[] args) throws IOException {
		System.setProperty("webdriver.chrome.driver","D:\\Software\\Coding\\Selenium\\WebDriver\\chromedriver_win32(74)\\chromedriver.exe");
//		System.setProperty("webdriver.chrome.driver", "/home/quanghuy25899/WebDriver/chromedriver_win32/chromedriver.exe");
		
		driver = new ChromeDriver();
		
		//get URL
		driver.get(URL);
		
		//import excel sheet
		File src = new File("F:\\eclipse-workspace\\WebTest\\src\\data\\testData.xlsx");
//		File src = new File("/media/quanghuy25899/Disk2/eclipse-workspace/WebTest/src/data/testData.xlsx");
		
		//load the file
		FileInputStream input = new FileInputStream(src);
		
		//load the workbook
		workbook = new XSSFWorkbook(input);
		
		login();
		RemoveClaim();
		
		workbook.close();
		driver.close();
	}
	
	public static void login() {
		//load the sheet in which data is stored
		sheet = workbook.getSheetAt(0);
		
		//	Login
		//import data for username
		cell = sheet.getRow(1).getCell(0);
		driver.findElement(By.id("id_login")).sendKeys(cell.getStringCellValue());
		
		//import data for password
		cell = sheet.getRow(1).getCell(1);
		driver.findElement(By.id("id_password")).sendKeys(cell.getStringCellValue());
		
		//Submit username and password
        driver.findElement(By.cssSelector("button[type=\"submit\"]")).submit();
	}
	
	public static void RemoveClaim() {
		//load the sheet in which data is stored
		sheet = workbook.getSheetAt(2);
		
		//Choose Claims
        driver.findElement(By.linkText("Claims")).click();
        
        //Search claim's invoice number
        driver.findElement(By.xpath("//*[@id=\"search-icon\"]")).click();        
        
        //Input invoice number
        cell = sheet.getRow(row).getCell(1);
        driver.findElement(By.id("invoice_number")).sendKeys(cell.getStringCellValue());
        
        //Search
        driver.findElement(By.xpath("//*[@id=\"search_row\"]/th[2]/i")).click();
        
        //check claim
        Boolean claim = driver.findElements(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table/tbody/tr[3]")).size() > 0;
        if(!claim) {
        	if(isBlank()) {
        		System.exit(0);
        	}
        	row++;
        	RemoveClaim();
        }
        
        //Check condition
        WebElement check = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table/tbody/tr[3]/td[8]/span"));

        if(check.getText().equals("Approved")) {
        	if(isBlank()) {
        		System.exit(0);
        	}
        	row++;
        	RemoveClaim();
        }
        
        //Enter Claim
        driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table/tbody/tr[3]/td[1]/a")).click();
        
        removeClaimDetail();
        
        //Remove claim
        driver.findElement(By.cssSelector("a[class=\"btn btn-sm btn-danger\"]")).click();
        
        //Confirm Remove
        driver.findElement(By.cssSelector("button[class=\"btn btn-danger\"]")).click();
        
        //Check next claim
        if(isBlank()) {
        	return;
        }
        row++;
        RemoveClaim();
	}
	
	public static void removeClaimDetail() {
		try {
			WebElement material = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table[1]/tbody/tr[7]/td/span"));
			driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table[2]/tbody/tr[2]/td[1]"));
	        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			
	        if(material.getText().equals("C - Containers")) {
	        	//find Modify and Remove option
	        	driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table[2]/tbody/tr[2]/td[10]/div/a")).sendKeys(Keys.RETURN);
	        	
	        	//select Remove
	        	driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table[2]/tbody/tr[2]/td[10]/div/ul/li[2]/a")).sendKeys(Keys.RETURN);
	        	
	        	//confirm Remove
	        	driver.findElement(By.cssSelector("button[class=\"btn btn-danger\"]")).click();
	        }
	        else {
				//find Modify and Remove option
				driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table[2]/tbody/tr[2]/td[9]/div/a")).sendKeys(Keys.RETURN);
				
				//select remove
		    	driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table[2]/tbody/tr[2]/td[9]/div/ul/li[2]/a")).sendKeys(Keys.RETURN);
		    	
		    	//Confirm Remove
		    	driver.findElement(By.cssSelector("button[class=\"btn btn-danger\"]")).click();
	        }
	        
	        //refresh browser
	    	driver.navigate().refresh(); 
	    	
	    	try {
		    	driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table[2]/tbody/tr[2]/td[1]")); //might throw NoSuchElementException
		    	removeClaimDetail();
	    	}
	    	catch(NoSuchElementException e) {
	    		return;
	    	}
		}
		catch(NoSuchElementException e) {
			return;
		}
	}
	
	public static boolean isBlank() {
		if(sheet.getRow(row+1) == null) {
			return true;
		}
		return false;
	}
}
