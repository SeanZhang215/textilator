package edu.gatech.seclass.textilator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Timeout(value = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class MyMainTest {
    // Place all  of your tests in this class, optionally using MainTest.java as an example
    private final String usageStr =
            "Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE"
                    + System.lineSeparator();

    @TempDir
    Path tempDirectory;

    @RegisterExtension
    OutputCapture capture = new OutputCapture();

    /*
     * Test Utilities
     */

    private Path createFile(String contents) {
        return createFile(contents, "sample.txt");
    }

    private Path createFile(String contents, String fileName) {
        Path file = tempDirectory.resolve(fileName);

        try {
            Files.write(file, contents.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }

    private String getFileContent(Path file) {
        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Test Cases
     */

    @Test
    // Test Case 1
    public void textilatorTest1() {

        String[] args = {"/remote/dir/server/"};
        Main.main(args);
        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
    }


    @Test
    // Test Case 2
    public void textilatorTest2() {
        String input = "The Krabby" ;
        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));

    }



    @Test
    // Test Case 3
    public void textilatorTest3() {
        String input = "";
        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));

    }

    @Test
    // Test Case 4
    public void textilatorTest4() {
        String input = "The Krabby" + System.lineSeparator();
        String expected = "the krabby" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c", "lower", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 5
    public void textilatorTest5() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", "24", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 6
    public void textilatorTest6() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "52", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 7
    public void textilatorTest7() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }



    @Test
    // Test Case 8
    public void textilatorTest8() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "-1", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 9
    public void textilatorTest9() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", "0","x","pre", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }






    @Test
    // Test Case 10
    public void textilatorTest10() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a", "0",inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 11
    public void textilatorTest11() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a", "e","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }



    @Test
    // Test Case 12
    public void textilatorTest12() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-p",inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 13
    public void textilatorTest13() {
        String input = "test" + System.lineSeparator();
        String expected = "test" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }



    @Test
    // Test Case 14
    public void textilatorTest14() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();
        String expected = "pre The Krabby Patty Secret Formula is..." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-p", "pre ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 15
    public void textilatorTest15() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "84 104 105 115 32 105 115 32 102 105 110 101 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 16
    public void textilatorTest16() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "test 84 104 105 115 32 105 115 32 102 105 110 101 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-a","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 17
    public void textilatorTest17() {
        String input = "This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "Krabby patty" + System.lineSeparator()
                + "Chocolate chip cookie" + System.lineSeparator()
                + "Pineapple" + System.lineSeparator()
                + "Computer" + System.lineSeparator();
        String expected = "This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "Chocolate chip cookie" + System.lineSeparator()
                + "Computer" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-s","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 18
    public void textilatorTest18() {
        String input = "This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "Krabby patty" + System.lineSeparator()
                + "Chocolate chip cookie" + System.lineSeparator()
                + "Pineapple" + System.lineSeparator()
                + "Computer" + System.lineSeparator();
        String expected = "test This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "test Chocolate chip cookie" + System.lineSeparator()
                + "test Computer" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-s","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 19
    public void textilatorTest19() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "84 104 105 115 32 105 115 32 102 105 110 101 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-a","-s","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 20
    public void textilatorTest20() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test 84 104 105 115 32 105 115 32 102 105 110 101 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-a","-s","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 21
    public void textilatorTest21() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "This is fine." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-s","1",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 22
    public void textilatorTest22() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test This is fine." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-s","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 23
    public void textilatorTest23() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "84 104 105 115 32 105 115 32 102 105 110 101 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-a","-s","1",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 24
    public void textilatorTest24() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test 84 104 105 115 32 105 115 32 102 105 110 101 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-a","-s","1","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }



    @Test
    // Test Case 25
    public void textilatorTest25() {
        String input = "This list is really important and should not get deleted." + System.lineSeparator()
                + "* watermelon" + System.lineSeparator()
                + "* sunflower" + System.lineSeparator()
                + "* community center" + System.lineSeparator()
                + "* pelican town" + System.lineSeparator();
        String expected = "This list is really important and should not get deleted." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-x","*",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 26
    public void textilatorTest26() {
        String input = "This list is really important and should not get deleted." + System.lineSeparator()
                + "* watermelon" + System.lineSeparator()
                + "* sunflower" + System.lineSeparator()
                + "* community center" + System.lineSeparator()
                + "* pelican town" + System.lineSeparator();
        String expected = "test This list is really important and should not get deleted." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-x","*","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 27
    public void textilatorTest27() {
        String input = "This is fine." + System.lineSeparator()
                + "& watermelon" + System.lineSeparator()
                + "& sunflower" + System.lineSeparator()
                + "& community center" + System.lineSeparator()
                + "& pelican town" + System.lineSeparator();
        String expected = "84 104 105 115 32 105 115 32 102 105 110 101 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-x","&","-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 28
    public void textilatorTest28() {
        String input = "This is fine." + System.lineSeparator()
                + "* watermelon" + System.lineSeparator()
                + "* sunflower" + System.lineSeparator()
                + "* community center" + System.lineSeparator()
                + "* pelican town" + System.lineSeparator();
        String expected = "test 84 104 105 115 32 105 115 32 102 105 110 101 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-x","*","-p","test ","-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 29
    public void textilatorTest29() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();
        String expected = "The Krabby Patty Secret Formula is..." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-e", "24","-e","0", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 30
    public void textilatorTest30() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();
        String expected = "test The Krabby Patty Secret Formula is..." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-e","0","-p","test ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 31
    public void textilatorTest31() {
        String input = "This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "Krabby patty" + System.lineSeparator()
                + "Chocolate chip cookie" + System.lineSeparator()
                + "Pineapple" + System.lineSeparator()
                + "Computer" + System.lineSeparator();
        String expected = "This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "Chocolate chip cookie" + System.lineSeparator()
                + "Computer" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-s","0","-e","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 32
    public void textilatorTest32() {
        String input = "This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "Krabby patty" + System.lineSeparator()
                + "Chocolate chip cookie" + System.lineSeparator()
                + "Pineapple" + System.lineSeparator()
                + "Computer" + System.lineSeparator();
        String expected = "test This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "test Chocolate chip cookie" + System.lineSeparator()
                + "test Computer" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-s","0","-e","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 33
    public void textilatorTest33() {
        String input = "This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "Krabby patty" + System.lineSeparator()
                + "Chocolate chip cookie" + System.lineSeparator()
                + "Pineapple" + System.lineSeparator()
                + "Computer" + System.lineSeparator();
        String expected = "Krabby patty" + System.lineSeparator()
                + "Pineapple" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1","-e","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 34
    public void textilatorTest34() {
        String input = "This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "Krabby patty" + System.lineSeparator()
                + "Chocolate chip cookie" + System.lineSeparator()
                + "Pineapple" + System.lineSeparator()
                + "Computer" + System.lineSeparator();
        String expected = "test Krabby patty" + System.lineSeparator()
                + "test Pineapple" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-s","1","-e","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }



    @Test
    // Test Case 35
    public void textilatorTest35() {
        String input = "This list is really important and should NOT get deleted." + System.lineSeparator()
                + "* watermelon" + System.lineSeparator()
                + "* sunflower" + System.lineSeparator()
                + "* community center" + System.lineSeparator()
                + "* pelican town" + System.lineSeparator();
        String expected = "This list is really important and should NOT get deleted." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-e","0","-x","*",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 36
    public void textilatorTest36() {
        String input = "This list is really important and should not get deleted." + System.lineSeparator()
                + "* watermelon" + System.lineSeparator()
                + "* sunflower" + System.lineSeparator()
                + "* community center" + System.lineSeparator()
                + "* pelican town" + System.lineSeparator();
        String expected = "test This list is really important and should not get deleted." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-e","0","-x","*","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 37
    public void textilatorTest37() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 38
    public void textilatorTest38() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "test THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 39
    public void textilatorTest39() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "84 72 73 83 32 73 83 32 70 73 78 69 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 40
    public void textilatorTest40() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "test 84 72 73 83 32 73 83 32 70 73 78 69 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-a","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 41
    public void textilatorTest41() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-s","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 42
    public void textilatorTest42() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-s","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 43
    public void textilatorTest43() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "84 72 73 83 32 73 83 32 70 73 78 69 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-s","0","-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 44
    public void textilatorTest44() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test 84 72 73 83 32 73 83 32 70 73 78 69 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-s","0","-a","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 45
    public void textilatorTest45() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-s","1",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 46
    public void textilatorTest46() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-s","1","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 47
    public void textilatorTest47() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "84 72 73 83 32 73 83 32 70 73 78 69 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-s","1","-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 48
    public void textilatorTest48() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test 84 72 73 83 32 73 83 32 70 73 78 69 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-s","1","-a","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 49
    public void textilatorTest49() {
        String input = "*This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-x","*",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 50
    public void textilatorTest50() {
        String input = "*This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-x","*","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 51
    public void textilatorTest51() {
        String input = "This is fine." + System.lineSeparator()
                +"*This is fine." + System.lineSeparator();
        String expected = "84 72 73 83 32 73 83 32 70 73 78 69 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-x","*","-a",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 52
    public void textilatorTest52() {
        String input = "This is fine." + System.lineSeparator()
                +"*This is fine." + System.lineSeparator();
        String expected = "test 84 72 73 83 32 73 83 32 70 73 78 69 46 " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-x","*","-a","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 53
    public void textilatorTest53() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-e","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 54
    public void textilatorTest54() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "test THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-e","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 55
    public void textilatorTest55() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-e","0","-s","0",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 56
    public void textilatorTest56() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-e","0","-s","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 57
    public void textilatorTest57() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-e","0","-s","1",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 58
    public void textilatorTest58() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        String expected = "test THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-e","0","-s","1","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 59
    public void textilatorTest59() {
        String input = "This is fine." + System.lineSeparator()
                +"*This is fine." + System.lineSeparator();
        String expected = "THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-e","0","-x","*",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 60
    public void textilatorTest60() {
        String input = "This is fine." + System.lineSeparator()
                +"*This is fine." + System.lineSeparator();
        String expected = "test THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-x","*","-c","upper","-e","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 61
    public void textilatorTest61() {
        String input = "This is fine." + System.lineSeparator()
                +"*This is fine." + System.lineSeparator();
        String expected = "test this is fine." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-x","*","-c","lower","-e","0","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


    @Test
    // Test Case 62
    public void textilatorTest62() {
        String input = "a" + System.lineSeparator();
        String expected = "test b" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-e","1","-p","test ", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 63
    public void textilatorTest63() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "-52", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 64
    public void textilatorTest64() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-haha", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 65
    public void textilatorTest65() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "THIS IS FINE." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-c","upper","-c","upper",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 66
    public void textilatorTest66() {
        String input = "This is fine." + System.lineSeparator();
        String expected = "test This is fine." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-p","test ","-p","test ",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 67
    public void textilatorTest67() {
        String input = "This list is really important and should not get deleted." + System.lineSeparator()
                + "* watermelon \t" + System.lineSeparator();
        String expected = "This list is really important and should not get deleted." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-x","*","-x","\t",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 68
    public void textilatorTest68() {
        String input = "a" + System.lineSeparator();
        String expected = "b" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-e","0","-e","1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 69
    public void textilatorTest69() {
        String input = "This is a list of items that start with the \"c\":" + System.lineSeparator()
                + "Krabby patty" + System.lineSeparator()
                + "Chocolate chip cookie" + System.lineSeparator()
                + "Pineapple" + System.lineSeparator()
                + "Computer" + System.lineSeparator();
        String expected = "Krabby patty" + System.lineSeparator()
                + "Pineapple" + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s","1","-s","1",inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 70
    public void textilatorTest70() {
        String input = "b" + System.lineSeparator();
        String expected = "a" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-e","0","-e","-1", inputFile.toString()};
        Main.main(args);

        Assertions.assertEquals(expected, capture.stdout());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 71
    public void textilatorTest71() {
        String input = "This is fine." + System.lineSeparator()
                +"This is fine." + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = {"-s","1.0",inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 72
    public void textilatorTest72() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-x","", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertTrue(capture.stderr().isEmpty());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 73
    public void textilatorTest73() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", "1.0", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 74
    public void textilatorTest74() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-e", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 75
    public void textilatorTest75() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-s", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 76
    public void textilatorTest76() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 77
    public void textilatorTest77() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 78
    public void textilatorTest78() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-c","UPPER", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }

    @Test
    // Test Case 79
    public void textilatorTest79() {
        String input = "The Krabby Patty Secret Formula is..." + System.lineSeparator();

        Path inputFile = createFile(input);
        String[] args = {"-a","-a", inputFile.toString()};
        Main.main(args);

        Assertions.assertTrue(capture.stdout().isEmpty());
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertEquals(input, getFileContent(inputFile));
    }


}
