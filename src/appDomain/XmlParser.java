package appDomain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import implementations.MyArrayList;

public class XmlParser {
    private MyArrayList<XmlTag> tags;
    private boolean isWellFormed;

    public XmlParser() {
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
                XmlTag tag = new XmlTag(line, lineNumber);
                tags.add(tag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseXml();
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
                if (!stack.isEmpty() && stack.peek().equals(tag)) {
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
                        stack.pop(); // pop the matched one
                    } else {
                        extrasQ.add(tag);
                    }
                }
            }
        }

        while (!stack.isEmpty()) {
            errorQ.add(stack.pop());
        }

        if (errorQ.isEmpty() && extrasQ.isEmpty()) {
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
        System.out.println("Error at line: " + tag.getLineNumber().toString() + " " + tag.getLineString()
                + " is not constructed correctly.");
    }

    public boolean getIsWellFormed() {
        return isWellFormed;

    }
}
