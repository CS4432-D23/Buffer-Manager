import java.util.ArrayList;

public class Frame {

   String[] myArray = new String[100]; // 100 records per file, store 1 file per frame
   private Boolean dirty; // has the file been modiftied?
   private int pageNumber; // page number of the file
   private boolean isPinned; // is the file pinned?

   public Frame() {
      this.pageNumber = -1;
      this.dirty = false;
      this.isPinned = false;
   }

   public int getPageNumber() {
      return pageNumber;
   }

   public void setPageNumber(int pageNumber) {
      this.pageNumber = pageNumber;
   }

   public boolean isPinned() {
      return isPinned;
   }

   public void pin() {
      this.isPinned = true;
   }
   public void unpin() {
      this.isPinned = false;
   }

}
