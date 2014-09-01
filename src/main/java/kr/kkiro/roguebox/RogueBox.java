package kr.kkiro.roguebox;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.scene.LoadScene;
import kr.kkiro.roguebox.util.TermApplication;
import kr.kkiro.roguebox.util.term.ANSITerminal;
import kr.kkiro.roguebox.util.term.BufferedTerminal;
import kr.kkiro.roguebox.util.term.GUITerminal;
import kr.kkiro.roguebox.util.term.Terminal;

public class RogueBox {

  public static final String VERSION="0.1.1";
  
  public static void main(String[] args) {
    Terminal guiTerm;
    if(args.length > 0) {
      guiTerm = new ANSITerminal(System.in, System.out);
    } else {
      guiTerm = new GUITerminal(_("gameName"));
    }
    Terminal term = new BufferedTerminal(guiTerm);
    TermApplication app = new TermApplication(term);
    app.setScene(new LoadScene());
  }

}
