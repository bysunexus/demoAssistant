package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.FunctionEnum;
import in.fireye.demoassistant.functions.IFunction;
import in.fireye.demoassistant.functions.IParameter;
import org.springframework.stereotype.Component;

@Component
public class FunctionAttractions implements IFunction<ParameterCity> {
  @Override
  public String execute(IParameter parameter) {
    ParameterCity parameterCity = (ParameterCity) parameter;
    return "1. 丽江古城（大研古城）\n" +
      "特色：丽江古城是一个完美保存的古老纳西族城镇，以其石板路、流水和古桥而著名。无需购票即可进入，但维护古城的费用需购买一种维护费证书。\n" +
      "建议时间：建议早上开始您的游览以避开人群。\n" +
      "提示：晚上的古城特别迷人，有许多酒吧和咖啡馆可以享受。\n" +
      "2. 黑龙潭公园\n" +
      "特色：位于丽江古城北边，是欣赏玉龙雪山倒影的绝佳地点。\n" +
      "建议时间：上午或傍晚。\n" +
      "提示：入园免费，是拍照和散步的好地方。\n" +
      "3. 玉龙雪山\n" +
      "特色：这座壮观的雪山是丽江的标志性景点，您可以乘坐索道至冰川公园或牦牛坪。\n" +
      "建议时间：全天，但需留出足够的时间上山下山。\n" +
      "提示：高海拔可能引起高反，建议提前做好准备。\n" +
      "4. 白沙古镇\n" +
      "特色：相对于丽江古城，白沙古镇更加宁静，保留了更多传统纳西族文化。\n" +
      "建议时间：下午。\n" +
      "提示：可以尝试纳西族的传统手工艺品制作体验。\n" +
      "5. 束河古镇\n" +
      "特色：比丽江古城小而更为宁静，是了解纳西族文化的另一个好地点。\n" +
      "建议时间：晚上。\n" +
      "提示：束河古镇的夜生活相对平和，适合享受安静的晚餐。";
  }

  @Override
  public FunctionEnum type() {
    return FunctionEnum.ATTRACTIONS;
  }
}
