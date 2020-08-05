Junit 5 test execution order Demo
====================================================

Install
-------

1. Install Java SDK
2. Maven from https://maven.apache.org/install.html
3. Execute command from Project folder:  "mvn clean install" 


Run tests
---------
Execute command from Project folder   
**Run all tests:** "mvn clean test"   
**Run desired classes:** mvn clean test -Dtest=Class1,Class2   
**Run desired groups:** mvn clean test -Dgroup=groupName   
**Run desired tags or suites:** mvn clean test -Dsuites=tag (tag name or expression see: [JUnit 5 guide](https://junit.org/junit5/docs/snapshot/user-guide/#running-tests-tag-expressions))
 