package document;

public class Clipboard {
    private String content = "";

    public void copy(String text) {
        this.content = text != null ? text : "";
    }

    public String paste() {
        return content;
    }

    public boolean hasContent() {
        return !content.isEmpty();
    }

    public void clear() {
        this.content = "";
    }
}