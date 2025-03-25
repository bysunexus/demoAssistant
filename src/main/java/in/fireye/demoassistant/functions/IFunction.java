package in.fireye.demoassistant.functions;

import org.springframework.beans.factory.InitializingBean;

public interface IFunction <T extends IParameter> extends InitializingBean {
  String execute(IParameter parameter);
  FunctionEnum type();

  default void afterPropertiesSet() throws Exception {
    FunctionFactory.getInstance().putFunction(type(), this);
  }
}
