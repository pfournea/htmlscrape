package be.fourneau.htmlscrape.htmlscrape.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("unittest")
class ScraperPropertiesIntegrationTest extends Specification {
    @Autowired
    ScraperProperties scraperProperties

    def "Test scraperProperties.scraperSites"() {
        given:

        when:
        def sites = scraperProperties.scraperSites

        then:
        sites.size() == 2
        sites.each {it.scraperSiteType != null}
        println scraperProperties

    }

    def "Test scraperProperties.items"() {
        given:

        when:
        def sites = scraperProperties.itemsToWatch

        then:
        sites.size() == 1
        sites.each {it.name != null && it.sites.size() == 2}
        println scraperProperties

    }
}
