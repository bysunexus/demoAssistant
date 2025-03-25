package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.FunctionEnum;
import in.fireye.demoassistant.functions.IFunction;
import in.fireye.demoassistant.functions.IParameter;
import org.springframework.stereotype.Component;

@Component
public class FunctionCatering  implements IFunction<ParameterCity> {
  @Override
  public String execute(IParameter parameter) {
    ParameterCity parameterCity = (ParameterCity) parameter;
    return "餐饮建议\n" +
      "早餐：在丽江古城的小巷内尝试当地的酸奶和烧饵块。\n" +
      "午餐：在玉龙雪山下的餐厅尝试藏族或纳西族风味的菜肴。\n" +
      "晚餐：束河古镇有许多提供地道纳西菜的餐馆，推荐尝试纳西烤鱼。";
  }

  @Override
  public FunctionEnum type() {
    return FunctionEnum.CATERING;
  }
}
