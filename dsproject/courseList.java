package dsproject;

public class courseList {
    Ratings[] courseTable;
    String name;
    int numofcollisions = 0;
    int numofinsertions = 0;
    int sum = 0;
    int count = 0;

    public courseList(String TeacherName, int s) {
        int size = s+(s/3); 
        int newSize = getPrime(size);
        courseTable = new Ratings[newSize];
        name = TeacherName;
    } 
    private int getPrime(int n){
        while(true){
            if(isPrime(n)) 
            return n;
            
            n++;
        }
    }
    private boolean isPrime(int n){
        for(int i=2; i<=n/2; i++){
            if(n%i==0)
            return false;
        }
        return true;
    }

    public int hashStr(String s) {
        int g = 31;
        int  hash = 0;
        for(int i=0; i<s.length();i++){
            hash = g * hash + s.charAt(i);
        }
        return hash;       
    }
    
    public void insertRatings(String courseName, Ratings ratings){
        int StrHashValue =  Math.abs(hashStr(courseName));
        int index =  Hash(StrHashValue);
        if(courseTable[index]==null){
        courseTable[index] = ratings;
        }
        else{
            int limit = 0;
            int i = 1;
            while(courseTable[index]!=null && limit<10){
            index = Rehash(StrHashValue, i*i); //quadratic probing
            i++;    
            limit++;
            numofcollisions++;
            }
            if(courseTable[index]==null){
            courseTable[index] = ratings;
            sum = sum + numofcollisions;
            count++;
              //  System.out.println("ind collision " + numofcollisions);
            numofcollisions = 0;
            }
            else
            System.out.println("Limit for Rehash reached");
        }
        numofinsertions++;
        
        double loadfactor = (double)numofinsertions/(double)courseTable.length;
        if(loadfactor>=0.76){
        DoubleSize();
        }
    }
    
    private void DoubleSize(){
        int newsize = courseTable.length*2;
        newsize = getPrime(newsize);
        Ratings[] newTable = new Ratings[newsize];
        
        for(int i=0;i<courseTable.length;i++){
            newTable[i] = courseTable[i];
        }
        courseTable = newTable;
    }
        
    public void printCollisions(){
        System.out.println("total collisions " +sum);
    }
     //avg rehash needed to insert value
    public void printAvg(){
         System.out.println("num of courses that were rehashed " + count);
         System.out.println("avg rehash per course " + (double)sum/(double)count);
    }
    //returns hash value for string numeric value
    public int Hash(int StrHashValue){
       return StrHashValue%courseTable.length;
    }
    
    public int Rehash(int HashValue, int i){
        return Math.abs((HashValue+i)%courseTable.length);
        // return (Hash(HashValue) + i*Hash2(HashValue))%courseTable.length;
    }
   /* public int Hash2(int StrHashVal){
        int num = getPrime(15);
        return (num - StrHashVal%num);
    } */

    public Ratings search(String courseName){
        int StrHashValue = Math.abs(hashStr(courseName));
        int HashValue = Hash(StrHashValue);
        if(courseTable[HashValue]==null){
            System.out.println("Course not found");
            return null;
        }
        else if(courseTable[HashValue].coursename.equals(courseName)){
        return courseTable[HashValue];
        }
        else{
            int limit = 0;
            int i = 1;
            while(courseTable[HashValue]!=null && limit<10 && !courseTable[HashValue].coursename.equals(courseName)){
            HashValue = Rehash(StrHashValue, i*i); //quadratic
            i++;    
            limit++;
            }
            if(courseTable[HashValue]==null || limit==10 ){
            System.out.println("Course not found");
            return null;
            }
            else{
            return courseTable[HashValue];
            }
        }
   } 
}
