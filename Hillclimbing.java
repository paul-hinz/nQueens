package nQueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nQueens.StateUtils.*;

/**
 * Class implements the Hillclimbing-algorithm to solve the n-Queens problem.
 */
public class Hillclimbing {

  public int consecutiveSidewaysMoves = 0;

  public Hillclimbing() {
  }

  public int[] enhanceState(int[] state){
    int[] bestState = state;
    for (int i = 0; i < HC_Steps; i++) {
      bestState = findNext(bestState);
    }
    return bestState;
  }

  /**
   * Generates a list of the follow-up states of possible single queen moves for a given board state.
   */
  public List<int[]> generateNeighbors(int[] state) {
    // [1, 2, 3, 4, 5, 7, 8]
    List<int[]> neighbors = new ArrayList<>();
    for (int i = 0; i < state.length; i++) {
      for (int j = 1; j < state.length+1; j++) {
        if (j != state[i]) {
          int[] neighborState = Arrays.copyOf(state, state.length);
          neighborState[i] = j;
          neighbors.add(neighborState);
        }
      }
    }
    return neighbors;
  }

  /**
   * Determines the best follow-up move from a given board state.
   */
  public int[] findNext(int[] state) {
    List<int[]> neighbors = generateNeighbors(state);
    boolean sideways = false;
    int startFitness = calculateFitness(state);
    int bestFitness = startFitness;
    int[] bestState = state;
    for (int[] neighbor : neighbors){
      int score = calculateFitness(neighbor);
      if(score > bestFitness) {
        bestFitness = score;
        bestState = neighbor;
        sideways = false;
      }
      else if(score == bestFitness &&
              score == startFitness &&
              !sideways &&
              SidewaysStepsAllowed){

        bestState = neighbor;
        sideways = true;

      }
    }
    return bestState;
  }

  public int[] findNextSidewaysLimit(int[] state) {
    List<int[]> neighbors = generateNeighbors(state);
    boolean sideways = false;
    int startFitness = calculateFitness(state);
    int bestFitness = startFitness;
    int[] bestState = state;
    for (int[] neighbor : neighbors){
      int score = calculateFitness(neighbor);
      if(score > bestFitness) {
        bestFitness = score;
        bestState = neighbor;
        sideways = false;
      }
      else if(score == bestFitness && score == startFitness && !sideways){
        bestState = neighbor;
        sideways = true;
      }
    }
    if (sideways) {
      consecutiveSidewaysMoves++;
    } else {
      consecutiveSidewaysMoves = 0;
    }
    return bestState;
  }
}
