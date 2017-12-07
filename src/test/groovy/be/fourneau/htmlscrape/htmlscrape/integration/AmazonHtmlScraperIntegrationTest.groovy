package be.fourneau.htmlscrape.htmlscrape.integration

import be.fourneau.htmlscrape.htmlscrape.dto.PriceDto
import spock.lang.Specification
import spock.lang.Unroll

class AmazonHtmlScraperIntegrationTest extends Specification {
    def subject = new AmazonHtmlScraper()

    @Unroll
    def "Get price"() {
        given:
        when:
        def result = subject.getPrice(url)

        then:
        result == expectedResult

        where:
        url                                   | expectedResult
  //      'https://www.amazon.de/dp/B01CCT2PQS' | new PriceDto(Double.parseDouble("69.95"))
  //      'https://www.amazon.de/dp/B00PGZP8HE' | new PriceDto(Double.parseDouble("99.98"))
        'https://www.amazon.de/dp/B01J41KZZ0' | new PriceDto(Double.parseDouble("12.21"))


    }

}