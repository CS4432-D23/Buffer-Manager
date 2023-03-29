package Project1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Frame class for the Buffer Pool
 */
public class Frame {

   private String[] content = new String[100]; // 100 records per file, store 1 file per frame
   private Boolean dirty; // has the file been modiftied?
   private int blockID; // file number
   private boolean isPinned; // is the file pinned?

   /**
    * Constructor for the Frame class
    */
   public Frame() {
      this.dirty = false;
      this.blockID = -1;
      this.isPinned = false;
   }

   /**
    * Initialize the Frame
    */
   public void initalize() {
      for (int i = 0; i < 100; i++) {
         this.content[i] = null;
      }
   }

   /**
    * Read the file into the Frame
    * @param fileNumber the file number to read
    */
   public void readFile(int fileNumber) {

      // getting file name
      String fileName = "F" + fileNumber + ".txt";

      // reading file
      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
         StringBuilder sb = new StringBuilder();
         String line = br.readLine();
   
         while (line != null) {
           sb.append(line);
           sb.append(System.lineSeparator());
           line = br.readLine();
         }
   
         // setting content to the file contents (what is read in SB)
         String[] sentences = sb.toString().split("(?<=\\.)");
         this.content = sentences;

       } catch (IOException e) {
         System.err.format("IOException: %s%n", e);
       }


   }

   /**
    * Write the Frame to the file
    * @param fileNumber the file number to write to
    */
   public void writeFile() {

      String fileName = "F" + blockID + ".txt";

      try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

         for (String sentence : this.content) {
            bw.write(sentence);
         }
         
       } catch (IOException e) {
         System.err.format("IOException: %s%n", e);
       }

   }
   
   /**
    * Print the contents of the Frame
    */
   public void printContents() {
      for (String record : content) {
         System.out.println(record);
      }
   }

   /**
    * Set the record at the given record number
    * @param recordNumber the record number to set
    * @param newRecord the new record to set
    */
   public void setRecord(int recordNumber, String newRecord) {
      this.content[recordNumber - 1] = newRecord;
      this.dirty = true;
   }
   
   /**
    * Get the record at the given record number
    * @param recordNumber the record number to get
    * @return the record at the given record number
    */
   public String getRecord(int recordNumber) {
      return this.content[recordNumber - 1]; // return the record at the given record number
   }

   /**
    * Update the record at the given record number
    * @param recordNumber the record number to update
    * @param updatedRecord the updated record
    */
   public void updateRecord(int recordNumber, String updatedRecord) {
      this.content[recordNumber] = updatedRecord;
      this.dirty = true;
   }


   public String[] getContent() {
      return this.content;
   }


   public int getBlockID() {
      return this.blockID;
   }

   public void setBlockID(int blockID) {
      this.blockID = blockID;
   }

   public void setPageNumber(int blockID) {
      this.blockID = blockID;
   }

   public Boolean isDirty() {
      return this.dirty;
   }

   public void setDirty(Boolean dirty) {
      this.dirty = dirty;
   }

   public boolean isPinned() {
      return this.isPinned;
   }

   public void pin() {
      this.isPinned = true;
   }

   public void unpin() {
      this.isPinned = false;
   }

}
