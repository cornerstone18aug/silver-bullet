package ca.ciccc.silverBullet.enums;

import ca.ciccc.silverBullet.components.menus.MenuItem;
import ca.ciccc.silverBullet.utils.ConstUtil;
import ca.ciccc.silverBullet.utils.ModalUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public enum Menu {
  START(Collections.emptyList()) {
    @Override
    public EventHandler<? super MouseEvent> event() {
      return mouseEvent -> {
        System.out.println("START");
      };
    }
  },
  SETTINGS(null) {
    @Override
    public EventHandler<? super MouseEvent> event() {
      return mouseEvent -> {
        System.out.println("SETTINGS");
      };
    }
  },
  QUIT(Collections.emptyList()) {
    @Override
    public EventHandler<? super MouseEvent> event() {
      return mouseEvent -> ModalUtil.confirm(this.getName(),
          "Did you really want to quit?",
          e -> System.exit(1));
    }
  };

  static {
    SETTINGS.subMenus = new ArrayList<>();
    SETTINGS.subMenus.add(SubMenu.BOARD_SIZE);
  }

  List<SubMenu> subMenus;

  Menu(List<SubMenu> subMenus) {
    this.subMenus = subMenus;
  }

  public String getName() {
    return ConstUtil.getRbString("menu." + this.name().toLowerCase());
  }

  abstract public EventHandler<? super MouseEvent> event();

  public static MenuItem[] createMenuItems() {
    return Arrays.stream(Menu.values()).map(MenuItem::new).toArray(MenuItem[]::new);
  }

}
