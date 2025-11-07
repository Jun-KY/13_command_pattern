package commands;

import command.UndoableCommand;
import document.TextDocument;

public class InsertCommand extends UndoableCommand {
    private TextDocument document;
    private int position;
    private String text;

    public InsertCommand(TextDocument document, int position, String text) {
        this.document = document;
        this.position = position;
        this.text = text;
    }

    @Override
    protected void doExecute() {
        document.insert(position, text);
    }

    @Override
    public void undo() {
        if (canUndo()) {
            document.delete(position, position + text.length());
        }
    }

    @Override
    public String getName() {
        return "Insert Text";
    }

    @Override
    public String getDescription() {
        return String.format("Insert '%s' at position %d", text.length() > 20 ? text.substring(0, 17) + "..." : text, position);
    }
}