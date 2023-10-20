package com.basejava.webapp.model;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Company {
    private final String url;
    private final String name;
    private final List<Period> periods;

    public Company(String url, String name, List<Period> periods) {
        Objects.requireNonNull(url, "url must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.url = url;
        this.name = name;
        this.periods = periods;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return url.equals(company.url) && name.equals(company.name) && periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, name, periods);
    }

    @Override
    public String toString() {
        return "Company{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", periods=" + periods +
                '}';
    }
}
