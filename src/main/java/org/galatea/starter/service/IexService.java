package org.galatea.starter.service;

import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * A layer for transformation, aggregation, and business required when retrieving data from IEX.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IexService {

  @NonNull
  private IexClient iexClient;


  /**
   * Get all stock symbols from IEX.
   *
   * @return a list of all Stock Symbols from IEX.
   */
  public List<IexSymbol> getAllSymbols() {
    return iexClient.getAllSymbols();
  }

  /**
   * Get the last traded price for each Symbol that is passed in.
   *
   * @param symbols the list of symbols to get a last traded price for.
   * @return a list of last traded price objects for each Symbol that is passed in.
   */
  public List<IexLastTradedPrice> getLastTradedPriceForSymbols(final List<String> symbols) {
    if (CollectionUtils.isEmpty(symbols)) {
      return Collections.emptyList();
    } else {
      return iexClient.getLastTradedPriceForSymbols(symbols.toArray(new String[0]));
    }
  }

  /**
   * Get adjusted historical price data of a symbol for up to 15 years
   *
   * @param symbol symbol to get historical data
   * @param range range to get price data (max, 5y, 2y, 1y, ytd, 6m, 3m, 1m, 1mm, 5d, 5dm, dynamic)
   * @return a list of historic symbol price objects for date or date range.
   */
  public List<IexHistoricalPrice> getHistoricalPriceForRange(final String symbol, final String range) {
    if (symbol.isEmpty()) {
      return Collections.emptyList();
    } else {
      return iexClient.getHistoricalPriceForRange(symbol, range);
    }
  }

  public List<IexHistoricalPrice> getHistoricalPriceForDate(final String symbol, final String date) {
    if (symbol.isEmpty()) {
      return Collections.emptyList();
    } else {
      return iexClient.getHistoricalPriceForDate(symbol, date);
    }
  }
}
