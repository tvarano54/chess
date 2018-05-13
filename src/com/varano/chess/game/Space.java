//Thomas Varano
//May 11, 2018

package com.varano.chess.game;

public class Space {
   private byte row;
   private char col;
   private boolean isBlack, occupied;
   
   /**
    * a space in a chessBoard. simply holds a row and column
    * @param col
    * @param row
    */
   public Space(char col, byte row) {
      this.col = col; this.row = row;
      isBlack = (getColumn() + row) % 2 == 0;
   }
   
   public byte getColumn() {
      return (byte) (col - 'a' + 1);
   }
   public String toString() {
      return col + row + "";
   }
   public void setOccupation(boolean occupied) {
      this.occupied = occupied;
   }
   public boolean isOccupied() {
      return occupied;
   }
   public boolean isBlack() {
      return isBlack;
   }
   public byte getRow() {
      return row;
   }
}