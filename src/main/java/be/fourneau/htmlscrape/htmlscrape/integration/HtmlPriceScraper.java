package be.fourneau.htmlscrape.htmlscrape.integration;

import be.fourneau.htmlscrape.htmlscrape.dto.PriceDto;

public interface HtmlPriceScraper {
    PriceDto getPrice(String url);
}
