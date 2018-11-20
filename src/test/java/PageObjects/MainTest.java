package PageObjects;

import ConfigDriver.Driver;
import Utils.CaptureScreenShot;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.*;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.testng.Assert.*;

public class MainTest {

    public static WebDriver driver = null;

    public void setUp1(){
        driver = Driver.Chrome();
    }

    ExtentReports reports;
    ExtentTest testInfo;
    ExtentHtmlReporter htmlReporter;

    @BeforeTest
    public void setUp(){
        htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"/AutomationReports.html" ));
        htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
        reports=new ExtentReports();
        reports.setSystemInfo("Enviroment","QA");
        reports.attachReporter(htmlReporter);
    }


    public void tearDown(){
        Boolean result = Driver.Close();
        System.out.println(result);
    }

    @Test
    public void main() {
        setUp1();
        driver.get("https://www.google.com/");
        System.out.println("Completed");
        tearDown();

    }

    @Test(priority = 0)
    public void dataExcelPull() throws IOException {
        File file = new File("/Users/brayanposada/IdeaProjects/TestingActions/src/Docs/UsersData.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        String dato1 = sheet.getRow(1).getCell(2).getStringCellValue();
        System.out.println(dato1);
    }

    @Test(priority = 1)
    public void dataExcelPush() throws IOException {
        File file = new File("/Users/brayanposada/IdeaProjects/TestingActions/src/Docs/UsersData.xlsx");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(1);
        if(cell == null)
            cell = row.createCell(1);

        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("Claudia");
        FileOutputStream fos = new FileOutputStream(file);
        wb.write(fos);
        fos.close();

    }

    @Test(priority=2)
    public void VerifyFacebookTitle(){
        File path = new File("/Users/brayanposada/Documents/phantomjs-2.1.1-macosx/bin/phantomjs");
        System.setProperty("phantomjs.binary.path",path.getAbsolutePath());
        WebDriver driver = new PhantomJSDriver();
        driver.get("https://www.youtube.com");
        driver.manage().window().maximize();
        System.out.println(driver.getTitle());
        CaptureScreenShot cap = new CaptureScreenShot();
        cap.captureScreenShot(driver, "PhantomJsDriverExample2");
    }

    @BeforeMethod
    public void register(Method method){
        String testName = method.getName();
        testInfo=reports.createTest(testName);
    }

    @AfterMethod
    public void captureStatus(ITestResult result){
        if(result.getStatus()==ITestResult.SUCCESS){
            testInfo.log(Status.PASS, "The test Method named as: "+result.getName()+" is passed");

        }
        else if(result.getStatus()==ITestResult.FAILURE){
            testInfo.log(Status.PASS,"The Test Method named as :"+result.getName()+" is failed");
            testInfo.log(Status.FAIL,"Test failure : "+result.getThrowable());
        }
        else if(result.getStatus()==ITestResult.SKIP){
            testInfo.log(Status.PASS,"The Test Method named as : "+result.getName()+" is passed");

        }
    }

    @AfterTest
    public void cleanUp(){
        reports.flush();
    }
}
