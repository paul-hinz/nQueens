package nQueens;


import java.util.*;

import static nQueens.StateUtils.*;

/**
 * Class implements a genetic algorithm to solve the 8-Queens problem.
 */
public class GeneticAlgorithm {
  public List<int[]> population;

  private int[] bestState;

  private int evolutionSteps;


  private Hillclimbing hc;

  public GeneticAlgorithm() {
    this.population = generatePopulation();
    this.evolutionSteps = 0;
    this.hc = new Hillclimbing();
  }


  public List<int[]> getPopulation() {
    return population;
  }

  public int getEvolutionSteps() {
    return evolutionSteps;
  }

  /**
   * Calculates a solution for the 8-Queens problem by evolving the population of board states until a solution has
   * been reached or the maximum amount of evolutions has been reached.
   */
  public int[] calculateSolution() {
    while (calculateMaximumFitness() < maximumFitness() && evolutionSteps < generations) {
      evolutionSteps++;
      makeEvolutionStep();
    }
    return bestState;
  }

  /**
   * Performs one evolution of the given population by going through the steps of selection, recombination and mutation.
   */
  public void makeEvolutionStep() {
    List<int[]> newPopulation = new ArrayList<>();
    Random random = new Random();
    makeSelectionDeterministicTruncation();
    for (int i = 0; i < population.size(); i++) {
      int parent2Index = random.nextInt(0, population.size());
      while (population.get(parent2Index) == population.get(i)) {
        parent2Index = random.nextInt(0, population.size());
      }
      List<int[]> children = makeChildren(population.get(i), population.get(parent2Index));
      newPopulation.addAll(children);
    }
    population = newPopulation;
  }

  /**
   * Generates the starting population of random board states.
   */
  public List<int[]> generatePopulation() {
    List<int[]> population = new ArrayList<>();
    for (int i = 0; i < populationSize; i++) {
      population.add(generateRandomState());
    }
    return population;
  }

  /**
   * Determines the maximum fitness found in the current population.
   */
  public int calculateMaximumFitness() {
    int maximumFitness = 0;
    for (int i = 0; i < population.size(); i++) {
      if (calculateFitness(population.get(i)) > maximumFitness) {
        bestState = population.get(i);
        maximumFitness = calculateFitness(population.get(i));
      }
    }
    return maximumFitness;
  }

  /**
   * Selection stage of GA, half of the population with the best fitness is kept.
   */
  public void makeSelectionDeterministicTruncation() {
    population.sort((state1, state2) -> Integer.compare(calculateFitness(state2), calculateFitness(state1)));
    for (int i = 0; i < populationSize / 2; i++) {
      population.remove(population.size() - 1);
    }
  }

  /**
   * Selection stage of GA, uses tournament selection to halve population.
   */
  public void makeSelectionTournament() {
    Random random = new Random();
    List<int[]> selection = new ArrayList<>();
    for (int i = 0; i < populationSize / 2; i++) {
      int[] genome1 = population.get(0);
      int[] genome2 = population.get(random.nextInt(0, population.size()));
      while (genome1 == genome2) {
        genome2 = population.get(random.nextInt(0, population.size()));
      }
      if (calculateFitness(genome1) >= calculateFitness(genome2)) {
        selection.add(genome1);
      } else {
        selection.add(genome2);
      }
      population.remove(genome1);
      population.remove(genome2);
    }
    population = selection;
  }

  /**
   * Generates two children for the given two parents by going through the recombination and mutation stages.
   * After each step a steepest hillclimbing algorithm is used to "enhance" the children
   */
  public List<int[]> makeChildren(int [] parent1, int[] parent2){
    int[] child1 = Arrays.copyOf(parent1, parent1.length);
    int[] child2 = Arrays.copyOf(parent2, parent2.length);

    //Rekombinieren
    Random r = new Random();
    int point1 = r.nextInt(0, N);
    int point2 = r.nextInt(point1, N);
    for (int i = point1; i <= point2; i++) {
      child1[i] = parent2[i];
      child2[i] = parent1[i];
    }
    child1 = hc.enhanceState(child1);
    child2 = hc.enhanceState(child2);

    // Mutieren
    if (r.nextFloat() <= mutationProbability) {
      int column = r.nextInt(0, 8);
      child1[column] = r.nextInt(1, 9);
    }
    if (r.nextFloat() <= mutationProbability) {
      int column = r.nextInt(0, 8);
      child2[column] = r.nextInt(1, 9);
    }
    child1 = hc.enhanceState(child1);
    child2 = hc.enhanceState(child2);

    return List.of(child1, child2);
  }



  /*

  public static void main(String[] args) {
    int NUM_ITERATIONS = 10000;
    List<Double> timeMeasurements = new ArrayList<>();
    List<Integer> evolutionSteps = new ArrayList<>();
    for (int i = 0; i < NUM_ITERATIONS; i++) {
      Logger logger = new Logger();
      GeneticAlgorithm ga = new GeneticAlgorithm(logger);
      int[] solution = ga.calculateSolution();
      if (calculateFitness(solution) == 28) {
        timeMeasurements.add(logger.getMeasuredTime());
        evolutionSteps.add(ga.evolutionSteps);
      }
    }
    double averageTime = 0;
    for (Double measurement : timeMeasurements) {
      averageTime += measurement;
    }
    averageTime = averageTime / timeMeasurements.size();
    double averageEvolutionSteps = 0;
    for (Integer evolutionStep : evolutionSteps) {
      averageEvolutionSteps += evolutionStep;
    }
    averageEvolutionSteps = averageEvolutionSteps / evolutionSteps.size();
    double solutionsFound = (double) timeMeasurements.size() / NUM_ITERATIONS;
    System.out.println(averageTime);
    System.out.println(averageEvolutionSteps);
    System.out.println(solutionsFound);
  }
*/

}