package utilities;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentListener extends ExtentManager implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {

        create_test(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getName() + " test passed successfully");
        test.log(Status.INFO, "The test name: "+result.getMethod().getDescription());
        test.log(Status.PASS, result.getMethod().getDescription()+"  succeed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestContext context = result.getTestContext();
        WebDriver driver = (WebDriver) context.getAttribute("WebDriver");
        System.out.println(result.getName() + " test failed");
        try {
//            System.out.println("pic path: "+  CaptureScreen(driver));
//            test.fail(result.getTestName(), MediaEntityBuilder.createScreenCaptureFromPath(CaptureScreen(driver)).build());
            String picPath = CaptureScreen(driver);
            System.out.println("pic path: " + picPath);
            String[] split = picPath.split("/");
            test.fail(result.getTestName(), MediaEntityBuilder.createScreenCaptureFromPath(split[split.length -1]).build());

        } catch (Exception e) {
            e.printStackTrace();
        }
        test.log(Status.INFO, "The test : "+result.getMethod().getDescription());
        test.fail(result.getThrowable());
        test.log(Status.FAIL, result.getMethod().getDescription()+" failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStart(ITestContext context) {
        init();

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

    }

}