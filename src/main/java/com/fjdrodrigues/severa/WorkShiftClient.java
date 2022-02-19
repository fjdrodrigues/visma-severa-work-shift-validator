/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fjdrodrigues.severa;

import java.lang.System;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author fjdro
 */
public class WorkShiftClient {
    
    public static void main(String args[]) {
        
        // Read from Console
        Scanner in = new Scanner(System.in);
        
        boolean exit = false;
        boolean incorrectInput = false;
        while(!exit) {
            System.out.println("Work Shift Client - Powered by Visma Severa");
            System.out.println("Menu:");
            System.out.println("1 - Add Shift");
            System.out.println("2 - Exit Application");
            if (incorrectInput) {
                System.out.println("Incorrect input, try again!");
                incorrectInput = false;
            }
            System.out.println("Select an option: ");
            String input = in.nextLine();
            if (input.equals("1")) {
                boolean wrongDate = false;
                LocalDate shiftDate = null;
                do {
                    if (wrongDate) {
                        System.out.println("Incorrect Date Format!");
                        wrongDate = false;
                    }
                    System.out.println("Date of the Shift (yyyy-MM-dd): ");
                    String dateInput = in.nextLine();

                    try {
                        shiftDate = LocalDate.parse(dateInput);
                    } catch (DateTimeParseException e) {
                    } finally {
                        if (shiftDate == null)
                            wrongDate = true;
                    }
                } while(wrongDate);
                boolean wrongShift = false;
                String errorMessage = "An Error occured!";
                do {
                    if (wrongShift) 
                        System.out.println(errorMessage);
                    System.out.println("Shift starting time (HH:mm): ");
                    String startingTime = in.nextLine();
                    System.out.println("Shift ending time (HH:mm): ");
                    String endingTime = in.nextLine();
                    WorkShiftValidator shift = new WorkShiftValidator(shiftDate, startingTime, endingTime);
                    wrongShift = !shift.isValid();
                    if (wrongShift) {
                        errorMessage = 
                            shift.getMessage().isPresent() ? shift.getMessage().get() : "An Error occured!";
                    } else {
                        System.out.println("Shift is Valid.");
                        if (shift.getDuration().isPresent())
                            System.out.println("Duration: "+shift.getDuration().get());
                    }
                } while(wrongShift);
            } else if (input.equals("2")) {
                exit = true;
            } else {
                incorrectInput = true;
            }
        }
        System.exit(0);
    }
}
