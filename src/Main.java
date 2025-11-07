import editor.TextEditor;

public class Main {
    public static void main(String[] args) {
//        demonstrateBasicEditing();

        demonstrateUndoRedo();
    }

    private static void demonstrateBasicEditing() {
        System.out.println("=== 1. 기본 편집 기능 데모 ===");

        TextEditor editor = new TextEditor();

        // 초기 텍스트 입력
        editor.insertText(0, "Hello World");

        // 텍스트 추가
        editor.insertText(5, " Beautiful");

        // 텍스트 삭제
        editor.deleteText(6, 15); // "Beautiful" 삭제

        // 텍스트 교체
        editor.replaceText(6, 11, "Java Programming");

        editor.showStatus();
        System.out.println("=".repeat(50) + "\n");
    }

    private static void demonstrateUndoRedo() {
        System.out.println("=== 2. 실행취소/재실행 데모 ===");

        TextEditor editor = new TextEditor();

        // 여러 편집 작업
        editor.insertText(0, "Command Pattern");
        editor.insertText(15, " Demo");
        editor.insertText(20, " Test");

        // 히스토리 확인
        editor.getHistory().showHistory();

        // 실행취소 테스트
        System.out.println("--- 실행취소 테스트 ---");
        editor.undo(); // " Test" 삭제
        editor.undo(); // " Demo" 삭제

        // 재실행 테스트
        System.out.println("--- 재실행 테스트 ---");
        editor.redo(); // " Demo" 복원

        // 새로운 편집 후 redo 스택이 클리어되는지 확인
        editor.insertText(20, "!!!");
        editor.redo(); // 실패해야 함 (redo 스택이 비워짐)

        System.out.println("=".repeat(50) + "\n");
    }
}