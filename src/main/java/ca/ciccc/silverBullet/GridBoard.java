package ca.ciccc.silverBullet;

public class GridBoard {
    GridNode[][] grid;

    public boolean tryMovePlayer(int oldGridX, int oldGridY){
        GridNode originGrid = grid[oldGridY][oldGridX];
        int targetX = 0;
        int targetY = 0;
        GridNode targetNode;
        if (originGrid.isHasPlayer()){
            switch (originGrid.playerInSpace.facingDirection){
                case NORTH:
                    targetX = oldGridX;
                    targetY = oldGridY - 1;
                    break;
                case SOUTH:
                    targetX = oldGridX;
                    targetY = oldGridY + 1;
                    break;
                case EAST:
                    targetX = oldGridX + 1;
                    targetY = oldGridY;
                    break;
                case WEST:
                    targetX = oldGridX - 1;
                    targetY = oldGridY;
                    break;
            }
            targetNode = grid[oldGridY][oldGridX];

            if (targetX < 0 || targetY < 0) {
                return false;
            } else {
                if(targetNode.isCanMoveTo()){
                    targetNode.playerInSpace = originGrid.playerInSpace;
                    originGrid.playerInSpace = null;
                    return true;
                }
            }
        }

        return false;
    }
}
