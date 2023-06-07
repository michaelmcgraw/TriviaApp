package com.kenzie.app;

// import necessary libraries

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Scanner;
import java.util.Random;

public class Application {

    static int finalScore;
    final static String URLSTRING = "https://jservice.kenzie.academy/api/clues/";
    public static void main(String[] args) throws JsonProcessingException {
        //Write main execution code here
        //loops until 10 questions are asked AND answered
        int questions = 1;
        while (questions < 11) {
            try{
                //Generate random number as ID for question
                Random rand = new Random();
                int randomNumber = rand.nextInt(355237) + 1;
                //Make get request to get random clue
                String randomClue = CustomHttpClient.sendGET(URLSTRING + randomNumber);
                checkForValidGet(randomClue);
                //Convert from JSON to DTO
                ObjectMapper objectMapper = new ObjectMapper();
                Clues clue = objectMapper.readValue(randomClue, Clues.class);
                //Print Category/Question
                System.out.println("Question "+questions+": "+"Category: "+clue.getCategory().getTitle()+" Question: "+clue.getQuestion());
                Scanner scan = new Scanner(System.in);
                //User enters answer
                System.out.println("Enter your answer: ");
                String answer = scan.nextLine();
                checkForEmptyString(answer);
                //Replaces white space in string
                String correctAnswer = clue.getAnswer().replaceAll("\\s","");
                answer= answer.replaceAll("\\s","");
                //checks if user entered correct answer
                if (answer.equalsIgnoreCase(correctAnswer)){
                    System.out.println("Correct Answer!");
                    finalScore++;
                }
                else {
                    System.out.println("Sorry! That answer was incorrect. The correct answer is "+ clue.getAnswer());
                }

            }
            catch (CustomEmptyStringException | CustomFailedGetException e){
                System.out.println(e.getMessage());
                questions--;
            }
            questions++;

        }
        //prints out total points
        System.out.println("Your total points are: "+finalScore);
    }
    public static void checkForEmptyString(String input){
        if (input.isEmpty()|| input.matches("^\\s*$")){
            throw new CustomEmptyStringException("Invalid input: Empty string entered. A new question will be provided.");
        }

    }
    public static void checkForValidGet(String input){
        if (input.equals("GET request failed: 404 status code received")){
            throw new CustomFailedGetException("Get request failed. Getting a new question");
        }
    }
}