package macro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MacroManager {
    private Map<String, MacroCommand> savedMacros;

    public MacroManager() {
        this.savedMacros = new HashMap<>();
    }

    public void saveMacro(MacroCommand macro) {
        String name = extractMacroName(macro.getName());
        savedMacros.put(name, macro);
        System.out.println("💾 매크로 저장됨: " + name);
    }

    public MacroCommand getMacro(String name) {
        return savedMacros.get(name);
    }

    public void deleteMacro(String name) {
        if (savedMacros.remove(name) != null) {
            System.out.println("🗑️  매크로 삭제됨: " + name);
        }
    }

    public Set<String> getMacroNames() {
        return new HashSet<>(savedMacros.keySet());
    }

    public void listMacros() {
        System.out.println("=== 저장된 매크로 목록 ===");
        if (savedMacros.isEmpty()) {
            System.out.println("저장된 매크로가 없습니다.");
        } else {
            for (Map.Entry<String, MacroCommand> entry : savedMacros.entrySet()) {
                MacroCommand macro = entry.getValue();
                System.out.println("📝 " + entry.getKey() +
                        " (" + macro.getCommandCount() + " commands)");
            }
        }
        System.out.println();
    }

    private String extractMacroName(String fullName) {
        // "Macro: name" 형태에서 name 부분만 추출
        return fullName.startsWith("Macro: ") ? fullName.substring(7) : fullName;
    }
}