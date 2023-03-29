package Project1;

/**
 * The buffer pool class is a singleton class that manages the buffer pool.
 */
public class BufferPool {

    private static BufferPool instance; // singleton instance
    private Frame[] frames; // the buffer pool frames
    private int numFrames; // the number of frames in the buffer pool
    private int numPinned; // the number of pinned frames in the buffer pool
    private int lastEvicted; // the index of the last evicted frame
    
    /**
     * Private constructor to prevent instantiation.
     */
    private BufferPool() {}

    /**
     * Initializes the buffer pool with a specified number of frames.
     *
     * @param numFrames the number of frames to allocate in the buffer pool
     * @return the singleton instance of the buffer pool
     */
    public static BufferPool initalize(int numFrames) {

        if (instance == null) {
            instance = new BufferPool();
            instance.numFrames = numFrames;
            instance.numPinned = 0;
            instance.frames = new Frame[numFrames];
            for (int i = 0; i < numFrames; i++) {
                instance.frames[i] = new Frame();
                instance.frames[i].initalize();
            }
        }
        return instance;
    }
    
    /**
     * Outputs the contents of the buffer pool to the console.
     */
    public void show() {
        String output = "";
        for (int i = 0; i < numFrames; i++) {
            output += "Frame " + i + ": " + frames[i].getBlockID() + " | Pinned: " + frames[i].isPinned() + " | Dirty: " + frames[i].isDirty() + "\n";
        }
        System.out.println(output);
    }

    
    /**
     * brings the specified record number to the buffer pool
     * @param recordNumber the record number to bring to the buffer pool
     * @return the frame number and line number of the record number
     */
    public int[] bringToBuffer(int recordNumber) {

        // calculate the filenumber (blockID) depending on the recordNumber (edge cases)
        int blockID = (recordNumber % 100 == 0) ? (recordNumber / 100) : (recordNumber / 100) + 1;
        // calculate the lineNumber depending on the recordNumber (edge cases)
        int lineNumber = (recordNumber % 100 == 0) ? 100 : (recordNumber % 100);

        // System.out.println(blockID);
        // System.out.println(lineNumber);

        // check if the blockID is in the buffer
        int frameNumber = inBuffer(blockID);

        // if the blockID is not in the buffer, get an empty frame or an unpinned frame
        if (frameNumber == -1) {

            // get an empty frame
            frameNumber = getEmptyFrame();

            // if there are no empty frames, get an unpinned frame
            if (frameNumber == -1) {

                frameNumber = getUnpinnedFrame();

                // if there are no unpinned frames, return BUFFER FULL (implement LRU if theres time here)
                if (frameNumber == -1) {
                    return new int[] {-1, -1}; // BUFFER FULL
                } else { // if there is an unpinned frame, load the blockID into the frame
                    System.out.println("Evicting frame unpinned frame: " + frameNumber);
                    frames[frameNumber].readFile(blockID);
                    frames[frameNumber].setBlockID(blockID);
                }
            } else { // if there is an empty frame, load the blockID into the frame

                System.out.println("Evicting empty frame: " + frameNumber);
                frames[frameNumber].readFile(blockID);
                frames[frameNumber].setBlockID(blockID);
            }
        } else {
            System.out.println("No Frame Eviction :)");
        }
        lastEvicted = frameNumber;
        return new int[] {frameNumber, lineNumber}; 
    }

    /**
     * get method that returns the record at the specified record number
     * @param recordNumber the record number to retrieve
     * @return the record at the specified record number
     */
    public String get(int recordNumber) { 

        // check if recordNumber is valid
        if (recordNumber > 700 || recordNumber < 1) {
            return "INVALID RECORD NUMBER";
        }

        int values[] = bringToBuffer(recordNumber);

        if(values[0] == -1 && values[1] == -1) {
            return "CANNOT ACCESS RECORD " + recordNumber + " FROM DISK, BUFFER FULL";
        }

        System.out.println("Stored in Frame Number: " + values[0]);
        
        // return the record at the specified record number
        return frames[values[0]].getRecord(values[1]);
    }

