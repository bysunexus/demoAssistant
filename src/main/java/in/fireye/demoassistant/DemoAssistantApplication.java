package in.fireye.demoassistant;

import com.alibaba.dashscope.threads.AssistantThread;
import in.fireye.demoassistant.service.AssistantStreamService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Scanner;

@SpringBootApplication
public class DemoAssistantApplication {

  public static void main(String[] args) throws IOException {
    ConfigurableApplicationContext ctx = SpringApplication.run(DemoAssistantApplication.class, args);
//    AssistantService service = ctx.getBean(AssistantService.class);
    AssistantStreamService service = ctx.getBean(AssistantStreamService.class);
    AssistantThread thread = service.createThread();
    System.out.println("请输入问题：");
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      String next = scanner.next();
      if ("exit".equals(next) || "quit".equals(next)) {
        System.exit(0);
      }
      PipedWriter out = new PipedWriter();
      PipedReader in = new PipedReader();
      out.connect(in);
      service.sendMessage(thread.getId(), next, out);
      System.out.println("===================================");
      int receive = 0;
      while ((receive = in.read()) != -1) {
        System.out.print((char) receive);
      }
      System.out.println("===================================");
    }
  }

}
