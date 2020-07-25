import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;

public class ExcelTest {
	public static final String URL = "https://dev.ri-claims.bc.smartrecycling.ca/";
	
	public static void main(String[] args) throws IOException {
		WebDriver driver;
		XSSFWorkbook workbook;
		XSSFSheet sheet;
		XSSFCell cell;
		
		System.setProperty("webdriver.chrome.driver","D:\\Software\\Coding\\Selenium\\WebDriver\\chromedriver_win32(74)\\chromedriver.exe");
		
		driver = new ChromeDriver();
		
		//get URL
		driver.get(URL);
		
		//import excel sheet
		File src = new File("F:\\eclipse-workspace\\Dev-Test\\src\\testData\\login.xlsx");
		
		//load the file
		FileInputStream input = new FileInputStream(src);
		
		//load the workbook
		workbook = new XSSFWorkbook(input);
		
		//load the sheet in which data is stored
		sheet = workbook.getSheetAt(0);
		
		for(int i=1; i<=sheet.getLastRowNum(); i++) {
			//import data for username
			cell = sheet.getRow(i).getCell(1);
			driver.findElement(By.id("id_login")).sendKeys(cell.getStringCellValue());
			
			//import data for password
			cell = sheet.getRow(i).getCell(2);
			driver.findElement(By.id("id_password")).sendKeys(cell.getStringCellValue());
			
			//write data in excel file
			FileOutputStream output = new FileOutputStream(src);
			
			//message
			String message = "Data imported successfully.";
			
			//create cell where data needs to be written
			sheet.getRow(i).createCell(3).setCellValue(message);
			
			//specify the file in which data needs to be written
			FileOutputStream fileOutput = new FileOutputStream(src);
			
			//write content
			workbook.write(fileOutput);
			
			//close workbook
			workbook.close();
		}
		
		//Submit username and password
        driver.findElement(By.cssSelector("button[type=\"submit\"]")).submit();
        
        driver.close();
	}
}
