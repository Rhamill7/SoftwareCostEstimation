package CostEstimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {

	
	private int customerProfit;
	private int customerRequests;
	private ArrayList<Integer> custRequirements;

	public Chromosome(int customerProfit, int customerRequests, ArrayList<Integer> custRequirements  ) {
		this.customerProfit = customerProfit;
		this.customerRequests = customerRequests;
		this.custRequirements = custRequirements;
	}

	public List<Integer> getCustomerRequirements() {
		return custRequirements;
	}

	public int getCustomerProfit() {
		return customerProfit;
	}
	
	public int getCustomerRequests() {
		return customerRequests;
	}
	
	// Compare Method for comparing fitness
	@Override
	public int compareTo(Chromosome gene) {
		return 0;
	}

}

