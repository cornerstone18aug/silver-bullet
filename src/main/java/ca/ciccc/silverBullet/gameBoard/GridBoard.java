
//        playerToAdd.getPlayerNode().setTranslateX(targetNode.getScreenX() + 30);
//        playerToAdd.getPlayerNode().setTranslateY(targetNode.getScreenY() + 30);
//        return playerToAdd;
//      }
//    }
   public Player addPlayer(int gridX, int gridY, int playerNumber) {
     GridNode targetNode = grid[gridY][gridX];
     if (targetNode.hasPlayer()) {

      return null;
    }

    Player playerToAdd =
        new Player(true, playerNumber, gridX, gridY, Directions.SOUTH);
    players.add(playerToAdd);
    targetNode.setPlayerInSpace(playerToAdd);

    playerToAdd.getPlayerNode().setTranslateX(targetNode.getScreenX() + GridBoardVariables.SPACE_TARGET_NODE.get());
    playerToAdd.getPlayerNode().setTranslateY(targetNode.getScreenY() + GridBoardVariables.SPACE_TARGET_NODE.get());

    return playerToAdd;

  }

  public Move tryShoot(Player playerShooting) {
    if (!playerShooting.isHasShot()) {
      return null;
    }

    GridNode playerStartingNode = grid[playerShooting.getGridPositionY()][playerShooting
        .getGridPositionX()];
    List<GridNode> nodesAffected = new ArrayList<>();
    GridNode currentTargetNode;
    int gridIterator = 1;

    System.out.println(playerShooting.getFacingDirection().name());

    switch (playerShooting.getFacingDirection()) {
      case NORTH:
        while (true) {
          if (playerShooting.getGridPositionY() - gridIterator >= 0) {

            currentTargetNode = grid[playerStartingNode.getGridY()
                - gridIterator][playerStartingNode.getGridX()];
            if (currentTargetNode.isCanMoveTo()) {
              nodesAffected.add(currentTargetNode);
            } else {
              break;
            }
            gridIterator++;
          } else {
            break;
          }
        }
        break;
      case SOUTH:
        while (playerShooting.getGridPositionY() + gridIterator <= gridSizeY) {

          currentTargetNode = grid[playerStartingNode.getGridY()
              + gridIterator][playerStartingNode.getGridX()];
          if (currentTargetNode.isCanMoveTo()) {
            nodesAffected.add(currentTargetNode);
          } else {
            break;
          }
          gridIterator++;
        }
        break;
      case EAST:
        while (playerShooting.getGridPositionX() + gridIterator <= gridSizeX) {

          currentTargetNode = grid[playerStartingNode.getGridY()][playerStartingNode.getGridX()
              + gridIterator];
          if (currentTargetNode.isCanMoveTo()) {
            nodesAffected.add(currentTargetNode);
          } else {
            break;
          }
          gridIterator++;
        }
        break;
      case WEST:
        while (playerShooting.getGridPositionX() - gridIterator >= 0) {

          currentTargetNode = grid[playerStartingNode.getGridY()][playerStartingNode.getGridX()
              - gridIterator];
          if (currentTargetNode.isCanMoveTo()) {
            nodesAffected.add(currentTargetNode);
          } else {
            break;
          }
          gridIterator++;
        }
    }
    //playerShooting.hasShot = false;

    Move resultMove = null;
    if (!nodesAffected.isEmpty()) {
      GridNode gn = nodesAffected.get(nodesAffected.size() - 1);
      resultMove = new Move(gn.getGridX(), gn.getGridY());
    }

    // Get the current Amo
    playerShooting.getCurrentAmo();

    return resultMove;
  }

  public void removePlayer(Player playerToRemove) {
    GameScene.instance.getChildren().remove(playerToRemove.getPlayerNode());
  }

  public void shootBullet(Player player) {
    Move finalLocation = tryShoot(player);
    if (finalLocation == null) {
      return;
    }

    gridBoard.getChildren().add(new Bullet(
        new Move(player.getGridPositionX(), player.getGridPositionY()),
        finalLocation,
        player
    ));
  }

  public GridNode getNodeFromGrid(int x, int y) {
    return grid[y][x];
  }

  public boolean areAllFull() {
    return players.stream().allMatch(Player::isActionsFull);
  }
}


            grid[playerToMove.getGridPositionY()][playerToMove.getGridPositionX()].setPlayerInSpace(null);
            targetNode.setPlayerInSpace(playerToMove);
     for (int i = 0; i < sizeY; i++) {
       for (int j = 0; j < sizeX; j++) {
         GridNode nodeToAdd = GridElement.createGridNode(imageToPrint[i][j], j, i);
         gridBoard.add(nodeToAdd.getImage(), j, i);
         grid[i][j] = nodeToAdd;
         nodeToAdd.setGridX(j);
         nodeToAdd.setGridY(i);
       }
     }
     //gridBoard.setTranslateX(50);
     //gridBoard.setTranslateY(50);
     gridBoard.setTranslateX(182);
    gridBoard.setTranslateY(50);
