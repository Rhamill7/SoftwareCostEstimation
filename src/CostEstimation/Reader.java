package CostEstimation;

import java.io.File;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

	ArrayList<String> attributes = new ArrayList<String>();
	ArrayList<Chromosome> customers = new ArrayList<Chromosome>();
	ArrayList<Double> scores = new ArrayList<Double>();
	private double budget = 0.0;
	private int totalProfit = 0;

	public Reader() {
		try {
			System.out.println("Made it to here");
			int i = 1;
			// change file path if required
			File infile = new File("miyazaki94.arff");
			if (!infile.canRead()) {
				infile.setReadable(true);
			}
			Scanner scanner = new Scanner(infile);
			// + "nrp1.txt"));
			while (scanner.hasNext()) {
				// System.out.println(i);
				// scanner.nextLine();
				String line = scanner.nextLine();
				System.out.println(line);
				if (line.contains("attribute")) {
					
					// System.out.println(" New Line " + line);
					Scanner miniScan = new Scanner(line);
					while (miniScan.hasNext()) {
						attributes.add(miniScan.next());
					}
					miniScan.close();
					// System.out.println(requirements);
				}
			
				else if (line.contains("data")) {
					 line = scanner.nextLine();
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
		

	}

	public double getBudget() {
		return budget;

	}

//	public int getReqCost(int position) {
//		return requirements.get(position);
//	}
//
//	public double getScore(int position) {
//		// System.out.println(scores.get(position));
//		return scores.get(position);
//	}
//
//	public ArrayList<Chromosome> getCust() {
//		return customers;
//
//	}

}
