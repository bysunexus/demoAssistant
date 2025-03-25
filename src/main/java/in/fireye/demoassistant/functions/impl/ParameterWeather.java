package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.IParameter;
import lombok.Data;

@Data
public class ParameterWeather implements IParameter {

  private String location;
  private String date;

}
