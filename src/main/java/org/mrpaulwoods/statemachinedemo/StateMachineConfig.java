package org.mrpaulwoods.statemachinedemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> configConfigurer)
            throws Exception {
        configConfigurer
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> stateConfigurer)
            throws Exception {
        stateConfigurer
                .withStates()
                .initial(States.SI)
                .end(States.SF)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitionConfigurer)
            throws Exception {
        transitionConfigurer

                .withExternal()
                .source(States.SI)
                .target(States.S1)
                .event(Events.E1)
                .guard(guard())
                .action(action(), errorAction())

                .and()

                .withExternal()
                .source(States.S1)
                .target(States.S2)
                .event(Events.E2)
                .guard(guard())
                .action(action(), errorAction());
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

    @Bean
    public Guard<States, Events> guard() {
        return context -> {

            boolean ret = context.getEvent() != Events.E2;

            System.out.printf(
                    "guarding event: %s : %s -> %s : %s%n", context.getEvent(),
                    context.getSource().getId(),
                    context.getTarget().getId(),
                    ret);

            return ret;
        };

    }

    @Bean
    public Action<States, Events> action() {
        return context -> {

            System.out.printf(
                    "action event: %s : %s -> %s%n", context.getEvent(),
                    context.getSource().getId(),
                    context.getTarget().getId());

            if (context.getEvent() == Events.E1) {
                throw new RuntimeException("simulated error");
            }

        };
    }

    @Bean
    public Action<States, Events> errorAction() {
        return context -> {
            Exception exception = context.getException();
            System.out.println("Caught Exception: " + exception.getMessage());
        };
    }

}
