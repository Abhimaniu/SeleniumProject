package Project.SeleniumAll;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Unit test for simple App.
 */
public class FKTest

{
	WebDriver driver;
	protected ExtentReports extent;
	protected ExtentTest test;
	//private static  ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new InheritableThreadLocal<WebDriver>();


	@BeforeMethod
	@Parameters("browser")
	public void setup(String browser) {
		try {
			File file = new File("E:\\chromedriver.exe");
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						file.getAbsolutePath());
				driver = new ChromeDriver();

			} else if (browser.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			}

			driver.get("http://www.flipkart.com/");
			driver.manage().window().maximize();
			extent = new ExtentReports("Reports\\Reports.html");
			
			
			//WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<WebDriver>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "item")
	public void firtsTest(String id, String item) {
		test = extent.startTest("searchtest");
		FKHomePage fk = new FKHomePage(driver);
		fk.enterSearchItem(item);
		fk.clickSearchButton();

		test.log(LogStatus.PASS, "Pass");

		AssertJUnit.assertEquals(test.getRunStatus(), LogStatus.PASS);
	}

	@Test
	public void secondTest() {
		try {
			test = extent.startTest("menSection");
			FKHomePage fk = new FKHomePage(driver);
			fk.clickMenSection();

			test.log(LogStatus.PASS, "Pass");

			AssertJUnit.assertEquals(test.getRunStatus(), LogStatus.PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DataProvider(name = "item")
	public Object[][] items() {
		Object[][] items = new Object[1][2];

		try {
			List<String> itemsList = new ArrayList<String>();
			FileInputStream file = new FileInputStream(new File(
					"E:\\Items.xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue() + "t");
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "t");
						itemsList.add(cell.getStringCellValue());
						break;
					}
				}

			}
			file.close();

			for (int i = 0; i < itemsList.size(); i++) {
				for (int j = 0; j < 2; j++) {
					if (j == 1) {
						items[i][j] = itemsList.get(i);
						System.out.println(itemsList.get(i));
					} else
						items[i][j] = "Hi";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;

	}
	
/*	public WebDriver getDriver() {
        return threadDriver.get();
    }*/

	@AfterMethod
	public void tearDown() {
		driver.quit();

		extent.endTest(test);
		extent.flush();
	}

}
