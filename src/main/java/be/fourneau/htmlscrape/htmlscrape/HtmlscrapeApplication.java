package be.fourneau.htmlscrape.htmlscrape;

import be.fourneau.htmlscrape.htmlscrape.config.ScraperProperties;
import be.fourneau.htmlscrape.htmlscrape.service.GoogleSheetServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HtmlscrapeApplication {
	@Autowired
	private ScraperProperties scraperProperties;

	@Autowired
	private GoogleSheetServiceTest googleSheetServiceTest;

	public static void main(String[] args) {
		SpringApplication.run(HtmlscrapeApplication.class, args);
	}

}
