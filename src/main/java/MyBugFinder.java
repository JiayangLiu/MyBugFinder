import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.printer.YamlPrinter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * MyBugFinder class that supports BadStringComparison and xxxx bugs inspection in the Java code
 * @author Jiayang Liu, Kechen Liu
 * @code Assignment 4, CS6501 Analysis of Software Artifacts @ UVA
 */
class MyBugFinder {
    private static Set<String> stringVariableSet;
    private static CompilationUnit compilationUnit;

    /**
     * Set the source code path and JavaParser compilationUnit for later inspection
     * @param sourceCodePath path to the source code to be inspected by the MyBugFinder
     * @throws Exception capture FileNoFound error
     */
    void setSourceCode(final String sourceCodePath) throws Exception {
        compilationUnit = StaticJavaParser.parse(new File(sourceCodePath));

        // print out the whole AST in YAML format
        YamlPrinter printer = new YamlPrinter(true);
        System.out.println(printer.output(compilationUnit));

        loadStringVariableSet();
    }

    /**
     * Initialization function for global variables, whose content also get loaded
     */
    private void loadStringVariableSet() {
        stringVariableSet = new HashSet<>();
        compilationUnit.findAll(FieldDeclaration.class).forEach(field -> field.getVariables().forEach(variable -> {
            // search all CLASS-LEVEL variables with "String" type
            if (variable.getType().toString().equals("String")) {
                stringVariableSet.add(variable.getNameAsString());
                System.out.println("New String variable loaded: " + variable.getNameAsString());
            }
        }));
    }

    /**
     * Functionality 1: find BadStringComparison bugs
     * @return BadStringComparison inspection result
     */
    boolean detectBadStringComparison() {
        AtomicBoolean goodStringComparison = new AtomicBoolean();    // to be updated in lambda
        goodStringComparison.setOpaque(true);
        compilationUnit.findAll(BodyDeclaration.class).forEach(method -> method.walk(node -> {
            if (node instanceof BinaryExpr) {
                BinaryExpr be = (BinaryExpr) node;
                // check if operator is "==" or "!="
                if (be.getOperator().asString().equals("==") || be.getOperator().asString().equals("!=")) {
                    // check if left and right operands are String type
                    if (stringVariableSet.contains(be.getLeft().toString()) && stringVariableSet.contains(be.getRight().toString())) {
                        goodStringComparison.setOpaque(false);
                        System.out.println("Bad String comparison captured: [" + be.toString() + "]");
                    }
                }
            }
        }));
        return goodStringComparison.getOpaque();
    }
}
