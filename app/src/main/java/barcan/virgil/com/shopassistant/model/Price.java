package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 29.11.2015.
 */
public class Price {

    private Double priceValue;
    private String priceCurrency;

    public Price() {
        priceValue = 0.0;
        priceCurrency = "";
    }

    public Price(Double priceValue, String priceCurrency) {
        this.priceValue = priceValue;
        this.priceCurrency = priceCurrency;
    }

    public Double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Double priceValue) {
        this.priceValue = priceValue;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }
}
