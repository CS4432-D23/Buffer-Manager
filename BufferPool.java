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
        if (numPinned == numFrames) {
            System.out.println("All frames are pinned");
            return;
        }
        for (int i = 0; i < numFrames; i++) {
            if (frames[i].getPageNumber() == pageNumber) {
                frames[i].pin();
                numPinned++;
                return;
            }
        }
        for (int i = 0; i < numFrames; i++) {
            if (frames[i].getPageNumber() == -1) {
                frames[i].pin();
                frames[i].setPageNumber(pageNumber);
                numPinned++;
                return;
            }
        }
        for (int i = 0; i < numFrames; i++) {
            if (!frames[i].isPinned()) {
                frames[i].pin();
                frames[i].setPageNumber(pageNumber);
                numPinned++;
                return;
            }
        }
    }
    public void unpin(int pageNumber) {
        for (int i = 0; i < numFrames; i++) {
            if (frames[i].getPageNumber() == pageNumber) {
                frames[i].unpin();
                numPinned--;
                return;
            }
        }
        System.out.println("Page number not found");
    }

    
}
