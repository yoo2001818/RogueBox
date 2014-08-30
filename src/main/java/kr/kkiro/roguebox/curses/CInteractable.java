package kr.kkiro.roguebox.curses;

public abstract class CInteractable extends CComponent {
  
  protected boolean focused = false;
  protected boolean enabled = true;
  protected IActionListener listener;
  
  public void setListener(IActionListener listener) {
    this.listener = listener;
  }
  
  public IActionListener getListener() {
    return listener;
  }
  
  public boolean receiveKeyTyped(int code) {
    if(listener != null) return listener.keyTyped(code, this);
    return false;
  }
  
  public boolean isFocused() {
    return focused;
  }
 
  public void setFocused(boolean focused) {
    this.focused = focused;
  }
  
  public UsageEntry[] getUsage() {
    return new UsageEntry[0];
  }
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
