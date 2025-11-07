package commands;

import command.UndoableCommand;
import document.TextDocument;

import java.util.ArrayList;
import java.util.List;

public class FindReplaceCommand extends UndoableCommand {
    private TextDocument document;
    private String searchText;
    private String replaceText;
    private boolean replaceAll;
    private List<ReplacementInfo> replacements;

    public FindReplaceCommand(TextDocument document, String searchText,
                              String replaceText, boolean replaceAll) {
        this.document = document;
        this.searchText = searchText;
        this.replaceText = replaceText;
        this.replaceAll = replaceAll;
        this.replacements = new ArrayList<>();
    }

    @Override
    protected void doExecute() {
        int position = 0;
        int searchLength = searchText.length();

        while (true) {
            int found = document.findText(searchText, position);
            if (found == -1) break;

            // 교체 정보 저장 (실행취소용)
            String originalText = document.substring(found, found + searchLength);
            replacements.add(new ReplacementInfo(found, originalText, replaceText));

            // 실제 교체 수행
            document.replace(found, found + searchLength, replaceText);

            // 다음 위치 계산 (교체된 텍스트 길이 고려)
            position = found + replaceText.length();

            if (!replaceAll) break; // 첫 번째만 교체
        }
    }

    @Override
    public void undo() {
        if (canUndo()) {
            // 역순으로 복원 (위치 오프셋 문제 방지)
            for (int i = replacements.size() - 1; i >= 0; i--) {
                ReplacementInfo info = replacements.get(i);
                document.replace(info.position,
                        info.position + info.newText.length(),
                        info.originalText);
            }
        }
    }

    @Override
    public String getName() {
        return replaceAll ? "Replace All" : "Replace";
    }

    @Override
    public String getDescription() {
        return String.format("%s '%s' with '%s' (%d replacements)",
                replaceAll ? "Replace all" : "Replace",
                searchText, replaceText, replacements.size());
    }

    // 교체 정보를 담는 내부 클래스
    private static class ReplacementInfo {
        final int position;
        final String originalText;
        final String newText;

        ReplacementInfo(int position, String originalText, String newText) {
            this.position = position;
            this.originalText = originalText;
            this.newText = newText;
        }
    }
}