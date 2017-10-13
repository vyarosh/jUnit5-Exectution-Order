package qa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.fail;

class SubSubClassDemo extends SubClassDemo {


    @Test
    void subSubTest1() {
        logTestName();
    }

    @Test
    void subSubTest2() {
        logTestName();
    }

    @Test
    void subSubTest3() {
        fail(getFailMessage());
    }

    @Disabled("Ignored message for demo purposes")
    @Test
    void subSubTest4() {
        logSkipTest();
    }
}
