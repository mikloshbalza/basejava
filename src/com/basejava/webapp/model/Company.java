package com.basejava.webapp.model;

import java.net.URL;
import java.util.Objects;

public class Company {
    private final String url;
    private final String name;

    public Company(String url, String name) {
        Objects.requireNonNull(url,"url must not be null");
        Objects.requireNonNull(name,"name must not be null");
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return url.equals(company.url) && name.equals(company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, name);
    }

    @Override
    public String toString() {
        return "Company{" +
                "url=" + url +
                ", name='" + name + '\'' +
                '}';
    }
}
