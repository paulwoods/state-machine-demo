package org.mrpaulwoods.statemachinedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Override
    public void run(String... args) {

        Message<Events> message1 = MessageBuilder.withPayload(Events.OnRun).build();

        stateMachine.sendEvents(Flux.just(message1, message1, message1, message1)).subscribe();

        System.out.println("final state: " + stateMachine.getState().getId());
    }

}
