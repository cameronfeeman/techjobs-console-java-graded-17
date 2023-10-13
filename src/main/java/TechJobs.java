import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll(),"");
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();


                //Add .toUpperCase to both of these to get rid of case in-sensitivity
                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm.toUpperCase()),searchTerm);

                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm.toUpperCase()),searchTerm);
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    //Method with Two Parameters: Array of HashMaps and Search Term(String)
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs,String searchTerm) {
        //Checks if array list is empty
        if (someJobs.isEmpty()) {
            //Check if search term is null or empty
            if (searchTerm == null || searchTerm.isEmpty()) {
                //if both if statements true print no results
                System.out.println("No Results");
                //Prints message listing the users search term and no results if none are found
            } else {
                System.out.println("Search term:\n" + searchTerm + " with No Results");
                System.out.println("No Results");
            }
        }
            // Looping through. for each job in the array do the following
            for (HashMap<String, String> job : someJobs) {
                System.out.println("*****");

                // nested loop. for each key-value pair in the HashMap do the following
                for (Map.Entry<String, String> entry : job.entrySet()){

                    //pull the key and value pair
                    String key = entry.getKey();
                    String value = entry.getValue();

                    //Print the key and it's value
                    System.out.println(key + ": " + value);

                }


                System.out.println("*****\n");

            }


        }


    }
