/**
 * om1pa
 * Omid Pakdel
 */

package AI;

import Main.Board;

class AlphaBetaPruning {

    private static double maxPly;

    private AlphaBetaPruning () {}


    static void run (Board.State player, Board board) {

        AlphaBetaPruning.maxPly = 100000;
        alphaBetaPruning(player, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
    }


    private static int alphaBetaPruning (Board.State player, Board board, double alpha, double beta, int currentPly) {
        if (currentPly++ == maxPly || board.isGameOver()) {
            return score(player, board);
        }

        if (board.getTurn() == player) {
            return getMax(player, board, alpha, beta, currentPly);
        } else {
            return getMin(player, board, alpha, beta, currentPly);
        }
    }


    private static int getMax (Board.State player, Board board, double alpha, double beta, int currentPly) {
        int bestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.getCopy();
            modifiedBoard.move(theMove);
            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentPly);

            if (score > alpha) {
                alpha = score;
                bestMove = theMove;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (bestMove != -1) {
            board.move(bestMove);
        }
        return (int)alpha;
    }


    private static int getMin (Board.State player, Board board, double alpha, double beta, int currentPly) {
        int bestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.getCopy();
            modifiedBoard.move(theMove);

            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentPly);

            if (score < beta) {
                beta = score;
                bestMove = theMove;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (bestMove != -1) {
            board.move(bestMove);
        }
        return (int)beta;
    }


    private static int score (Board.State player, Board board) {
        if (player == Board.State.Blank) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        Board.State opponent = (player == Board.State.X) ? Board.State.O : Board.State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return 10;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return -10;
        } else {
            return 0;
        }
    }

}
