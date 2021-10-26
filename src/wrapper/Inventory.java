package wrapper;

import Cube.Block;
import Cube.BlockType;
import gui.GuiObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class Inventory {
    Item[] inventorySlots = new Item[40];

    static Vector2f[] hotbarLocations = new Vector2f[]{
            new Vector2f(-0.444f, -0.77777777777f),
            new Vector2f(-0.333f, -0.77777777777f),
            new Vector2f(-0.222f, -0.77777777777f),
            new Vector2f(-0.111f, -0.77777777777f),
            new Vector2f(0, -0.77777777777f),
            new Vector2f(0.111f, -0.77777777777f),
            new Vector2f(0.222f, -0.77777777777f),
            new Vector2f(0.333f, -0.77777777777f),
            new Vector2f(0.444f, -0.77777777777f)
    };

    static Vector2f[] clickLocations = new Vector2f[] {
            new Vector2f(-0.35614306f, -0.4274084f),
            new Vector2f(-0.2768274f, -0.4274084f),
            new Vector2f(-0.19751167f, -0.4274084f),
            new Vector2f(-0.11819595f, -0.4274084f),
            new Vector2f(-0.03888023f, -0.4274084f),
            new Vector2f(0.03888023f, -0.4274084f),
            new Vector2f(0.11819601f, -0.4274084f),
            new Vector2f(0.19595647f, -0.4274084f),
            new Vector2f(0.27527213f, -0.4274084f),

            new Vector2f(-0.35614306f, -0.25915873f),
            new Vector2f(-0.2768274f, -0.25915873f),
            new Vector2f(-0.19751167f, -0.25915873f),
            new Vector2f(-0.11819595f, -0.25915873f),
            new Vector2f(-0.03888023f, -0.25915873f),
            new Vector2f(0.03888023f, -0.25915873f),
            new Vector2f(0.11819601f, -0.25915873f),
            new Vector2f(0.19595647f, -0.25915873f),
            new Vector2f(0.27527213f, -0.25915873f),


            new Vector2f(-0.35614306f, -0.112618744f),
            new Vector2f(-0.2768274f, -0.112618744f),
            new Vector2f(-0.19751167f, -0.112618744f),
            new Vector2f(-0.11819595f, -0.112618744f),
            new Vector2f(-0.03888023f, -0.112618744f),
            new Vector2f(0.03888023f, -0.112618744f),
            new Vector2f(0.11819601f, -0.112618744f),
            new Vector2f(0.19595647f, -0.112618744f),
            new Vector2f(0.27527213f, -0.112618744f),


            new Vector2f(-0.35614306f, 0.02306652f),
            new Vector2f(-0.2768274f, 0.02306652f),
            new Vector2f(-0.19751167f, 0.02306652f),
            new Vector2f(-0.11819595f, 0.02306652f),
            new Vector2f(-0.03888023f, 0.02306652f),
            new Vector2f(0.03888023f, 0.02306652f),
            new Vector2f(0.11819601f, 0.02306652f),
            new Vector2f(0.19595647f, 0.02306652f),
            new Vector2f(0.27527213f, 0.02306652f),
    };

    static Vector2f blocksize = new Vector2f(46, 46);

    static {
        blocksize.x /= 1286;
        blocksize.y /= 737;
    }

    int currentItem = -1;
    int currentHotbarItem = 0;

    public Inventory() {
        for(int i=0;i<40;i++) {
            inventorySlots[i] = new Item(null, 0);
        }
    }

    public BlockType getCurrentItemID() {
        return inventorySlots[currentItem].type;
    }

    public int getCurrentItemQuantity() {
        return inventorySlots[currentItem].quantity;
    }

    public void hotbarInput() {
        if(Input.getKeyDown(Keyboard.KEY_1)) currentHotbarItem=0;
        if(Input.getKeyDown(Keyboard.KEY_2)) currentHotbarItem=1;
        if(Input.getKeyDown(Keyboard.KEY_3)) currentHotbarItem=2;
        if(Input.getKeyDown(Keyboard.KEY_4)) currentHotbarItem=3;
        if(Input.getKeyDown(Keyboard.KEY_5)) currentHotbarItem=4;
        if(Input.getKeyDown(Keyboard.KEY_6)) currentHotbarItem=5;
        if(Input.getKeyDown(Keyboard.KEY_7)) currentHotbarItem=6;
        if(Input.getKeyDown(Keyboard.KEY_8)) currentHotbarItem=7;
        if(Input.getKeyDown(Keyboard.KEY_9)) currentHotbarItem=8;
    }

    public void input() {
        int clickIndex = -1;
        if(Input.getMouseButtonDown(0)) {
            Vector2f mousepos = Input.getMousePosition();
            mousepos.x /= Display.getWidth();
            mousepos.y /= Display.getHeight();
            mousepos.x *= 2;
            mousepos.y *= 2;
            mousepos.x -= 1;
            mousepos.y -= 1;
            //System.out.print("new Vector2f(" + mousepos.x + "f, " + mousepos.y + "f),");
            for(int i=0;i<clickLocations.length;i++) {
                if(mousepos.x > clickLocations[i].x &&
                        mousepos.y > clickLocations[i].y - 2*blocksize.y &&
                        mousepos.x < clickLocations[i].x + 2*blocksize.x &&
                        mousepos.y < clickLocations[i].y) {
                    clickIndex = i;
                    break;
                }
            }
            if(clickIndex == -1) return;
            if(currentItem == -1) {
                if(!inventorySlots[clickIndex].empty())
                    currentItem = clickIndex;
            } else {
                if(inventorySlots[clickIndex].empty()) {
                    move(currentItem, clickIndex);
                    currentItem = -1;
                } else if(clickIndex == currentItem) {
                    currentItem = -1;
                }
            }
        }
    }

    private void move(int currentItem, int clickIndex) {
        Item i = inventorySlots[currentItem];
        inventorySlots[currentItem] = inventorySlots[clickIndex];
        inventorySlots[clickIndex] = i;
    }

    public void addItem(BlockType type) {
        for(int i=0;i<inventorySlots.length;i++) {
            if(inventorySlots[i].type == type) {
                inventorySlots[i].quantity++;
                return;
            }
        }
        for(int i=0;i<inventorySlots.length;i++) {
            if(inventorySlots[i].empty()) {
                inventorySlots[i].type = type;
                inventorySlots[i].quantity = 1;
                return;
            }
        }
    }

    public void remove1SelectedHotbarItem() {
        inventorySlots[currentHotbarItem].quantity--;
        if(inventorySlots[currentHotbarItem].quantity==0) {
            inventorySlots[currentHotbarItem].type = null;
        }
    }

    public void renderPrepare(Rendering renderer) {
        for(int i=0;i<inventorySlots.length;i++) {
            if(!inventorySlots[i].empty()) {
                if(i==currentItem) {
                    renderer.getRenderer().addGUI(new GuiObject(inventorySlots[i].type.getTex(), new Vector2f( Input.getMousePosition().x / Display.getWidth() * 2 - 1, Input.getMousePosition().y / Display.getHeight() * 2 - 1), (Vector2f) new Vector2f(blocksize).scale(0.75f)));
                } else {
                    renderer.getRenderer().addGUI(new GuiObject(inventorySlots[i].type.getTex(), new Vector2f(clickLocations[i].x + blocksize.x, clickLocations[i].y - blocksize.y), (Vector2f) new Vector2f(blocksize).scale(0.75f)));
                }
            }
        }
    }

    public BlockType getCurrentHotbarType() {
        return inventorySlots[currentHotbarItem].type;
    }

    public void renderHotbarPrepare(Rendering renderer, GuiObject hotbarSelectedGUI) {
        hotbarSelectedGUI.getPosition().setX(0.111f * (currentHotbarItem-4) - blocksize.x/2 * 0.75f);
        hotbarSelectedGUI.getPosition().setY(-0.9f + blocksize.y);
        for(int i=0;i<9;i++) {
            if(!inventorySlots[i].empty()) {
                renderer.getRenderer().addGUI(new GuiObject(inventorySlots[i].type.getTex(), new Vector2f(hotbarLocations[i].x, hotbarLocations[i].y - blocksize.y), (Vector2f) new Vector2f(blocksize).scale(0.75f)));
            }
        }
    }
}

class Item {
    BlockType type;
    int quantity;

    public Item(BlockType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public boolean empty() {
        return type == null;
    }
}
