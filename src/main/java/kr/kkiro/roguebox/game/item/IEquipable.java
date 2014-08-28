package kr.kkiro.roguebox.game.item;

public interface IEquipable {
  public boolean isEquipable();
  public boolean equip(ItemStack stack);
  public boolean deEquip(ItemStack stack);
}
