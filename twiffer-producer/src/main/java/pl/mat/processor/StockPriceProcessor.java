package pl.mat.processor;

import org.json.JSONObject;

public interface StockPriceProcessor {
    void processStockPrice(JSONObject responseObj);
}
