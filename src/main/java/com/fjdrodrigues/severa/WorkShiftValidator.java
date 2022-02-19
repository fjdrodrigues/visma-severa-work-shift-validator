/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fjdrodrigues.severa;

import java.time.LocalDate;
import java.util.Optional;

/**
 *
 * @author fjdro
 */
public class WorkShiftValidator {
    
    private LocalDate date;
    private String startingTime;
    private String endingTime;
    private Optional<Double> duration;
    private Optional<String> causeOfInvalidity;
    private boolean isValid;
    
    /**
     * Construct WorkShiftValidator
     * @param date LocalDate representing the date of the shift
     * @param startingTime HH:mm String representing the starting time of the shift
     * @param endingTime HH:mm String representing the ending time of the shift
     */
    public WorkShiftValidator(LocalDate date, String startingTime, String endingTime) {
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.duration = Optional.empty();
        this.causeOfInvalidity = Optional.empty();
        this.isValid = validate();
    }

    /**
     * Validates the Shift and defines the duration or the causeOfInvalidity.
     * @return Returns true if the shift is valid, false otherwise.
     */
    private boolean validate() {
        if (!validateHourPattern(this.startingTime)) {
            this.causeOfInvalidity = Optional.of("Invalid starting time format.");
            return false;
        }
        if (!validateHourPattern(this.endingTime)) {
            this.causeOfInvalidity = Optional.of("Invalid ending time format.");
            return false;
        }
        double startingHours = Double.parseDouble(this.startingTime.substring(0, 2));
        double startingMinutes = Double.parseDouble(this.startingTime.substring(3, 5));
        
        double endingHours = Double.parseDouble(this.endingTime.substring(0, 2));
        double endingMinutes = Double.parseDouble(this.endingTime.substring(3, 5));
        
        if (endingHours < startingHours) {
            this.causeOfInvalidity = Optional.of("Ending preceeds starting time.");
            return false;
        } else if ( endingHours == startingHours && startingMinutes > endingMinutes) {
            this.causeOfInvalidity = Optional.of("Ending preceeds starting time.");
            return false;
        }
        
        double duration = (endingHours + endingMinutes/60) - (startingHours + startingMinutes/60);

        if (duration == 0) {
            this.causeOfInvalidity = Optional.of("End is equal to starting time.");
            return false;
        }
        if (duration > 16) {
            this.causeOfInvalidity = Optional.of("Shift is over 16 hours long.");
            return false;
        }
        this.duration = Optional.of(duration);
        return true;
    }
    
    /**
     * Validates the format of time in HH:mm format.
     * @param time
     * @return Returns true if the format is valid, false otherwise.
     */
    private boolean validateHourPattern(String time) {
        return time.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
    }
    
    /**
     * Access validity of the shift.
     * @return Returns true if Work Shift is Valid, false otherwise.
     */
    public boolean isValid() {
        return this.isValid;
    }
    
    /**
     * Access causeOfInvalidity.
     * @return Returns an Optional containing the causeOfInvalidity or an empty Optional,
     * if the work shift is valid.
     */
    public Optional<String> getCauseOfInvalidity() {
        return this.causeOfInvalidity;
    }
    
    /**
     * 
     * @return Returns an Optional containing the duration of the shift, if the shift is valid, or
     * an empty Optional, if the shift is not valid.
     */
    public Optional<Double> getDuration() {
        return this.duration;
    }
}
