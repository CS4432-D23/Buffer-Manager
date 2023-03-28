package Project1;

import javax.sound.sampled.Line;

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

    public void show() {
        String output = "";
        for (int i = 0; i < numFrames; i++) {
            output += "Frame " + i + ": " + frames[i].getBlockID() + " | Pinned: " + frames[i].isPinned() + " | Dirty: " + frames[i].isDirty() + "\n";
        }
        System.out.println(output);
    }
    

    public String get(int recordNumber) {

        if (recordNumber > 700 || recordNumber < 1) {
            return "INVALID RECORD NUMBER";
        }
 
        int blockID = (recordNumber % 100 == 0) ? (recordNumber / 100) : (recordNumber / 100) + 1;
        int lineNumber = (recordNumber % 100 == 0) ? 100 : (recordNumber % 100);

        System.out.println(blockID);
        System.out.println(lineNumber);
        
        int frameNumber = inBuffer(blockID);
        if (frameNumber == -1) {
            frameNumber = getEmptyFrame();
            if (frameNumber == -1) {
                frameNumber = getUnpinnedFrame();
                if (frameNumber == -1) {
                    return "BUFFER FULL"; //TODO evict a buffer frame here
                } else {
                    frames[frameNumber].setContent(blockID);
                    frames[frameNumber].setBlockID(blockID);
                }
            } else {
                frames[frameNumber].setContent(blockID);
                frames[frameNumber].setBlockID(blockID);
            }
        } 
        return frames[frameNumber].getRecord(lineNumber);
    }


    public int inBuffer(int blockID) {
        for (int i = 0; i < numFrames; i++) {
            if (frames[i].getBlockID() == blockID) {
                return i;
            }
        }
        return -1;
    }

    public int showContent(int blockID) {
        int frameNumber = inBuffer(blockID);
        if (frameNumber == -1) {
            return -1;
        } else {
            for (int i = 0; i < 100; i++) {
                System.out.println(frames[frameNumber].getRecord(i));
            }
            return 0;
        }
    }

    public int getEmptyFrame() {
        for (int i = 0; i < numFrames; i++) {
            if (frames[i].getBlockID() == -1) {
                return i;
            }
        }
        return -1;
    }

    public int getUnpinnedFrame() {
        for (int i = 0; i < numFrames; i++) {
            if (!frames[i].isPinned()) {
                return i;
            }
        }
        return -1;
    }
    


    public void pin(int pageNumber) {
        for (int i = 0; i < numFrames; i++) {
            if (frames[i].getBlockID() == pageNumber) {
                frames[i].pin();
                numPinned++;
            }
        }
    }
    public void unpin(int pageNumber) {

    }

    
}
