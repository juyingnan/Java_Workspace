package cn.edu.pku.ss.test.testcase.generators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.impl.BulkFitnessOffsetRemover;
import org.jgap.impl.DefaultConfiguration;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.testcase.ExecuteResult;
import cn.edu.pku.ss.test.testcase.MethodParam;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public abstract class GeneGenerator implements IJob{

	private static final int MAX_EVOLUTIONS = 200; // initial : 200

	protected TestCaseExecutor executor;
	protected GeneEvaluator evaluator;	
	private int maxEvolutions = MAX_EVOLUTIONS;
	private int populationSize = 100;
	
	public GeneGenerator(){
		this(new GeneEvaluator());
	}
	
	public GeneGenerator(GeneEvaluator evaluator) {		
		this.evaluator = evaluator;	
		this.evaluator.setGenerator(this);
	}

	public boolean run(TestData data){
		this.executor = new TestCaseExecutor(
				data.getStr(JobConst.TARGET_PATH_BIN),	data.getStr(JobConst.TARGET_CLASS), 
				data.getStr(JobConst.TARGET_METHOD), data.getStr(JobConst.MONITOR));		
		System.out.println("GeneGenerator:before executor.loadClass...");
		if(!executor.loadClass()){
//			System.out.println("...load class failed...");
			data.getResult().addReport("Executor load failed!");
			return false;
		}
		evaluator.setExecutor(executor);
		data.put(JobConst.TEST_CASE_EXECUTOR, executor);
		String geneMax=PropertiesUtil.getInstance().readValue(JobConst.GENE_MAX_EVOLUTIONS);
		String geneSize=PropertiesUtil.getInstance().readValue(JobConst.GENE_POPULATION_SIZE);
		
		if(data.containsArg(JobConst.GENE_MAX_EVOLUTIONS)){
			this.setMaxEvolutions((Integer)data.get(JobConst.GENE_MAX_EVOLUTIONS));
		} else if(geneMax!=null && !geneMax.isEmpty()){
			this.setMaxEvolutions(Integer.parseInt(geneMax));
		}
		if(data.containsArg(JobConst.GENE_POPULATION_SIZE)){
			this.setPopulationSize((Integer)data.get(JobConst.GENE_POPULATION_SIZE));
		} else if(geneSize!=null && !geneSize.isEmpty()){
			this.setPopulationSize(Integer.parseInt(geneSize));
		}
		return test(data);
	}
	
	public abstract boolean test(TestData data);
	public abstract double evaluate(Object target, Object actual);
	
	protected MethodParam geneFind(Object target) {
//		System.out.println("&&&&&&&&&&&&&In GeneGenerator target : " + target);
		if (executor == null || evaluator == null || target == null){
			Log.println("nul arg for gene");
			return null;
		}	
		
		Configuration.reset();

		Configuration conf = new DefaultConfiguration();
		
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(true);
		
		//
		evaluator.setTarget(target);
		// 自动匹配参数个数
//		System.out.println("&&&&&&&&&&&&In GeneGenerator before GeneParam.createGene()");
		Gene[] genes = GeneParam.createGene(executor.getTestMethod(), conf);
//		System.out.println("&&&&&&&&&&&&&In GeneGenerator genes : " + genes.toString());
		try {
			//设置评价函数
			conf.setBulkFitnessFunction(new BulkFitnessOffsetRemover(evaluator));			
			IChromosome sampleChromosome = new Chromosome(conf, genes);
			conf.setSampleChromosome(sampleChromosome);
			conf.setPopulationSize(this.getPopulationSize()); // initial : 100
			Genotype population;
			IChromosome bestThisEvolution = null;
			IChromosome theBestEvolution = null;
			List<IChromosome> best30Chromosomes;
			ArrayList<IChromosome> tempChromosomeList;
			double annealTemperature = 0;
			double bestThisFitnessValue;
			double theBestFitnessValue = -1000;
			int count = 0;
			int firstEvolution = 0;
//			Population p;
			
			population = Genotype.randomInitialGenotype(conf);
			long startTime = System.currentTimeMillis();
			
			while(count<10){
//				if(theBestEvolution!=null){
//					System.out.println("000bestEvolution = " + GeneParam.convertParam(theBestEvolution.getGenes()).toString());
//				}
//			for (int i = 0; i < this.getMaxEvolutions(); i++) {
//			for (int i = 0; i < 5; i++) {
//				if (!uniqueChromosomes(population.getPopulation())) {
//					throw new RuntimeException("Invalid state in generation "
//							+ i);
//				}
				
				//add by lijie 4/3
				population.getPopulation().setChromosomes(uniqueChromosomes(population.getPopulation()));
				
				//add by lijie 3/21
//				System.out.println("All Chromosomes in population : ");
				
				IChromosome[] allSolutions = population.getChromosomes();
				
//				for(int k=0;k<allSolutions.length;k++){
//					IChromosome currentSolution = allSolutions[k];
//					System.out.println(k + " IChromosome before evolve : " + GeneParam.convertParam(currentSolution.getGenes()).toString());
//				}
				
				population.evolve();
				count++;
				bestThisEvolution = population.getFittestChromosome();
//				System.out.println("bestThisEvolution Value : " + getFitnessValue(bestThisEvolution, target));
				bestThisFitnessValue = getFitnessValue(bestThisEvolution,target);
				best30Chromosomes = population.getFittestChromosomes(30);
				
				if(bestThisFitnessValue>theBestFitnessValue){
//					System.out.println("!!!!!!!!!!~~~~~~~~");
					theBestFitnessValue = bestThisFitnessValue;
					theBestEvolution = bestThisEvolution;
//					System.out.println("bestEvolution = " + GeneParam.convertParam(theBestEvolution.getGenes()).toString());
					count = 0;
				}
				
				if(firstEvolution == 0){
					int k = 100;
//					annealTemperature = k*bestThisEvolution.getFitnessValue();
					annealTemperature = k*bestThisFitnessValue; 
				}
				else{
					annealTemperature = 0.9*annealTemperature;
				}
//				System.out.println("*****bestThisEvolution : " + GeneParam.convertParam(bestThisEvolution.getGenes()).toString() + "bestThisFitnessValue : " + bestThisFitnessValue);
				
				if(count<10){
					tempChromosomeList = new ArrayList<IChromosome>();
//					tempChromosomeList.add(bestThisEvolution);
//					for(int j=0;j<best30Chromosomes.size();j++){
//						tempChromosomeList.add(best30Chromosomes.get(j));
//					}
					tempChromosomeList.addAll(best30Chromosomes);
					
					IChromosome currentSolution = null;
					for(int k=0;k<allSolutions.length;k++){
						currentSolution = allSolutions[k];
//						if(!currentSolution.equals(bestThisEvolution)){
						if(!best30Chromosomes.contains(currentSolution)){
							tempChromosomeList.add(currentSolution);
						}
						else{
//							System.out.println(k + " IChromosome is the Best Chromosome this Evolution : " + GeneParam.convertParam(currentSolution.getGenes()).toString());
						}
					}
					
//					System.out.println("tempChromosomeList size : " + tempChromosomeList.size());
					//针对tempChromosomeList里的被淘汰的个体，首先计算它的执行测试的覆盖率；生成一个领域个体，并计算其覆盖率，通过比较评估值决定是否更新种群
//					System.out.println("111bestEvolution = " + GeneParam.convertParam(theBestEvolution.getGenes()).toString());
					for(int j=30;j<tempChromosomeList.size();j++){
						IChromosome currentChromosome = tempChromosomeList.get(j);
//						double obsoleteFitnessValue = currentChromosome.getFitnessValue();
						double obsoleteFitnessValue = getFitnessValue(currentChromosome,target);
						IChromosome oldChromosome = currentChromosome;
//						double currentFitnessValue = evaluator.evaluate(currentChromosome);
//						if(j==0){
//							int k = 100;
//							annealTemperature = k*currentFitnessValue;
//
//						}
//						else{
//						System.out.println("XXXbestEvolution = " + GeneParam.convertParam(theBestEvolution.getGenes()).toString());
						if(!currentChromosome.equals(theBestEvolution)){
							Gene[] obsoleteGene = currentChromosome.getGenes();
							for(int l=0;l<obsoleteGene.length;l++){
								Random r = new Random();
								double randomPercentage = r.nextDouble()*0.5+0.75;
								obsoleteGene[l].applyMutation(0, randomPercentage);
							}
						}
						
//						System.out.println("YYYbestEvolution = " + GeneParam.convertParam(theBestEvolution.getGenes()).toString());
//						double mutationFitnessValue = currentChromosome.getFitnessValue();
						double mutationFitnessValue = getFitnessValue(currentChromosome,target);
//						}
//						System.out.println("%%%%%mutationFitnessValue : " + mutationFitnessValue + " obsoleteFitnessValue : " + obsoleteFitnessValue);
						if(mutationFitnessValue>obsoleteFitnessValue){
//							System.out.println("mutationFitnessValue>obsoleteFitnessValue");
							tempChromosomeList.set(j, currentChromosome);
						}
						else{
							Random r = new Random();
							double randomProbability = r.nextDouble();
							double receiveProbability = Math.exp((mutationFitnessValue-obsoleteFitnessValue)/annealTemperature);
							if(randomProbability<receiveProbability){
//								System.out.println("randomProbability : " + randomProbability + "receiveProbability : " + receiveProbability + "else if randomProbability<receiveProbability");
								tempChromosomeList.set(j, currentChromosome);
							}
							else{
//								System.out.println("randomProbability : " + randomProbability + "receiveProbability : " + receiveProbability + "else else randomProbability>=receiveProbability");
								tempChromosomeList.set(j, oldChromosome);
							}
						}
					}
//					System.out.println("222bestEvolution = " + GeneParam.convertParam(theBestEvolution.getGenes()).toString());
					population.getPopulation().setChromosomes(tempChromosomeList);
				}
				
				
//				MethodParam testCase = GeneParam.convertParam(currentSolution.getGenes());
//				MethodParam testCase=GeneParam.createParam(executor.getTestMethod(), genes);
//				if(executor!=null){
//					ExecuteResult rt = executor.execute(testCase);
//					//监控实际执行路径
//					Object actual = ((Monitor)rt.getMonitor()).getResult();
//					return generator.evaluate(target, actual);
//				}
				firstEvolution++;
			}
			
			long endTime = System.currentTimeMillis();

			Log.println("Total evolution time: " + (endTime - startTime) + " ms");
			
//			IChromosome bestSolutionSoFar = population.getFittestChromosome(); // 算法找到的最优的染色体,zwz
//			System.out.println("*****bestEvolution : " + GeneParam.convertParam(theBestEvolution.getGenes()).toString() + "bestFitnessValue : " + theBestFitnessValue);
			Gene[] solutions = theBestEvolution.getGenes(); // 最优染色体中的基因,zwz
//			Gene[] solutions = bestSolutionSoFar.getGenes(); // 最优染色体中的基因,zwz
			
			return GeneParam.convertParam(solutions);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}


	private double getFitnessValue(IChromosome chromosome, Object target) {
		// TODO Auto-generated method stub
		Gene[] genes = chromosome.getGenes();
		MethodParam testCase=GeneParam.createParam(executor.getTestMethod(), genes);
		if(executor!=null){
			ExecuteResult rt = executor.execute(testCase);
			//监控实际执行路径
			Object actual = ((Monitor)rt.getMonitor()).getResult();
			return this.evaluate(target, actual);
		}
		else return 0;
	}

	public static List<IChromosome> uniqueChromosomes(Population a_pop) {
		// check that all chromosomes are unique
//		for (int i = 0; i < a_pop.size() - 1; i++) {
//			IChromosome c = a_pop.getChromosome(i);
//			for (int j = i + 1; j < a_pop.size(); j++) {
//				IChromosome c2 = a_pop.getChromosome(j);
//				if (c == c2) {
//					return false;
//				}
//			}
//		}
//		return true;
		
		// check that all chromosomes are unique
		ArrayList<IChromosome> uniqueChromosomeList = new ArrayList<IChromosome>();
		for (int i = 0; i < a_pop.size(); i++) {
			IChromosome c = a_pop.getChromosome(i);
			int uniqueFlag = 1;
			for(int j = 0;j < uniqueChromosomeList.size();j++){
				IChromosome c2 = uniqueChromosomeList.get(j);
				if(c.equals(c2)){
					uniqueFlag = 0;
					break;
				}
			}
			if(uniqueFlag == 1){
				uniqueChromosomeList.add(c);
			}
		}
		
		return uniqueChromosomeList;
		
	}

	public void setMaxEvolutions(int maxEvolutions) {
		this.maxEvolutions = maxEvolutions;
	}

	public int getMaxEvolutions() {
		return maxEvolutions;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public int getPopulationSize() {
		return populationSize;
	}
}
