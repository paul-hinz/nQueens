package nQueens;

import java.util.Random;

/**
 * Utility functions for n-Queens problem to be used with Hillclimbing and Genetic Algorithm.
 */
public class StateUtils {
  /**
   * Constants for experimenting
   */

  public static int N = 50;

  public static final int HC_Steps = 4;

  public static final boolean SidewaysStepsAllowed = true;

  public static final double mutationProbability = 1f;

  public static final int populationSize = 8;

  public static final int generations = 100;





  /**
   * Calculates the number of non-attacking pairs of queens on the board. A solved board state has a fitness of N*(N-1) / 2.
   */
  public static int calculateFitness(int[] state) {
    // Check horizontally and diagonally
    int fitness = 0;
    for (int i = 0; i < state.length - 1; i++) {
      for (int j = i + 1; j < state.length; j++) {
        if (state[j] != state[i] && state[j] != state[i] + (j - i) && state[j] != state[i] - (j - i)) {
          fitness++;
        }
      }
    }
    return fitness;
  }

  public static int maximumFitness() {
    return N * (N-1) / 2;
  }

  /**
   * Generates a board with N queens of random ranks of each column.
   */
  public static int[] generateRandomState() {
    int[] state = new int[N];
    Random random = new Random();
    for (int i = 0; i < N; i++) {
      state[i] = random.nextInt(1, N+1);
    }
    return state;
  }

  /**
   * Constructs a visual representation of the given board state.
   */
  public static String stateToString(int[] state){
    String board = "";
    int n = state.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if(state[j] - 1 == i){
          board = board + "|Q";
        }else {
          board = board + "|·";
        }
      }
      board = board + "|\n";
    }
    return board;
  }
}
