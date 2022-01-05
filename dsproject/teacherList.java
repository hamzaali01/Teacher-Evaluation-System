package dsproject;

public class teacherList{
    courseList[] Table;
    int numofcollisions=0;
    int numofinsertions = 0;
    int sum = 0;
    int count = 0;

    public teacherList(int s){
        int size = s+(s/3);
       // int size  = s*2;
        int newSize = getPrime(size);
        Table = new courseList[newSize];
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
    //returns numeric value for String
    public int hashStr(String s) {
        int g = 31;
        int  hash = 0;
        for(int i=0; i<s.length();i++){
            hash = g * hash + s.charAt(i);
        }
        return hash; 
       } 

    public void insertCourseList(String s, courseList courses){
        int StrHashValue = Math.abs(hashStr(s));
        int index = Hash(StrHashValue);
        if(Table[index]==null){
        Table[index] = courses;
        }
        else{
           // System.out.println("Rehashing on index " + index);
            int limit = 0;
            int i = 1;
            while(Table[index]!=null && limit<10){
            index = Rehash(StrHashValue, i); //linear probing i*i for quadratic
            i++;    
            limit++;
            numofcollisions++;
           // System.out.println(numofcollisions);
            }
            if(Table[index]==null){
            Table[index] = courses;
           // System.out.println(numofcollisions);
            sum = sum + numofcollisions;
            count++;
            numofcollisions = 0;
            }
            else
            System.out.println("Limit for Rehash reached");
            
        }
        numofinsertions++;
        
        double loadfactor = (double)numofinsertions/(double)Table.length;
        if(loadfactor>=0.76){
        DoubleSize();
        }
    }
    
    private void DoubleSize(){
        int newsize = Table.length*2;
        newsize = getPrime(newsize);
        courseList[] newTable = new courseList[newsize];
        
        for(int i=0;i<Table.length;i++){
            newTable[i] = Table[i];
        }
        Table = newTable;
    }
    
    public void printCollisions(){
        System.out.println("total collisions " +sum);
    }
    //avg rehash needed to insert value
     public void printAvg(){
         System.out.println("num of teachers that were rehashed " + count);
         System.out.println("avg rehash per teacher " + (double)sum/(double)count);
    }
    //returns hash value for string numeric value
    public int Hash(int StrHashValue){
        return StrHashValue%Table.length;
    }
    public int Rehash(int StrHashValue, int i){
       return Math.abs((StrHashValue+i)%Table.length);
      // return (Hash(StrHashValue) + i*Hash2(StrHashValue))%Table.length;
    }
   /* public int Hash2(int StrHashVal){
        int num = getPrime(80);
        return (num - StrHashVal%num);
    } */

    public Ratings search(String TeacherName, String courseName){
        int StrHashValue = Math.abs(hashStr(TeacherName));
        int HashValue = Hash(StrHashValue);
        if(Table[HashValue]==null){
            System.out.println("Teacher not found");
            return null;
        }
        else if(Table[HashValue].name.equals(TeacherName)){
            return Table[HashValue].search(courseName);
        }
        else{
            int limit = 0;
            int i = 1;
            while(Table[HashValue]!=null && limit<10 && !Table[HashValue].name.equals(TeacherName)){
            HashValue = Rehash(StrHashValue, i); //linear probing i*i for quadratic
            i++;    
            limit++;
            }
            if(Table[HashValue]==null || limit==10){
                System.out.println("Teacher not found");
                return null;
            }
            else{
            return Table[HashValue].search(courseName);
            }
        }
   } 
}

