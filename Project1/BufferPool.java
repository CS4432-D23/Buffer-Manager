package Project1;

public class BufferPool {

    private Frame[] frames;
    private int numFrames;
    private int numPinned;
    
    public BufferPool(int numFrames) {
        this.numFrames = numFrames;
        this.numPinned = 0;
        this.frames = new Frame[numFrames];
        for (int i = 0; i < numFrames; i++) {
            this.frames[i] = new Frame();
        }
    } 

    public void pin(int pageNumber) {
        // pin page
    }
    public void unpin(int pageNumber) {

    }

    
}
