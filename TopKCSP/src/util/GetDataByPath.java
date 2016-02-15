package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * author: Hao 
 * date:2015��3��4��
 * time:����5:32:17
 * purpose: Read the data set from disk and store it in menory
 */
public enum GetDataByPath {
	INSTANCE;

	public ArrayList<String> getData(String path) throws FileNotFoundException {
		ArrayList<String> dataset = new ArrayList<String>();
		File file = new File(path);
		Scanner sc = new Scanner(file);
		while(sc.hasNext()){
			String tmp = sc.nextLine();
			dataset.add(tmp);
		}
		sc.close();
		return dataset;
	}
}
