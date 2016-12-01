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

	public CostEstimation() throws InvalidConfigurationException {
		super(new GPConfiguration());

		Reader r = new Reader();
		r.read();
		ArrayList<String> names = r.getAttrNames();
		// r.checkLists();
		int attributes = r.getListSize() - 1;
		for (int i = 0; i < attributes - 1; i++) {
			List<Double> INPUT = r.getAttributeList(i);
			inputs.add(INPUT);
		}
		outputs = r.getAttributeList(r.getListSize() - 2);

		GPConfiguration config = getGPConfiguration();
//System.out.println(attributes);
		for (int j = 0; j < attributes-1; j++) {
			Variable var = Variable.create(config, names.get(j), CommandGene.DoubleClass);
			vars.add(var);
		}
		//System.out.println(vars.size());
		// _xVariable = Variable.create(config, "X", CommandGene.IntegerClass);
		// _yVariable = Variable.create(config, "Y", CommandGene.IntegerClass);

		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		config.setMaxInitDepth(4);
		config.setPopulationSize(1000);
		config.setMaxCrossoverDepth(10);
		config.setFitnessFunction(new FitnessFunction(inputs, outputs, vars));
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
		test.add(new Terminal(config, CommandGene.DoubleClass, 0.0, 10.0, false));
		// List<T> list = new ArrayList<T>();

		CommandGene[] test2 = test.toArray(new CommandGene[test.size()]);
		CommandGene[][] nodeSets = { test2 };

		// CommandGene[][] nodeSets = {
		// {
		// _xVariable, _yVariable,
		// new Add(config, CommandGene.IntegerClass),
		// new Multiply(config, CommandGene.IntegerClass),
		// new Subtract(config, CommandGene.IntegerClass),
		// new Terminal(config, CommandGene.IntegerClass, 0.0, 10.0, true) } };

		GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);

		return result;
	}

	public static void main(String[] args) throws Exception {
		GPProblem problem = new CostEstimation();

		GPGenotype gp = problem.create();
		gp.setVerboseOutput(true);
		gp.evolve(1000);

		// System.out.println("Formulaiscover: x^2 + 2y + 3x + 5");
		gp.outputSolution(gp.getAllTimeBest());
	}

}