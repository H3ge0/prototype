package ai;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNodes();
    }

    public void instantiateNodes(){
        node = new Node[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        int col=0;
        int row=0;

        while(col<gp.MAX_WORLD_COL && row<gp.MAX_WORLD_ROW){
            node[col][row] = new Node(col,row);

            col++;
            if(col==gp.MAX_WORLD_COL){
                col=0;
                row++;
            }
        }
    }

    public void resetNodes(){
        int col=0;
        int row=0;

        while(col<gp.MAX_WORLD_COL && row<gp.MAX_WORLD_ROW){
            node[col][row].open=false;
            node[col][row].checked=false;
            node[col][row].solid=false;

            col++;
            if(col==gp.MAX_WORLD_COL){
                col=0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached=false;
        step=0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();

        startNode=node[startCol][startRow];
        currentNode=startNode;
        goalNode=node[goalCol][goalRow];
        openList.add(currentNode);

        int col=0;
        int row=0;

        while(col<gp.MAX_WORLD_COL && row<gp.MAX_WORLD_ROW){
            //Tile
            int tileNum = gp.tileManager.map[gp.currentMap][col][row];
            if(gp.tileManager.tiles[tileNum].collision)
                node[col][row].solid=true;

            getCost(node[col][row]);

            col++;
            if(col==gp.MAX_WORLD_COL){
                col=0;
                row++;
            }
        }

        //iTile
        for(int i = 0; i<gp.interactiveTiles[gp.currentMap].length; i++){
            if(gp.interactiveTiles[gp.currentMap][i]!=null && gp.interactiveTiles[gp.currentMap][i].destructible){
                int iTileCol = gp.interactiveTiles[gp.currentMap][i].worldX/gp.TILE_SIZE;
                int iTileRow = gp.interactiveTiles[gp.currentMap][i].worldY/gp.TILE_SIZE;
                node[iTileCol][iTileRow].solid = true;
            }
        }
    }

    private void getCost(Node node) {
        //G cost
        int xDistance = Math.abs(node.col-startNode.col);
        int yDistance = Math.abs(node.row-startNode.row);
        node.gCost = xDistance + yDistance;

        //H cost
        xDistance = Math.abs(node.col-goalNode.col);
        yDistance = Math.abs(node.row-goalNode.row);
        node.hCost = xDistance + yDistance;

        //F cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(!goalReached && step<500){
            int col = currentNode.col;
            int row = currentNode.row;

            //Check the current node
            currentNode.checked=true;
            openList.remove(currentNode);

            //Open the adjacent nodes
            if(row-1>=0)
                openNode(node[col][row-1]);
            if(col-1>=0)
                openNode(node[col-1][row]);
            if(row+1<gp.MAX_WORLD_ROW)
                openNode(node[col][row+1]);
            if(col+1<gp.MAX_WORLD_COL)
                openNode(node[col+1][row]);

            //Find the best node
            int bestNodeIndex=0;
            int bestNodeFCost=999;

            for(int i=0;i<openList.size();i++){
                if(openList.get(i).fCost<bestNodeFCost){
                    bestNodeIndex=i;
                    bestNodeFCost=openList.get(i).fCost;
                }
                if(openList.get(i).fCost==bestNodeFCost){
                    if(openList.get(i).gCost<openList.get(bestNodeIndex).gCost){
                        bestNodeIndex=i;
                    }
                }
            }

            //End if no more open nodes
            if(openList.size()==0)
                break;

            currentNode=openList.get(bestNodeIndex);

            if(currentNode==goalNode){
                goalReached=true;
                trackThePath();
            }

            step++;
        }

        return goalReached;
    }

    public void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){
            node.open=true;
            node.parent=currentNode;
            openList.add(node);
        }
    }

    public void trackThePath(){
        Node current = goalNode;
        while(current!=startNode){
            pathList.add(0,current);
            current=current.parent;
        }
    }
}