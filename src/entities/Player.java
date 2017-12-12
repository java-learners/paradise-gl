package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 120;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrain[][] terrain) {
        // find which terrain player is in
        int terrainX = (int) Math.floor(super.getPosition().x / Terrain.getSIZE());
        int terrainZ = -(int) Math.floor(super.getPosition().z / Terrain.getSIZE()) - 1;
        handleInput();
        super.rotate(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = distance * (float) Math.sin(Math.toRadians(super.getRotY()));
        float dz = distance * (float) Math.cos(Math.toRadians(super.getRotY()));
        super.increasePosition(dx, 0, dz);
        float terrainHeight = terrain[terrainX][terrainZ].getTerrainHeight(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight + 5) {
            super.getPosition().y = terrainHeight + 5;
        }
    }

    public void handleInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = RUN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            this.currentSpeed *= 3;

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }
    }
}