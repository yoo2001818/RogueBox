package kr.kkiro.roguebox.scene;

import static kr.kkiro.roguebox.util.I18n._;

import java.awt.event.KeyEvent;
import java.util.Map.Entry;

import kr.kkiro.roguebox.curses.CBackground;
import kr.kkiro.roguebox.curses.CComponent;
import kr.kkiro.roguebox.curses.CInteractable;
import kr.kkiro.roguebox.curses.CLabel;
import kr.kkiro.roguebox.curses.RootTextGraphics;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.EntityMap;
import kr.kkiro.roguebox.game.Map;
import kr.kkiro.roguebox.game.MapRenderer;
import kr.kkiro.roguebox.game.entity.Character;
import kr.kkiro.roguebox.game.entity.Stairs;
import kr.kkiro.roguebox.game.item.Inventory;
import kr.kkiro.roguebox.game.item.InventoryDisplayer;
import kr.kkiro.roguebox.game.item.InventoryDisplayer.IExitListener;
import kr.kkiro.roguebox.game.item.ItemBank;
import kr.kkiro.roguebox.game.item.PotionNamePool;
import kr.kkiro.roguebox.game.item.type.BreadItem;
import kr.kkiro.roguebox.game.item.type.BronzeSwordItem;
import kr.kkiro.roguebox.game.item.type.HealPotionItem;
import kr.kkiro.roguebox.game.item.type.IronArmorItem;
import kr.kkiro.roguebox.game.item.type.IronSwordItem;
import kr.kkiro.roguebox.game.item.type.PoisonPotionItem;
import kr.kkiro.roguebox.game.item.type.RatMeatItem;
import kr.kkiro.roguebox.game.item.type.ResistancePotionItem;
import kr.kkiro.roguebox.game.item.type.SpiderCorpseItem;
import kr.kkiro.roguebox.game.item.type.SteelSwordItem;
import kr.kkiro.roguebox.game.item.type.StrengthPotionItem;
import kr.kkiro.roguebox.game.item.type.TreasureItem;
import kr.kkiro.roguebox.game.item.type.WeaknessPotionItem;
import kr.kkiro.roguebox.game.item.type.WoodShieldItem;
import kr.kkiro.roguebox.game.tile.BlankTile;
import kr.kkiro.roguebox.game.tile.RoomTile;
import kr.kkiro.roguebox.game.tile.TileBank;
import kr.kkiro.roguebox.game.tile.TunnelTile;
import kr.kkiro.roguebox.game.tile.WallTile;
import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.DefinedIcon;
import kr.kkiro.roguebox.util.MapGenerator;
import kr.kkiro.roguebox.util.MapStructureGenerator;

public class GameScene extends Scene implements IExitListener {
  
  private Map map;
  private Character character;
  private MapRenderer renderer;
  private CHelpDisplay helpdisp;
  private CLabel messdisp;
  private CLabel statdisp;
  private InventoryDisplayer inventorydisp;
  private long cooltime;
  private TileBank tileBank;
  private ItemBank itemBank;
  private int floors = 3;

  @Override
  public void init() {
    tileBank = new TileBank(256);
    tileBank.set(0, new BlankTile());
    tileBank.set(1, new RoomTile());
    tileBank.set(2, new WallTile());
    tileBank.set(3, new TunnelTile());
    
    PotionNamePool.clear();
    
    itemBank = new ItemBank(256);
    itemBank.set(0, new BreadItem());
    itemBank.set(1, new HealPotionItem());
    itemBank.set(2, new BronzeSwordItem());
    itemBank.set(3, new TreasureItem());
    itemBank.set(4, new IronSwordItem());
    itemBank.set(5, new SteelSwordItem());
    itemBank.set(6, new IronArmorItem());
    itemBank.set(7, new WoodShieldItem());
    itemBank.set(8, new SpiderCorpseItem());
    itemBank.set(9, new RatMeatItem());
    itemBank.set(10, new PoisonPotionItem());
    itemBank.set(11, new ResistancePotionItem());
    itemBank.set(12, new StrengthPotionItem());
    itemBank.set(13, new WeaknessPotionItem());
    
    character = new Character(7, 7, new Inventory(itemBank));
    regenFloor();
    
    //Have some free bread to debug.
    character.getInventory().obtainItem(0, 3);
    
    TextGraphics g = new RootTextGraphics(getApp().getTerminal());
    
    inventorydisp = new InventoryDisplayer(80-4, 24-4, character);
    inventorydisp.setVisible(false);
    inventorydisp.setEnabled(false);
    inventorydisp.setExitListener(this);
    
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
    
    messdisp = new CLabel("");
    messdisp.x = 1;
    messdisp.y = 22;
    background.add(messdisp);
    
    statdisp = new CLabel("") {
      @Override
      public void render(TextGraphics g) {
        this.text = _("statusInfo", character.health, character.maxHealth, character.strength, character.defense);
        this.x = 80-1-this.getWidth(g);
        super.render(g);
      }
    };
    statdisp.x = 1;
    statdisp.y = 0;
    background.add(statdisp);
    
    background.placeCenter(inventorydisp, g);
    background.add(inventorydisp);
    
    messdisp.text = _("startSummary");
    
    renderer.setListener(this);
    inventorydisp.setListener(this);
    background.render(g);
    startInput(background);
  }
  
  public void regenFloor() {
    MapStructureGenerator genstr = new MapStructureGenerator(16, 16, 20, 10, 4, System.currentTimeMillis());
    while(genstr.step());
    MapGenerator genmap = new MapGenerator(genstr, tileBank, 19, 9, 2, 1, 3, System.currentTimeMillis());
    genmap.generate();
    
    EntityMap entityMap = new EntityMap();
    entityMap.add(character);
    character.setX(genstr.getStartRoom().x * 19 + 9);
    character.setY(genstr.getStartRoom().y * 9 + 4);
    Stairs stairs = new Stairs(genstr.getEndRoom().x * 19 + 9, genstr.getEndRoom().y * 9 + 4);
    entityMap.add(stairs);
    
    genmap.generateEntity(entityMap);
    map = new Map(genmap.getTileMap(), entityMap);
    character.revealMap(10);
  }
  
  @Override
  public boolean keyTyped(int code, CComponent object) {
    if(object == renderer && cooltime < System.currentTimeMillis()) {
      map.setMessage("", 65535);
      map.setMessagePriority(0);
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
        case 'i':
          renderer.setFocused(false);
          renderer.setEnabled(false);
          this.setFocus(inventorydisp.nextFocus());
          inventorydisp.setEnabled(true);
          inventorydisp.setVisible(true);
        return true;
        default:
        return super.keyTyped(code, object);
      }
      if(ts) {
        map.tick();
        character.revealMap(10);
        messdisp.text = map.getMessage();
        if(character.health < 0) {
          getApp().setScene(new GameClearScene(false));
        }
        if(map.cleared) {
          floors -= 1;
          if(floors <= 0) {
            //Clear!
            getApp().setScene(new GameClearScene(true));
          }
          regenFloor();
          renderer.setMap(map);
          messdisp.text = _("enterFloor", floors);
          map.setMessage(_("enterFloor", floors), 65535);
        }
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

  @Override
  public void windowExit(CInteractable c) {
    c.setFocused(false);
    renderer.setEnabled(true);
    renderer.setFocused(true);
    this.setFocus(renderer);
  }

}
