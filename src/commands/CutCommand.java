package commands;

import command.UndoableCommand;
import document.Clipboard;
import document.Selection;
import document.TextDocument;

public class CutCommand extends UndoableCommand {
    private TextDocument document;
    private Clipboard clipboard;
    private Selection selection;
    private String cutText;

    public CutCommand(TextDocument document, Clipboard clipboard, Selection selection) {
        this.document = document;
        this.clipboard = clipboard;
        this.selection = selection;
    }

    @Override
    protected void doExecute() {
        if (!selection.isEmpty()) {
            cutText = document.substring(selection.getStart(), selection.getEnd());
            clipboard.copy(cutText);
            document.delete(selection.getStart(), selection.getEnd());
        }
    }

    @Override
    public void undo() {
        if (canUndo() && cutText != null) {
            document.insert(selection.getStart(), cutText);
            clipboard.copy(cutText); // 클립보드 복원
        }
    }

    @Override
    public String getName() {
        return "Cut";
    }

    @Override
    public String getDescription() {
        if (cutText != null) {
            String preview = cutText.length() > 15 ? cutText.substring(0, 12) + "..." : cutText;
            return "Cut '" + preview + "'";
        }
        return "Cut selected text";
    }
}