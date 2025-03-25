package in.fireye.demoassistant.functions;

import in.fireye.demoassistant.functions.impl.ParameterCity;
import in.fireye.demoassistant.functions.impl.ParameterDestination;
import in.fireye.demoassistant.functions.impl.ParameterPath;
import in.fireye.demoassistant.functions.impl.ParameterWeather;
import lombok.Getter;

import java.util.Map;

@Getter
public enum FunctionEnum {

  WEATHER("天气查询", ParameterWeather.class),
  PATH("路径规划", ParameterPath.class),
  DESTINATION("获取目的地建议", ParameterDestination.class),
  ATTRACTIONS("获取景点推荐", ParameterCity.class),
  CATERING("获取餐饮推荐", ParameterCity.class),
  PRECAUTIONS("获取旅行提示", ParameterCity.class),
  CUSTOMS("获取当地风俗", ParameterCity.class);

  FunctionEnum(String functionName, Class<? extends IParameter> parameterClass) {
    this.functionName = functionName;
    this.parameterClass = parameterClass;
  }

  private final String functionName;
  private final Class<? extends IParameter> parameterClass;

  private final static Map<String, FunctionEnum> MAP = Map.of(
    WEATHER.getFunctionName(), WEATHER,
    PATH.getFunctionName(), PATH,
    DESTINATION.getFunctionName(), DESTINATION,
    ATTRACTIONS.getFunctionName(), ATTRACTIONS,
    CATERING.getFunctionName(), CATERING,
    PRECAUTIONS.getFunctionName(), PRECAUTIONS,
    CUSTOMS.getFunctionName(), CUSTOMS
  );

  public static FunctionEnum getFunctionEnum(String functionName) {
    return MAP.get(functionName);
  }

}
