package pl.korotkevics.ships.client.gui.util;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.RequiredArgsConstructor;

/**
 * Helper class to convert board represented by grid to array.
 * @author Magdalena Aarsman
 * @since 2018-01-01
 */
@RequiredArgsConstructor
public class GridToBoardConverter {

  private static final int BOARD_SIZE = 10;
  private final GridPane gridBoard;

  /**
   * Converts grid to array.
   * @return FieldState array
   */
  public FieldState[] convert() {
    FieldState[] board = new FieldState[BOARD_SIZE * BOARD_SIZE];

    for (int i = 1; i < gridBoard.getChildren().size(); i++) {
      if (((Rectangle) gridBoard.getChildren().get(i)).getFill().equals(Color.GREEN)) {
        board[i - 1] = FieldState.OCCUPIED;
      } else {
        board[i - 1] = FieldState.EMPTY;
      }
    }

    return board;
  }
}
