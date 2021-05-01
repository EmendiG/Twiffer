package pl.mat.client;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import pl.mat.processor.CoindeskPriceProcessor;

@Slf4j
@Getter
@Component
@Builder
public class StockClientImpl implements StockClient {

    private static final String API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    private final CoindeskPriceProcessor coindeskPriceProcessor;

    public StockClientImpl(CoindeskPriceProcessor coindeskPriceProcessor) {
        this.coindeskPriceProcessor = coindeskPriceProcessor;
    }

    @Override
    public void startFetchProcess() {
        HttpResponse<JsonNode> response = Unirest.get(API_URL)
                .header("accept", "application/json")
                .asJson();

        JSONObject responseObj = response.getBody().getObject();
        coindeskPriceProcessor.processStockPrice(responseObj);
    }

}