//        playerToAdd.getPlayerNode().setTranslateX(targetNode.getScreenX() + 30);
//        playerToAdd.getPlayerNode().setTranslateY(targetNode.getScreenY() + 30);
//        return playerToAdd;
//      }
//    }
   public Player addPlayer(int gridX, int gridY, int playerNumber) {
     GridNode targetNode = grid[gridY][gridX];
     if (targetNode.hasPlayer()) {

      return null;
    }

    Player playerToAdd =
        new Player(true, playerNumber, gridX, gridY, Directions.SOUTH);
    players.add(playerToAdd);
    targetNode.setPlayerInSpace(playerToAdd);

    playerToAdd.getPlayerNode().setTranslateX(targetNode.getScreenX() + GridBoardVariables.SPACE_TARGET_NODE.get());
    playerToAdd.getPlayerNode().setTranslateY(targetNode.getScreenY() + GridBoardVariables.SPACE_TARGET_NODE.get());

    return playerToAdd;

  }

  public Move tryShoot(Player playerShooting) {
    if (!playerShooting.isHasShot()) {
      return null;
    }

    GridNode playerStartingNode = grid[playerShooting.getGridPositionY()][playerShooting
        .getGridPositionX()];
    List<GridNode> nodesAffected = new ArrayList<>();
    GridNode currentTargetNode;
    int gridIterator = 1;

    System.out.println(playerShooting.getFacingDirection().name());

    switch (playerShooting.getFacingDirection()) {
      case NORTH:
        while (true) {
          if (playerShooting.getGridPositionY() - gridIterator >= 0) {

            currentTargetNode = grid[playerStartingNode.getGridY()
                - gridIterator][playerStartingNode.getGridX()];
            if (currentTargetNode.isCanMoveTo()) {
              nodesAffected.add(currentTargetNode);
            } else {
              break;
            }
            gridIterator++;
          } else {
            break;
          }
        }
        break;
      case SOUTH:
        while (playerShooting.getGridPositionY() + gridIterator <= gridSizeY) {

          currentTargetNode = grid[playerStartingNode.getGridY()
              + gridIterator][playerStartingNode.getGridX()];
          if (currentTargetNode.isCanMoveTo()) {
            nodesAffected.add(currentTargetNode);
          } else {
            break;
          }
          gridIterator++;
        }
        break;
      case EAST:
        while (playerShooting.getGridPositionX() + gridIterator <= gridSizeX) {

          currentTargetNode = grid[playerStartingNode.getGridY()][playerStartingNode.getGridX()
              + gridIterator];
          if (currentTargetNode.isCanMoveTo()) {
            nodesAffected.add(currentTargetNode);
          } else {
            break;
          }
          gridIterator++;
        }
        break;
      case WEST:
        while (playerShooting.getGridPositionX() - gridIterator >= 0) {

          currentTargetNode = grid[playerStartingNode.getGridY()][playerStartingNode.getGridX()
              - gridIterator];
          if (currentTargetNode.isCanMoveTo()) {
            nodesAffected.add(currentTargetNode);
          } else {
            break;
          }
          gridIterator++;
        }
    }
    //playerShooting.hasShot = false;

    Move resultMove = null;
    if (!nodesAffected.isEmpty()) {
      GridNode gn = nodesAffected.get(nodesAffected.size() - 1);
      resultMove = new Move(gn.getGridX(), gn.getGridY());
    }

    // Get the current Amo
    playerShooting.getCurrentAmo();

    return resultMove;
  }

  public void removePlayer(Player playerToRemove) {
    GameScene.instance.getChildren().remove(playerToRemove.getPlayerNode());
  }

  public void shootBullet(Player player) {
    Move finalLocation = tryShoot(player);
    if (finalLocation == null) {
      return;
    }

    gridBoard.getChildren().add(new Bullet(
        new Move(player.getGridPositionX(), player.getGridPositionY()),
        finalLocation,
        player
    ));
  }

  public GridNode getNodeFromGrid(int x, int y) {
    return grid[y][x];
  }

  public boolean areAllFull() {
    return players.stream().allMatch(Player::isActionsFull);
  }
}

