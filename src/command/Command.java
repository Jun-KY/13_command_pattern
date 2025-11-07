package command;

import java.time.LocalDateTime;

public interface Command {
    void execute();

    String getName();

    default String getDescription() {
        return getName();
    }

    default LocalDateTime getExecutionTime() {
        return LocalDateTime.now();
    }

    default boolean modifiesDocument() {
        return true;
    }
}