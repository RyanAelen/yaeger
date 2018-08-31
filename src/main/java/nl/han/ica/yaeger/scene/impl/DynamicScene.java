package nl.han.ica.yaeger.scene.impl;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import nl.han.ica.yaeger.entities.Entity;
import nl.han.ica.yaeger.entities.EntityCollection;
import nl.han.ica.yaeger.entities.spawners.EntitySpawner;
import nl.han.ica.yaeger.exceptions.YaegerLifecycleException;

import java.util.HashSet;
import java.util.Set;

/**
 * Een {@code DynamicScene} is een {@link StaticScene} met een eigen {@code GameLoop}. Deze {@code GameLoop}
 * roept ieder frame (waarbij wordt uitgegaan van 60fps) op alle geregistreerde {@link nl.han.ica.yaeger.entities.Entity}s
 * die de interface {@link nl.han.ica.yaeger.entities.interfaces.Updatable} implementeren, de {@code update()} methode aan.
 */
public abstract class DynamicScene extends StaticScene {

    private EntityCollection entityCollection;

    private Set<Entity> initialEntities = new HashSet<>();
    private AnimationTimer animator;

    private boolean gameLoopIsRunning = false;

    @Override
    public void setupScene() {
        super.setupScene();

        setupEntities();

        createGameLoop();

        setupSpawners();

        startGameLoop();
    }

    protected abstract void setupSpawners();

    /**
     * Voeg {@link Entity}s toe, die initiëel al deel uitmaken van een {@link nl.han.ica.yaeger.scene.YaegerScene}. Hierbij kun je
     * denken aan elementen van een {@code Dashboard} of een Speler-{@link Entity}. Elementen van het scherm die tijdens het spel
     * moeten worden toegevoegd, moeten gebruik maken van een {@link EntitySpawner}.
     */
    protected abstract void setupEntities();

    /**
     * Voeg een {@link Entity} toe aan de {@code Scene}. Iedere {@link Entity} kunnen maar één keer worden toegevoegd.
     * Deze methode kan enkel gebruikt worden voor een {@link Entity} die bij initialisatie aan het spel moeten worden
     * toegevoegd. Indien er tijdens het spel een extra {@link Entity} moet worden toegevoegd, gebruik dan een
     * {@link EntitySpawner}.
     *
     * @param entity Het {@link Entity} dat moet worden toegevoegd.
     */
    @Override
    protected void addEntity(Entity entity) {
        if (gameLoopIsRunning) {
            throw new YaegerLifecycleException("De GameLoop is al bezig. Het is niet langer toegestaan een Entity toe " +
                    "te voegen. Gebruik hiervoor een EntitySpawner");
        }
        initialEntities.add(entity);
    }

    @Override
    public void onInputChanged(Set<KeyCode> input) {
        entityCollection.notifyGameObjectsOfPressedKeys(input);
    }

    /**
     * Registreer een {@link EntitySpawner}. Na registratie zal de {@link EntitySpawner} verantwoordelijk worden voor
     * het aanmaken (spawnen) van nieuwe {@link Entity}s.
     *
     * @param spawner De {@link EntitySpawner} die geregistreerd moet worden.
     */
    protected void registerSpawner(EntitySpawner spawner) {
        entityCollection.registerSpawner(spawner);
    }

    @Override
    public void tearDownScene() {
        stopGameLoop();
        clearInitialEntities();
        clearEntityCollection();

        super.tearDownScene();
    }

    private void clearInitialEntities() {
        initialEntities.clear();
    }

    private void clearEntityCollection() {
        entityCollection.clear();
        entityCollection = null;
    }

    private void startGameLoop() {
        gameLoopIsRunning = true;
        animator.start();
    }

    private void stopGameLoop() {
        animator.stop();
        animator = null;
        gameLoopIsRunning = false;
    }

    private void createGameLoop() {
        entityCollection = new EntityCollection(getRoot(), initialEntities);

        animator = new AnimationTimer() {
            @Override
            public void handle(long arg0) {

                entityCollection.update();
            }
        };
    }
}