    /**
     * check buffers to see if the blockID is in the buffer
     * @param blockID the blockID to check for
     * @return the frame number if the blockID is in the buffer, -1 if not
     */
    public int inBuffer(int blockID) {
        for (int i = 0; i < numFrames; i++) {
            if (frames[i].getBlockID() == blockID) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returns an empty frame
     * @return the frame number of an empty frame, -1 if there are no empty frames
     */
    public int getEmptyFrame(int lastEvicted) {
        for (int i = 0; i < numFrames; i++) { // start from the beginning and write to the first empty frame
            if (frames[i].getBlockID() == -1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returns an unpinned frame
     * @return the frame number of an unpinned frame, -1 if there are no unpinned frames
     */
    public int getUnpinnedFrame() {
        for (int i = 0; i < numFrames; i++) {
            if (!frames[i].isPinned()) {
                if (!frames[i].isDirty()) {
                    return i;
                }
                else {
                    System.out.println("Writing frame " + i + " to disk");
                    frames[i].writeFile();
                    frames[i].setDirty(false);
                    return i;
                }
            }
        }
        return -1;
    }

    // TODO, edit content in frame, set dirty bit to true, and write to disk
    public String set(int recordNumber, String edittedRecord) { 

         // check if recordNumber is valid
         if (recordNumber > 700 || recordNumber < 1) {
            return "INVALID RECORD NUMBER";
        }

        int values[] = bringToBuffer(recordNumber);
        int frameNumber = values[0];
        int lineNumber = values[1];

        if(values[0] == -1 && values[1] == -1) {
            return "CANNOT ACCESS RECORD " + recordNumber + " FROM DISK, BUFFER FULL";
        }

        System.out.println("Stored in Frame Number: " + frameNumber);
        System.out.println("Line Number Changed: " + lineNumber);

        frames[frameNumber].setRecord(lineNumber, edittedRecord); // edit the record
        
        return "SUCCESSFULLY SET RECORD";
    }
    

    /**
     * pin a specific page number in the buffer
     * 
     * @param pageNumber the page number to pin
     * @return string indicating success or failure
     */
    public String pin(int pageNumber) {

        // check if pageNumber is valid
        if (pageNumber > 7 || pageNumber < 1) {
            return "INVALID PAGE NUMBER";
        }
        
        // bring the page to the buffer
        int[] values = bringToBuffer(pageNumber * 100);

        // System.out.println(values[0]);

        // CASE 3 if the buffer is full, return BUFFER FULL
        if (values[0] == -1 && values[1] == -1) {
            return "CANNOT PIN PAGE NUMBER " + pageNumber + " FROM DISK, BUFFER FULL";
        } else { // if the buffer is not full, pin the frame
            // CASE the frame is already pinned
            if (frames[values[0]].isPinned()) {
                return "PAGE " + values[0] + " ALREADY PINNED";
            }
            else {
                // CASE pin the frame
                frames[values[0]].pin();
                numPinned++;
                return "Pinned Frame: " + values[0];
            }
        }

    }

    /**
     * unpin a specific page number in the buffer
     * @param pageNumber the page number to unpin
     */ 
    public String unpin(int pageNumber) {
            
        // check if pageNumber is valid
        if (pageNumber > 7 || pageNumber < 1) {
            return "INVALID PAGE NUMBER";
        }

        for (int i = 0; i < frames.length; i++) {
            if (frames[i].getBlockID() == pageNumber) {
                if (!frames[i].isPinned()) {
                    return "PAGE " + pageNumber + " ALREADY UNPINNED";
                } else {
                    frames[i].unpin();
                    numPinned--;
                    return "Unpinned Frame: " + i;
                }
            }
        }
        return "What kind of edge case is this";

    }

    private void updateNumberOfPins() {
        for (int i = 0; i < numFrames; i++) {
            int numberOfPins = 0;
            if (frames[i].isPinned()) {
                this.numPinned = numberOfPins;
            }
        }
    }

    
}
