package in.fireye.demoassistant.config;

import com.alibaba.dashscope.assistants.Assistant;
import com.alibaba.dashscope.assistants.AssistantParam;
import com.alibaba.dashscope.assistants.Assistants;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.tools.FunctionDefinition;
import com.alibaba.dashscope.tools.ToolFunction;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class AssistantCreator {
  public static void main(String[] args) throws IOException, NoApiKeyException {
    Properties properties = new Properties();
    properties.load(AssistantCreator.class.getClassLoader().getResourceAsStream("functions.properties"));
    Assistants assistants = new Assistants();
    Assistant result = assistants.create(
      AssistantParam.builder()
        .model("qwen-max")
        .name("smart assistant")
        .description("一个旅游助手，可以通过用户诉求，调用天气查询，路径推荐，当地餐厅推荐等帮助用户。")
        .instructions("你是一个旅游助手，可以通过调用插件解决问题。插件例如，天气查询，路径推荐，当地餐厅推荐等，当你无法回答问题时应当结合插件回复进行回答。请根据插件结果适当丰富回复内容。")
        .tools(List.of(
          ToolFunction.builder()
            .function(
              FunctionDefinition.builder()
                .name("天气查询")
                .description("用于查询天气的插件和函数")
                .parameters(JsonParser.parseString(properties.getProperty("weather")).getAsJsonObject())
                .build()
            ).build(),
          ToolFunction.builder()
            .function(
              FunctionDefinition.builder()
                .name("路径规划")
                .description("用于推荐出行和旅游的路径规划，包含查询路线，规划起点到终点的路线。也用于推荐最近最热门的旅游行程。")
                .parameters(JsonParser.parseString(properties.getProperty("path")).getAsJsonObject())
                .build()
            ).build(),
          ToolFunction.builder()
            .function(
              FunctionDefinition.builder()
                .name("获取目的地建议")
                .description("用于推荐最近热门的旅游目的地。")
                .parameters(JsonParser.parseString(properties.getProperty("destination")).getAsJsonObject())
                .build()
            ).build(),
          ToolFunction.builder()
            .function(
              FunctionDefinition.builder()
                .name("获取景点推荐")
                .description("用于推荐指定城市的旅游景点。")
                .parameters(JsonParser.parseString(properties.getProperty("attractions")).getAsJsonObject())
                .build()
            ).build(),
          ToolFunction.builder()
            .function(
              FunctionDefinition.builder()
                .name("获取餐饮推荐")
                .description("用于推荐指定城市的餐饮。")
                .parameters(JsonParser.parseString(properties.getProperty("catering")).getAsJsonObject())
                .build()
            ).build(),
          ToolFunction.builder()
            .function(
              FunctionDefinition.builder()
                .name("获取旅行提示")
                .description("用于获取指定城市的旅行注意事项。")
                .parameters(JsonParser.parseString(properties.getProperty("precautions")).getAsJsonObject())
                .build()
            ).build(),
          ToolFunction.builder()
            .function(
              FunctionDefinition.builder()
                .name("获取当地风俗")
                .description("用于获取指定城市的当地风俗。")
                .parameters(JsonParser.parseString(properties.getProperty("customs")).getAsJsonObject())
                .build()
            ).build()
        )).build()
    );

    System.out.println(result.getId());
  }
}
