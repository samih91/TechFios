package sesion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Longintest {

	WebDriver driver;

	By userNameField = By.id("user_name");
	By passwordField = By.id("password");
	By logInBottonField = By.id("login_submit");
	By dashboardText = By.xpath("/html/body/div[1]/section/div/div[2]/div/div/header/div/strong");
	By costumersMenuField = By.xpath("/html/body/div[1]/aside[1]/div/nav/ul[2]/li[2]/a/span");
	By addCostumerField = By.xpath("//*[@id=\"customers\"]/li[2]/a/span");
	By addFullNameField = By.xpath("//*[@id=\"general_compnay\"]/div[1]/div/input");
	By addCompanyField = By.xpath("//*[@id=\"general_compnay\"]/div[2]/div/select");
	By addEmailField = By.xpath("//*[@id=\"general_compnay\"]/div[3]/div/input");
	By addcostumerPageText = By.className("panel-title");
	By phoneNumber = By.id("phone");
	By selectGroup = By.id("customer_group");
	By saveCostumerBottun = By.id("save_btn");
	By selectFile = By.xpath("//*[@id=\"form\"]/div/div[1]/div/div/span[1]/input");

	String User_Name ;
	String User_Password ;
	String DashBoard_header = "Dashboard";
	String addcostumer_header = "New Customer";
	String Full_name = "selenium 123";
	String email = "selenium@techfios.com";
	String company_name = "Techfios";
	String phone_number = "4567";
	String groupe = "java";
	String file_path = "C:\\Users\\samih\\Downloads";
	String browser;
	String url;

	@BeforeTest
	public void readconfig() {

		// FileReader , BufferReader , InputStream , Scanner

		try {

			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
			User_Name = prop.getProperty("UserName");
			User_Password = prop.getProperty("UserPassword");

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "Driver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "Driver\\msedgedriver.exe");
			driver = new EdgeDriver();
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	//@Test
	public void login() {

		driver.findElement(userNameField).sendKeys(User_Name);
		driver.findElement(passwordField).sendKeys(User_Password);
		driver.findElement(logInBottonField).click();

		Assert.assertEquals(driver.findElement(dashboardText).getText(), DashBoard_header,
				"DashBoard Page is Not Found!!");

	}

//	@Test
	public void addCostumer() {

		login();

		driver.findElement(costumersMenuField).click();
		driver.findElement(addCostumerField).click();

		Assert.assertEquals(driver.findElement(addcostumerPageText).getText(), addcostumer_header,
				"add costumer Page is Not Found!!");

		driver.findElement(addFullNameField).sendKeys(Full_name + generatedRandomNumber(999));

		selectFromDropDown(driver.findElement(addCompanyField), company_name);

		driver.findElement(addEmailField).sendKeys(generatedRandomNumber(999) + email);
		driver.findElement(phoneNumber)
				.sendKeys(generatedRandomNumber(999) + phone_number + generatedRandomNumber(999));
		selectFromDropDown(driver.findElement(selectGroup), groupe);

		// driver.findElement(selectFile).sendKeys(file_path);
		// driver.findElement(saveCostumerBottun).click();

	}

	public void selectFromDropDown(WebElement element, String vesibleText) {

		Select sel1 = new Select(element);
		sel1.selectByVisibleText(vesibleText);
	}

	public int generatedRandomNumber(int bound) {
		Random rnd = new Random();
		int randomNumber = rnd.nextInt(bound) + 100;
		return randomNumber;
	}

	// @AfterMethod
	public void tearDown() {

		driver.close();
		driver.quit();
	}
}
