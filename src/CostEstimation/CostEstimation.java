package CostEstimation;

import java.util.ArrayList;
import java.util.List;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Exp;
import org.jgap.gp.function.Log;
import org.jgap.gp.function.Pow;
import org.jgap.gp.function.Abs;
import org.jgap.gp.function.Cosine;
import org.jgap.gp.function.Sine;

import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

import examples.gp.symbolicRegression.Sqrt;

/**
 * @author Robbie
 *
 */
public class CostEstimation extends GPProblem {

	List<List<Double>> inputs = new ArrayList<List<Double>>();
	List<Double> outputs = new ArrayList<Double>();
	List<Variable> vars = new ArrayList<Variable>();
	boolean[] kemerer = {false, true, false, false, true, false};
	boolean[] miyazaki94 = {true, false, false, false, true, false, true};
	boolean[] china = {true, true, true, true, true, true, true, false, false, true, true, true, false, true, false, false, true};


	public CostEstimation() throws InvalidConfigurationException {
		super(new GPConfiguration());

		Reader r = new Reader();
		r.read();
		ArrayList<String> names = r.getAttrNames();
		int attributes = r.getListSize() - 1;
		for (int i = 0; i < attributes - 1; i++) {
			List<Double> INPUT = r.getAttributeList(i);
			inputs.add(INPUT);
		}
		outputs = r.getAttributeList(r.getListSize() - 2);

		GPConfiguration config = getGPConfiguration();
		for (int j = 0; j < attributes - 1; j++) {
			Variable var = Variable.create(config, names.get(j), CommandGene.DoubleClass);
			vars.add(var);
		}
		
		//removing unneeded here
//		String name = r.getFileName();
//		boolean[] use = {};
//		if (name.equals("kemerer.arff.txt")) {
//			use = kemerer;
//		} else if (name.equals("miyazaki94.arff")){
//			use = miyazaki94;
//		}
//		else if (name.equals("china.arff.txt")){
//			use = china;
//		}
//		
//		List<List<Double>> inputs2 = new ArrayList<List<Double>>();
//		List<Variable> vars2 = new ArrayList<Variable>();
//		for(int i = 0; i < inputs.size(); i++){
//			if (use[i] == true ){
//				
//				inputs2.add(inputs.get(i));
//				
//				vars2.add(vars.get(i));
//				
//			}
//			
//		}
//		vars.clear();
		/////
		
		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		config.setMaxInitDepth(4);
		config.setPopulationSize(1000);
		config.setMaxCrossoverDepth(8);
		config.setFitnessFunction(new FitnessFunction(inputs, outputs, vars));
		//config.setFitnessFunction(new FitnessFunction(inputs2, outputs, vars2));
		config.setStrictProgramCreation(true);
	}

	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		GPConfiguration config = getGPConfiguration();

		// The return type of the GP program.
		@SuppressWarnings("rawtypes")
		Class[] types = { CommandGene.DoubleClass };

		// Arguments of result-producing chromosome: none
		@SuppressWarnings("rawtypes")
		Class[][] argTypes = { {} };

		// Next, we define the set of available GP commands and terminals to
		// use.
		ArrayList<CommandGene> test = new ArrayList<CommandGene>();
		test.addAll(vars);
		test.add(new Add(config, CommandGene.DoubleClass));
		test.add(new Multiply(config, CommandGene.DoubleClass));
		test.add(new Subtract(config, CommandGene.DoubleClass));
		test.add(new Divide(config, CommandGene.DoubleClass));
		test.add(new Pow(config, CommandGene.IntegerClass));
		test.add(new Sqrt(config, CommandGene.DoubleClass));
		test.add(new Exp(config, CommandGene.DoubleClass));
		test.add(new Log(config, CommandGene.DoubleClass));
		test.add(new Abs(config, CommandGene.DoubleClass));
		test.add(new Sine(config, CommandGene.DoubleClass));
		test.add(new Cosine(config, CommandGene.DoubleClass));
		test.add(new Terminal(config, CommandGene.DoubleClass, 0.0, 10.0, false));

		CommandGene[] test2 = test.toArray(new CommandGene[test.size()]);
		CommandGene[][] nodeSets = { test2 };
		GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);

		return result;
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		GPProblem problem = new CostEstimation();

		GPGenotype gp = problem.create();
		gp.setVerboseOutput(true);
		gp.evolve(1000);
		gp.outputSolution(gp.getAllTimeBest());
		long finish = System.currentTimeMillis();
		
		System.out.println("Time elapsed in ms: " + (finish - start));
	}

}