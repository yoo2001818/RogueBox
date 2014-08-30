package kr.kkiro.roguebox.game.item;

public abstract class ItemEntryUseable extends ItemEntry {
  
  public ItemEntryUseable(String name, ItemType type) {
    super(name, type);
  }

  public boolean isUseable() {
    return true;
  }
  
  public abstract String use(ItemStack stack);
}
