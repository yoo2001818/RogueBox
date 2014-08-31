package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.EquipType;
import kr.kkiro.roguebox.game.item.ItemEntryEquippable;
import kr.kkiro.roguebox.game.item.ItemStack;
import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.util.I18n;

public class SteelSwordItem extends ItemEntryEquippable {

  public SteelSwordItem() {
    super(I18n._("steelSword"), ItemType.EQUIPABLE, EquipType.PRIMARY);
  }

  @Override
  public String equip(ItemStack stack) {
    stack.getInventory().getCharacter().strength += 4;
    return I18n._("equipMsg", getName());
  }

  @Override
  public String unEquip(ItemStack stack) {
    stack.getInventory().getCharacter().strength -= 4;
    return null;
  }

  @Override
  public void obtain(ItemStack stack) {
  }

}
