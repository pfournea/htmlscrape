package be.fourneau.htmlscrape.htmlscrape.config;

public class ScraperSite {
    private String name;
    private String url;
    private ScraperSiteType scraperSiteType;

    public ScraperSite(String name, String url, ScraperSiteType scraperSiteType) {
        this.name = name;
        this.url = url;
        this.scraperSiteType = scraperSiteType;
    }

    public ScraperSite() {
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ScraperSiteType getScraperSiteType() {
        return scraperSiteType;
    }

    public void setScraperSiteType(ScraperSiteType scraperSiteType) {
        this.scraperSiteType = scraperSiteType;
    }

    @Override
    public String toString() {
        return "ScraperSite{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", scraperSiteType=" + scraperSiteType +
                '}';
    }
}
