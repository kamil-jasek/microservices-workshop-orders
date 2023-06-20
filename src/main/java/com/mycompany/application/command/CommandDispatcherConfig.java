package com.mycompany.application.command;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
class CommandDispatcherConfig {

    @Bean
    CommandDispatcher commandDispatcher(Set<CommandHandler> handlers) {
        return new CommandDispatcher(handlers);
    }
}
