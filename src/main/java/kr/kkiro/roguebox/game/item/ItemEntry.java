package kr.kkiro.roguebox.game.item;

public abstract class ItemEntry {

  protected String name;
  protected ItemType type;
  
  public ItemEntry(String name, ItemType type) {
    this.name = name;
    this.type = type;
  }
  
  public boolean isDumpable() {
    return true;
  }
  
  public String getName() {
    return name;
  }
  
  public String getDesc() {
    return null;
  }
  
  public ItemType getType() {
    return type;
  }
  
  public abstract void obtain(ItemStack stack);
  
}
