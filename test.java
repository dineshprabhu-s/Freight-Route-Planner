import java.util.*;
import java.io.*;

public class test{
	public static int N;
	public static void main(String args[]){
		city[] map;
		try {
    			FileInputStream fin=new FileInputStream("city_data.ser");
    			ObjectInputStream oin=new ObjectInputStream(fin);
    			map = (city[])oin.readObject();
    			oin.close();
    			fin.close();
			N=map.length;
			System.out.println("\n\tCODE\t\tCITY\n\n");
			for(int i=0;i<N;i++){
				map[i].table();
			}			
			journey travel=new journey(map);
		} catch(Exception e) {
    			System.out.println(e);
		}
	}
}