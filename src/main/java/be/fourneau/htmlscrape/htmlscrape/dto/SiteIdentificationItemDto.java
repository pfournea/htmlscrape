package be.fourneau.htmlscrape.htmlscrape.dto;

import be.fourneau.htmlscrape.htmlscrape.config.ScraperSiteType;

public class SiteIdentificationItemDto {
    private String id;
    private ScraperSiteType scraperSiteType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ScraperSiteType getScraperSiteType() {
        return scraperSiteType;
    }

    public void setScraperSiteType(ScraperSiteType scraperSiteType) {
        this.scraperSiteType = scraperSiteType;
    }

    @Override
    public String toString() {
        return "SiteIdentificationItemDto{" +
                "id='" + id + '\'' +
                ", scraperSiteType=" + scraperSiteType +
                '}';
    }
}
