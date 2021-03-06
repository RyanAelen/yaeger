package com.github.hanyaeger.api.engine.entities.entity.shape.ellipse;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.google.inject.Injector;
import javafx.geometry.Bounds;
import javafx.scene.shape.Ellipse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class EllipseEntityTest {
    private static final Coordinate2D LOCATION = new Coordinate2D(37, 37);
    public static final double RADIUS_X = 37;
    public static final double RADIUS_Y = 42;

    private Ellipse ellipse;
    private Injector injector;
    private EllipseEntity sut;

    @BeforeEach
    void setup() {
        ellipse = mock(Ellipse.class);
        injector = mock(Injector.class);

        sut = new EllipseEntityImpl(LOCATION);
    }

    @Test
    void settingRadiusXAfterShapeIsSetDelegatesTheRadiusX() {
        // Arrange
        sut.setShape(ellipse);
        sut.init(injector);

        // Act
        sut.setRadiusX(RADIUS_X);

        // Assert
        verify(ellipse).setVisible(true);
        verify(ellipse).setRadiusX(RADIUS_X);
    }

    @Test
    void settingRadiusYAfterShapeIsSetDelegatesTheRadiusY() {
        // Arrange
        sut.setShape(ellipse);
        sut.init(injector);

        // Act
        sut.setRadiusY(RADIUS_Y);

        // Assert
        verify(ellipse).setVisible(true);
        verify(ellipse).setRadiusY(RADIUS_Y);
    }

    @Test
    void ifShapeNotYetSetRadiusXIsStoredAndSetAtInit() {
        // Arrange
        sut.setRadiusX(RADIUS_X);
        sut.setShape(ellipse);

        // Act
        sut.init(injector);

        // Assert
        verify(ellipse).setRadiusX(RADIUS_X);
    }

    @Test
    void ifShapeNotYetSetRadiusYIsStoredAndSetAtInit() {
        // Arrange
        sut.setRadiusY(RADIUS_Y);
        sut.setShape(ellipse);

        // Act
        sut.init(injector);

        // Assert
        verify(ellipse).setRadiusY(RADIUS_Y);
    }

    @Test
    void getLeftXTakesRadiusIntoAccount() {
        // Arrange
        sut.setRadiusX(RADIUS_X);
        sut.setShape(ellipse);
        sut.init(injector);
        var bounds = mock(Bounds.class);
        when(ellipse.getBoundsInLocal()).thenReturn(bounds);
        when(bounds.getMinX()).thenReturn(LOCATION.getX());

        // Act
        var actual = sut.getLeftX();

        // Assert
        var expected = LOCATION.getX() + RADIUS_X;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTopYTakesRadiusIntoAccount() {
        // Arrange
        sut.setRadiusY(RADIUS_Y);
        sut.setShape(ellipse);
        sut.init(injector);
        var bounds = mock(Bounds.class);
        when(ellipse.getBoundsInLocal()).thenReturn(bounds);
        when(bounds.getMinY()).thenReturn(LOCATION.getY());

        // Act
        var actual = sut.getTopY();

        // Assert
        var expected = LOCATION.getY() + RADIUS_Y;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void setReferenceXCallsSetXOnShapeAfterInitHasBeenCalled() {
        // Arrange
        sut.setShape(ellipse);
        sut.init(injector);

        var referenceX = 1d;

        // Act
        sut.setReferenceX(referenceX);

        // Assert
        verify(ellipse).setCenterX(referenceX);
    }

    @Test
    void setReferenceYCallsSetYOnShapeAfterInitHasBeenCalled() {
        // Arrange
        sut.setShape(ellipse);
        sut.init(injector);

        var referenceY = 1d;

        // Act
        sut.setReferenceY(referenceY);

        // Assert
        verify(ellipse).setCenterY(referenceY);
    }

    private class EllipseEntityImpl extends EllipseEntity {

        public EllipseEntityImpl(Coordinate2D initialPosition) {
            super(initialPosition);
        }
    }
}
