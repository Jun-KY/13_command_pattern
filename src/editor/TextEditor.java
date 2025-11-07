package editor;

import command.Command;
import command.UndoableCommand;
import commands.*;
import document.Clipboard;
import document.Selection;
import document.TextDocument;
import macro.MacroCommand;
import macro.MacroManager;
import macro.MacroRecorder;

public class TextEditor {
    private TextDocument document;
    private CommandHistory history;
    private Clipboard clipboard;
    private Selection currentSelection;
    private MacroRecorder macroRecorder;
    private MacroManager macroManager;

    public TextEditor() {
        this.document = new TextDocument();
        this.history = new CommandHistory();
        this.clipboard = new Clipboard();
        this.currentSelection = new Selection(0, 0);
        this.macroRecorder = new MacroRecorder();
        this.macroManager = new MacroManager();

        // 문서 변경 리스너 등록
        document.addListener(() -> {
            // 문서가 변경될 때마다 화면 갱신 등을 할 수 있음
        });
    }

    public void executeCommand(Command command) {
        // 매크로 녹화 중이면 명령 기록
        macroRecorder.recordCommand(command);

        // 명령 실행
        command.execute();

        // 실행취소 가능한 명령이면 히스토리에 추가
        if (command instanceof UndoableCommand) {
            history.addCommand((UndoableCommand) command);
        }

        System.out.println("✅ " + command.getName() + " 실행됨");
        showDocumentState();
    }

    public void undo() {
        UndoableCommand command = history.undo();
        if (command != null) {
            System.out.println("↶  실행취소: " + command.getName());
            showDocumentState();
        } else {
            System.out.println("❌ 실행취소할 작업이 없습니다.");
        }
    }

    public void redo() {
        UndoableCommand command = history.redo();
        if (command != null) {
            System.out.println("↷  재실행: " + command.getName());
            showDocumentState();
        } else {
            System.out.println("❌ 재실행할 작업이 없습니다.");
        }
    }

    // 편의 메소드들
    public void insertText(int position, String text) {
        executeCommand(new InsertCommand(document, position, text));
    }

    public void deleteText(int start, int end) {
        executeCommand(new DeleteCommand(document, start, end));
    }

    public void replaceText(int start, int end, String newText) {
        executeCommand(new ReplaceCommand(document, start, end, newText));
    }

    public void cut() {
        if (!currentSelection.isEmpty()) {
            executeCommand(new CutCommand(document, clipboard, currentSelection));
            currentSelection.setSelection(currentSelection.getStart(), currentSelection.getStart());
        }
    }

    public void copy() {
        if (!currentSelection.isEmpty()) {
            executeCommand(new CopyCommand(document, clipboard, currentSelection));
        }
    }

    public void paste(int position) {
        executeCommand(new PasteCommand(document, clipboard, position));
    }

    public void findReplace(String searchText, String replaceText, boolean replaceAll) {
        executeCommand(new FindReplaceCommand(document, searchText, replaceText, replaceAll));
    }

    // 매크로 관련 메소드들
    public void startMacroRecording(String macroName) {
        macroRecorder.startRecording(macroName);
    }

    public void stopMacroRecording() {
        MacroCommand macro = macroRecorder.stopRecording();
        if (macro != null) {
            macroManager.saveMacro(macro);
        }
    }

    public void playMacro(String macroName) {
        MacroCommand macro = macroManager.getMacro(macroName);
        if (macro != null) {
            executeCommand(macro);
        } else {
            System.out.println("❌ 매크로를 찾을 수 없습니다: " + macroName);
        }
    }

    // 상태 관련 메소드들
    public void setSelection(int start, int end) {
        currentSelection.setSelection(start, end);
        System.out.println("📍 선택 영역 설정: " + currentSelection);
    }

    public void showDocumentState() {
        System.out.println("📄 현재 문서: \"" + document.getContent() + "\"");
        System.out.println("   길이: " + document.length() + " 문자");
        if (!currentSelection.isEmpty()) {
            System.out.println("   선택: " + currentSelection);
        }
        System.out.println();
    }

    public void showStatus() {
        System.out.println("=== 에디터 상태 ===");
        System.out.println("문서 길이: " + document.length() + " 문자");
        System.out.println("선택 영역: " + currentSelection);
        System.out.println("클립보드: " + (clipboard.hasContent() ? "내용 있음" : "비어있음"));
        System.out.println("실행취소 가능: " + history.canUndo());
        System.out.println("재실행 가능: " + history.canRedo());
        System.out.println("매크로 녹화 중: " + macroRecorder.isRecording());
        if (macroRecorder.isRecording()) {
            System.out.println("  녹화 중인 매크로: " + macroRecorder.getCurrentMacroName());
            System.out.println("  녹화된 명령 수: " + macroRecorder.getRecordedCommandCount());
        }
        System.out.println();
    }

    // Getter 메소드들
    public TextDocument getDocument() { return document; }
    public CommandHistory getHistory() { return history; }
    public Clipboard getClipboard() { return clipboard; }
    public Selection getCurrentSelection() { return currentSelection; }
    public MacroManager getMacroManager() { return macroManager; }
}