package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import game.*;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import shaders.StaticShader;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        /*StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);*/

        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("bamboo"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TexturedModel texturedModel = new TexturedModel(model, texture);
        //Entity entity = new Entity(texturedModel, new Vector3f(0,0,-18f), 0,0,0,1f);
        Light light = new Light(new Vector3f(0,15,20), new Vector3f(1,1,1));
        Camera camera = new Camera();

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();

        for(int i = 0; i < 4; i++) {
            float x = 0.0f;
            float y = 0.0f;
            float z = random.nextFloat() * -70;
            entities.add(new Entity(texturedModel, new Vector3f(x,y,z),
                    random.nextFloat() * 180f, random.nextFloat() * 180f, 0, random.nextFloat() % 1));
        }

        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()) {

            camera.move();
            /*renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);*/
            for(Entity entity: entities) {
                /*renderer.render(entity, shader);*/
                renderer.processEntity(entity);
            }
            renderer.render(light, camera);
            /*shader.stop();*/
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        /*shader.cleanUp();*/
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}