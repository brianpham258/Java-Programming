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
import org.openqa.selenium.support.ui.Select;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class AddClaims {
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
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
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
		createClaim();
		
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
	
	//create new claim
	public static void createClaim() {
		//Create Used Oil Claim
		sheet = workbook.getSheetAt(1);
		System.out.println(sheet.getLastRowNum());
		
  		//Choose Claims
        driver.findElement(By.linkText("Claims")).click();
        
        //Create Claim
        driver.findElement(By.linkText("Create Claim")).click();
        
        //Processor
        cell = sheet.getRow(row).getCell(1);
        String processor_input = cell.getStringCellValue();
        
        Select processor = new Select(driver.findElement(By.name("registrant")));
        processor.selectByVisibleText(processor_input);
        
        //Received date
        cell = sheet.getRow(row).getCell(2);
        String claim_date_input = cell.getRawValue();
        
        WebElement claim_date = driver.findElement(By.name("received_date"));
        claim_date.clear();
        claim_date.sendKeys(claim_date_input);
        
        //Invoice number
        cell = sheet.getRow(row).getCell(3);
        String invoice_input = cell.getStringCellValue();
        
        driver.findElement(By.name("invoice_number")).sendKeys(invoice_input);
        
        //Submitted subtotal
        cell = sheet.getRow(row).getCell(4);
        String subtotal_input = cell.getRawValue();
        
        driver.findElement(By.name("submitted_subtotal")).sendKeys(subtotal_input);
        
        //Material type
        cell = sheet.getRow(row).getCell(5);
        String material_input = cell.getStringCellValue();
        
        Select material = new Select(driver.findElement(By.name("material_type")));
        material.selectByVisibleText(material_input);
        
        //Claim comments
        try {
	        cell = sheet.getRow(row).getCell(6);
	        String comment_input = cell.getStringCellValue();
	        
	        driver.findElement(By.name("comments")).sendKeys(comment_input); //might throw NullPointerException
        }
        catch(NullPointerException e) {
        	System.out.println(e);
        }
        
        //UOMA memo
        try {
	        cell = sheet.getRow(row).getCell(7);
	        String memo_input = cell.getStringCellValue();
	        
	        driver.findElement(By.name("memo")).sendKeys(memo_input); //might throw NullPointerException
        }
        catch(NullPointerException e) {
        	System.out.println(e);
        }
        
        //Save
        driver.findElement(By.name("save")).submit();
      	
        //Decide whether to create new claim or claim detail
        if(sheet.getRow(row).getCell(8) != null) { //Check claim detail
        	createClaimDetail();
        }

	    if(!isEqual()) { //Check new claim
	        createClaim();
	    }
	    else {
	    	return;
	    }
	}
	
	//create claim detail
	public static void createClaimDetail() {
		//Note: if click() and submit() not working, simulate a click buy using sendKeys(Keys.RETURN) or sendKeys(Keys.ENTER)
        driver.findElement(By.linkText("Create Detail")).sendKeys(Keys.RETURN);
        
        //Collector
        cell = sheet.getRow(row).getCell(8);
        String collector_input = cell.getStringCellValue();
        
        Select collector = new Select(driver.findElement(By.name("registrant")));
        collector.selectByVisibleText(collector_input);
        
        //Date Received
        cell = sheet.getRow(row).getCell(9);
        String detail_date_input = cell.getRawValue();
        
        WebElement detail_date = driver.findElement(By.name("date"));
        detail_date.clear();
        detail_date.sendKeys(detail_date_input);
        
        //Zone
        cell = sheet.getRow(row).getCell(10);
        String zone_input = cell.getStringCellValue();
        
        Select zone = new Select(driver.findElement(By.name("zone")));
        zone.selectByVisibleText(zone_input);
        
        //Find material type
        cell = sheet.getRow(row).getCell(5);
        String material = cell.getStringCellValue();
        
        //Add Detail
        switch(material) {
        case "O - Used Oil":
        	usedOil();
        	break;
        
        case "A - Used Antifreeze":
        	usedAntifreeze();
        	break;
        
        case "F - Used Filters":
        	usedFilter();
        	break;
        
        case "C - Containers":
        	usedContainer();
        	break;
        }
        
        //Save
        driver.findElement(By.name("save")).submit();
        
        //check next invoice number to decide to stop or continue running the program
	    try {
        	if(isBlank()) {
	        	System.exit(0);
	        }
	    }
	    catch(NullPointerException e) {
	    	System.exit(0);
	    }
        
        //compare invoice number to decide to create a new claim or a new claim detail
        try {
		    if(isEqual()) {
	        	createClaimDetail();
	        } else {
	        	createClaim();
	        }
        }
        catch(NullPointerException e) {
        	System.exit(0);
        }
	}
	
	//Claim Detail for O - Used Oil
	public static void usedOil() {
		//Gross Volume
		cell = sheet.getRow(row).getCell(12);
        String volume_input = cell.getRawValue();
        
        driver.findElement(By.id("id_initial_measure")).sendKeys(volume_input);
		
		//Water Test
        cell = sheet.getRow(row).getCell(13);
        String water_input = cell.getRawValue();
        
        driver.findElement(By.id("id_test_percentage")).sendKeys(water_input);
		
		//Adjusted Volume
        cell = sheet.getRow(row).getCell(14);
        String adjusted_input = cell.getRawValue();
        
        driver.findElement(By.id("id_final_measure")).sendKeys(adjusted_input);
	}
	
	//Claim Detail for F - Used Filters
	public static void usedFilter() {
		//No of Drums
		cell = sheet.getRow(row).getCell(12);
		String no_of_drum = cell.getRawValue();
		
		driver.findElement(By.id("id_quantity")).sendKeys(no_of_drum);
		
		//Net Weight
		cell = sheet.getRow(row).getCell(13);
		String net_weight = cell.getRawValue();
		
		driver.findElement(By.id("id_initial_measure")).sendKeys(net_weight);
		
		//Crushed Weight
		cell = sheet.getRow(row).getCell(14);
		String crushed_weight = cell.getRawValue();
		
		driver.findElement(By.id("id_final_measure")).sendKeys(crushed_weight);
	}
	
	//Claim Detail for A - Used Antifreeze
	public static void usedAntifreeze() {
		//Gross Volume
		cell = sheet.getRow(row).getCell(12);
        String gross_volume = cell.getRawValue();
        
        driver.findElement(By.id("id_initial_measure")).sendKeys(gross_volume);
        
        //Glycol Test
        cell = sheet.getRow(row).getCell(13);
        String glycol_test = cell.getRawValue();
        
        driver.findElement(By.id("id_test_percentage")).sendKeys(glycol_test);
        
        //Adjusted Volume
        cell = sheet.getRow(row).getCell(14);
        String adjusted_volume = cell.getRawValue();
        
        driver.findElement(By.id("id_final_measure")).sendKeys(adjusted_volume);
	}
	
	//Claim Detail for C- Container
	public static void usedContainer() {
		//Container Type
		cell = sheet.getRow(row).getCell(11);
		String container = cell.getStringCellValue();
		
		Select container_type = new Select(driver.findElement(By.xpath("//*[@id=\"id_container_type\"]")));
		container_type.selectByVisibleText(container);

		//No of Container
		cell = sheet.getRow(row).getCell(12);
		String total_container = cell.getRawValue();
		
		driver.findElement(By.id("id_quantity")).sendKeys(total_container);
		
		//Gross Weight
		cell = sheet.getRow(row).getCell(13);
		String gross_weight = cell.getRawValue();
		
		driver.findElement(By.id("id_initial_measure")).sendKeys(gross_weight);
		
		//Shredded Weight
		cell = sheet.getRow(row).getCell(14);
		String shredded_weight = cell.getRawValue();
		
		driver.findElement(By.id("id_final_measure")).sendKeys(shredded_weight);
	}
	
	//compare invoice number of the current claim with the next claim
	public static boolean isEqual() {
		cell = sheet.getRow(row).getCell(3);
	    String curr_invoice = cell.getStringCellValue();
	    cell = sheet.getRow(row+1).getCell(3);
	    String next_invoice = cell.getStringCellValue();
	    
	    if(curr_invoice.equals(next_invoice)) {
	    	row++;
	    	return true;
	    }
	    row++;
	    return false;
	}
	
	//check if the next invoice number available or not
	public static boolean isBlank() {
		if(sheet.getRow(row+1).getCell(3) == null) {
			return true;
		}
		return true;
	}
}
