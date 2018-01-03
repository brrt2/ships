package com.epam.ships.server.gamestates;

public interface GameState {

  GameState process();

  default boolean shouldBeContinued() {
    return true;
  }

}
