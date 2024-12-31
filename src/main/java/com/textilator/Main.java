package edu.gatech.seclass.textilator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    private static String inputFile;
    private static String caseOpt;
    private static Integer shiftValue;
    private static String subStr;
    private static Integer skipLineValue;
    private static boolean asciiEncoding;
    private static String prefixStr;

    public static void main(String[] args) {
        asciiEncoding = false;
        prefixStr = null;
        skipLineValue = null;
        subStr = null;
        shiftValue = null;
        caseOpt = null;
        inputFile = null;

        boolean argsValid = processArgs(args) && validateArgs();
        if (!argsValid || !hasLineSeparator(inputFile)) {
            printUsage();
            return;
        }

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(inputFile))) {
            String line;
            int lineNum = 1;
            while ((line = bufferedReader.readLine()) != null) {

                if (skipLineValue != null && lineNum % 2 == skipLineValue) {
                    lineNum++;
                    continue;
                }

                if (subStr != null && line.contains(subStr)) {
                    lineNum++;
                    continue;
                }

                if (caseOpt != null) {
                    line = changeCase(line, caseOpt);
                }

                if (shiftValue != null) {
                    line = shiftEncode(line, shiftValue);
                } else if (asciiEncoding) {
                    line = encodeToAscii(line);
                }

                if (prefixStr != null) {
                    line = addPrefixToLine(line, prefixStr);
                }

                System.out.println(line);
                lineNum++;
            }
        } catch (IOException e) {
            printUsage();
        }
    }

    private static void printUsage() {
        System.err.println("Usage: textilator [ -s number | -x substring | -c case | -e num | -a | -p prefix ] FILE");
    }

    private static boolean processArgs(String[] args) {
        try {
            boolean hasAsciiOpt = false;
            for (int i = 0; i < args.length - 1; i++) {
                switch (args[i]) {
                    case "-c":
                        caseOpt = args[++i];
                        break;
                    case "-e":
                        shiftValue = Integer.parseInt(args[++i]);
                        break;
                    case "-x":
                        if (i + 1 < args.length - 1) {
                            subStr = args[++i];
                        } else {
                            return false;
                        }
                        break;
                    case "-s":
                        skipLineValue = Integer.parseInt(args[++i]);
                        break;
                    case "-a":
                        if (hasAsciiOpt) {
                            return false;
                        }
                        asciiEncoding = true;
                        hasAsciiOpt = true;
                        break;
                    case "-p":
                        if (i + 1 < args.length - 1) {
                            prefixStr = args[++i];
                        } else {
                            return false;
                        }
                        break;
                    default:
                        return false;
                }
            }
            inputFile = args[args.length - 1];
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String changeCase(String line, String caseOption) {
        StringBuilder result = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (Character.isLetter(c) && ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                if ("upper".equals(caseOption)) {
                    result.append(Character.toUpperCase(c));
                } else if ("lower".equals(caseOption)) {
                    result.append(Character.toLowerCase(c));
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    private static String shiftEncode(String line, int shiftAmount) {
        StringBuilder result = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                int shifted = (c - 'A' + shiftAmount) % 26;
                if (shifted < 0) {
                    shifted += 26;
                }
                result.append((char) (shifted + 'A'));
            } else if (c >= 'a' && c <= 'z') {
                int shifted = (c - 'a' + shiftAmount) % 26;
                if (shifted < 0) {
                    shifted += 26;
                }
                result.append((char) (shifted + 'a'));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }


    private static String encodeToAscii(String line) {
        StringBuilder result = new StringBuilder();

        for (char ch : line.toCharArray()) {
            if (ch >= 32 && ch <= 126) {
                result.append((int) ch).append(' ');
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    private static String addPrefixToLine(String line, String prefix) {
        return prefix + line;
    }

    private static boolean validateArgs() {
        try {
            if (skipLineValue != null && subStr != null) {
                throw new IllegalArgumentException();
            }

            if (shiftValue != null && asciiEncoding) {
                throw new IllegalArgumentException();
            }

            if (skipLineValue != null && (skipLineValue < 0 || skipLineValue > 1)) {
                throw new IllegalArgumentException();
            }

            if (prefixStr != null && prefixStr.isEmpty()) {
                throw new IllegalArgumentException();
            }

            if (caseOpt != null && !("upper".equals(caseOpt) || "lower".equals(caseOpt))) {
                throw new IllegalArgumentException();
            }

            if (shiftValue != null && (shiftValue < -25 || shiftValue > 25)) {
                throw new IllegalArgumentException();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean hasLineSeparator(String filePath) {
        try {
            String fileContent = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
            if (fileContent.isEmpty()) {
                return true;
            }
            return fileContent.contains(System.lineSeparator());
        } catch (IOException e) {
            return false;
        }
    }
}

