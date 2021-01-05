package json.currency;

import java.util.List;

public class CurrencyInfo {
    String table;
    String currency;
    String code;
    List<Rates> rates;

    public CurrencyInfo() {
    }

    public String getTable() {
        return table;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public List<Rates> getRates() {
        return rates;
    }
}
