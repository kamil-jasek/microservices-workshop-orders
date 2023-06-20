package com.mycompany.application.command;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

import static java.util.Arrays.stream;

public abstract class CommandHandler<C extends Command<R>, R> {

    private final static Set<Class<?>> ASPECT_ANNOTATIONS = Set.of(
        Transactional.class,
        jakarta.transaction.Transactional.class
    );

    public abstract R handle(C cmd);

    boolean canHandle(Command<R> cmd) {
        return supportedCommandClass().equals(cmd.getClass());
    }

    private Class<C> supportedCommandClass() {
        if (hasAspects()) {
            return (Class<C>) ((ParameterizedType) ((Class) (getClass().getGenericSuperclass()))
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        } else {
            return (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        }
    }

    private boolean hasAspects() {
        return stream(getClass().getAnnotations())
            .map(Annotation::annotationType)
            .anyMatch(ASPECT_ANNOTATIONS::contains);
    }
}
