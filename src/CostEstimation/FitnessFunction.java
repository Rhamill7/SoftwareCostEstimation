package CostEstimation;

import java.util.List;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

@SuppressWarnings("serial")
public class FitnessFunction extends GPFitnessFunction {

	List<Variable> vars2;
	List<List<Double>> inputs2;
	List<Double> output2;

	private static Object[] NO_ARGS = new Object[0];

	public FitnessFunction(List<List<Double>> inputs, List<Double> output, List<Variable> vars) {
		// _input1 = input1;
		// _input2 = input2;
		// _output = output;
		// _xVariable = x;
		// _yVariable = y;
		inputs2 = inputs;
		output2 = output;
		vars2 = vars;
	//	vars2.remove(vars2.size() - 1);
		// Object[] output3 = output2.toArray();
	}

	@Override
	protected double evaluate(final IGPProgram program) {

		double result = 0.0;

		long longResult = 0;
		for (int i = 0; i < output2.size(); i++) {
			for (int j = 0; j < inputs2.size(); j++) {
				// Set the input values
				// _xVariable.set(_input1[i]);
				// _yVariable.set(_input2[i]);
				vars2.get(j).set(inputs2.get(j).get(i));
			}
			// Execute the genetically engineered algorithm
			// long value = program.execute_int(0, NO_ARGS);
			long value = (long)program.execute_double(0, NO_ARGS);
			//System.out.println(value);
			// The closer longResult gets to 0 the better the algorithm.
			longResult += Math.abs(value - output2.get(i));
		}

		result = longResult;

		return result;
	}

}