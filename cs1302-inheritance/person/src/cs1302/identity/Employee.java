package cs1302.identity;

import java.time.LocalDate;

/**
 * An {@code Employee} is a person who is employed.
 */
public class Employee extends Person {

    private long id;
    private LocalDate dateOfHire;

    /**
     * Constructs a {@code Employee} object with the given {@code id},
     * {@code name}, {@code dateOfBirth}, and {@code dateOfHire}.
     *
     * @param id           employee id
     * @param name         name of employee
     * @param dateOfBirth  date of birth
     * @param dateOfHire   date of hire
     */
    public Employee(long id, String name, LocalDate dateOfBirth, LocalDate dateOfHire) {
        super(name, dateOfBirth);
        this.id = id;
        this.dateOfHire = dateOfHire;
    } // Employee

    /**
     * Returns the employee id.
     *
     * @return employee id
     */
    public long getId() {
        return id;
    } // getId

    /**
     * Returns the hiring date for this employee.
     *
     * @return hiring date for this employee
     */
    public LocalDate getDateOfHire() {
        return dateOfHire;
    } // getDateOfHire

    @Override
    public String toString() {
        return String.format("Employee(id = %d, name = %s, dateOfBirth = %s, dateOfHire = %s)",
        id, getName(), getDateOfBirth(), dateOfHire);
    } // toString

} // Employee
