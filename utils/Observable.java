package SMATP3.utils;

import java.util.ArrayList;
import java.util.List;

public class Observable implements IObservable {

	private List<Observer> observers = new ArrayList<Observer>();
	private boolean dirty = false;

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void unregisterObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(Object arg) {
		if(dirty) {
			for (Observer observer : observers) {
				observer.update(arg);
			}
			dirty = false;
		}
	}

	@Override
	public void setDirty() {
		dirty = true;
	}
}
