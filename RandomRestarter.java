package nQueens;

import static nQueens.StateUtils.*;

public class RandomRestarter {
  public static void main(String[] args) {
    long startTime = System.nanoTime();
    Hillclimbing hc = new Hillclimbing();
    int[] currentState = new int[N];
    int[] nextState = new int[N];
    while (calculateFitness(currentState) != maximumFitness()) {
      hc.consecutiveSidewaysMoves = 0;
      nextState = generateRandomState();
      while (calculateFitness(currentState) != maximumFitness() && hc.consecutiveSidewaysMoves < 5 && nextState != currentState) {
        currentState = nextState;
        nextState = hc.findNextSidewaysLimit(currentState);
      }
    }
    long runTime = ((System.nanoTime()-startTime)/1000000);
    System.out.println(runTime);
    System.out.println(calculateFitness(currentState) + " | " + maximumFitness());
  }
}
