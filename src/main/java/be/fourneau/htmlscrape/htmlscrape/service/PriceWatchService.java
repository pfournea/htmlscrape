package be.fourneau.htmlscrape.htmlscrape.service;

import be.fourneau.htmlscrape.htmlscrape.config.ScraperSite;
import be.fourneau.htmlscrape.htmlscrape.dto.ItemDto;

import java.util.Collection;

public interface PriceWatchService {
    void handle(ItemDto itemToWatch, Collection<ScraperSite> scraperSiteCollection);
}
