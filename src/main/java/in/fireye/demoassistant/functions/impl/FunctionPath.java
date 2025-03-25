package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.FunctionEnum;
import in.fireye.demoassistant.functions.IFunction;
import in.fireye.demoassistant.functions.IParameter;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class FunctionPath implements IFunction<ParameterPath> {
  @Override
  public String execute(IParameter parameter) {
    ParameterPath parameterPath = (ParameterPath) parameter;

    return MessageFormat.format("建议通过 {0} 到 {1} \n以下是航班信息\n" +
      "航班号\t机型\t起飞时间\t起飞机场\t降落时间\t降落机场\t班期\t餐食服务\t票价\n" +
      "CA1469\t32N\t06:30\t首都国际机场 T2\t10:25\t三义机场\t每天\t93%\t¥1730 起\n" +
      "ZH1469\t32N\t06:30\t首都国际机场 T2\t10:25\t三义机场\t每天\t93%\t查看时价\n" +
      "JD5181\t32Q\t06:55\t大兴国际机场\t10:15\t三义机场\t每天\t97%\t¥630 起\n" +
      "CA1459\t32N\t16:20\t首都国际机场 T2\t20:15\t三义机场\t每天\t97%\t¥1150 起\n" +
      "ZH1459\t32N\t16:20\t首都国际机场 T2\t20:15\t三义机场\t每天\t97%\t查看时价\n" +
      "3U5234\t737\t16:55\t大兴国际机场\t20:05\t三义机场\t每天\t-\t¥2860 起\n" +
      "KN6139\t737\t16:55\t大兴国际机场\t20:05\t三义机场\t每天\t-\t查看时价\n" +
      "MU5716\t737\t16:55\t大兴国际机场\t20:05\t三义机场\t每天\t-\t¥900 起\n" +
      "3U5234\t737\t17:00\t大兴国际机场\t20:05\t三义机场\t每天\t-\t¥2860 起\n" +
      "KN6139\t737\t17:00\t大兴国际机场\t20:05\t三义机场\t每天\t-\t查看时价\n" +
      "MU5716\t737\t17:00\t大兴国际机场\t20:05\t三义机场\t每天\t-\t¥900 起", parameterPath.getStart(), parameterPath.getDest());
  }

  @Override
  public FunctionEnum type() {
    return FunctionEnum.PATH;
  }
}
