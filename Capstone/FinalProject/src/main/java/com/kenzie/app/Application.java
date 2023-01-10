package com.kenzie.app;

// import necessary libraries

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Scanner;
import java.util.Random;

public class Application {
    /* Java Fundamentals Capstone project:
       - Define as many variables, properties, and methods as you decide are necessary to
       solve the program requirements.
       - You are not limited to only the class files included here
       - You must write the HTTP GET call inside the CustomHttpClient.sendGET(String URL) method
         definition provided
       - Your program execution must run from the main() method in Main.java
       - The rest is up to you. Good luck and happy coding!
     */
    public static void main(String[] args) throws JsonProcessingException {
        //Write main execution code here
        int questions = 0;
        int points =0;
        while (questions < 10) {
            try{
                //Generate random number as ID for question
                Random rand = new Random();
                int randomNumber = rand.nextInt(100) + 1;
                //Make get request to get random clue
                String randomClue = CustomHttpClient.sendGET("https://jservice.kenzie.academy/api/clues/" + randomNumber);

                ObjectMapper objectMapper = new ObjectMapper();
                Clues clue = objectMapper.readValue(randomClue, Clues.class);

                System.out.println("Category: "+clue.getCategory().getTitle()+" Question: "+clue.getQuestion());
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter your answer: ");
                String answer = scan.nextLine();
                checkForEmptyString(answer);

                String correctAnswer = clue.getAnswer().replaceAll("\\s","");
                answer= answer.replaceAll("\\s","");

                if (answer.equalsIgnoreCase(correctAnswer)){
                    System.out.println("Correct Answer!");
                    points++;
                }
                else {
                    System.out.println("Sorry! That answer was incorrect. The correct answer is "+ clue.getAnswer());
                }

            }
            catch (CustomEmptyStringException e){
                System.out.println(e.getMessage());
            }
            questions++;
        }
        System.out.println("Your total points are: "+points);
    }
    public static void checkForEmptyString(String input){
        if (input.isEmpty()|| input.matches("^\\s*$")){
            throw new CustomEmptyStringException("Invalid input: Empty string entered. A new question will be provided.");
        }

    }
}