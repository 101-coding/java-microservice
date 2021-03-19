package com.coding.sk;

import com.coding.grpc.Hello;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class GrpcServer extends com.coding.grpc.GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greeting(com.coding.grpc.Hello.HelloRequest request, StreamObserver<Hello.HelloResponse> responseObserver) {
        String name = request.getName();
        List<String> hobbisList = request.getHobbiesList();
        Hello.HelloResponse.Builder builder = Hello.HelloResponse.newBuilder();
        Hello.HelloResponse response = builder.setGreeting("Hello " + name
                + " Good that you have hobbis: " + hobbisList.toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
