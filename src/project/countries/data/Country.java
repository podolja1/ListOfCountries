package project.countries.data;

import java.io.Serializable;

/**
 * trida definujici "objekt" Country
 * obsahuje inicializacni prommenne, konstruktory, gettery a settery
 */
public class Country implements Serializable {
    /**
     * inicializacni promenne
     * popisuji vlastnosti tridy (objektu)
     */
    private String name;            // nazev zeme
    private String capitol;         // hlavni mesto
    private String currency;        // mena
    private String domain;          // internetova domena

    /**
     * parametrizovany konstruktor (4 param.)
     * inicializuji tridu, tj. tvori objekt
     */
    public Country(String name, String capitol, String currency, String domain) {
        this.name = name;
        this.capitol = capitol;
        this.currency = currency;
        this.domain = domain;
    }
    /**
     * pristupove metody
     * zjistuji a upravuji promenne
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapitol() {
        return capitol;
    }

    public void setCapitol(String capitol) {
        this.capitol = capitol;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}