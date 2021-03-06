import org.junit.Before;

import java.io.File;

import static org.junit.Assert.*;

public class MyBugFinderTest {
    private static MyBugFinder myBugFinder;

    @Before
    public void setUp() {
        myBugFinder = new MyBugFinder();
    }

    @org.junit.Test
    public void detectBadStringComparisonTest() throws Exception {
        // test "=="
        String testPatternPath_1_1 = new File("").getAbsolutePath().concat("/src/test/java/testpatterns/testpattern1-1.java");
        myBugFinder.setSourceCode(testPatternPath_1_1, true);
        assertFalse(myBugFinder.detectBadStringComparison());

        // test "!="
        String testPatternPath_1_2 = new File("").getAbsolutePath().concat("/src/test/java/testpatterns/testpattern1-2.java");
        myBugFinder.setSourceCode(testPatternPath_1_2, true);
        assertFalse(myBugFinder.detectBadStringComparison());

        // test ".equlas"
        String testPatternPath_1_3 = new File("").getAbsolutePath().concat("/src/test/java/testpatterns/testpattern1-3.java");
        myBugFinder.setSourceCode(testPatternPath_1_3, true);
        assertTrue(myBugFinder.detectBadStringComparison());
    }

    @org.junit.Test
    public void checkStringLiteralTest() throws Exception {
        // test "multiple global string variable with duplicated value"
        String testPatternPath_2_1 = new File("").getAbsolutePath().concat("/src/test/java/testpatterns/testpattern2-1.java");
        myBugFinder.setSourceCode(testPatternPath_2_1,true);
        assertFalse(myBugFinder.checkStringLiteral());

        // test "multiple global and local string variable with duplicated value"
        String testPatternPath_2_2 = new File("").getAbsolutePath().concat("/src/test/java/testpatterns/testpattern2-2.java");
        myBugFinder.setSourceCode(testPatternPath_2_2,true);
        assertFalse(myBugFinder.checkStringLiteral());

        // test "single string value"
        String testPatternPath_2_3 = new File("").getAbsolutePath().concat("/src/test/java/testpatterns/testpattern2-3.java");
        myBugFinder.setSourceCode(testPatternPath_2_3,true);
        assertTrue(myBugFinder.checkStringLiteral());
    }

    @org.junit.Test
    public void implementsCloneableWhenDefinesCloneTest() throws Exception {
        // test "defines clone() without any interface implemented"
        String testPatternPath_EC_1 = new File("").getAbsolutePath().concat("/src/test/java/testpatterns/testpatternEC-1.java");
        myBugFinder.setSourceCode(testPatternPath_EC_1, true);
        assertFalse(myBugFinder.implementsCloneableWhenDefinesClone());

        // test "defines clone() but not implements Cloneable"
        String testPatternPath_EC_2 = new File("").getAbsolutePath().concat("/src/test/java/testpatterns/testpatternEC-2.java");
        myBugFinder.setSourceCode(testPatternPath_EC_2, true);
        assertFalse(myBugFinder.implementsCloneableWhenDefinesClone());

        // test "defines clone() and implements Cloneable"
        String testPatternPath_EC_3 = new File("").getAbsolutePath().concat("/src/test/java/testpatterns/testpatternEC-3.java");
        myBugFinder.setSourceCode(testPatternPath_EC_3, true);
        assertTrue(myBugFinder.implementsCloneableWhenDefinesClone());
    }
}
