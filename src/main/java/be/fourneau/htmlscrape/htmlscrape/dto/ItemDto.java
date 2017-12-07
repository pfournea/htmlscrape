package be.fourneau.htmlscrape.htmlscrape.dto;

import java.util.ArrayList;
import java.util.Collection;

public class ItemDto {
    private String name;
    private Collection<SiteIdentificationItemDto> sites = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<SiteIdentificationItemDto> getSites() {
        return sites;
    }

    public void setSites(Collection<SiteIdentificationItemDto> sites) {
        this.sites = sites;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "name='" + name + '\'' +
                ", sites=" + sites +
                '}';
    }
}
