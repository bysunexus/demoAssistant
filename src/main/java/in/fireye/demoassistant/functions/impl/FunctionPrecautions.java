package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.FunctionEnum;
import in.fireye.demoassistant.functions.IFunction;
import in.fireye.demoassistant.functions.IParameter;
import org.springframework.stereotype.Component;

@Component
public class FunctionPrecautions implements IFunction<ParameterCity> {
  @Override
  public String execute(IParameter parameter) {
    ParameterCity parameterCity = (ParameterCity) parameter;
    return "旅行提示\n" +
      "衣着：丽江日夜温差大，即使在夏天也需携带厚外套。\n" +
      "高反：丽江海拔约2400米，部分人可能会有高原反应。建议游客到达后先适应一下高海拔环境，适当休息，多喝水，少进行剧烈运动。前往高海拔地区如玉龙雪山前，建议预防高原反应。";
  }

  @Override
  public FunctionEnum type() {
    return FunctionEnum.PRECAUTIONS;
  }
}
