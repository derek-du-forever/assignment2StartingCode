package appDomain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import implementations.MyArrayList;

public class XmlParser {
    private MyArrayList<XmlTag> tags;
    private boolean isWellFormed;
    private List<String> errors;

    public XmlParser() {
        errors = new ArrayList<>();
    }

    public void parseXmlFromFile(String fileName) {
        tags = new MyArrayList<>();
        File inputFile = new File("res/" + fileName);
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile.getPath()));

            int lineNumber = 0;
            for (String line : lines) {
                lineNumber++;
                String lineString = line.trim();
                if (lineString.equals("")) {
                    continue;
                }
                if (lineString.startsWith("<?xml")) {
                    continue;
                }

                // Detect extra '>' characters
                if (detectExtraClosingBracket(lineString, lineNumber)) {
                    continue;
                }

                // Extract all tags from the current line
                List<XmlTag> lineTags = extractTagsFromLine(line, lineNumber);
                for (XmlTag tag : lineTags) {
                    tags.add(tag);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseXml();
    }

    /**
     * Detects extra closing brackets
     */
    private boolean detectExtraClosingBracket(String line, int lineNumber) {
        // Look for cases where there are multiple '>' characters after a tag
        Pattern pattern = Pattern.compile(">>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String errorMsg = "Error at line: " + lineNumber + " " + line.trim() + " is not constructed correctly.";
            errors.add(errorMsg);
            System.out.println(errorMsg);
            isWellFormed = false;
            return true;
        }
        return false;
    }

    /**
     * Extracts all XML tags from a line
     */
    private List<XmlTag> extractTagsFromLine(String line, int lineNumber) {
        List<XmlTag> lineTags = new ArrayList<>();
        // Matches all tags: <tag>, </tag>, <tag/>, <tag attr="value">
        Pattern pattern = Pattern.compile("<[^>]+>");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String tagString = matcher.group();
            XmlTag tag = new XmlTag(tagString, lineNumber);
            lineTags.add(tag);
        }

        return lineTags;
    }

    private XmlTag nextTag() {
        if (tags.size() == 0) {
            return null;
        }
        return tags.remove(0);
    }

    private void parseXml() {
        Stack<XmlTag> stack = new Stack<>();
        Queue<XmlTag> errorQ = new LinkedList<>();
        Queue<XmlTag> extrasQ = new LinkedList<>();

        XmlTag tag;
        while ((tag = nextTag()) != null) {
            if (tag.isSelfClosing()) {
                continue;
            }
            if (tag.isStart()) {
                stack.push(tag);
            } else if (tag.isEnd()) {
                // Detect mismatched or crossed tags
                if (!stack.isEmpty() && !stack.peek().equals(tag)) {

                    // Try to determine if this is a tag-crossing case
                    boolean foundMatch = false;
                    Stack<XmlTag> tempStack = new Stack<>();

                    // Look for a matching start tag deeper in the stack
                    while (!stack.isEmpty()) {
                        XmlTag startTag = stack.pop();
                        tempStack.push(startTag);
                        if (startTag.equals(tag)) {
                            foundMatch = true;
                            break;
                        }
                    }

                    if (foundMatch) {
                        // Tag crossing detected
                        // String errorMsg = "Error at line: " + tag.getLineNumber() + " " +
                        // tag.getLineString() + " is not constructed correctly.";
                        // errors.add(errorMsg);
                        // System.out.println(errorMsg);
                        report(tag);
                        isWellFormed = false;

                        // Add unmatched tags to the error queue
                        while (!tempStack.isEmpty()) {
                            XmlTag unmatched = tempStack.pop();
                            if (!unmatched.equals(tag)) {
                                errorQ.add(unmatched);
                            }
                        }
                    } else {
                        // Restore the original stack
                        while (!tempStack.isEmpty()) {
                            stack.push(tempStack.pop());
                        }

                        // Original logic handling unmatched end tags
                        if (!errorQ.isEmpty() && errorQ.peek().equals(tag)) {
                            errorQ.poll();
                        } else if (stack.isEmpty()) {
                            errorQ.add(tag);
                        } else {
                            if (stack.contains(tag)) {
                                while (!stack.peek().equals(tag)) {
                                    errorQ.add(stack.pop());
                                }
                                stack.pop();
                            } else {
                                extrasQ.add(tag);
                            }
                        }
                    }
                } else if (!stack.isEmpty() && stack.peek().equals(tag)) {
                    // Proper pair: pop it
                    stack.pop();
                } else if (!errorQ.isEmpty() && errorQ.peek().equals(tag)) {
                    errorQ.poll();
                } else if (stack.isEmpty()) {
                    errorQ.add(tag);
                } else {
                    if (stack.contains(tag)) {
                        while (!stack.peek().equals(tag)) {
                            errorQ.add(stack.pop());
                        }
                        stack.pop();
                    } else {
                        extrasQ.add(tag);
                    }
                }
            }
        }

        // Add any remaining unmatched start tags
        while (!stack.isEmpty()) {
            errorQ.add(stack.pop());
        }

        if (errorQ.isEmpty() && extrasQ.isEmpty() && errors.isEmpty()) {
            isWellFormed = true;
            return;
        }

        while (true) {
            if (errorQ.isEmpty() && extrasQ.isEmpty()) {
                return;
            }
            if (errorQ.isEmpty()) {
                report(extrasQ);
                return;
            }

            if (extrasQ.isEmpty()) {
                report(errorQ);
                return;
            }
            if (!errorQ.peek().equals(extrasQ.peek())) {
                report(errorQ.poll());
                continue;
            }

            errorQ.poll();
            extrasQ.poll();
        }
    }

    private void report(Queue<XmlTag> items) {
        for (XmlTag tag : items) {
            report(tag);
        }
    }

    private void report(XmlTag tag) {
        String errorMsg = "Error at line: " + tag.getLineNumber() + " " +
                tag.getLineString() + " is not constructed correctly.";
        System.out.println(errorMsg);
        errors.add(errorMsg);
    }

    public boolean getIsWellFormed() {
        return isWellFormed;
    }

    public List<String> getErrors() {
        return errors;
    }
}
