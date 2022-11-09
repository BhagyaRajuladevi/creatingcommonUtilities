package genericLibraries;

import java.util.Random;

public class JavaUtility {
	
	public int generateRandomNumber(int limit) {
		Random random = new Random();
		int randomNum = random.nextInt(limit);
		return randomNum;
	}

}
