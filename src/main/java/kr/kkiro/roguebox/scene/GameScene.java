package kr.kkiro.roguebox.scene;

import static kr.kkiro.roguebox.util.I18n._;

import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Map.Entry;

import kr.kkiro.roguebox.curses.CBackground;
import kr.kkiro.roguebox.curses.CComponent;
import kr.kkiro.roguebox.curses.CLabel;
import kr.kkiro.roguebox.curses.RootTextGraphics;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.EntityMap;
import kr.kkiro.roguebox.game.Map;
import kr.kkiro.roguebox.game.MapRenderer;
import kr.kkiro.roguebox.game.entity.Character;
import kr.kkiro.roguebox.game.entity.Monster;
import kr.kkiro.roguebox.game.entity.Stairs;
import kr.kkiro.roguebox.game.entity.Treasure;
import kr.kkiro.roguebox.game.tile.BlankTile;
import kr.kkiro.roguebox.game.tile.RoomTile;
import kr.kkiro.roguebox.game.tile.TileBank;
import kr.kkiro.roguebox.game.tile.TunnelTile;
import kr.kkiro.roguebox.game.tile.WallTile;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;
import kr.kkiro.roguebox.util.MapGenerator;
import kr.kkiro.roguebox.util.MapStructureGenerator;
import kr.kkiro.roguebox.util.MapStructureGenerator.MapPosition;

public class GameScene extends Scene {
  
  private Map map;
  private Character character;
  private MapRenderer renderer;
  private CHelpDisplay helpdisp;
  private CLabel messdisp;
  private long cooltime;

  @Override
  public void init() {
    TileBank tileBank = new TileBank(256);
    tileBank.set(0, new BlankTile());
    tileBank.set(1, new RoomTile());
    tileBank.set(2, new WallTile());
    tileBank.set(3, new TunnelTile());
    MapStructureGenerator genstr = new MapStructureGenerator(16, 16, 20, 10, 4, System.currentTimeMillis());
    while(genstr.step());
    MapGenerator genmap = new MapGenerator(genstr, tileBank, 19, 9, 2, 1, 3, System.currentTimeMillis());
    genmap.generate();
    /* TileMap tileMap = new TileMap(32, 16, tileBank, 2);
    for(int y = 1; y < 15; ++y) {
      for(int x = 1; x < 31; ++x) {
        tileMap.get(x, y).setTileId(1);
      }
    } */
    EntityMap entityMap = new EntityMap();
    character = new Character(7, 7);
    entityMap.add(character);
    character.setX(genstr.getStartRoom().x * 19 + 9);
    character.setY(genstr.getStartRoom().y * 9 + 4);
    Stairs stairs = new Stairs(genstr.getEndRoom().x * 19 + 9, genstr.getEndRoom().y * 9 + 4);
    entityMap.add(stairs);
    Random random = new Random();
    for(MapPosition p : genstr.getRoomList()) {
      if(p.equals(genstr.getStartRoom()) || p.equals(genstr.getEndRoom())) continue;
      Monster monster = new Monster(p.x * 19 + 4, p.y * 9 + 2);
      entityMap.add(monster);
      if(random.nextInt(255) > 128) {
        Treasure treasure = new Treasure(p.x * 19 + random.nextInt(15)+2, p.y * 9 + random.nextInt(5) + 2);
        entityMap.add(treasure);
      }
    }
    map = new Map(genmap.getTileMap(), entityMap);
    character.revealMap(10);
    TextGraphics g = new RootTextGraphics(getApp().getTerminal());
    renderer = new MapRenderer(map, 80, 24);
    renderer.x = 0;
    renderer.y = 0;
    renderer.cameraX = character.getX() - renderer.getWidth(g)/2;
    renderer.cameraY = character.getY() - renderer.getHeight(g)/2;
    DefinedIcon.clear();
    CBackground background = new CBackground(_("gameName"));
    background.add(renderer);
    
    helpdisp = new CHelpDisplay();
    background.add(helpdisp);
    
    messdisp = new CLabel("Message comes here");
    messdisp.x = 1;
    messdisp.y = 22;
    background.add(messdisp);
    
    renderer.setListener(this);
    background.render(g);
    startInput(background);
  }
  
  @Override
  public boolean keyTyped(int code, CComponent object) {
    if(object == renderer && cooltime < System.currentTimeMillis()) {
      map.setMessage("");
      boolean ts;
      switch(code) {
        case -KeyEvent.VK_UP:
        case 'w':
          ts = character.translate(0, -1);
        break;
        case 's':
        case -KeyEvent.VK_DOWN:
          ts = character.translate(0, 1);
        break;
        case 'a':
        case -KeyEvent.VK_LEFT:
          ts = character.translate(-1, 0);
        break;
        case 'd':
        case -KeyEvent.VK_RIGHT:
          ts = character.translate(1, 0);
        break;
        default:
        return super.keyTyped(code, object);
      }
      if(ts) {
        map.tick();
        character.revealMap(10);
        messdisp.text = map.getMessage();
        if(!map.getMessage().isEmpty()) cooltime = System.currentTimeMillis()+300;
        renderer.cameraX = character.getX() - renderer.getWidth(null)/2;
        renderer.cameraY = character.getY() - renderer.getHeight(null)/2;
      }
    }
    return super.keyTyped(code, object);
  }
  
  public static class CHelpDisplay extends CComponent {

    @Override
    public void render(TextGraphics g) {
      int i = 0;
      for(Entry<java.lang.Character, String> c : DefinedIcon.entrySet()) {
        g.setBackColor(ANSIColor.BLACK);
        g.setTextColor(ANSIColor.GRAY);
        g.drawString(0, i, c.getKey() + " - "+ c.getValue());
        i ++;
      }
    }
    
    @Override
    public int getWidth(TextGraphics g) {
      return 256;
    }
   
    @Override
    public int getHeight(TextGraphics g) {
      return 256;
    }
  }

}
