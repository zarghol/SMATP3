package SMATP3.utils;


public interface IObservable {
	public void registerObserver(Observer observer);
	public void unregisterObserver(Observer observer);
	public void notifyObservers();
	public void setDirty();
}
