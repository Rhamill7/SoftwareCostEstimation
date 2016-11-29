package CostEstimation;
import java.util.List;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class FitnessFunction extends GPFitnessFunction {

    private Integer[] _input1;
    private Integer[] _input2;
    private int[] _output;
    private Variable _xVariable;
    private Variable _yVariable;
    List<Variable> vars2;
    List<List<Double>> inputs2;
    List<Double> output2; 

    private static Object[] NO_ARGS = new Object[0];

    public FitnessFunction(List<List<Double>> inputs, List<Double> output, List<Variable> vars) {
       // _input1 = input1;
       //_input2 = input2;
        //_output = output;
        //_xVariable = x;
        //_yVariable = y;
    	inputs2 = inputs;
    	output2 = output;
    	vars2 =vars;
    }

    @Override
    protected double evaluate(final IGPProgram program) {
    	
    	setUp();
        double result = 0.0f;

        long longResult = 0;
     //   for (int i = 0; i < _input1.length; i++) {
        for (int i = 0; i < vars2.size(); i++) {
            // Set the input values
            _xVariable.set(_input1[i]);
            _yVariable.set(_input2[i]);
            for (Variable v: vars2){
            	v.set(a_value);
            }
            // Execute the genetically engineered algorithm
          //  long value = program.execute_int(0, NO_ARGS);
            double value = program.execute_double(0, NO_ARGS);
            // The closer longResult gets to 0 the better the algorithm.
            longResult += Math.abs(value - _output[i]);
        }

        result = longResult;

        return result;
    }

    public void setUp(){}
    
}