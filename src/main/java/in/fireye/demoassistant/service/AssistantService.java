package in.fireye.demoassistant.service;

import com.alibaba.dashscope.common.GeneralListParam;
import com.alibaba.dashscope.common.ListResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.InvalidateParameter;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.threads.*;
import com.alibaba.dashscope.threads.messages.Messages;
import com.alibaba.dashscope.threads.messages.TextMessageParam;
import com.alibaba.dashscope.threads.messages.ThreadMessage;
import com.alibaba.dashscope.threads.runs.*;
import com.alibaba.dashscope.tools.ToolCallBase;
import com.alibaba.dashscope.tools.ToolCallFunction;
import com.alibaba.dashscope.utils.JsonUtils;
import in.fireye.demoassistant.functions.FunctionEnum;
import in.fireye.demoassistant.functions.FunctionFactory;
import in.fireye.demoassistant.functions.IFunction;
import in.fireye.demoassistant.functions.IParameter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AssistantService {

  @Value("${com.alibaba.dashscope.assistant.id}")
  private String assistantId;

  @Resource
  private Threads threads;
  @Resource
  private Runs runs;
  @Resource
  private Messages messages;


  public String sendMessage(String threadId, String message) {

    try {
      ThreadMessage threadMessage = messages.create(threadId, TextMessageParam.builder().role("user").content(message).build());
      Run run = runs.create(
        threadId,
        RunParam.builder()
          .assistantId(assistantId)
          .stream(false).build()
      );
      String runId = run.getId();
      while (!run.getStatus().equals(Run.Status.CANCELLED) &&
        !run.getStatus().equals(Run.Status.COMPLETED) &&
        !run.getStatus().equals(Run.Status.FAILED) &&
        !run.getStatus().equals(Run.Status.REQUIRES_ACTION) &&
        !run.getStatus().equals(Run.Status.EXPIRED)) {
        Thread.sleep(1000);
        run = runs.retrieve(threadId, runId);
      }
      if (run.getStatus().equals(Run.Status.REQUIRES_ACTION)) {
        // submit action output.
        RequiredAction requiredAction = run.getRequiredAction();
        if (requiredAction.getType().equals("submit_tool_outputs")) {
          ToolCallBase toolCall = requiredAction.getSubmitToolOutputs().getToolCalls().get(0);
          if (toolCall.getType().equals("function")) {
            // get function call name and argument, both String.
            String functionName = ((ToolCallFunction) toolCall).getFunction().getName();
            String functionArgument = ((ToolCallFunction) toolCall).getFunction().getArguments();
            FunctionEnum functionEnum = FunctionEnum.getFunctionEnum(functionName);
            if (null != functionEnum) {
              IFunction<? extends IParameter> function = FunctionFactory.getInstance().getFunction(functionEnum);
              IParameter parameter = JsonUtils.fromJson(functionArgument, functionEnum.getParameterClass());
              String result = function.execute(parameter);
              run = runs.submitToolOutputs(
                threadId,
                runId,
                SubmitToolOutputsParam.builder()
                  .toolOutput(
                    ToolOutput.builder()
                      .toolCallId(toolCall.getId())
                      .output(result).build()
                  ).build()
              );
            }
          }
        }
      }

      while (!run.getStatus().equals(Run.Status.CANCELLED) &&
        !run.getStatus().equals(Run.Status.COMPLETED) &&
        !run.getStatus().equals(Run.Status.FAILED) &&
        !run.getStatus().equals(Run.Status.REQUIRES_ACTION) &&
        !run.getStatus().equals(Run.Status.EXPIRED)) {
        Thread.sleep(1000);
        run = runs.retrieve(threadId, runId);
      }

      GeneralListParam listParam = GeneralListParam.builder().limit(1l).build();
      ListResult<ThreadMessage> threadMessages = messages.list(threadId, listParam);
      StringBuilder stringBuilder = new StringBuilder();
      for (ThreadMessage resultMessage : threadMessages.getData()) {
        List<ContentBase> contents = resultMessage.getContent();
        for (ContentBase content : contents) {
          if(content instanceof ContentText){
            stringBuilder.append(((ContentText) content).getText().getValue());
          }
        }

      }
      return stringBuilder.toString();
    } catch (NoApiKeyException | InputRequiredException | InvalidateParameter | InterruptedException e) {
      log.error("创建失败", e);
      throw new RuntimeException(e);
    }
  }

  public AssistantThread createThread() {
    try {
      return threads.create(ThreadParam.builder().build());
    } catch (NoApiKeyException e) {
      log.error("创建失败", e);
      throw new RuntimeException(e);
    }
  }
}
