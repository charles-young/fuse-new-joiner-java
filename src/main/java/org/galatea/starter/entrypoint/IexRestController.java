package org.galatea.starter.entrypoint;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import net.sf.aspect4log.Log.Level;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.galatea.starter.service.IexService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Log(enterLevel = Level.INFO, exitLevel = Level.INFO)
@Validated
@RestController
@RequiredArgsConstructor
public class IexRestController {

  @NonNull
  private IexService iexService;

  /**
   * Exposes an endpoint to get all of the symbols available on IEX.
   *
   * @return a list of all IexStockSymbols.
   */
  @GetMapping(value = "${mvc.iex.getAllSymbolsPath}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public List<IexSymbol> getAllStockSymbols() {
    return iexService.getAllSymbols();
  }

  /**
   * Get the last traded price for each of the symbols passed in.
   *
   * @param symbols list of symbols to get last traded price for.
   * @return a List of IexLastTradedPrice objects for the given symbols.
   */
  @GetMapping(value = "${mvc.iex.getLastTradedPricePath}", produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public List<IexLastTradedPrice> getLastTradedPrice(
      @RequestParam(value = "symbols") final List<String> symbols) {
    return iexService.getLastTradedPriceForSymbols(symbols);
  }

  /**
   * Get adjusted historical price data of a symbol for up to 15 years
   * @param symbol symbol to get historical data
   * @param date (optional) date to get price data (YYYYMMDD)
   * @param range (optional) range to get price data (max, 5y, 2y, 1y, ytd, 6m, 3m, 1m, 1mm, 5d, 5dm, date, dynamic)
   * @param token access token for API
   * @return a list of historic symbol price objects for date or date range.
   */
  @GetMapping(value = "${mvc.iex.getHistoricalPricesPath}", produces = {
          MediaType.APPLICATION_JSON_VALUE
  })
  public List<IexHistoricalPrice> getHistoricalPrices(
          @RequestParam(value = "symbol") final String symbol,
          @RequestParam(value = "date", required = false) final String date,
          @RequestParam(value = "range", required = false) final String range,
          @RequestParam(value = "token") final String token
          ) {
    return iexService.getHistoricalPriceForSymbol(symbol, date, range, token);
  }
}
