
package CostEstimation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {

	List<List<Double>> lists = new ArrayList<List<Double>>();
	ArrayList<String> attrNames = new ArrayList<String>();

	public Reader() {

	}

	public void read() {
		try {
			File infile = new File("china.arff.txt");//("miyazaki94.arff");//("kemerer.arff.txt");
			if (!infile.canRead()) {
				infile.setReadable(true);
			}
			Scanner scanner = new Scanner(infile);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.contains("attribute")) {
					Scanner miniScan = new Scanner(line);
					miniScan.next();
					String name = miniScan.next();
					attrNames.add(name);
					miniScan.close();
					List<Double> list = new ArrayList<Double>();
					lists.add(list);
				}
			}
			scanner.close();
			Scanner scanner2 = new Scanner(infile);
			while (scanner2.hasNext()) {
				String line = scanner2.nextLine();
				if (line.contains("data")) {
					while (scanner2.hasNext()) {
						line = scanner2.nextLine();
						Scanner miniScan2 = new Scanner(line);
						int j = 0;
						miniScan2.useDelimiter(",");
						while (miniScan2.hasNext()) {
							if (j == 0) {
								miniScan2.next();
							}
							String variable = miniScan2.next();
							Double var = Double.valueOf(variable);
							lists.get(j).add(var);
							j++;
						}
						miniScan2.close();
					}

				}
			}
			scanner2.close();

		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	public void checkLists() {
		System.out.println("Got to method.");
		System.out.println(lists.size());
		for (int k = 0; k < lists.size(); k++) {
			System.out.println(lists.get(k));
		}
	}

	public List<Double> getAttributeList(int attribute) {
		return lists.get(attribute);
	}

	public List<List<Double>> getLists() {
		return lists;
	}

	public int getListSize() {
		return lists.size();
	}

	public ArrayList<String> getAttrNames() {
//		System.out.println(attrNames);
		attrNames.remove(0);
		attrNames.remove(attrNames.size() - 1);
	//	System.out.println(attrNames);
		return attrNames;
	}

}
