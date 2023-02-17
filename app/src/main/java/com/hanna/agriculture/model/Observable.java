package com.hanna.agriculture.model;

import java.util.ArrayList;

public class Observable {

    private ArrayList<Observer> observers = null;

    public Observable(){
        observers = new ArrayList<Observer>();
    }

    // Notify observers when need to update any changes made to model
    public void notifyObservers() {
        if (observers != null) {
            for (Observer observer : observers) {
                observer.update();
            }
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

}
