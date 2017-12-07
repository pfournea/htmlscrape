package be.fourneau.htmlscrape.htmlscrape.integration;

import be.fourneau.htmlscrape.htmlscrape.dto.PriceDto;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BricklinkHtmlScraper implements HtmlPriceScraper {
    private final HttpClient httpClient;
    private static final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

    public BricklinkHtmlScraper(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public PriceDto getPrice(String url) {
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        get.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");
        get.setHeader("X-Requested-With", "XMLHttpRequest");
        try {
            HttpResponse response = httpClient.execute(get);
            JsonParser jsonParser = jacksonFactory.createJsonParser(response.getEntity().getContent());
            Map<String, Object> parsedObject = new HashMap<>();
            jsonParser.parse(parsedObject);
            List<Map<String, Object>> lists = (List<Map<String, Object>>) parsedObject.get("list");
            Double averagePrice = lists.stream()
                    .map(stringObjectMap -> stringObjectMap.get("mDisplaySalePrice"))
                    .map(priceAsString -> StringUtils.replace((String) priceAsString, "EUR", ""))
                    .map(priceAsStringWith -> StringUtils.replace(priceAsStringWith, ",", "."))
                    .mapToDouble(Double::valueOf)
                    .average()
                    .orElse(new Double(0));
            return new PriceDto(averagePrice);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }

        return new PriceDto(new Double(0));
    }
}
