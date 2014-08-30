package kr.kkiro.roguebox.game.item;

public abstract class ItemEntryEquippable extends ItemEntry {
  
  protected EquipType equipType;
  
  public ItemEntryEquippable(String name, ItemType type, EquipType equipType) {
    super(name, type);
  }
  
  public boolean isEquipable() {
    return true;
  }
  
  public abstract String equip(ItemStack stack);
  public abstract String unEquip(ItemStack stack);
  
  public EquipType getEquipType() {
    return equipType;
  }
}
