package Project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Frame {

   private String[] content = new String[100]; // 100 records per file, store 1 file per frame
   private Boolean dirty; // has the file been modiftied?
   private int blockID; // file number
   private boolean isPinned; // is the file pinned?

   public Frame() {
      this.blockID = -1;
      this.dirty = false;
      this.isPinned = false;
   }

   public void setContent(int fileNumber) {

      String fileName = "F" + fileNumber + ".txt";

      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
         StringBuilder sb = new StringBuilder();
         String line = br.readLine();
   
         while (line != null) {
           sb.append(line);
           sb.append(System.lineSeparator());
           line = br.readLine();
         }
   
         String[] sentences = sb.toString().split("\\.");
         this.content = sentences;
   
         // // Print each sentence on a new line
         // for (String sentence : sentences) {
         //   System.out.println(sentence.trim());
         // }


         // System.out.println(content[99]);

       } catch (IOException e) {
         System.err.format("IOException: %s%n", e);
       }
   }

   public String getRecord(int recordNumber) {
      return this.content[recordNumber - 1]; // return the record at the given record number
   }

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
