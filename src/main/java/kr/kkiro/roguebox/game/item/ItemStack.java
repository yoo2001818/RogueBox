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
  
  public ItemType getItem() {
    return getBank().get(code);
  }
  
  

}
