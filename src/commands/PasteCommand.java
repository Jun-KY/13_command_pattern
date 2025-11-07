package commands;

import command.UndoableCommand;
import document.Clipboard;
import document.TextDocument;

public class PasteCommand extends UndoableCommand {
    private TextDocument document;
    private Clipboard clipboard;
    private int position;
    private String pastedText;

    public PasteCommand(TextDocument document, Clipboard clipboard, int position) {
        this.document = document;
        this.clipboard = clipboard;
        this.position = position;
    }

    @Override
    protected void doExecute() {
        pastedText = clipboard.paste();
        if (!pastedText.isEmpty()) {
            document.insert(position, pastedText);
        }
    }

    @Override
    public void undo() {
        if (canUndo() && pastedText != null && !pastedText.isEmpty()) {
            document.delete(position, position + pastedText.length());
        }
    }

    @Override
    public String getName() {
        return "Paste";
    }

    @Override
    public String getDescription() {
        if (pastedText != null) {
            String preview = pastedText.length() > 15 ? pastedText.substring(0, 12) + "..." : pastedText;
            return "Paste '" + preview + "'";
        }
        return "Paste from clipboard";
    }
}