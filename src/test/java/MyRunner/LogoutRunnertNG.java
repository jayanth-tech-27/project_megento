package MyRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src\\test\\java\\features\\Logout.feature", 
glue= {"StepDef","Hooks"},  
plugin= {"pretty","html:target/com.Magento.CucumberReport/AddtoCart_report.html"}) 
public class LogoutRunnertNG extends AbstractTestNGCucumberTests{

}