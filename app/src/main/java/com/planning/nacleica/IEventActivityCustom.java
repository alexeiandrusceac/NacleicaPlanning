package com.planning.nacleica;

public interface IEventActivityCustom {
    void NetworkConnectionEvent(boolean connected);
    void StateChanged(String oldState, String newState);
}