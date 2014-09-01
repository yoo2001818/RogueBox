package kr.kkiro.roguebox.game.item.type;

import kr.kkiro.roguebox.game.item.ItemType;
import kr.kkiro.roguebox.game.item.PotionItem;
import kr.kkiro.roguebox.game.item.PotionNamePool;
import kr.kkiro.roguebox.game.status.ResistanceEffect;
import kr.kkiro.roguebox.game.status.StatusEffect;

public class ResistancePotionItem extends PotionItem {

  public ResistancePotionItem() {
    super(PotionNamePool.get(), ItemType.POTION);
  }

  @Override
  public StatusEffect getEffect() {
    return new ResistanceEffect(15);
  }

}
