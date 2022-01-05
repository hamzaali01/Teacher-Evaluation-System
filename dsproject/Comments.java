package dsproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Comments {
    
    node head;
    node tail;
    int totalCount = 0;
    int goodComment = 0;
    int averageComment = 0;
    int badComment = 0;
    

    public void insertComment(String s) {
        node newNode = new node(s);
        if(head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
         //tail.next = head;
         //head.prev = tail;
        totalCount++;
        
        if(s.contains("good") || s.contains("excellent") || s.contains("great") || s.contains("fair") || s.contains("amazing")|| s.contains("accomodating")){
        goodComment++;
        }
        else if(s.contains("worst") || s.contains("bad") || s.contains("unfair") || s.contains("confusing")|| s.contains("poor")|| s.contains("incomprehensible"))
        badComment++;
        else{
        averageComment++;
        }
    }

    public String toString() {
        String s = "";
        node curr = head;
        while(curr != null) {
            s += curr.comment + "   upvotes: " + curr.upvotes + "\n";
            curr = curr.next;
        }
        return s;
    }

    public node find(String comment) {
        node curr = head;
        while(curr != null && curr.comment != comment) {
            curr = curr.next;
        }
        return curr;
    }

    /*public void upvote(String comment) {
        node curr = find(comment);
        curr.upvotes++;
      //  sortListAccordingToUpvotes();
    }*/

    //bubble sort
   /* public void sortListAccordingToUpvotes() {
        String temp;
        int temp2;
        node curr = head;
        node curr2 = null;
        while(curr != null) {
            curr2 = curr.next;
            while(curr2 != head) {
                if(curr.upvotes < curr2.upvotes) {
                    temp = curr.comment;                         
                    curr.comment = curr2.comment;
                    curr2.comment = temp;

                    temp2 = curr.upvotes;
                    curr.upvotes = curr2.upvotes;
                    curr2.upvotes = temp2;
                                               
                }
                curr2 = curr2.next;
            }
            if(curr==tail)
            break;
            curr = curr.next;
        }
    } */
    public void insertionSort(){
		node tempPrev = this.head;
		node temp = null;
		while (tempPrev != null)
		{
			temp = tempPrev.next;
			while (temp != null && temp.prev != null && temp.upvotes > temp.prev.upvotes)
			{
					swapComment(temp, temp.prev);
					temp = temp.prev;
				
			}
			tempPrev = tempPrev.next;
		}
	}
    public void swapComment(node first, node second)
	{
		String value = first.comment;
		first.comment = second.comment;
		second.comment = value;
                
                int value2 = first.upvotes;
		first.upvotes = second.upvotes;
		second.upvotes = value2;
	}

    

   
}

class node {
    node next;
    node prev;
    String comment;
    int upvotes;

    public node(String comment) {
        this.comment = comment;
            // getting upvotes of the comment to be inserted from the data file
           try {
             File myObj = new File("C:\\Users\\HP\\Documents\\NetBeansProjects\\DSProject\\src\\dsproject\\Data\\commentsData.txt");
             Scanner myReader = new Scanner(myObj);
             boolean flag = false;
             while(myReader.hasNextLine()) {
             String data = myReader.nextLine();
             if(flag==true){
              upvotes = Integer.parseInt(data);
              break;
             }
             if(comment.equals(data) && !data.isBlank()){
                 flag = true;
             }
             }
             myReader.close();
            } catch (FileNotFoundException e) {
             System.out.println("An error occurred.");
             e.printStackTrace();
              }
    }
    public void upvote(){
     upvotes++;
    }
}