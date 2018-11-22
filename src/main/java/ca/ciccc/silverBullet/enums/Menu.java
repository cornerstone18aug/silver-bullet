package ca.ciccc.silverBullet.enums;

import ca.ciccc.silverBullet.components.menus.MenuItem;
import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.ModalUtil;
import java.util.Arrays;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public enum Menu {
  START {
    @Override
    public EventHandler<? super MouseEvent> event() {
      return mouseEvent -> {
        System.out.println("START");
      };
    }
  },
  HOW_TO_PLAY {
    @Override
    public EventHandler<? super MouseEvent> event() {
      return mouseEvent -> {
        System.out.println("SETTINGS");
      };
    }
  },
  QUIT {
    @Override
    public EventHandler<? super MouseEvent> event() {
      return mouseEvent -> ModalUtil.confirm(this.getName(),
          "Did you really want to quit?",
          e -> System.exit(1));
    }
  };

  public String getName() {
    String[] names = this.name().toLowerCase().split("_");
    String name = names[0];
    if (names.length > 2) {
      for (int i=1; i< names.length; i++) {
        name += names[i].substring(0, 1).toUpperCase() + names[i].substring(1);
      }
    }

    return ConstUtil.getRbString("menu." + name);
  }

  abstract public EventHandler<? super MouseEvent> event();

  public static MenuItem[] createMenuItems() {
    return Arrays.stream(Menu.values()).map(MenuItem::new).toArray(MenuItem[]::new);
  }

}
