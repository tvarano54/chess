//Thomas Varano
//May 11, 2018

package com.varano.chess.game.pieces;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;

import com.varano.chess.game.Board;
import com.varano.chess.game.Game;
import com.varano.chess.game.Move;
import com.varano.chess.game.Space;
import com.varano.chess.information.logging.Logger;

public abstract class Piece {
   private byte id;
   protected boolean white, alive;
   protected Space location, start;
   protected Game parent;
   protected BufferedImage skin;
   protected static final Logger log = Logger.getLogger(Piece.class.getName());
   
   public Piece(byte id, boolean isWhite, Space location, Game parent) {
      log.setThreshold(Level.FINE);
      this.id = id; setWhite(isWhite); setLocation(location); this.parent = parent;
      alive = true;
      start = location;
      location.setOccupation(true);
   }
   
   public Piece(byte id, boolean isWhite) {
      this (id, isWhite, null, null);
   }
   
   public Piece() {
      this((byte) 0, false);
   }
   
   public boolean moveLegal(Move m) {
      log.config("checking "+m);
      if (!(m.getEnd().isDiagonal(location) || m.getEnd().isHorizontal(location) || m.getEnd().isVertical(location)))
         return false;
      if (isBlocked(m)) return false;
      log.config("checking 2 "+m);
      return true;
   }
   
   public boolean isBlocked(Move m) {
      ArrayList<Space> path = parent.getBoard().spacesBetween(location, m.getEnd());
      log.config("path="+path);
      for (int i = 1; i < path.size() - 1; i++)
         if (path.get(i).isOccupied())
            return true;
      return false;
   }
   
   public void reset() {
      location.setOccupation(false);
      parent.putMove(new Move(this.getId(), start));
      setAlive(true);
   }
   
   public static Piece[] createStart(Game g) {
      byte id = 1;
      Piece[] ret = new Piece[PieceConstants.amtOnTeam * 2];
      int index = 0;
      Board b = g.getBoard();
      for (int i = 0; i < 2; i++) {
         boolean white = i == 0;
         for ( ; index % PieceConstants.amtOnTeam < PieceConstants.amtPawn; index++) {
            ret[index] = new Pawn(id, white, b.get(PieceConstants.getCol(id), PieceConstants.getRow(id)), g);
            log.fine("created " + ret[index]);
            id++;
         }
         ret[index] = new Rook(id, white, b.get(PieceConstants.getCol(id), PieceConstants.getRow(id)), g);
         ret[index].setStart(b.get(PieceConstants.getCol(id), PieceConstants.getRow(id))); index++; id++;
         ret[index] = new Knight(id, white, b.get(PieceConstants.getCol(id), PieceConstants.getRow(id)), g);
         ret[index].setStart(b.get(PieceConstants.getCol(id), PieceConstants.getRow(id))); index++; id++;
         ret[index] = new Bishop(id, white, b.get(PieceConstants.getCol(id), PieceConstants.getRow(id)), g);
         ret[index].setStart(b.get(PieceConstants.getCol(id), PieceConstants.getRow(id))); index++; id++;
         ret[index] = new King(id, white, b.get(PieceConstants.getCol(id), PieceConstants.getRow(id)), g);
         ret[index].setStart(b.get(PieceConstants.getCol(id), PieceConstants.getRow(id))); index++; id++;
         ret[index] = new Queen(id, white, b.get(PieceConstants.getCol(id), PieceConstants.getRow(id)), g);
         ret[index].setStart(b.get(PieceConstants.getCol(id), PieceConstants.getRow(id))); index++; id++;
         ret[index] = new Bishop(id, white, b.get(PieceConstants.getCol(id), PieceConstants.getRow(id)), g); 
         ret[index].setStart(b.get(PieceConstants.getCol(id), PieceConstants.getRow(id))); index++; id++;
         ret[index] = new Knight(id, white, b.get(PieceConstants.getCol(id), PieceConstants.getRow(id)), g);
         ret[index].setStart(b.get(PieceConstants.getCol(id), PieceConstants.getRow(id))); index++; id++;
         ret[index] = new Rook(id, white, b.get(PieceConstants.getCol(id), PieceConstants.getRow(id)), g); 
         ret[index].setStart(b.get(PieceConstants.getCol(id), PieceConstants.getRow(id))); index++; id++;
      }
      return ret;
   }
   
   public void die() {
      log.config("piece "+id + " is dead");
      setAlive(false);
      setLocation(Space.DEAD_SPACE);
   }

   public boolean isWhite() {
      return white;
   }

   public void setWhite(boolean white) {
      this.white = white;
   }

   public boolean isAlive() {
      return alive;
   }

   public void setAlive(boolean alive) {
      this.alive = alive;
   }

   public Space getLocation() {
      return location;
   }

   public void setLocation(Space location) {
      this.location = location;
   }

   public byte getId() {
      return id;
   }
   
   public BufferedImage getSkin() {
      return skin;
   }

   public void setSkin(BufferedImage skin) {
      this.skin = skin;
   }
   
   public void setStart(Space s) {
      start = s;
   }

   public Game getParent() {
      return parent;
   }

   public String toString() {
      return getClass().getName() + "[id= " + id + ", location=" + location + "]";
   }
}
