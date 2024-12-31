package edu.gatech.seclass.textilator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Textilator implements TextilatorInterface {

    private String inputFile;
    private Case letterCase;
    private Integer shiftValue;
    private String excludeString;
    private LineParity lineParity;
    private boolean encodeLines;
    private String prefix;

    @Override
    public void reset() {
        inputFile = null;
        letterCase = null;
        shiftValue = null;
        excludeString = null;
        lineParity = null;
        encodeLines = false;
        prefix = null;
    }

    @Override
    public void setFilepath(String filepath) {
        inputFile = filepath;
    }

    @Override
    public void setLineToSkip(LineParity lineToSkip) {
        lineParity = lineToSkip;
    }

    @Override
    public void setExcludeString(String excludeString) {
        this.excludeString = excludeString;
    }

    @Override
    public void setLetterCase(Case letterCase) {
        this.letterCase = letterCase;
    }

    @Override
    public void setCipherText(int shiftAmount) {
        this.shiftValue = shiftAmount;
    }

    @Override
    public void setEncodeLines(boolean encodeLines) {
        this.encodeLines = encodeLines;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void textilator() throws TextilatorException {
        if (inputFile == null) {
            throw new TextilatorException("no Input file");
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(inputFile))) {
            String line;
            int lineNum = 1;
            boolean hasLineSeparator = false;
            while ((line = bufferedReader.readLine()) != null) {
                hasLineSeparator = true;
                if (lineParity != null && lineNum % 2 == (lineParity == LineParity.even ? 0 : 1)) {
                    lineNum++;
                    continue;
                }

                if (excludeString != null && line.contains(excludeString)) {
                    lineNum++;
                    continue;
                }

                if (letterCase != null) {
                    line = changeCase(line, String.valueOf(letterCase));
                }

                if (shiftValue != null) {
                    line = shiftEncode(line, shiftValue);
                } else if (encodeLines) {
                    line = encodeToAscii(line);
                }

                if (prefix != null) {
                    line = addPrefixToLine(line, prefix);
                }

                System.out.println(line);
                lineNum++;
            }
            if (!hasLineSeparator) {
                throw new TextilatorException("No line separators");
            }
        } catch (IOException e) {
            throw new TextilatorException("Error");
        }
    }

    private  String changeCase(String line, String caseOption) throws TextilatorException {
        StringBuilder result = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (Character.isLetter(c) && ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                if ("upper".equals(caseOption)) {
                    result.append(Character.toUpperCase(c));
                } else if ("lower".equals(caseOption)) {
                    result.append(Character.toLowerCase(c));
                } else {
                    throw new TextilatorException("Error");
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    private  String shiftEncode(String line, int shiftAmount) {
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
    private  String encodeToAscii(String line) {
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

    private  String addPrefixToLine(String line, String prefix) {
        return prefix + line;
    }

}
