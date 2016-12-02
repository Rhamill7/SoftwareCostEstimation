package CostEstimation;

import java.util.ArrayList;
import java.util.List;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

@SuppressWarnings("serial")
public class FitnessFunction extends GPFitnessFunction {

	List<Variable> vars2= new ArrayList<Variable>();
	List<List<Double>> inputs2 = new ArrayList<List<Double>>();
	List<Double> output2 =new ArrayList<Double>();
	
	private static Object[] NO_ARGS = new Object[0];

	public FitnessFunction(List<List<Double>> inputs, List<Double> output, List<Variable> vars) {
		inputs2 = inputs;
		output2 = output;
		vars2 = vars;
		System.out.println(output.size());
		System.out.println(inputs.size());
		System.out.println(vars.size());
//		for(int i = 0; i < inputs.size(); i++){
//			if (unneeded[i] == true ){
//				inputs2.add(inputs.get(i));
//				vars2.add(vars.get(i));
//				
//			}
//		}
		System.out.println(inputs2.size());
		System.out.println(vars2.size());
		System.out.println(output2.size());
	
	}

	@Override
	protected double evaluate(final IGPProgram program) {

		double result = 0.0f;
		// MARE 
		double longResult = 0;
		//long longResult = 0;
		for (int i = 0; i < output2.size(); i++) {
			for (int j = 0; j < inputs2.size(); j++) {
				// Set the input values
				vars2.get(j).set(inputs2.get(j).get(i));
			}
			// Execute the genetically engineered algorithm
			long value = (long) program.execute_double(0, NO_ARGS);
			
			// The closer longResult gets to 0 the better the algorithm.
			// Mean Absolute error
			//longResult += Math.abs(value - output2.get(i));
			
			// Mean magnitude of relative error
			 longResult += (Math.abs( value - output2.get(i))/output2.get(i));
		}

		result = longResult;

		return result;
	}

}