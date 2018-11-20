package ConfigDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {

    public static WebDriver driver = null;

    public static WebDriver Firefox(){
        WebDriverManager.firefoxdriver().setup();
        return driver;
    }

    public static WebDriver Chrome(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        return driver;
    }

    public static boolean Close(){
        try{
            driver.quit();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }



}
