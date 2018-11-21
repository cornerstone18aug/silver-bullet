package ca.ciccc.silverBullet.enums;

import ca.ciccc.silverBullet.components.menus.MenuItem;
import ca.ciccc.silverBullet.utils.ConstUtil;
import java.util.ArrayList;
import java.util.Arrays;
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

  public String get() {
    return ConstUtil.getRbString(this.name().toLowerCase());
  }

  public static MenuItem[] createMenuItems() {
    return Arrays.stream(Menu.values()).map(menu ->
       new MenuItem(menu.get())
    ).toArray(MenuItem[]::new);
  }

}
