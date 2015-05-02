package kr.kkiro.roguebox.scene;

import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.MapStructureGenerator;
import kr.kkiro.roguebox.util.MapStructureGenerator.MapPosition;


public class NewProfileScene extends Scene {

  @Override
  public void init() {
    getApp().getTerminal().clear();
    getApp().getTerminal().setCursor(0, 0);
    getApp().getTerminal().setBackColor(ANSIColor.BLACK);
    getApp().getTerminal().setTextColor(ANSIColor.GRAY);
    getApp().getTerminal().writeString("...\n");
    getApp().getTerminal().writeString("Please type any text and press RETURN.\n");
    getApp().getTerminal().setCursorVisible(true);
    String seedStr = getApp().getTerminal().readLine();
    int seed = seedStr.hashCode();
    getApp().getTerminal().writeString("The seed is "+Integer.toHexString(seed)+"\n");
    getApp().getTerminal().writeString("Generating Map... Please wait.\n");
    MapStructureGenerator mapGen = new MapStructureGenerator(16, 16, 20, 10, 4, seed);
    try {
      Thread.sleep(400);
    } catch (InterruptedException e) {
    }
    getApp().getTerminal().setCursorVisible(false);
    while(true) {
      boolean result = mapGen.step();
      getApp().getTerminal().clear();
      getApp().getTerminal().setCursor(0, 0);
      //draw map.
      for(int y = 0; y < mapGen.getHeight(); ++y) {
        for(int x = 0; x < mapGen.getWidth(); ++x) {
          getApp().getTerminal().setBackColor(ANSIColor.BLACK);
          getApp().getTerminal().setTextColor(ANSIColor.WHITE);
          for(MapPosition p : mapGen.getRoomList()) {
            if(p == null) continue;
            if(p.x == x && p.y == y) {
              getApp().getTerminal().setBackColor(ANSIColor.WHITE);
              getApp().getTerminal().setTextColor(ANSIColor.BLACK);
              break;
            }
          }
          if(mapGen.getStartRoom() != null && mapGen.getStartRoom().x == x && mapGen.getStartRoom().y == y) {
            getApp().getTerminal().setBackColor(ANSIColor.GREEN);
            getApp().getTerminal().setTextColor(ANSIColor.WHITE);
          } else if(mapGen.getEndRoom() != null && mapGen.getEndRoom().x == x && mapGen.getEndRoom().y == y) {
            getApp().getTerminal().setBackColor(ANSIColor.YELLOW);
            getApp().getTerminal().setTextColor(ANSIColor.WHITE);
          }
          getApp().getTerminal().setCursor(x, y);
          getApp().getTerminal().writeChar(mapGen.getMap()[y][x].getIcon());
        }
      }
      getApp().getTerminal().setBackColor(ANSIColor.BLACK);
      getApp().getTerminal().setTextColor(ANSIColor.WHITE);
      getApp().getTerminal().writeString("\n"+mapGen.getStack().size());
      for(MapPosition p : mapGen.getStack()) {
        getApp().getTerminal().setBackColor(ANSIColor.RED);
        getApp().getTerminal().setCursor(p.x, p.y);
        getApp().getTerminal().writeChar(mapGen.getMap()[p.y][p.x].getIcon());
      }
      if(!mapGen.getStack().isEmpty()) {
        MapPosition p2 = mapGen.getStack().peek();
        getApp().getTerminal().setBackColor(ANSIColor.BLUE);
        getApp().getTerminal().setCursor(p2.x, p2.y);
        getApp().getTerminal().writeChar(mapGen.getMap()[p2.y][p2.x].getIcon());
      }
      if(!result) break;
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
      }
    }
    getApp().getTerminal().setBackColor(ANSIColor.BLACK);
    getApp().getTerminal().setTextColor(ANSIColor.WHITE);
    getApp().getTerminal().writeString("\nPress RETURN key to continue...");
    getApp().getTerminal().setCursorVisible(true);
    getApp().getTerminal().readLine();
    init();
  }

}
