package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.IParameter;
import lombok.Data;

@Data
public class ParameterDestination implements IParameter {
  private String query;
}
