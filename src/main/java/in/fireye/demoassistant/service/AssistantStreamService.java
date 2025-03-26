package in.fireye.demoassistant.service;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.InvalidateParameter;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.threads.*;
import com.alibaba.dashscope.threads.messages.Messages;
import com.alibaba.dashscope.threads.messages.TextMessageParam;
import com.alibaba.dashscope.threads.messages.ThreadMessage;
import com.alibaba.dashscope.threads.messages.ThreadMessageDelta;
import com.alibaba.dashscope.threads.runs.*;
import com.alibaba.dashscope.tools.ToolCallBase;
import com.alibaba.dashscope.tools.ToolCallFunction;
import com.alibaba.dashscope.utils.JsonUtils;
import in.fireye.demoassistant.functions.FunctionEnum;
import in.fireye.demoassistant.functions.FunctionFactory;
import in.fireye.demoassistant.functions.IFunction;
import in.fireye.demoassistant.functions.IParameter;
import io.reactivex.Flowable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.beans.EventHandler;
import java.io.IOException;
import java.io.PipedWriter;

@Service
@Slf4j
public class AssistantStreamService {

  @Value("${com.alibaba.dashscope.assistant.id}")
  private String assistantId;

  @Resource
  private Threads threads;
  @Resource
  private Runs runs;
  @Resource
  private Messages messages;


  public void sendMessage(String threadId, String message, PipedWriter out) {

    try {
      ThreadMessage threadMessage = messages.create(threadId, TextMessageParam.builder().role("user").content(message).build());
      Flowable<AssistantStreamMessage> runStream = runs.createStream(threadId, RunParam.builder().assistantId(assistantId).stream(true).build());
      runStream.forEach(msg -> handler(threadId, msg, out));
    } catch (NoApiKeyException | InputRequiredException | InvalidateParameter e) {
      log.error("创建失败", e);
      throw new RuntimeException(e);
    }
  }

  private void handler(String threadId, AssistantStreamMessage msg, PipedWriter out) throws IOException, NoApiKeyException, InputRequiredException {

//    System.out.println("Event: " + msg.getEvent());
//    System.out.println("data: ");
//    System.out.println(msg.getData());
    switch (msg.getEvent()) {
      case THREAD_MESSAGE_DELTA:
        ThreadMessageDelta data = (ThreadMessageDelta) msg.getData();
        for (ContentBase contentBase : data.getDelta().getContent()) {
          ContentText text = (ContentText) contentBase;
          out.write(text.getText().getValue());
          out.flush();
        }
        break;
      case THREAD_RUN_REQUIRES_ACTION:
        Run run = (Run) msg.getData();
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
              Flowable<AssistantStreamMessage> runStream =runs.submitStreamToolOutputs(
                threadId,
                run.getId(),
                SubmitToolOutputsParam.builder()
                  .toolOutput(
                    ToolOutput.builder()
                      .toolCallId(toolCall.getId())
                      .output(result).build()
                  )
                  .stream(true).build()
              );
              runStream.forEach(msg1 -> handler(threadId, msg1, out));
            }
          }
        }
        break;
      case THREAD_RUN_COMPLETED:
        out.write('\n');
        out.close();
        break;
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
