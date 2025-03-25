package MyRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
@RunWith(Cucumber.class)

@CucumberOptions(
    features = "src\\test\\java\\features\\Logout.feature",
    glue = {"StepDef"},
    plugin = {"junit:target/JunitReport/report.xml"}
)
public class LogoutRunnertest{
}
