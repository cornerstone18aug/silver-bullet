package ca.ciccc.spaceInvader;

public enum Type {
  PLAYER,
  ENEMY,
  PLAYER_BULLET,
  ENEMY_BULLET;

  public static Type getBullet(Type who) {
    switch (who) {
      case PLAYER:
        return PLAYER_BULLET;
      case ENEMY:
        return ENEMY_BULLET;
      default:
        throw new IllegalStateException();
    }
  }

}
