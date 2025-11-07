package editor;

import command.Command;
import command.UndoableCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CommandHistory {
    private Stack<UndoableCommand> undoStack;
    private Stack<UndoableCommand> redoStack;
    private List<Command> allCommands;
    private int maxHistorySize;

    public CommandHistory() {
        this(100);
    }

    public CommandHistory(int maxHistorySize) {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.allCommands = new ArrayList<>();
        this.maxHistorySize = maxHistorySize;
    }

    public void addCommand(UndoableCommand command) {
        undoStack.push(command);
        redoStack.clear();

        if (undoStack.size() > maxHistorySize) {
            Stack<UndoableCommand> tempStack = new Stack<>();
            while (undoStack.size() > 1) {
                tempStack.push(undoStack.pop());
            }
            undoStack.clear();
            while (!tempStack.isEmpty()) {
                undoStack.push(tempStack.pop());
            }
        }

        allCommands.add(command);
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public UndoableCommand undo() {
        if (canUndo()) {
            UndoableCommand command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            return command;
        }
        return null;
    }

    public UndoableCommand redo() {
        if (canRedo()) {
            UndoableCommand command = redoStack.pop();
            command.execute();
            undoStack.push(command);
            return command;
        }
        return null;
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
        allCommands.clear();
    }

    public List<UndoableCommand> getUndoHistory() {
        return new ArrayList<>(undoStack);
    }

    public List<UndoableCommand> getRedoHistory() {
        return new ArrayList<>(redoStack);
    }

    public List<Command> getAllCommands() {
        return new ArrayList<>(allCommands);
    }

    public void showHistory() {
        System.out.println("=== 명령 히스토리 ===");
        System.out.println("실행취소 가능: " + undoStack.size() + "개");
        System.out.println("재실행 가능: " + redoStack.size() + "개");
        System.out.println("전체 실행된 명령: " + allCommands.size() + "개");

        if (!undoStack.isEmpty()) {
            System.out.println("\n최근 명령들:");
            List<UndoableCommand> recentCommands = new ArrayList<>(undoStack);
            int start = Math.max(0, recentCommands.size() - 5);
            for (int i = start; i < recentCommands.size(); i++) {
                UndoableCommand cmd = recentCommands.get(i);
                System.out.println("  " + (i + 1) + ". " + cmd.getDescription());
            }
        }
        System.out.println();
    }
}