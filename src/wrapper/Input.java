package wrapper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class Input {
    private static boolean[] keys = new boolean[Keyboard.getKeyCount()];
    private static boolean[] mouseButtons = new boolean[Mouse.getButtonCount()];

    public static void update() {
        for(int i=0;i<keys.length;i++) {
            keys[i] = getKey(i);
        }
        for(int i=0;i<mouseButtons.length;i++) {
            mouseButtons[i] = getMouseButton(i);
        }
    }

    public static boolean getKey(int keyCode) {
        return Keyboard.isKeyDown(keyCode);
    }

    public static boolean getKeyDown(int keyCode) {
        return getKey(keyCode) && !keys[keyCode];
    }

    public static boolean getKeyUp(int keyCode) {
        return !getKey(keyCode) && keys[keyCode];
    }

    public static boolean getMouseButton(int mouseButton) {
        return Mouse.isButtonDown(mouseButton);
    }

    public static boolean getMouseButtonDown(int mouseButton) {
        return getMouseButton(mouseButton) && !mouseButtons[mouseButton];
    }

    public static boolean getMouseButtonUp(int mouseButton) {
        return !getMouseButton(mouseButton) && mouseButtons[mouseButton];
    }

    public static Vector2f getMousePosition() {
        return new Vector2f(Mouse.getX(), Mouse.getY());
    }

    public static int getMouseButtons() {
        return Mouse.getButtonCount();
    }

    public static int getKeys() {
        return Keyboard.getKeyCount();
    }
}
