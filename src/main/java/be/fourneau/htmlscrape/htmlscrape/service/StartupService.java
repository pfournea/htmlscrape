package be.fourneau.htmlscrape.htmlscrape.service;

import be.fourneau.htmlscrape.htmlscrape.config.ScraperProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!unittest")
public class StartupService implements CommandLineRunner {
    private final ScraperProperties scraperProperties;
    private final PriceWatchService priceWatchService;

    public StartupService(ScraperProperties scraperProperties, PriceWatchService priceWatchService) {
        this.scraperProperties = scraperProperties;
        this.priceWatchService = priceWatchService;
    }


    @Override
    public void run(String... args) throws Exception {
        process();
    }


    private void process() {
        scraperProperties.getItemsToWatch().stream()
                .forEach(itemDto -> {
                    priceWatchService.handle(itemDto, scraperProperties.getScraperSites());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }
}
