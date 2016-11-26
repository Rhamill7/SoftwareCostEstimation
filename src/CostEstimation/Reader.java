package CostEstimation;

import java.io.File;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

	ArrayList<Integer> requirements = new ArrayList<Integer>();
	ArrayList<Chromosome> customers = new ArrayList<Chromosome>();
	ArrayList<Double> scores = new ArrayList<Double>();
	private double budget = 0.0;
	private int totalProfit = 0;

	public Reader() {
		try {
			// ====================================Realistic===================================================//
			int i = 1;
			// change file path if required
			File infile = new File("C:/Users/Robbie/Documents/realistic-nrp/nrp-e1.txt");
			if (!infile.canRead()) {
				infile.setReadable(true);
			}
			Scanner scanner = new Scanner(infile);
			// + "nrp1.txt"));
			while (scanner.hasNextInt()) {
				// System.out.println(i);
				// scanner.nextLine();
				if (i == 3) {
					String line = scanner.nextLine();
					// System.out.println(" New Line " + line);
					Scanner miniScan = new Scanner(line);
					while (miniScan.hasNextInt()) {
						requirements.add(miniScan.nextInt());
					}
					miniScan.close();
					// System.out.println(requirements);
				}

				else if (i > 10) {
					String line = scanner.nextLine();
					Scanner miniScan2 = new Scanner(line);
					int custProf = miniScan2.nextInt();
					totalProfit += custProf;
					int requestNo = miniScan2.nextInt();
					ArrayList<Integer> reqs = new ArrayList<Integer>();
					for (int j = 0; j < requestNo; j++) {
						reqs.add(miniScan2.nextInt());
					}
					// System.out.println(custProf + " " + requestNo + " " +
					// reqs);
					customers.add(new Chromosome(custProf, requestNo, reqs));

				} else {
					scanner.nextLine();
				}

				i++;
				// System.out.println("Total profit" + totalProfit);

			}

		
		} catch (IOException e) {
			e.printStackTrace();
		}
		// working out score and total budget
		System.out.println("TotalProfit" + totalProfit);
		int cost = 0;
		System.out.println(requirements);
		for (int l = 0; l < requirements.size(); l++) {
			cost += requirements.get(l);
			double score = 0.0;
			for (int m = 0; m < customers.size(); m++) {
				double customerValue = ((double) customers.get(m).getCustomerProfit() / (double) totalProfit);
				int customerReqValue = 0;
				if (customers.get(m).getCustomerRequirements().contains(l)) {
					customerReqValue = customers.get(m).getCustomerRequirements().size()
							- customers.get(m).getCustomerRequirements().indexOf(l);
				} else {
					customerReqValue = 0;
				}
				score += (customerValue * (double) customerReqValue);
			}
			scores.add(score);
		}
		System.out.println("Total cost" + cost);
		budget = (double) cost * 0.4;
		System.out.println("budget" + budget);

	}

	public double getBudget() {
		return budget;

	}

	public int getReqCost(int position) {
		return requirements.get(position);
	}

	public double getScore(int position) {
		// System.out.println(scores.get(position));
		return scores.get(position);
	}

	public ArrayList<Chromosome> getCust() {
		return customers;

	}

}
