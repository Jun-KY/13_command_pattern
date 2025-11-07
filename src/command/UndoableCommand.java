package command;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class UndoableCommand implements Command {
    protected LocalDateTime executionTime;
    protected boolean executed = false;

    @Override
    public void execute() {
        if (!executed) {
            doExecute();
            executionTime = LocalDateTime.now();
            executed = true;
        }
    }

    protected abstract void doExecute();

    public abstract void    undo();

    public boolean canUndo() {
        return executed;
    }

    @Override
    public LocalDateTime
    getExecutionTime() {
        return executionTime;
    }
}
