import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
//import ucll.project.domain.db.DatabaseTest;
import ucll.project.domain.db.StarDatabaseTest;
import ucll.project.domain.user.SampleUserTest;
import ucll.project.ui.HomePageTest;
//import ucll.project.ui.LogInTest;

public class AllTests {
    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));

        Result result = junit.run(
                HomePageTest.class,
                //LogInTest.class,
                SampleUserTest.class,
                //DatabaseTest.class,
                StarDatabaseTest.class

        );

        System.out.println("Tests finished, results: Failures " + result.getFailureCount() + " Ignored " + result.getIgnoreCount());
    }
}
