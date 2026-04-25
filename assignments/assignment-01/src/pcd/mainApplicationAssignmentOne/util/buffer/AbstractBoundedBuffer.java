package pcd.mainApplicationAssignmentOne.util.buffer;

import java.util.LinkedList;
import java.util.Optional;

public abstract class AbstractBoundedBuffer<Item> implements BoundedBuffer<Item>{
    protected LinkedList<Item> buffer;
	protected int maxSize;

	public synchronized void put(Item item) throws InterruptedException {
		while (isFull()) {
			wait();
		}
		buffer.addLast(item);
		notifyAll();
	}

	public synchronized Item get() throws InterruptedException {
		while (isEmpty()) {
			wait();
		}
		Item item = buffer.removeFirst();
		notifyAll();
		return item;
	}

	protected boolean isFull() {
		return buffer.size() == maxSize;
	}

	protected boolean isEmpty() {
		return buffer.size() == 0;
	}

    public abstract Optional<Item> poll() throws InterruptedException;
}
