package kr.kkiro.roguebox.game.item;

import static kr.kkiro.roguebox.util.I18n._;

import java.util.Map.Entry;

import kr.kkiro.roguebox.curses.CComponent;
import kr.kkiro.roguebox.curses.CInteractable;
import kr.kkiro.roguebox.curses.CLabel;
import kr.kkiro.roguebox.curses.CSimpleButton;
import kr.kkiro.roguebox.curses.CSingleList;
import kr.kkiro.roguebox.curses.CWindow;
import kr.kkiro.roguebox.curses.IActionListener;
import kr.kkiro.roguebox.curses.ListItem;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.game.entity.Character;

public class InventoryDisplayer extends CWindow implements IActionListener {

  protected Character character;
  protected IExitListener exitListener;
 
  private int previousHash;
  private CSingleList inventoryList = new CSingleList();
  private CLabel label = new CLabel(_("inventoryMsg"));
  private CSimpleButton closeButton = new CSimpleButton(_("close"));
  private CSimpleButton dumpButton = new CSimpleButton(_("dump"));
  private CSimpleButton useButton = new CSimpleButton("");
  
  public InventoryDisplayer(int width, int height, Character character) {
    super(width, height, _("inventory"));
    this.character = character;
    this.add(label);
    this.add(inventoryList);
    this.add(closeButton);
    this.add(useButton);
    this.add(dumpButton);
    inventoryList.setListener(this);
    closeButton.setListener(this);
    useButton.setListener(this);
    dumpButton.setListener(this);
  }
  
  public Character getCharacter() {
    return character;
  }
  
  public void setCharacter(Character character) {
    this.character = character;
  }
  
  public IExitListener getExitListener() {
    return exitListener;
  }
  
  public void setExitListener(IExitListener exitListener) {
    this.exitListener = exitListener;
  }
  
  @Override
  public void render(TextGraphics g) {
    if((character.getInventory().getContents().hashCode() + character.getEquipList().hashCode()) != previousHash) {
      // Regenerate the whole list.
      inventoryList.list.clear();
      for(Entry<EquipType, ItemEntryEquippable> item : character.getEquipList().entrySet()) {
        if(item.getValue() == null) continue;
        inventoryList.list.add(new EquipListItem(item.getValue()));
      }
      for(ItemStack item : character.getInventory()) {
        inventoryList.list.add(new InventoryListItem(item));
      }
      previousHash = (character.getInventory().getContents().hashCode() + character.getEquipList().hashCode());
    }
    if(inventoryList.list.size() >= 1)
      inventoryList.chosenEntry = inventoryList.list.get(inventoryList.currentIndex);
    label.x = 1;
    label.y = 0;
    inventoryList.x = 1;
    inventoryList.y = 2;
    inventoryList.setWidth(this.getWidth(g)-4);
    inventoryList.setHeight(this.getHeight(g)-5-1);
    closeButton.x = 1;
    closeButton.y = this.getHeight(g)-3;
    dumpButton.x = this.getWidth(g)-3-dumpButton.getWidth(g);
    dumpButton.y = this.getHeight(g)-3;
    ListItem selectedItem = inventoryList.chosenEntry;
    if(selectedItem == null) {
      useButton.text = _("use");
      useButton.setEnabled(false);
      dumpButton.setEnabled(false);
    } else {
      if(selectedItem instanceof EquipListItem) {
        useButton.text = _("unequip");
        // Should be enabled even if it's not equippable
        useButton.setEnabled(true);
        // Should be disabled, you have to unequip that before dumping it
        dumpButton.setEnabled(false);
      }
      if(selectedItem instanceof InventoryListItem) {
        InventoryListItem eItem = (InventoryListItem) selectedItem;
        dumpButton.setEnabled(eItem.getStack().getItem().isDumpable());
        useButton.text = _("use");
        useButton.setEnabled(false);
        if(eItem.getStack().isEquipable()) {
          useButton.text = _("equip");
          useButton.setEnabled(true);
        }
        if(eItem.getStack().isUseable()) {
          useButton.setEnabled(true);
        }
      }
    }
    useButton.x = dumpButton.x-1-useButton.getWidth(g);
    useButton.y = dumpButton.y;
    super.render(g);
  }
  
  public static class InventoryListItem implements ListItem {
   
    protected ItemStack stack;
    
    public InventoryListItem(ItemStack stack) {
      this.stack = stack;
    }
    
    public ItemStack getStack() {
      return stack;
    }
    
    @Override
    public String getLabel() {
      String desc = "";
      if(stack.getItem().getDesc() != null) {
        desc = " - "+stack.getItem().getDesc();
      }
      return stack.getItem().getName() + " x " + stack.getQuantity() + desc;
    }

    @Override
    public void action() {
    }
    
  }
  
  public static class EquipListItem implements ListItem {
    
    protected ItemEntryEquippable entry;
    
    public EquipListItem(ItemEntryEquippable entry) {
      this.entry = entry;
    }
    
    public ItemEntryEquippable getEntry() {
      return entry;
    }
    
    @Override
    public String getLabel() {
      String desc = "";
      if(entry.getDesc() != null) {
        desc = " - "+entry.getDesc();
      }
      return entry.getName() + " x 1 (" + _("equipped") + ")"+desc;
    }
    
    @Override
    public void action() {
    }
 
  }

  @Override
  public boolean keyTyped(int code, CComponent object) {
    if(object == closeButton) {
      if(code == '\n') {
        this.enabled = false;
        this.visible = false;
        if(exitListener != null) exitListener.windowExit(this);
        return true;
      }
    }
    if(object == dumpButton) {
      if(code == '\n') {
        ListItem item = inventoryList.chosenEntry;
        if(item instanceof InventoryListItem) {
          InventoryListItem litem = (InventoryListItem) item;
          if(litem.getStack().getItem().isDumpable() && litem.getStack().getQuantity() >= 1) {
            litem.getStack().getInventory().removeItem(litem.getStack().getCode(), 1);
            label.text = _("dumped", litem.getStack().getItem().getName());
          }
        }
      }
    }
    if(object == useButton) {
      if(code == '\n') {
        ListItem item = inventoryList.chosenEntry;
        if(item instanceof InventoryListItem) {
          InventoryListItem litem = (InventoryListItem) item;
          if(litem.getStack().isUseable() && litem.getStack().getQuantity() >= 1) {
            label.text = litem.getStack().use();
          }
        }
      }
    }
    if(code == 0x1b) {
      this.enabled = false;
      this.visible = false;
      if(exitListener != null) exitListener.windowExit(this);
      return true; 
    }
    if(listener != null) return listener.keyTyped(code, this);
    return false;
  }
  
  public static interface IExitListener {
    public void windowExit(CInteractable c);
  }
  

}
