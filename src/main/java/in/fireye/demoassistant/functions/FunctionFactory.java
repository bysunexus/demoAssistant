package in.fireye.demoassistant.functions;

import java.util.HashMap;
import java.util.Map;

public class FunctionFactory {
    private FunctionFactory() {}

   private Map<FunctionEnum, IFunction<? extends IParameter>> functions = new HashMap<>(20);

    private static class Holder {
        private static final FunctionFactory INSTANCE = new FunctionFactory();
    }

    public static FunctionFactory getInstance() {
        return Holder.INSTANCE;
    }
    public void putFunction(FunctionEnum functionEnum, IFunction<? extends IParameter> function){
      functions.put(functionEnum,function);
    }

    public IFunction<? extends IParameter> getFunction(FunctionEnum functionEnum){
      return functions.get(functionEnum);
    }

}
