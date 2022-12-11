package edu.upenn.cit594.ui;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

public class UI {
	
	protected Processor processor;
	protected Scanner in;
	protected String covidFile = null;
	protected String propertiesFile =null;
	protected String populationFile = null;
	
	public UI(Processor processor, Logger logger, String covidFile, String propertiesFile, String populationFile) {
		// TODO Auto-generated constructor stub
			this.processor = processor;
			this.covidFile = covidFile;
			this.propertiesFile = propertiesFile;
			this.populationFile = populationFile;
			in = new Scanner(System.in);
	}
	
	public void displayMenu() throws Exception {
		System.out.print("Please select one of the following possible actions by entering 0-7:\n"
						+"0. Exit the program.\n"
				        +"1. Show the available actions.\n"
						+"2. Show the total population for all ZIP Codes.\n"
						+"3. Show the total vaccinations per capita for each ZIP Code for the specified date.\n"
						+"4. Show the average market value for properties in a specified ZIP code.\n"
						+"5. Show the average total livable area for properties in a specified ZIP Code.\n"
						+"6. Show the total market value of properties, per capita, for a specified ZIP Code.\n"
						+"7. Show the results of custom feature.\n"
						+"> ");
		
		System.out.flush();
		int choice = 0;
		String str = in.nextLine();
		try {
			choice = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			System.out.println(str + " is not a valid option.");
			displayMenu();
		}
		
		switch (choice) {
			case 0:
				in.close();
				return;
			case 1:
				showAvailableActions();
				break;
			case 2:
				showTotalPopulation();
				break;
			case 3: 
				showVacciPerCapita();
				break;
				
			case 4:
				showAveragePropertyMarketValue();
				break;
			case 5:
				showAverageTotalLivableArea();
				break;
			case 6: 
				showTotalPropertyMarketValue();
				break;
			case 7:
				showTopTenExpensiveZipcodeDeath();
				break;
			default:
				System.out.println(choice + " is not a valid option.");	
				break;
		}
		displayMenu();
	}



	private void showAvailableActions() {
		// TODO Auto-generated method stub
		System.out.println("BEGIN OUTPUT");
		System.out.println(0);
		System.out.println(1);
		if(populationFile != null) { System.out.println(2);}
		if(populationFile != null && covidFile != null) { System.out.println(3);}
		if(propertiesFile != null) { System.out.println(4);System.out.println(5);}
		if(populationFile != null && propertiesFile != null) { System.out.println(6);}
		if(populationFile != null && propertiesFile != null && covidFile != null) { System.out.println(7);}
		System.out.println("END OUTPUT");
		
	}

	private void showTotalPopulation() {
		// TODO Auto-generated method stub
		if(populationFile != null) {
			System.out.println("BEGIN OUTPUT");
			int totalPopulation = processor.getTotalPopulationAll();
			System.out.println(totalPopulation);
			System.out.println("END OUTPUT");
		} else {
			System.out.println("Option 2 is not available.");
		}
	}
	
	private String specifyADate(){
		
		System.out.print("Which day are you searching for? Please enter a date in the format: YYYY-MM-DD.\n"+"> ");
		System.out.flush();
		String date = in.nextLine();
		Pattern pattern = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
	    Matcher matcher = pattern.matcher(date);
	    if (!matcher.find()) {
	    	System.out.println( date + " is not a valid date.");
	    	specifyADate();
	    }
		return date;
	}
	
	
	private String specifyAZipcode(){
		
		System.out.print("Which area are you searching for? Please enter a 5-digit Zipcode.\n"+"> ");
		System.out.flush();
		String zipcode = in.nextLine();
		Pattern pattern = Pattern.compile("^[0-9]{5}$");
	    Matcher matcher = pattern.matcher(zipcode);
	    if (!matcher.find()) {
	    	System.out.println( zipcode + " is not a valid date.");
	    	specifyAZipcode();
	    }
		return zipcode;
	}
	
	

	private void showVacciPerCapita() {
		// TODO Auto-generated method stub
		
		if(populationFile != null && covidFile != null) {
			System.out.print("Partial or full vaccinations per capita? Please type \"partial\" or \"full\". \n"+"> ");
			System.out.flush();
			String str = in.nextLine();
			if (str.toLowerCase().equals("partial") || str.toLowerCase().equals("full") ) {
				String date = specifyADate();
				Map<String, Double> vacciMap =  processor.getVaccinationsPerCapita(str, date);  
				System.out.println("BEGIN OUTPUT");
				if(vacciMap == null) {
					System.out.println(0);
				}else {
					for (Map.Entry<String,Double> entry : vacciMap.entrySet()) 
			            System.out.println(entry.getKey() + " " + entry.getValue());
				}
				System.out.println("END OUTPUT");
			}
			else {
				System.out.println(str + " is not a valid input.");
				showVacciPerCapita();
			}
			
		} else {
			System.out.println("Option 3 is not available.");
		}
		
	}

	private void showAveragePropertyMarketValue() {
		// TODO Auto-generated method stub
		if(propertiesFile != null) {
			String zip = specifyAZipcode();
			System.out.println("BEGIN OUTPUT");
			int averageMarketValue = processor.getAverageMarketValue(zip);
			System.out.println(averageMarketValue);
			System.out.println("END OUTPUT");
		} else {
			System.out.println("Option 4 is not available.");
		}
		
	}

	private void showAverageTotalLivableArea() {
		// TODO Auto-generated method stub
		if(propertiesFile != null) {
			String zip = specifyAZipcode();
			System.out.println("BEGIN OUTPUT");
			int averageLivable= processor.getAverageLivableArea(zip);
			System.out.println(averageLivable);
			System.out.println("END OUTPUT");
		} else {
			System.out.println("Option 5 is not available.");
		}
	}

	private void showTotalPropertyMarketValue() {
		// TODO Auto-generated method stub
		if(populationFile != null && propertiesFile != null) {
			String zip = specifyAZipcode();
			System.out.println("BEGIN OUTPUT");
			int totalPropertyValue = processor.getMarketValuePerCapita(zip);
			System.out.println(totalPropertyValue);
			System.out.println("END OUTPUT");
		}else {
			System.out.println("Option 6 is not available.");
		}
	}
	
	//this method will display the top ten expensive real estate zipcodes by ranking dollar/sqft value of zipcodes and their corresponding death rate.
	
	private void showTopTenExpensiveZipcodeDeath() {
		// TODO Auto-generated method stub
		if(populationFile != null && propertiesFile != null && covidFile != null) {
			System.out.println("BEGIN OUTPUT");
			Map<String, String[]> ppsf = processor.getPPSF_Death();
			for (Map.Entry<String,String[]> entry : ppsf.entrySet()) 
	            System.out.println(entry.getKey() + " " + entry.getValue()[0] + " "+entry.getValue()[1]);
			System.out.println("END OUTPUT");
		}else {
			System.out.println("Option 7 is not available.");
		}
		
	}
	

	
	
	
	

}
