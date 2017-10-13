package qa;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


/**
 * Base test behaviour @before, @after, @rules method tor Test cases and log test results. Web driver initialization.
 *
 * @author Speroteck QA Team (qa@speroteck.com)
 */
@ExtendWith(TestWatcher.class)
public abstract class TestFactory {
    TestInfo testInfo;

    @BeforeAll
    static void setUpAll() {
        staticLog("@BeforeAll");
    }

    @AfterAll
    static void tearDownAll() {
        staticLog("@AfterAll");
    }


    //TODO: Hooks!
    // @ClassRule goes here
//    @Rule
//    public TestWatcher watcher = new MyTestWatcher();
//
//    @Rule
//    public FailedTestHook onFailure = new FailedTestHook();


    @BeforeEach
    public void setUp(TestInfo testInf) {
        testInfo = testInf;
        log("@BeforeEach");
    }

    @AfterEach
    public void tearDown() {
        log("@AfterEach");
    }


    void log(String msg) {
//        String currentClass = Thread.currentThread().getStackTrace()[2].getClassName().replace("test.", "");
        String currentClass = this.toString().replace("test.", "");

        System.out.println(currentClass+ ":: \t\t" + msg);
    }

    static void staticLog(String msg) {
        String currentClass = Thread.currentThread().getStackTrace()[2].getClassName().replace("test.", "");
        System.out.println(msg + " \t\t<< " + currentClass);
    }

    void logTestName() {
        log("@Test " + testInfo.getTestMethod().get().getName() + "()");
    }

    void logSkipTest() {
        log("ERROR: If you read this: @Test " + testInfo.getTestMethod().get().getName() + "() was not skipped!");
    }

    String getFailMessage() {
        return "@Test " + testInfo.getTestMethod().get().getName() + "() - Fail";
    }

}
