package macro;

import command.Command;
import command.UndoableCommand;

import java.util.ArrayList;
import java.util.List;

public class MacroCommand extends UndoableCommand {
    private String name;
    private List<Command> commands;
    private List<UndoableCommand> executedUndoableCommands;

    public MacroCommand(String name, List<Command> commands) {
        this.name = name;
        this.commands = new ArrayList<>(commands);
        this.executedUndoableCommands = new ArrayList<>();
    }

    @Override
    protected void doExecute() {
        for (Command command: commands) {
            command.execute();

            if (command instanceof UndoableCommand) {
                executedUndoableCommands.add((UndoableCommand) command);
            }
        }
    }

    @Override
    public void undo() {
        if (canUndo()) {
            for (int i = executedUndoableCommands.size() - 1; i >= 0; i--) {
                UndoableCommand command = executedUndoableCommands.get(i);
                if (command.canUndo()) {
                    command.undo();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Macro: " + name;
    }

    @Override
    public String getDescription() {
        return String.format("Macro '%s' with %d commands", name, commands.size());
    }

    public int getCommandCount() {
        return commands.size();
    }

    public List<Command> getCommands() {
        return new ArrayList<>(commands);
    }
}