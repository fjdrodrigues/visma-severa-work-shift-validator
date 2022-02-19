/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.fjdrodrigues.severa;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author fjdro
 */
public class WorkShiftValidatorTest {
    
    public WorkShiftValidatorTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    /**
     * Test of isValid method, of class WorkShiftValidator.
     * @param expResult
     * @param date
     * @param startingTime
     * @param endingTime
     */
    //@org.junit.jupiter.api.Test
    @ParameterizedTest
    @MethodSource("isValidArguments")
    public void testIsValid(boolean expResult, String date, String startingTime, String endingTime) {
        System.out.println("isValid - "+expResult);
        WorkShiftValidator instance =
                new WorkShiftValidator(LocalDate.parse(date), startingTime, endingTime);
        boolean result = instance.isValid();
        assertEquals(expResult, result);
    }
    
    private static Stream<Arguments> isValidArguments() {
        return Stream.of(
          Arguments.of(true, "2022-02-02", "18:30", "19:20"),
          Arguments.of(false, "2022-02-02", "19:00", "19:00"),
          Arguments.of(false, "2022-02-02", "20:00", "19:50"),
          Arguments.of(false, "2022-02-02", "02:33", "19:50")
        );
    }
    
   
    /**
     * Test of getMessage method, of class WorkShiftValidator.
     * @param expResult
     * @param date
     * @param startingTime
     * @param endingTime
     */
    @ParameterizedTest
    @MethodSource("getMessageArguments")
    public void testGetMessage(String expResult, String date, String startingTime, String endingTime) {
        System.out.println("getMessage");
        WorkShiftValidator instance =
                new WorkShiftValidator(LocalDate.parse(date), startingTime, endingTime);
        if (expResult != null) {
            String result = instance.getMessage().get();
            assertEquals(expResult, result);
        } else {
            Optional<String> expResultOptional = Optional.empty();
            Optional<String> resultOptional = instance.getMessage();
            assertEquals(expResultOptional, resultOptional);
        }
    }
    
    private static Stream<Arguments> getMessageArguments() {
        return Stream.of(
          Arguments.of(null, "2022-02-02", "18:30", "19:20"),
          Arguments.of("Invalid starting time format.", "2022-02-02", "8:30", "19:20"),
          Arguments.of("Invalid starting time format.", "2022-02-02", "38:20", "19:20"),
          Arguments.of("Invalid starting time format.", "2022-02-02", "18:61", "19:40"),
          Arguments.of("Invalid ending time format.", "2022-02-02", "18:20", "9:20"),
          Arguments.of("Invalid ending time format.", "2022-02-02", "18:20", "19:70"),
          Arguments.of("Invalid ending time format.", "2022-02-02", "18:20", "24:20"),
          Arguments.of("Ending preceeds starting time.", "2022-02-02", "19:30", "19:20"),
          Arguments.of("Ending preceeds starting time.", "2022-02-02", "18:30", "18:20"),
          Arguments.of("End is equal to starting time.", "2022-02-02", "18:30", "18:30"),
          Arguments.of("Shift is over 16 hours long.", "2022-02-02", "03:30", "19:31")
        );
    }

    /**
     * Test of getDuration method, of class WorkShiftValidator.
     * @param expResult
     * @param date
     * @param startingTime
     * @param endingTime
     */
    @ParameterizedTest
    @MethodSource("getDurationArguments")
    public void testGetDuration(Object expResult, String date, String startingTime, String endingTime) {
        System.out.println("getDuration");
        WorkShiftValidator instance = 
                new WorkShiftValidator(LocalDate.parse(date), startingTime, endingTime);
        if (expResult != null) {
            double result = instance.getDuration().get();
            assertEquals((double) expResult, result);
        } else {
            Optional<Double> expResultOptional = Optional.empty();
            Optional<Double> resultOptional = instance.getDuration();
            assertEquals(expResultOptional, resultOptional);
        }
    }
    
    private static Stream<Arguments> getDurationArguments() {
        return Stream.of(
          Arguments.of(1.0, "2022-02-02", "18:20", "19:20"),
          Arguments.of(1.5, "2022-02-02", "18:00", "19:30"),
          Arguments.of(16.0, "2022-02-02", "00:00", "16:00"),
          Arguments.of(null, "2022-02-02", "01:30", "18:20")
        );
    }
    
}
