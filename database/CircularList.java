package database;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircularList<T> {
	private LinkedList<T> list;
	Lock l = new ReentrantLock();
	public CircularList(){
		list = new LinkedList<T>();
	}
	
	public synchronized void add(T value){
		list.add(value);
		//System.out.println(list.size());
	}
	public synchronized T fetch(){
		if(list.size() == 0){
			return null;
		}
		return list.pop();
	}
	public synchronized int size(){
		return list.size();
	}
}
