package in.fireye.demoassistant;

import com.alibaba.dashscope.threads.AssistantThread;
import in.fireye.demoassistant.service.AssistantService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class DemoAssistantApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(DemoAssistantApplication.class, args);
    AssistantService service = ctx.getBean(AssistantService.class);
    AssistantThread thread = service.createThread();
    System.out.println("请输入问题：");
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      String next = scanner.next();
      if ("exit".equals(next) || "quit".equals(next)) {
        System.exit(0);
      }
      System.out.println(service.sendMessage(thread.getId(), next));
    }
  }

}
