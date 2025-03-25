package in.fireye.demoassistant.config;

import com.alibaba.dashscope.threads.Threads;
import com.alibaba.dashscope.threads.messages.Messages;
import com.alibaba.dashscope.threads.runs.Runs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AssistantConfig {
  @Bean
  public Messages messages() {
    return new Messages();
  }

  @Bean
  public Threads threads() {
    return new Threads();
  }
  @Bean
  public Runs runs() {
    return new Runs();
  }
}
