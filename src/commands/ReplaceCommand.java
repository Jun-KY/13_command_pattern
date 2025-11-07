package commands;

import command.UndoableCommand;
import document.TextDocument;

public class ReplaceCommand extends UndoableCommand {
    private TextDocument document;
    private int start;
    private int end;
    private String newText;
    private String oldText;

    public ReplaceCommand(TextDocument document, int start, int end, String newText) {
        this.document = document;
        this.start = start;
        this.end = end;
        this.newText = newText;
    }

    @Override
    protected void doExecute() {
        oldText = document.substring(start, end);
        document.replace(start, end, newText);
    }

    @Override
    public void undo() {
        if (canUndo() && oldText != null) {
            document.replace(start, start + newText.length(), oldText);
        }
    }

    @Override
    public String getName() {
        return "Replace Text";
    }

    @Override
    public String getDescription() {
        String oldPreview = oldText != null ?
                (oldText.length() > 10 ? oldText.substring(0, 7) + "..." : oldText) : "?";
        String newPreview = newText.length() > 10 ?
                newText.substring(0, 7) + "..." : newText;
        return String.format("Replace '%s' with '%s'", oldPreview, newPreview);
    }
}