package macro;

import command.Command;

import java.util.ArrayList;
import java.util.List;

public class MacroRecorder {
    private boolean recording = false;
    private String macroName;
    private List<Command> recordedCommands;

    public MacroRecorder() {
        this.recordedCommands = new ArrayList<>();
    }

    public void startRecording(String macroName) {
        if (!recording) {
            this.macroName = macroName;
            this.recording = true;
            this.recordedCommands.clear();
            System.out.println("📹 매크로 녹화 시작: " + macroName);
        }
    }

    public MacroCommand stopRecording() {
        if (recording) {
            recording = false;
            MacroCommand macro = new MacroCommand(macroName, recordedCommands);
            System.out.println("⏹️  매크로 녹화 완료: " + macroName +
                    " (" + recordedCommands.size() + " commands)");
            return macro;
        }
        return null;
    }

    public void recordCommand(Command command) {
        if (recording && command.modifiesDocument()) {
            // 매크로 자체는 녹화하지 않음 (무한 재귀 방지)
            if (!(command instanceof MacroCommand)) {
                recordedCommands.add(command);
                System.out.println("🔴 명령 녹화됨: " + command.getName());
            }
        }
    }

    public boolean isRecording() {
        return recording;
    }

    public String getCurrentMacroName() {
        return macroName;
    }

    public int getRecordedCommandCount() {
        return recordedCommands.size();
    }
}