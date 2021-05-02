package pl.mat.processor;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import pl.mat.model.db.Bitcoin;
import pl.mat.parser.DateParser;
import pl.mat.repository.StockRepository;

import java.util.Date;

@Slf4j
@Component
@Builder
public class CoindeskPriceProcessor implements StockPriceProcessor {

    private StockRepository stockRepository;

    public CoindeskPriceProcessor(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void processStockPrice(JSONObject responseObj) {
        Bitcoin bitcoin = getBitcoin(responseObj);
        stockRepository.save(bitcoin);
    }

    private Bitcoin getBitcoin(JSONObject responseObj) {
        String stockName = responseObj.getString("chartName");
        double stockPrice = responseObj
                .getJSONObject("bpi")
                .getJSONObject("USD")
                .getDouble("rate_float");
        String updatedTimeString = responseObj
                .getJSONObject("time")
                .getString("updatedISO");
        Date updatedTime = DateParser.parseFromString(updatedTimeString);

        return Bitcoin.builder()
                .price(stockPrice)
                .stockName(stockName)
                .timeUpdated(updatedTime)
                .build();
    }

}
