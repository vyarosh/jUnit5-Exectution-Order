package qa;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

public class AnotherClassDemo extends TestFactory {

 
    @Test
    public void anotherTest1() {
        logTestName();
    }
 
    @Test
    public void anotherTest2() {
        logTestName();
    }


    @Test
    public void anotherTest3() {
        fail(getFailMessage());
    }

    @Disabled("Ignored message for demo purposes")
    @Test
    public void anotherTest4() {
        logSkipTest();
    }

}
