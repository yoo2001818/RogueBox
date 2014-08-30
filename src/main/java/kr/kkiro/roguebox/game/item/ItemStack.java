package kr.kkiro.roguebox.game.item;

public class ItemStack {

  protected int quantity;
  protected int code;
  protected Inventory inventory;
  
  public ItemStack(Inventory inventory, int code, int quantity) {
    this.quantity = quantity;
    this.code = code;
    this.inventory = inventory;
  }
  
  public Inventory getInventory() {
    return inventory;
  }
  
  public ItemBank getBank() {
    return inventory.getBank();
  }
 
  public int getCode() {
    return code;
  }
  
  public int getQuantity() {
    return quantity;
  }
  
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  public ItemEntry getItem() {
    return getBank().get(code);
  }
  
  public boolean isEquipable() {
    ItemEntry item = getItem();
    if(item instanceof ItemEntryEquippable) {
      ItemEntryEquippable equip = (ItemEntryEquippable) item;
      return equip.isEquipable();
    }
    return false;
  }
  
  public boolean isUseable() {
    ItemEntry item = getItem();
    if(item instanceof ItemEntryUseable) {
      ItemEntryUseable useable = (ItemEntryUseable) item;
      return useable.isUseable();
    }
    return false;
  }
  
  public String equip() {
    if(!isEquipable()) return "";
    ItemEntryEquippable equip = (ItemEntryEquippable) getItem();
    return equip.equip(this);
  }
  
  public String unEquip() {
    if(!isEquipable()) return "";
    ItemEntryEquippable equip = (ItemEntryEquippable) getItem();
    return equip.unEquip(this);
  }
  
  public String use() {
    if(!isUseable()) return "";
    ItemEntryUseable useable = (ItemEntryUseable) getItem();
    return useable.use(this);
  }
  
  public ItemType getType() {
    return getItem().getType();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + code;
    result = prime * result + quantity;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ItemStack other = (ItemStack) obj;
    if (code != other.code)
      return false;
    if (quantity != other.quantity)
      return false;
    return true;
  }
}
