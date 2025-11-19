package appDomain;

public class XmlTag {
    private String lineString;
    private String name;
    private boolean isSelfClosing;
    private boolean isStart;
    private boolean isEnd;
    private int lineNumber;

    public XmlTag(String lineString, int lineNumber) {
        this.lineString = lineString.trim();
        this.lineNumber = lineNumber;

        if (this.lineString.startsWith("</")) {
            isStart = false;
            isEnd = true;
            name = this.lineString.substring(2, this.lineString.length() - 1).trim();
        } else if (this.lineString.endsWith("/>")) {
            isSelfClosing = true;
            isStart = false;
            isEnd = false;
            name = this.lineString.substring(1, this.lineString.length() - 2).trim().split(" ")[0];
        } else {
            isStart = true;
            isEnd = false;
            name = this.lineString.substring(1, this.lineString.length() - 1).trim().split(" ")[0];
        }
    }

    public String getLineString() {
        return lineString;
    }

    public String getName() {
        return name;
    }

    public boolean isSelfClosing() {
        return isSelfClosing;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        XmlTag other = (XmlTag) obj;
        return name.equals(other.getName());
    }

}
