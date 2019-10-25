import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MyBugFinderForJoda is for finding target bugs in different versions of Joda-time
 * @author Jiayang Liu, Kechen Liu
 * @code Assignment 4, CS6501 Analysis of Software Artifacts @ UVA
 */
public class MyBugFinderForJoda {
    private static List<String> javaFilesPathList;

    public static void main(String[] args) {
        try {
            findBugsInJoda();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * find target bugs within all .java files inside Joda-time
     * @throws Exception
     */
    private static void findBugsInJoda() throws Exception {
        MyBugFinder myBugFinder = new MyBugFinder();
        javaFilesPathList = new ArrayList<>();
        getJavaFilesList(new File("").getAbsolutePath().concat("/src/main/resourse/JodaFiles/JodaTime-pre-abstract"));

        // analyze each java file one by one
        for (String javaFilesPath : javaFilesPathList) {
            System.out.println("Analyzing: " + javaFilesPath);
            myBugFinder.setSourceCode(javaFilesPath, false);
            myBugFinder.detectBadStringComparison();
            myBugFinder.implementsCloneableWhenDefinesClone();
            myBugFinder.checkStringLiteral();
        }
    }

    /**
     * helper function to find all .java files inside Joda-time
     * @param directoryPath root directory path to begin searching
     * @return a list of files as the iteration result
     */
    private static List<File> getJavaFilesList(String directoryPath) {
        File directory = new File(directoryPath);
        // get all the files from a directory
        File[] fList = directory.listFiles();
        assert fList != null;
        List<File> resultList = new ArrayList<>(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                if (file.getName().endsWith(".java")) {
                    javaFilesPathList.add(file.getAbsolutePath());
                }
            } else if (file.isDirectory()) {
                resultList.addAll(getJavaFilesList(file.getAbsolutePath()));
            }
        }
        return resultList;
    }
}
