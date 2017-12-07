package be.fourneau.htmlscrape.htmlscrape.domain.model;

import java.util.Collection;

public interface ItemRepository {
    void save(Collection<Item> items);
}
