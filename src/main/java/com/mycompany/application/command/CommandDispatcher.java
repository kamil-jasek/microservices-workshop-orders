package com.mycompany.application.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class CommandDispatcher {

    @NonNull
    final Set<CommandHandler> handlers;

    @SuppressWarnings("unchecked")
    public <R> R dispatch(Command<R> cmd) {
        return handlers.stream()
            .filter(handler -> handler.canHandle(cmd))
            .findFirst()
            .map(handler -> (R) handler.handle(cmd))
            .orElseThrow(() -> new IllegalArgumentException(
                "cannot find handler for command: " + cmd.getClass().getName()));
    }
}
