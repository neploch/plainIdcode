package tests;

import api.Apis;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlTest;
import ui.Reusable;
import ui.delegated_admin.DelegatedAdmin;
import ui.external.*;
import ui.gigya.*;
import ui.partnerManagment.*;
import utilities.ExtentListener;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

import static org.openqa.selenium.PageLoadStrategy.EAGER;
import static utilities.Common.setRepValues;


public class BaseTest implements ITestContext{

	public WebDriver driver;
	public PMLoginPage pmLoginPage;
	public Partners partners;
	public Environments env;
	public Authorization authorization;
	public PartnersWorkspace partnersWorkspace;
	public DelegatedAdmin da;
	public AmazonAWSPage amazonAWS;
	public GigyaLoginPage gigyaLogin;
	public GigyaPage gigya;
	public Reusable re;
	public ExtentListener listener;
	public Apis apis;
	public Oid oid;
	public Atko atko;
	public Tenant tenant;

	public WallaHomePage wallaHomePage;
	public WallaFinance wallaFinance;
	public Cocktailale cocktailale;
	public CocktailaleInner cocktailaleInner;

	public BiziHomePage biziHomePage;
	public BiziLoginPage biziLoginPage;

	public SorbetLoginPage sorbetLoginPage;

	private final String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads";

	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	public void setup(@Optional("Chrome")String browser, ITestContext con) {
		//Check if parameter passed from TestNG is 'firefox'
		if(browser.equalsIgnoreCase("FireFox")){
			//create firefox instance
			System.setProperty("webdriver.gecko.driver", "./src/main/resources/geckodriver.exe");
			FirefoxProfile profile = new FirefoxProfile();
			FirefoxOptions options = new FirefoxOptions();
			profile.setPreference("browser.download.dir", downloadsPath);
			profile.setPreference("browser.download.folderList",2);
			//neverAsk - saveToDisk - CSV
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/csv,application/excel,application/vnd.ms-excel,application/vnd.msexcel,text/anytext,text/comma-separated-values,text/csv,text/plain,text/x-csv,application/x-csv,text/x-comma-separated-values,text/tab-separated-values,data:text/csv");
//neverAsk - saveToDisk - ANY
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/xml,text/plain,text/xml,image/jpeg,application/octet-stream,data:text/csv");
			profile.setPreference("browser.download.manager.showWhenStarting",false);
//neverAsk - openFile - CSV
			profile.setPreference("browser.helperApps.neverAsk.openFile","application/csv,application/excel,application/vnd.ms-excel,application/vnd.msexcel,text/anytext,text/comma-separated-values,text/csv,text/plain,text/x-csv,application/x-csv,text/x-comma-separated-values,text/tab-separated-values,data:text/csv");
//neverAsk - openFile - ANY
			profile.setPreference("browser.helperApps.neverAsk.openFile","application/xml,text/plain,text/xml,image/jpeg,application/octet-stream,data:text/csv");
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("dom.file.createInChild", true);

			options.setProfile(profile);
			driver = new FirefoxDriver(options);
		}
		//Check if parameter passed as 'Edge'
		else if(browser.equalsIgnoreCase("Edge")){
			//set path to Edge.exe
			System.setProperty("webdriver.edge.driver", "./src/main/resources/msedgedriver.exe");
			//create Edge instance
			driver = new EdgeDriver();
		}
		else {
			//Define and initialize Chrome WebDriver as default case
			WebDriverManager.chromedriver().setup();
			// Get the OS running on name
			String OS = System.getProperty("os.name", "unknown").toLowerCase();
			if(OS.contains("wind")) {
//				driver = new ChromeDriver();
				LoggingPreferences logPrefs = new LoggingPreferences();
				logPrefs.enable(LogType.DRIVER, Level.ALL);
				DesiredCapabilities cap = DesiredCapabilities.chrome();
//				cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
				ChromeOptions opts = new ChromeOptions();
//				opts.setPageLoadStrategy(EAGER);
				opts.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
				driver = new ChromeDriver(opts);
			}else {
				Map<String,String> prefs = new HashMap<>();
				prefs.put("download.default_directory", downloadsPath); // Bypass default download directory in Chrome
				prefs.put("safebrowsing.enabled", "false"); // Bypass warning message, keep file anyway (for .exe, .jar, etc.)
				ChromeOptions opts = new ChromeOptions();
				opts.setExperimentalOption("prefs", prefs);
				opts.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080","--ignore-certificate-errors","--no-sandbox", "--disable-dev-shm-usage");
				opts.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

				driver = new ChromeDriver(opts);
			}
		}

		con.setAttribute("WebDriver", driver);

	//	setRepValues();

		// maximize the browser window
		driver.manage().window().maximize();
		//initialize page class objects
		pmLoginPage = new PMLoginPage(driver);
		partners = new Partners(driver);
		env = new Environments(driver);
		authorization = new Authorization(driver);
		partnersWorkspace = new PartnersWorkspace(driver);
		da = new DelegatedAdmin(driver);
		amazonAWS = new AmazonAWSPage(driver);
		gigyaLogin = new GigyaLoginPage(driver);
		gigya = new GigyaPage(driver);
		re = new Reusable(driver);
		apis = new Apis();
		oid = new Oid(driver);
		atko = new Atko(driver);
		tenant = new Tenant(driver);

		wallaHomePage = new WallaHomePage(driver);
		wallaFinance = new WallaFinance(driver);
		cocktailale = new Cocktailale(driver);
		cocktailaleInner = new CocktailaleInner(driver);

		biziHomePage = new BiziHomePage(driver);
		biziLoginPage = new BiziLoginPage(driver);

		sorbetLoginPage = new SorbetLoginPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void closeMethod(){
		driver.quit();
	}

	@Override
	public Object getAttribute(String s) {
		return null;
	}

	@Override
	public void setAttribute(String s, Object o) {

	}

	@Override
	public Set<String> getAttributeNames() {
		return null;
	}

	@Override
	public Object removeAttribute(String s) {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Date getStartDate() {
		return null;
	}

	@Override
	public Date getEndDate() {
		return null;
	}

	@Override
	public IResultMap getPassedTests() {
		return null;
	}

	@Override
	public IResultMap getSkippedTests() {
		return null;
	}

	@Override
	public IResultMap getFailedButWithinSuccessPercentageTests() {
		return null;
	}

	@Override
	public IResultMap getFailedTests() {
		return null;
	}

	@Override
	public String[] getIncludedGroups() {
		return new String[0];
	}

	@Override
	public String[] getExcludedGroups() {
		return new String[0];
	}

	@Override
	public String getOutputDirectory() {
		return null;
	}

	@Override
	public ISuite getSuite() {
		return null;
	}

	@Override
	public ITestNGMethod[] getAllTestMethods() {
		return new ITestNGMethod[0];
	}

	@Override
	public String getHost() {
		return null;
	}

	@Override
	public Collection<ITestNGMethod> getExcludedMethods() {
		return null;
	}

	@Override
	public IResultMap getPassedConfigurations() {
		return null;
	}

	@Override
	public IResultMap getSkippedConfigurations() {
		return null;
	}

	@Override
	public IResultMap getFailedConfigurations() {
		return null;
	}

	@Override
	public XmlTest getCurrentXmlTest() {
		return null;
	}

	@Override
	public List<Module> getGuiceModules(Class<? extends Module> aClass) {
		return null;
	}

	@Override
	public Injector getInjector(List<Module> list) {
		return null;
	}

	@Override
	public Injector getInjector(IClass iClass) {
		return null;
	}

	@Override
	public void addInjector(List<Module> list, Injector injector) {

	}
}