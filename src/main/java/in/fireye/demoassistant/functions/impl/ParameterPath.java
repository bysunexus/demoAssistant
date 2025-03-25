package in.fireye.demoassistant.functions.impl;

import in.fireye.demoassistant.functions.IParameter;
import lombok.Data;

@Data
public class ParameterPath implements IParameter {
  private String start;
  private String dest;
}
