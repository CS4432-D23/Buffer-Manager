import java.util.ArrayList;

public class Frame {

   private ArrayList<String> content = new ArrayList<>(); //to hold the file content
   private Boolean dirty;     // set to True if the content of this block has changed and need to be written to disk when this frame is taken out
   private Boolean pinned;    // True if there is a request to keep this block in memory and not take it out. False, means it can be taken out.
   private int blockID;       // It should be the Id of the block stored in this frame. E.g., if we need to read file #3 as in Example 2, then “blockId = 3”.
   // You can use “-1” to indicate that the fame is empty and there is no block in this frame
   //… any other variables you think useful and you need it in your design

   





}
