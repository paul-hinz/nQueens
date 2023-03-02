package nQueens;


import java.util.ArrayList;

import static nQueens.StateUtils.*;

public class Simulation {
  public static void main(String[] args) {

    ArrayList<Integer> generations = new ArrayList<>(10);
    ArrayList<Long> runtimes = new ArrayList<>(10);
    long startTime;
    long runTime;
    for (int i = 0; i < 10; i++) {
      startTime = System.nanoTime();
      GeneticAlgorithm ga = new GeneticAlgorithm();
      int[] solution = ga.calculateSolution();
      runTime = ((System.nanoTime()-startTime)/1000000);
      if (maximumFitness() == calculateFitness(solution)){
        generations.add(ga.getEvolutionSteps());
        runtimes.add(runTime);
      };
    }
    int sumGen = 0;
    int sumRun = 0;
    for (Integer in : generations){
      sumGen += in;
    }
    for (Long l : runtimes){
      sumRun += l;
    }
    System.out.println(sumRun/10f);
    System.out.println(sumGen/10f);
    System.out.println(generations.size()*10);


    /*
    long startTime;
    long runTime;
    int[] Ns = {8,25,50,75,150,200};

    for (int i = 0; i < 6; i++) {
      N = Ns[i];
      startTime = System.nanoTime();
      GeneticAlgorithm ga = new GeneticAlgorithm();
      int[] solution = ga.calculateSolution();
      runTime = ((System.nanoTime() - startTime) / 1000000);
      System.out.print(N +"::  " + runTime);
      System.out.println("  bei  "+ calculateFitness(solution) + "  |  " + maximumFitness());
    }

     */
  }
}
