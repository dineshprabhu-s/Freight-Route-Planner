import java.util.*;
import java.io.*;

public class train{
	public static void main(String args[]){
		int des;
		Scanner in=new Scanner(System.in);
		System.out.print("\n\nEnter Number of cities: ");
		int N=in.nextInt();
		city cities[]=new city[N];
		for(int i=0;i<N;i++){
			System.out.print("\n\nCity Name: ");
			String name=in.next();
			int[] dist=new int[N];
			Arrays.fill(dist,Integer.MAX_VALUE);
        		dist[i]=0;
			do{
				System.out.print("\n\nConnected City code: ");
				des=in.nextInt();
				if(des!=-1){
					System.out.print("\n\nDistance: ");
					dist[des-100]=in.nextInt();
				}
			}while(des!=-1);
			cities[i]=new city(name,(100+i),dist);
		}
		for(int i=0;i<N;i++){
			cities[i].display();
		}
		try{
			FileOutputStream fout=new FileOutputStream("city_data.ser");
			ObjectOutputStream oout=new ObjectOutputStream(fout);
			oout.writeObject(cities);
			oout.close();
			fout.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}