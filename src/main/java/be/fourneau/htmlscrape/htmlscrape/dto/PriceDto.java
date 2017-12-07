package be.fourneau.htmlscrape.htmlscrape.dto;

public class PriceDto {
    private final Double price;

    public PriceDto(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceDto priceDto = (PriceDto) o;

        return price != null ? price.equals(priceDto.price) : priceDto.price == null;
    }

    @Override
    public int hashCode() {
        return price != null ? price.hashCode() : 0;
    }
}
