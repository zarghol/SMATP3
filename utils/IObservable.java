package SMATP3.utils;


public interface IObservable {
	public void registerObserver(Observer observer);
	public void unregisterObserver(Observer observer);
	public void notifyObservers(Object arg);
	public void setDirty();
}
