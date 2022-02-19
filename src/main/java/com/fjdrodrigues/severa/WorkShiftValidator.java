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
    private Optional<String> message;
    private boolean isValid;
    
    public WorkShiftValidator(LocalDate date, String startingTime, String endingTime) {
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.duration = Optional.empty();
        this.message = Optional.empty();
        this.isValid = validate();
    }
    
    /* Validate Shift */
    private boolean validate() {
        if (!validateHourPattern(this.startingTime)) {
            this.message = Optional.of("Invalid starting time format.");
            return false;
        }
        if (!validateHourPattern(this.endingTime)) {
            this.message = Optional.of("Invalid ending time format.");
            return false;
        }
        double startingHours = Double.parseDouble(this.startingTime.substring(0, 2));
        double startingMinutes = Double.parseDouble(this.startingTime.substring(3, 5));
        
        double endingHours = Double.parseDouble(this.endingTime.substring(0, 2));
        double endingMinutes = Double.parseDouble(this.endingTime.substring(3, 5));
        
        if (endingHours < startingHours) {
            this.message = Optional.of("Ending preceeds starting time.");
            return false;
        } else if ( endingHours == startingHours && startingMinutes > endingMinutes) {
            this.message = Optional.of("Ending preceeds starting time.");
            return false;
        }
        
        double duration = (endingHours + endingMinutes/60) - (startingHours + startingMinutes/60);

        if (duration == 0) {
            this.message = Optional.of("End is equal to starting time.");
            return false;
        }
        if (duration > 16) {
            this.message = Optional.of("Shift is over 16 hours long.");
            return false;
        }
        this.duration = Optional.of(duration);
        return true;
    }
    
    private boolean validateHourPattern(String time) {
        return time.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
    }
    
    public boolean isValid() {
        return this.isValid;
    }
    
    public Optional<String> getMessage() {
        return this.message;
    }
    
    public Optional<Double> getDuration() {
        return this.duration;
    }
}
