package commands;

import command.Command;
import document.Clipboard;
import document.Selection;
import document.TextDocument;

public class CopyCommand implements Command {
    private TextDocument document;
    private Clipboard clipboard;
    private Selection selection;

    public CopyCommand(TextDocument document, Clipboard clipboard, Selection selection) {
        this.document = document;
        this.clipboard = clipboard;
        this.selection = selection;
    }

    @Override
    public void execute() {
        if (!selection.isEmpty()) {
            String text = document.substring(selection.getStart(), selection.getEnd());
            clipboard.copy(text);
        }
    }

    @Override
    public String getName() {
        return "Copy";
    }

    @Override
    public boolean modifiesDocument() {
        return false; // 복사는 문서를 변경하지 않음
    }

    @Override
    public String getDescription() {
        if (!selection.isEmpty()) {
            String text = document.substring(selection.getStart(), selection.getEnd());
            String preview = text.length() > 15 ? text.substring(0, 12) + "..." : text;
            return "Copy '" + preview + "'";
        }
        return "Copy selected text";
    }
}