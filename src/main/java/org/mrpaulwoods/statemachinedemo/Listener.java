package org.mrpaulwoods.statemachinedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Listener extends StateMachineListenerAdapter<States, Events> {
    @Override
    public void stateChanged(State<States, Events> from, State<States, Events> to) {
        log.info("State change to {}", to.getId());
    }

}
