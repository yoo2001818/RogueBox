package kr.kkiro.roguebox.game.item;

public abstract class ItemType {

  protected String name;
  
  public ItemType(String name) {
    this.name = name;
  }
  
  public boolean isWasteable() {
    return true;
  }
  
  public String getName() {
    return name;
  }
  
  public abstract void get(ItemStack stack);
  
}
