package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 29.11.2015.
 */
public class Price {

    private String priceID;
    private Double priceValue;
    private String priceCurrency;

    public Price() {
        priceID = "";
        priceValue = 0.0;
        priceCurrency = "";
    }

    public Price(Double priceValue, String priceCurrency) {
        this.priceID = "";
        this.priceValue = priceValue;
        this.priceCurrency = priceCurrency;
    }

    public Price(String priceID, Double priceValue, String priceCurrency) {
        this.priceID = priceID;
        this.priceValue = priceValue;
        this.priceCurrency = priceCurrency;
    }

    public Price(Price price) {
        this.priceID = new String(price.getPriceID());
        this.priceValue = new Double(price.getPriceValue());
        this.priceCurrency = new String(price.getPriceCurrency());
    }

    public String getPriceID() {
        return priceID;
    }

    public void setPriceID(String priceID) {
        this.priceID = priceID;
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
