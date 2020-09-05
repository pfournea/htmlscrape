package be.fourneau.htmlscrape.htmlscrape.integration;

import be.fourneau.htmlscrape.htmlscrape.dto.PriceDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class AmazonHtmlScraper implements HtmlPriceScraper {
    @Override
    public PriceDto getPrice(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .header("Accept-Language", "nl-NL,nl;q=0.9,en-US;q=0.8,en;q=0.7,fr;q=0.6,de;q=0.5")
                    .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36")
                    .timeout(3000)
                    .get();
            Element priceblock_ourprice = doc.getElementById("priceblock_ourprice");
            if (priceblock_ourprice != null) {
                return getPriceDto(priceblock_ourprice);
            } else {
                Element buyBox = doc.getElementById("unqualifiedBuyBox");
                if (null != buyBox) {
                    Elements elementsByClass = buyBox.getElementsByClass("a-color-price");
                    if (elementsByClass != null && elementsByClass.first() != null) {
                        return getPriceDto(elementsByClass.first());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PriceDto(new Double(0));
    }

    private PriceDto getPriceDto(Element priceblock_ourprice) {
        String priceWithEuroAsString = priceblock_ourprice.text();
        String priceAsString = priceWithEuroAsString.replace("EUR", "").replace(",", ".").replace("€", "").replaceAll("[^\\d.]", "");
        System.out.println(priceWithEuroAsString);
        return new PriceDto(Double.valueOf(priceAsString));
    }
}
