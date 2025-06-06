package libreria;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    private List<Observer> observers = new ArrayList<>();

    public void aggiungiObserver(Observer obs) {
        observers.add(obs);
    }

    public void rimuoviObserver(Observer obs) {
        observers.remove(obs);
    }

    public void notificaObservers() {
        for (Observer obs : observers) {
            obs.aggiorna();
        }
    }
}
