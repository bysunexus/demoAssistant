package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.FunctionEnum;
import in.fireye.demoassistant.functions.IFunction;
import in.fireye.demoassistant.functions.IParameter;
import org.springframework.stereotype.Component;

@Component
public class FunctionDestination implements IFunction<ParameterDestination> {
  @Override
  public String execute(IParameter parameter) {
    ParameterDestination parameterDestination = (ParameterDestination) parameter;
    return "以下是一些推荐的旅游目的地：\n" +
      "1. 杭州（中国）\n" +
      "西湖：享有“人间天堂”的美誉，是中国最著名的自然景观之一。\n" +
      "灵隐寺：杭州最古老的寺庙之一，提供宁静的精神修养场所。\n" +
      "西溪湿地：天然的湿地生态区，可以近距离接触自然和野生生物。\n" +
      "2. 丽江（中国）\n" +
      "古城：保留了完好的历史建筑和纳西族文化，是一个不可多得的步行城市。\n" +
      "玉龙雪山：雄伟的山峰，提供滑雪和登山活动，景色壮观。\n" +
      "束河古镇：比丽江古城更为宁静的地方，适合品茶和静思。\n" +
      "3. 巴黎（法国）\n" +
      "埃菲尔铁塔：法国的象征，提供城市全景视角。\n" +
      "卢浮宫：世界上最大的艺术博物馆之一，收藏有《蒙娜丽莎》等珍贵艺术品。\n" +
      "凡尔赛宫：法国历史上的王宫，代表了欧洲花园和宫殿的顶峰。\n" +
      "4. 开普敦（南非）\n" +
      "桌山：通过缆车访问，顶部可俯瞰开普敦市和罗宾岛的壮丽景色。\n" +
      "好望角：著名的海角，为航海家提供的历史地标。\n" +
      "克尔斯滕博施植物园：世界上生物多样性最丰富的植物园之一。";
  }

  @Override
  public FunctionEnum type() {
    return FunctionEnum.DESTINATION;
  }
}
