package be.fourneau.htmlscrape.htmlscrape.service;

import be.fourneau.htmlscrape.htmlscrape.config.ScraperSite;
import be.fourneau.htmlscrape.htmlscrape.config.ScraperSiteType;
import be.fourneau.htmlscrape.htmlscrape.domain.model.Item;
import be.fourneau.htmlscrape.htmlscrape.domain.model.ItemRepository;
import be.fourneau.htmlscrape.htmlscrape.domain.model.PricePerStore;
import be.fourneau.htmlscrape.htmlscrape.dto.ItemDto;
import be.fourneau.htmlscrape.htmlscrape.dto.PriceDto;
import be.fourneau.htmlscrape.htmlscrape.integration.AmazonHtmlScraper;
import be.fourneau.htmlscrape.htmlscrape.integration.BricklinkHtmlScraper;
import be.fourneau.htmlscrape.htmlscrape.integration.HtmlPriceScraper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class DefaultPriceWatchService implements PriceWatchService {
    private final BricklinkHtmlScraper bricklinkHtmlScraper;
    private final AmazonHtmlScraper amazonHtmlScraper;
    private final ItemRepository itemRepository;

    public DefaultPriceWatchService(BricklinkHtmlScraper bricklinkHtmlScraper, AmazonHtmlScraper amazonHtmlScraper, ItemRepository itemRepository) {
        this.bricklinkHtmlScraper = bricklinkHtmlScraper;
        this.amazonHtmlScraper = amazonHtmlScraper;
        this.itemRepository = itemRepository;
    }

    @Override
    public void handle(ItemDto itemToWatch, Collection<ScraperSite> scraperSiteCollection) {
        ConcurrentMap<ScraperSiteType, PriceDto> pricesByScraperSiteType = itemToWatch.getSites()
                .parallelStream()
                .map(siteIdentificationItemDto -> {
                    ScraperSite site = scraperSiteCollection.stream()
                            .filter(scraperSite -> scraperSite.getScraperSiteType() == siteIdentificationItemDto.getScraperSiteType())
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException());
                    String url = String.format(site.getUrl(), siteIdentificationItemDto.getId());
                    return new PriceDtoWithScrapeSiteType(getHtmlPriceScraper(siteIdentificationItemDto.getScraperSiteType()).getPrice(url),
                            siteIdentificationItemDto.getScraperSiteType());
                })
                .collect(Collectors.toConcurrentMap(o -> o.getType(), o -> o.getPriceDto()));
        Collection<PricePerStore> pricePerStores = new ArrayList<>();
        pricesByScraperSiteType.forEach((type, priceDto) -> pricePerStores.add(new PricePerStore(type,priceDto.getPrice())));
        Item item = new Item(LocalDate.now(), itemToWatch.getName(), pricePerStores);
        itemRepository.save(Arrays.asList(item));
    }

    private HtmlPriceScraper getHtmlPriceScraper(ScraperSiteType scraperSiteType) {
        switch (scraperSiteType) {
            case BRICKLINK:
                return bricklinkHtmlScraper;
            case AMAZONDE:
                return amazonHtmlScraper;
            case AMAZONFR:
                return amazonHtmlScraper;
            default:
                throw new RuntimeException();
        }
    }

    private static final class PriceDtoWithScrapeSiteType {
        private final PriceDto priceDto;
        private final ScraperSiteType type;

        public PriceDtoWithScrapeSiteType(PriceDto priceDto, ScraperSiteType type) {
            this.priceDto = priceDto;
            this.type = type;
        }

        public PriceDto getPriceDto() {
            return priceDto;
        }

        public ScraperSiteType getType() {
            return type;
        }
    }
}
