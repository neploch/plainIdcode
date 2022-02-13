package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
    ExtentSparkReporter spark;
    public String reportDate;
    public String year;
    public String month;
    public String day;
    public String hour;
    public String filePath;
    public String projectPath;
    public String reportTime;

    public void init() {
//        reportDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        reportTime = new SimpleDateFormat("mm_ss").format(new Date());
        year = new SimpleDateFormat("yyyy").format(new Date());
        month = new SimpleDateFormat("MM").format(new Date());
        day = new SimpleDateFormat("dd").format(new Date());
        hour = new SimpleDateFormat("HH").format(new Date());
        filePath = "./reports/" + year + "/" + month + "/" + day + "/" + hour + "/" +  reportTime + "_report";
        new File(filePath).mkdirs();
        projectPath = System.getProperty("user.dir");


        spark = new ExtentSparkReporter(filePath + "/report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        spark.config().setDocumentTitle("Automation test report");
        spark.config().setReportName("PlainId Test");
        spark.config().setEncoding("windows-1255");
    }

    public void create_test(String testName) {
        test = extent.createTest(testName);
    }

    public String CaptureScreen(WebDriver driver) throws IOException {
        String picDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String imagePath = filePath + "/pic" + picDate + ".jpg";
        String absolutePath =  projectPath + imagePath;

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File(imagePath));

        return absolutePath;
    }
}
