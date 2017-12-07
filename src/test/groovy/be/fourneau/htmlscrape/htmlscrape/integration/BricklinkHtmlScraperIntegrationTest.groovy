package be.fourneau.htmlscrape.htmlscrape.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BricklinkHtmlScraperIntegrationTest extends Specification {


    @Autowired
    BricklinkHtmlScraper bricklinkHtmlScraper

    @Unroll
    def "Get price"() {
        given:
        def url = 'https://www.bricklink.com/ajax/clone/catalogifs.ajax?itemid=150086&ss=BE&cond=N&ii=0&iconly=0'
        when:
        def result = bricklinkHtmlScraper.getPrice(url)

        then:
        result != null
        println 'average price is '+ result.price

    }
}
