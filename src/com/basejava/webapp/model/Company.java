package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String name;
    private List<Period> periods;

    public Company() {
    }

    public Company(String url, String name,Period... periods){
        this(url, name, Arrays.asList(periods));
    }
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
