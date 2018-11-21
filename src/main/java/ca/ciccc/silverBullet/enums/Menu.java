package ca.ciccc.silverBullet.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Menu {
  START(Collections.emptyList()),
  SETTINGS(null),
  QUIT(Collections.emptyList());

  static {
    SETTINGS.subMenus = new ArrayList<>();
    SETTINGS.subMenus.add(SubMenu.BOARD_SIZE);
  }

  List<SubMenu> subMenus;

  Menu(List<SubMenu> subMenus) {
    this.subMenus = subMenus;
  }

}
