package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.FunctionEnum;
import in.fireye.demoassistant.functions.IFunction;
import in.fireye.demoassistant.functions.IParameter;
import org.springframework.stereotype.Component;

@Component
public class FunctionCustoms implements IFunction<ParameterCity> {
  @Override
  public String execute(IParameter parameter) {
    ParameterCity parameterCity = (ParameterCity) parameter;
    return "纳西族礼仪：\n" +
      "\n" +
      "纳西族是丽江的主要民族，他们非常看重礼仪。与长辈交谈时，说话要礼貌且避免直视对方的眼睛，这是尊重的表现。\n" +
      "在参观纳西族家庭或参与当地活动时，接受主人提供的食物和饮料是礼貌的表现。\n" +
      "传统服饰：\n" +
      "\n" +
      "当地人在特定节日或有重要活动时可能会穿着传统服装。游客可以租借纳西民族服饰拍照，但应保持尊重，不要对服饰进行任何不适当的处理。\n" +
      "节日和庆典：\n" +
      "\n" +
      "参与当地节日如三月街（每年农历三月举行的大型集市和庆典）、庙会等，需遵守当地的规矩和传统，比如不大声喧哗、不随意涉足祭祀区域等。";
  }

  @Override
  public FunctionEnum type() {
    return FunctionEnum.CUSTOMS;
  }
}
