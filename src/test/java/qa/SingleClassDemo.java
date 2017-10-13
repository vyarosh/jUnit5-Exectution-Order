package qa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.fail;


class SingleClassDemo extends TestFactory {

    @Test
    void test1() {
        logTestName();
    }
 
    @Test
    void test2() {
        logTestName();
    }

    @Test
    void test3() {
        fail(getFailMessage());
    }

    @Disabled("Ignored message for demo purposes")
    @Test
    void test4() {
        logSkipTest();
    }
}