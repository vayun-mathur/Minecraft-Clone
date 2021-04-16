package RenderEngine;

import gui.GuiObject;
import gui.GuiShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class GuiRenderer {

    public void render(List<GuiObject> entities, GuiShader shader) {
        for (GuiObject model : entities) {

            GL30.glBindVertexArray(GuiObject.getModel().getVaoID());
            GL20.glEnableVertexAttribArray(0);

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
            shader.loadPosition(model.getPosition());
            shader.loadScale(model.getSize());

            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, GuiObject.getModel().getVertexCount());

            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);

        }

    }
}
