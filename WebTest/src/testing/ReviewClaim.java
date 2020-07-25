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
import org.openqa.selenium.NoSuchElementException;


public class ReviewClaim {
	public static final String URL = "https://dev.ri-claims.bc.smartrecycling.ca/";
	public static WebDriver driver;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFCell cell;
	public static int row = 1;
	
	public static void main(String[] args) throws IOException {
		System.setProperty("webdriver.chrome.driver","D:\\Software\\Coding\\Selenium\\WebDriver\\chromedriver_win32(74)\\chromedriver.exe");
	//	System.setProperty("webdriver.chrome.driver", "/home/quanghuy25899/WebDriver/chromedriver_win32/chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		//get URL
		driver.get(URL);
		
		//import excel sheet
		File src = new File("F:\\eclipse-workspace\\WebTest\\src\\data\\testData.xlsx");
	//	File src = new File("/media/quanghuy25899/Disk2/eclipse-workspace/WebTest/src/data/testData.xlsx");
		
		//load the file
		FileInputStream input = new FileInputStream(src);
		
		//load the workbook
		workbook = new XSSFWorkbook(input);
		
		login();
		reviewClaim();
		
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
	
	public static void reviewClaim() {
		//load the sheet in which data is stored
		sheet = workbook.getSheetAt(3);
		
		//Choose Claims
        driver.findElement(By.linkText("Claims")).click();
        
        //Search claim's invoice number
        driver.findElement(By.xpath("//*[@id=\"search-icon\"]")).click();        
        
        //Input invoice number
        cell = sheet.getRow(row).getCell(1);
        driver.findElement(By.id("invoice_number")).sendKeys(cell.getStringCellValue());
        
        //Search
        driver.findElement(By.xpath("//*[@id=\"search_row\"]/th[2]/i")).click();
        
        //Check claim
    	WebElement claim = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table/tbody/tr[3]/td[1]/a"));
    	WebElement condition = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table/tbody/tr[3]/td[8]/span"));
    	
    	if(claim == null) {
    		if(isBlank()) {
    			System.exit(0);
    		}
    		row++;
    		reviewClaim();
    	}
    	else if(!condition.getText().equals("Received")) {
    		if(isBlank()) {
    			System.exit(0);
    		}
    		row++;
    		reviewClaim();
    	}
    	
    	if(sheet.getRow(row) != null) {
			//Go to claim
			driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/table/tbody/tr[3]/td[1]/a")).click();
			
			try {
				//Select review claim
				driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div/div[1]/a[3]")).click();
				
				//Confirm review
				driver.findElement(By.cssSelector("button[type=\"submit\"]")).submit();
			}
			catch(NoSuchElementException e) {
				if(isBlank()) {
		    		System.exit(0);
		    	}
		    	row++;
		    	reviewClaim();
			}
    	}
    	
    	//check next claim
    	if(isBlank()) {
    		System.exit(0);
    	}
    	row++;
    	reviewClaim();
	}
	
	public static boolean isBlank() {
		if(sheet.getRow(row+1) == null) {
			return true;
		}
		return false;
	}
}
