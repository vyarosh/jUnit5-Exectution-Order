package qa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.fail;

class SubClassDemo extends SingleClassDemo {

 
    @Test
    void subTest1() {
        logTestName();
    }
 
    @Test
    void subTest2() {
        logTestName();
    }

    @Test
    void subTest3() {
        fail(getFailMessage());
    }

    @Disabled("Ignored message for demo purposes")
    @Test
    void subTest4() {
        logSkipTest();
    }
}
