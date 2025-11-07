package commands;

import command.UndoableCommand;
import document.TextDocument;

public class DeleteCommand extends UndoableCommand {
    private TextDocument document;
    private int start;
    private int end;
    private String deletedText;

    public DeleteCommand(TextDocument document, int start, int end) {
        this.document = document;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void doExecute() {
        deletedText = document.delete(start, end);
    }

    @Override
    public void undo() {
        if (canUndo() && deletedText != null) {
            document.insert(start, deletedText);
        }
    }

    @Override
    public String getName() {
        return "Delete Text";
    }

    @Override
    public String getDescription() {
        if (deletedText != null) {
            String preview = deletedText.length() > 20 ?
                    deletedText.substring(0, 17) + "..." : deletedText;
            return String.format("Delete '%s' from %d to %d", preview, start, end);
        }
        return String.format("Delete from %d to %d", start, end);
    }
}