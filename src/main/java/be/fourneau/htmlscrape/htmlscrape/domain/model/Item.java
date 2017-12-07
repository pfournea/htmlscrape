package be.fourneau.htmlscrape.htmlscrape.domain.model;

import java.time.LocalDate;
import java.util.Collection;

public class Item {
    private final LocalDate date;
    private final String name;
    private final Collection<PricePerStore> prices;


    public Item(LocalDate date, String name, Collection<PricePerStore> prices) {
        this.date = date;
        this.name = name;
        this.prices = prices;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Collection<PricePerStore> getPrices() {
        return prices;
    }
}
