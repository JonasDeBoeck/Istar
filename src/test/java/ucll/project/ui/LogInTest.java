//package ucll.project.ui;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//
//import java.net.MalformedURLException;
//
//import static org.junit.Assert.assertEquals;
//
//public class LogInTest {
//
//    private static WebDriver driver;
//
//    @BeforeClass
//    public static void SetupDriver() throws MalformedURLException {
//        driver = DriverHelper.getDriver();
//    }
//
//    @AfterClass
//    public static void CloseBrowser() {
//        // close it in the end, comment this away to keep chrome open
//       driver.close();
//    }

    /**
     * This is a sample test, remove this test and write your own!
     */
/*    @Test
    public void LogInTest() {
        driver.get(Config.BASE_URL);
        WebElement username = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        username.sendKeys("test@test.be");
        WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        password.sendKeys("derdejaarsstopmetxss");
        WebElement login = driver.findElement(By.xpath("//*[@id=\"logIn\"]/div[3]/button"));
        login.click();
        assertEquals("Main page", driver.getTitle());
    }*/
//}
