package org.mrpaulwoods.statemachinedemo;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.statemachinedemo.actions.NewToScrappedAction;
import org.mrpaulwoods.statemachinedemo.actions.ProcessedToDoneAction;
import org.mrpaulwoods.statemachinedemo.actions.ScrappedToProcessedAction;
import org.mrpaulwoods.statemachinedemo.guards.NewToScrappedGuard;
import org.mrpaulwoods.statemachinedemo.guards.ProcessedToDoneGuard;
import org.mrpaulwoods.statemachinedemo.guards.ScrappedToProcessedGuard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
@RequiredArgsConstructor
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    private final Listener listener;
    private final NewToScrappedGuard newToScrappedGuard;
    private final ScrappedToProcessedGuard scrappedToProcessedGuard;
    private final NewToScrappedAction newToScrappedAction;
    private final ScrappedToProcessedAction scrappedToProcessedAction;
    private final ProcessedToDoneGuard processedToDoneGuard;
    private final ProcessedToDoneAction processedToDoneAction;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> configConfigurer)
            throws Exception {
        configConfigurer
                .withConfiguration()
                .autoStartup(true)
                .listener(listener);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> stateConfigurer)
            throws Exception {
        stateConfigurer
                .withStates()
                .initial(States.NEW)
                .end(States.DONE)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitionConfigurer)
            throws Exception {
        transitionConfigurer

                .withExternal()
                .source(States.NEW)
                .event(Events.OnRun)
                .target(States.SCRAPPED)
                .guard(context -> Boolean.TRUE.equals(newToScrappedGuard.apply(context).block()))
                .actionFunction(newToScrappedAction)

                .and()

                .withExternal()
                .source(States.SCRAPPED)
                .event(Events.OnRun)
                .target(States.PROCESSED)
                .guard(context -> Boolean.TRUE.equals(scrappedToProcessedGuard.apply(context).block()))
                .actionFunction(scrappedToProcessedAction)

                .and()

                .withExternal()
                .source(States.PROCESSED)
                .event(Events.OnRun)
                .target(States.DONE)
                .guard(context -> Boolean.TRUE.equals(processedToDoneGuard.apply(context).block()))
                .actionFunction(processedToDoneAction)
        ;

    }

    @Bean
    public Action<States, Events> errorAction() {
        return context -> {
            Exception exception = context.getException();
            System.out.println("Caught Exception: " + exception.getMessage());
        };
    }

}
