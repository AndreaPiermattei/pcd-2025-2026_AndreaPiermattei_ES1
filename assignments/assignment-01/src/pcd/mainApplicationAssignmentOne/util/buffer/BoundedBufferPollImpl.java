package pcd.mainApplicationAssignmentOne.util.buffer;

import java.util.LinkedList;
import java.util.Optional;

public class BoundedBufferPollImpl<Item> extends AbstractBoundedBuffer<Item>{


    
    public BoundedBufferPollImpl(int size) {
		super.buffer = new LinkedList<Item>();
		super.maxSize = size;
	}

    @Override
    public synchronized Optional<Item> poll() throws InterruptedException {
        if(!super.isEmpty()){
            Item item = buffer.removeFirst();
		    notifyAll();
		    return Optional.of(item);
        }
        
        return Optional.empty();
        
    }
    
}
