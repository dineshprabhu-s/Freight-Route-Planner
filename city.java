import java.util.*;
import java.io.*;

public class city implements Serializable{
	String name;
	int code;
	int dist[];
	city(String name,int code,int dist[]){
		this.name=name;
		this.code=code;
		this.dist=dist;
	}
	void display(){
	    	System.out.println("\nName:"+this.name+"\nCode:"+this.code);
		for(int i=0;i<dist.length;i++){
			if(dist[i]<Integer.MAX_VALUE) System.out.print(dist[i] + " ");
			else System.out.print("-1 ");
		}
	}
	void table(){
		System.out.println("\n\t"+this.code+"\t\t"+this.name);
	}	
	public int[] get_dist(){
		return this.dist;
	}
}