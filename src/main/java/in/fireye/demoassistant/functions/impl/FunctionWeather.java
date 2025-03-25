package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.FunctionEnum;
import in.fireye.demoassistant.functions.IFunction;
import in.fireye.demoassistant.functions.IParameter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class FunctionWeather implements IFunction<ParameterWeather> {

  //  @Resource
//  private IWeatherService weatherService;
  @Override
  public String execute(IParameter parameter) {
    ParameterWeather weatherParameter = (ParameterWeather) parameter;
    String date = weatherParameter.getDate();
    if (StringUtils.isAnyEmpty(date)) {
      date = DateTime.now().toString("yyyy-MM-dd");
    }

    return String.format("%s %s 的天气是晴天", date, weatherParameter.getLocation());
  }

  @Override
  public FunctionEnum type() {
    return FunctionEnum.WEATHER;
  }
}
