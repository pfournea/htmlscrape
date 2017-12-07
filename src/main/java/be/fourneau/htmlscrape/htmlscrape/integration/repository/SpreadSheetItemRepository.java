package be.fourneau.htmlscrape.htmlscrape.integration.repository;

import be.fourneau.htmlscrape.htmlscrape.config.ScraperSiteType;
import be.fourneau.htmlscrape.htmlscrape.domain.model.Item;
import be.fourneau.htmlscrape.htmlscrape.domain.model.ItemRepository;
import be.fourneau.htmlscrape.htmlscrape.domain.model.PricePerStore;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
public class SpreadSheetItemRepository implements ItemRepository {
    private static final String SPREADSHEET_ID = "1nlCvsrdD5T8bA1JFlB4PRIz6BPkV5gfGO_KTSKcJjpI";
    private final Sheets sheetService;

    public SpreadSheetItemRepository(Sheets sheetService) {
        this.sheetService = sheetService;
    }

    @Override
    public void save(Collection<Item> items) {
        items.forEach(item -> {
            try {
                save(item);
            } catch (IOException e) {
                throw new RuntimeException("error saving new data to spreadsheet", e);
            }
        });

    }

    public void save(Item item) throws IOException {
        List<List<Object>> values = Arrays.asList(
                Arrays.asList(
                        item.getDate().format(DateTimeFormatter.ISO_DATE),
                        getPrice(item.getPrices(), ScraperSiteType.AMAZONDE),
                        getPrice(item.getPrices(), ScraperSiteType.BRICKLINK),
                        getPrice(item.getPrices(), ScraperSiteType.AMAZONFR)
                )
        );
        ValueRange body = new ValueRange()
                .setValues(values);

        AppendValuesResponse res = sheetService.spreadsheets().values()
                .append(SPREADSHEET_ID, String.format("%s!A1:E1", item.getName()), body)
                .setValueInputOption("RAW").execute();
    }

    private Object getPrice(Collection<PricePerStore> prices, ScraperSiteType scraperSiteType) {
        Double price = prices.stream()
                .filter(pricePerStore -> pricePerStore.getScraperSiteType() == scraperSiteType)
                .findFirst()
                .orElse(new PricePerStore(scraperSiteType, new Double(0)))
                .getPrice();
        return price.equals(new Double(0)) ? "" : price;
    }
}
