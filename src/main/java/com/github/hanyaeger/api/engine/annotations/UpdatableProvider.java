package com.github.hanyaeger.api.engine.annotations;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with {@link UpdatableProvider} should return an {@link Updatable}
 * and will automatically be called during activation of an {@link YaegerEntity}
 * or {@link YaegerScene} to gathers all objects that contain an {@link Updatable#update(long)}
 * method that should be called at each Game world update.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UpdatableProvider {
    boolean asFirst() default false;
}
