package be.fourneau.htmlscrape.htmlscrape.domain.model;

import be.fourneau.htmlscrape.htmlscrape.config.ScraperSiteType;

public class PricePerStore {
    private final ScraperSiteType scraperSiteType;
    private final Double price;


    public PricePerStore(ScraperSiteType scraperSiteType, Double price) {
        this.scraperSiteType = scraperSiteType;
        this.price = price;
    }

    public ScraperSiteType getScraperSiteType() {
        return scraperSiteType;
    }

    public Double getPrice() {
        return price;
    }
}
