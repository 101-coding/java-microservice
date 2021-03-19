package com.coding.sk;

import com.coding.grpc.GreetingServiceGrpc;
import com.coding.grpc.Hello;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class SkApplication {

    ManagedChannel channel;
    GreetingServiceGrpc.GreetingServiceBlockingStub greetingServiceStub;

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8082)
                .addService(new GrpcServer())
                .build();
        server.start();

        SpringApplication.run(SkApplication.class, args);
        server.awaitTermination();
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(value = "name", defaultValue = "sk") String name,
                        @RequestParam(value = "hobbis", defaultValue = "") String hobbis) {
        if (channel == null) {
            channel = ManagedChannelBuilder.forAddress("localhost", 8082)
                    .usePlaintext(true)
                    .build();
        }
        if (greetingServiceStub == null)
            greetingServiceStub = GreetingServiceGrpc.newBlockingStub(channel);

        Hello.HelloRequest helloRequest = Hello.HelloRequest.newBuilder()
                .setName(name)
                .addAllHobbies(Arrays.stream(hobbis.split(",")).collect(Collectors.toList()))
                .build();
        Hello.HelloResponse response = greetingServiceStub.greeting(helloRequest);
        return response.getGreeting();
    }
}