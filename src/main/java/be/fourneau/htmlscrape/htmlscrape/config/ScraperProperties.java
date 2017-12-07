package be.fourneau.htmlscrape.htmlscrape.config;

import be.fourneau.htmlscrape.htmlscrape.dto.ItemDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@ConfigurationProperties(prefix = "scraper")
public class ScraperProperties {
    private Collection<ScraperSite> scraperSites = new ArrayList<>();
    private Collection<ItemDto> itemsToWatch = new ArrayList<>();
    private ScraperSiteType scraperSiteType;

    public ScraperProperties() {
    }

    public Collection<ScraperSite> getScraperSites() {
        return scraperSites;
    }

    public void setScraperSites(Collection<ScraperSite> scraperSites) {
        this.scraperSites = scraperSites;
    }

    public ScraperSiteType getScraperSiteType() {
        return scraperSiteType;
    }

    public void setScraperSiteType(ScraperSiteType scraperSiteType) {
        this.scraperSiteType = scraperSiteType;
    }

    public Collection<ItemDto> getItemsToWatch() {
        return itemsToWatch;
    }

    public void setItemsToWatch(Collection<ItemDto> itemsToWatch) {
        this.itemsToWatch = itemsToWatch;
    }

    @Override
    public String toString() {
        return "ScraperProperties{" +
                "scraperSites=" + scraperSites +
                ", itemsToWatch=" + itemsToWatch +
                ", scraperSiteType=" + scraperSiteType +
                '}';
    }
}
