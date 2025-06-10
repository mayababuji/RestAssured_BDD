package utilities;



import java.util.concurrent.ThreadLocalRandom;

import com.github.javafaker.Faker;

public class RandomGenerator {

	private Faker faker = new Faker();

//	public String generateRandomContactNumber() {
//
//		// Generate a random phone number
//		int firstDigit = faker.number().numberBetween(1, 9);
//		String remainingDigits = faker.number().digits(9);
//		return firstDigit + remainingDigits;
//	}
	public long generateRandomContactNumber() {
	    int firstDigit = faker.number().numberBetween(1, 9);
	    String remainingDigits = faker.number().digits(9); // 9 digits
	    String fullNumber = firstDigit + remainingDigits; // 10-digit number as String

	    return Long.parseLong(fullNumber); // Convert to long
	}

	public String generateRandomEmail() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 100); // Generates a number between 1 and 99
		return String.format("team2Maya%02d@gmail.com", randomNum); // Ensures two-digit format
	}

}
