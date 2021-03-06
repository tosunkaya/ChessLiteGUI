/*
 * Class for the Bishop StackPane GUI and functionality
 * 7/3/20
 */
package gui.pieces;

import gui.ChessLite;
import gui.Game;
import gui.Piece;
import gui.Tile;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import gui.GameInfo;

/**
 *
 * @author Joseph
 */
public final class Bishop extends Piece {
    
    /*
     * Attack pattern:
     * O    O    O    O    O    O    O    X 
     * X    O    O    O    O    O    X    O 
     * O    X    O    O    O    X    O    O 
     * O    O    X    O    X    O    O    O 
     * O    O    O    B    O    O    O    O 
     * O    O    X    O    X    O    O    O 
     * O    X    O    O    O    X    O    O 
     * X    O    O    O    O    O    X    O 
     * 
     */
    
    public final String WHITE_BISHOP = "/resources/" + ChessLite.PATH + "/whitebishop.png";
    public final String BLACK_BISHOP = "/resources/" + ChessLite.PATH + "/blackbishop.png";
    
    /**
     * Constructs a Bishop
     * 
     * @param isWhite side of the piece
     * @param tile tile piece belongs to
    */
    public Bishop(boolean isWhite, Tile tile) {
        super(isWhite, tile);
        Image image;
        if(isWhite) {
            image = new Image(WHITE_BISHOP);
        } else {
            image = new Image(BLACK_BISHOP);
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(TILE_SIZE);
        imageView.setFitWidth(TILE_SIZE);
        this.getChildren().add(imageView);
    }
    
    @Override
    public void pieceAvaliableMoves() {
        Game controller = getController();
        Tile[][] tiles = controller.getTiles();
        int row = getTile().getRow();
        int col = getTile().getCol();
        ArrayList<Tile> avaliable = getAvaliable();
        
        int[][] multipliers = {{1,1},{-1,1},{1,-1},{-1,-1}};
        for(int[] multiplier : multipliers) {
            int i = 1;
            boolean canContinue = true;
            while(canContinue) {
                if(withinBounds(row+(i*multiplier[0]),col+(i*multiplier[1]))) {
                    Tile tile = tiles[row+(i*multiplier[0])][col+(i*multiplier[1])];
                    if((!tile.hasPiece())) {
                        avaliable.add((tile));
                    } else if((tile.getPiece().isWhite() != isWhite())) {
                        avaliable.add((tile));
                        canContinue = false;
                    } else {
                        canContinue = false;
                    }
                } else {
                    canContinue = false;
                }
                i++;
            }
        }
    }
    
    @Override
    public void pieceAvaliableMoves(ArrayList<Tile> whiteList) {
        Game controller = getController();
        Tile[][] tiles = controller.getTiles();
        int row = getTile().getRow();
        int col = getTile().getCol();
        ArrayList<Tile> avaliable = getAvaliable();

        int[][] multipliers = {{1,1},{-1,1},{1,-1},{-1,-1}};
        for(int[] multiplier : multipliers) {
            int i = 1;
            boolean canContinue = true;
            while(canContinue) {
                if(withinBounds(row+(i*multiplier[0]),col+(i*multiplier[1]))) {
                    Tile tile = tiles[row+(i*multiplier[0])][col+(i*multiplier[1])];
                    if((!tile.hasPiece())) {
                        if(whiteListed(whiteList, tile)) {
                            avaliable.add((tile));
                        }
                    } else if((tile.getPiece().isWhite() != isWhite())) {
                        if(whiteListed(whiteList, tile)) {
                            avaliable.add((tile));
                        }
                        canContinue = false;
                    } else {
                        canContinue = false;
                    }
                } else {
                    canContinue = false;
                }
                i++;
            }
        }
    }
    
    @Override
    public ArrayList<int[]> calcCommonPieceLocations(int[] location) {
        Game controller = getController();
        Tile[][] tiles = controller.getTiles();
        int row = location[0];
        int col = location[1];
        ArrayList<int[]> locations = new ArrayList<>();
        int[][] multipliers = {{1,1},{-1,1},{1,-1},{-1,-1}};
        for(int[] multiplier : multipliers) {
            int i = 1;
            boolean canContinue = true;
            while(canContinue) {
                if(withinBounds(row+(i*multiplier[0]),col+(i*multiplier[1]))) {
                    Tile tile = tiles[row+(i*multiplier[0])][col+(i*multiplier[1])];
                    if(tile.hasPiece()) {
                        if((tile.getPiece().isWhite() == isWhite()) 
                                && tile.getPiece().isBishop() && tile.getPiece() != this) {
                            int[] loc = {row+(i*multiplier[0]), col+(i*multiplier[1])};
                            locations.add(loc);
                        }
                        canContinue = false;
                    }
                } else {
                    canContinue = false;
                }
                i++;
            }
        }
        return locations;
    }
    
    @Override
    public boolean isBishop() {
        return true;
    }
    
    @Override
    public String getNotation() {
        return "B";
    }
    
    @Override
    public int getValue() {
        return 3;
    }
    
    @Override
    public byte getInfoCode() {
        return isWhite() ? GameInfo.WHITE_BISHOP : GameInfo.BLACK_BISHOP;
    }

}
