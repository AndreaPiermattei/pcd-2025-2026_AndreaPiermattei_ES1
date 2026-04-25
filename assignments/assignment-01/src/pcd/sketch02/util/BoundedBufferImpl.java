package pcd.sketch02.util;

import java.util.LinkedList;
import java.util.Optional;

import pcd.mainApplicationAssignmentOne.util.buffer.AbstractBoundedBuffer;

/**
 * 
 * Simple implementation of a bounded buffer
 * as a monitor, using raw mechanisms
 * 
 * @param <Item>
 */
public class BoundedBufferImpl<Item> extends AbstractBoundedBuffer<Item> {

	
	public BoundedBufferImpl(int size) {
		super.buffer = new LinkedList<Item>();
		super.maxSize = size;
	}


	@Override
	public Optional<Item> poll() throws InterruptedException {
		System.out.println("WARNING! IMPLEMENTATION IS ACTUALLY .get()");
		return Optional.ofNullable(super.get());
	}
}
