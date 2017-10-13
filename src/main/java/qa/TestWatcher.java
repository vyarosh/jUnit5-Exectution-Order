package qa;

import org.junit.jupiter.api.extension.*;

import static qa.TestFactory.staticLog;

public class TestWatcher implements
        BeforeEachCallback,
        AfterEachCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        BeforeAllCallback,
        AfterAllCallback,
        TestInstancePostProcessor,
        TestExecutionExceptionHandler {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        staticLog("BeforeAll-Callback Hook");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        staticLog("BeforeAll-Callback Hook");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        staticLog("BeforeTestExecution-Callback Hook");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        staticLog("AfterEach-Callback Hook");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        staticLog("AfterTestExecution-Callback Hook");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        staticLog("AfterAll-Callback Hook");
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        staticLog("TestInstancePostProcessor Hook");
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        staticLog("Exception appeared Hook");
        throw throwable;
    }
}
