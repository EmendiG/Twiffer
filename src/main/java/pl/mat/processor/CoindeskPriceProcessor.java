package pl.mat.processor;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import pl.mat.model.db.Bitcoin;
import pl.mat.repository.StockRepository;

import java.time.OffsetDateTime;

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
        String stockName = responseObj.getString("chartName");
        double stockPrice = responseObj
                .getJSONObject("bpi")
                .getJSONObject("USD")
                .getDouble("rate_float");
        String updatedTimeString = responseObj
                .getJSONObject("time")
                .getString("updatedISO");
        long updatedTime = parseDate(updatedTimeString);

        Bitcoin bitcoin = buildBitcoin(stockName, stockPrice, updatedTime);
        stockRepository.save(bitcoin);
    }
    private static long parseDate(String updatedTime) {
        return OffsetDateTime.parse( updatedTime ).toInstant().toEpochMilli();
    }


    private Bitcoin buildBitcoin(String stockName, double stockPrice, long timeUpdated) {
        return Bitcoin.builder()
                .price(stockPrice)
                .stockName(stockName)
                .timeUpdated(timeUpdated)
                .build();
    }

}
