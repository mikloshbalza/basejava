package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final String name;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(String name, String description, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(name,"name must not be null");
        Objects.requireNonNull(description,"description must not be null");
        Objects.requireNonNull(startDate,"start date must not be null");
        Objects.requireNonNull(endDate,"end date must not be null");
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return name.equals(period.name) && description.equals(period.description) && startDate.equals(period.startDate) && endDate.equals(period.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Period{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
