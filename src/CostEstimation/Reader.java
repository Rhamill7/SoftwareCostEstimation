package CostEstimation;

import java.io.File;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {

	ArrayList<String> attributes = new ArrayList<String>();
	ArrayList<Chromosome> customers = new ArrayList<Chromosome>();
	ArrayList<Double> scores = new ArrayList<Double>();
	List<List<Integer>> lists = new ArrayList<List<Integer>>();
	private double budget = 0.0;
	private int totalProfit = 0;

	public Reader() {
		try {
			System.out.println("Made it to here");
			// change file path if required
			File infile = new File("miyazaki94.arff");
			if (!infile.canRead()) {
				infile.setReadable(true);
			}
			Scanner scanner = new Scanner(infile);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				System.out.println(line);
				if (line.contains("attribute")) {
					List<Integer> list = new ArrayList<>();
					lists.add(list);
				}

				else if (line.contains("data")) {
					while (scanner.hasNext()) {
						line = scanner.nextLine();
						int j = 0;
						Scanner miniScan2 = new Scanner(line);
						while (miniScan2.hasNext()) {
							int variable = miniScan2.nextInt();
							lists.get(j).add(variable);
							j++;
						}
					}

				} else {
					scanner.nextLine();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
