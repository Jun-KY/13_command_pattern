package document;

import java.util.ArrayList;
import java.util.List;

public class TextDocument {
    private StringBuilder content;
    private List<DocumentListener> listeners;

    public TextDocument() {
        this.content = new StringBuilder();
        this.listeners = new ArrayList<>();
    }

    public TextDocument(String initialContent) {
        this.content = new StringBuilder(initialContent);
        this.listeners = new ArrayList<>();
    }

    public void insert(int position, String text) {
        if (position >= 0 && position <= content.length() && text != null) {
            content.insert(position, text);
            notifyListeners();
        }
    }

    public String delete(int start, int end) {
        if (start >= 0 && end <= content.length() && start < end) {
            String deletedText = content.substring(start, end);
            content.delete(start, end);
            notifyListeners();
            return deletedText;
        }
        return "";
    }

    public void replace(int start, int end, String newText) {
        delete(start, end);
        insert(start, newText);
    }

    public String getContent() {
        return content.toString();
    }

    public String substring(int start, int end) {
        if (start >= 0 && end <= content.length() && start <= end) {
            return content.substring(start, end);
        }
        return "";
    }

    public int length() {
        return content.length();
    }

    public int findText(String searchText, int startPosition) {
        String content = this.content.toString();
        return content.indexOf(searchText, startPosition);
    }

    public void addListener(DocumentListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (DocumentListener listener : listeners) {
            listener.onDocumentChanged();
        }
    }

    public interface DocumentListener {
        void onDocumentChanged();
    }
}