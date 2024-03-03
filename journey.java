import java.util.*;
import java.io.*;

public class journey{
	private static final double efficiency_loss = 0.025;
	private static final int INF = Integer.MAX_VALUE;
	double mileage,f_cost;
	int cap,cont,src;
	destination source_city;
	destination[] dest;
	city source;
	
	journey(city[] map){
		Scanner in =new Scanner(System.in);
		System.out.print("\n\nTrain engine Mileage: ");
		this.mileage=in.nextFloat();
		System.out.print("\n\nMAX Capacity of Train: ");
		this.cap=in.nextInt();
		System.out.print("\n\nFuel Cost(per litre): ");
		this.f_cost=in.nextFloat();
		System.out.print("\n\nSource City code: ");
		this.src=in.nextInt();
		for(int i=0;i<map.length;i++){
			if(src==map[i].code) source_city=new destination(src,0,map);
		}
		do{
			System.out.print("\n\nNumber of Containers to be loaded: ");
			this.cont=in.nextInt();
			if(this.cont>this.cap) System.out.println("\nMAX CAPACITY IS "+this.cap+" !!!");
		}while(this.cont>this.cap);
		System.out.print("\n\nNumber of Destination City: ");
		int n=in.nextInt();
		dest=new destination[n];
		for(int i=0;i<n;i++){
			System.out.print("\n\nDestination city code: ");
			int des=in.nextInt();
			System.out.print("\n\nNumber of containers to be unloaded: ");
			int un=in.nextInt();
			destination d=new destination(des,un,map);
			this.dest[i]=d;
		}
		int[][] distance=new int[map.length][];
		for(int i=0;i<map.length;i++){
			distance[i]=map[i].get_dist();
		}
			
		/*for (int i = 0; i < distance.length; i++) {
      		for (int j = 0; j < distance[i].length; j++) {
        			if(distance[i][j]<INF) System.out.print(distance[i][j] + " ");
				else System.out.print(" - ");
      		}
      		System.out.println();
    		};*/

		int[][][] path_data=shortest_path(distance);
		distance=shortest_dist(distance);

		/*for (int i = 0; i < distance.length; i++) {
            	for (int j = 0; j < distance.length; j++) {
                	if (i != j) {
                    		int[] path =path_data[i][j];
                    		System.out.print("Shortest path from " + i + " to " + j + " is ");
                		for (int k = 0; k < path.length; k++) {
                    			System.out.print(path[k]);
                    			if (k < path.length - 1) {
                        			System.out.print(" -> ");
                    			}
                		}
                		System.out.println(", distance = " + distance[i][j]);
                	}
            	}
        	}*/	

		double fuel[][][]=estimate_fuel(distance,cont);

		/*for(int i=0;i<fuel.length;i++){
			for(int j=0;j<fuel[i].length;j++){
					System.out.println("src: "+(100+i)+"\tdes: "+(100+j));
				for(int k=0;k<fuel[i][j].length;k++){
					System.out.println(" "+fuel[i][j][k]);
				}
			}
		}*/
		/*int[] destin=new int[dest.length];
		for(int i=0;i<dest.length;i++){
			destin[i]=(dest[i].code)-100;
		}*/

		destination[][] perms = permutations(dest);

		/*for (int i = 0; i < perms.length; i++) {
    			System.out.println(Arrays.toString(perms[i]));
		}*/

		for (int i=0;i<perms.length;i++) {
    			destination[] temp=new destination[perms[i].length+1];
			temp[0]=source_city;
			for(int j=0;j<perms[i].length;j++) {
    				temp[j+1]=perms[i][j];
			}	
			perms[i]=temp;
		}
		
		travelpath[] possible_path=new travelpath[perms.length];

		for(int i=0;i<perms.length;i++){
			possible_path[i]=new travelpath(perms[i],distance,fuel,this.cont,path_data,this.f_cost);
		}
		double min=Double.MAX_VALUE;
		travelpath solution=possible_path[0];
		for(int i=0;i<possible_path.length;i++){
			if((possible_path[i].fuel_cost)<min){
				solution=possible_path[i];
				min=solution.fuel_cost;
			}
		}
		solution.statement();
	}
	
	destination[][] permutations(destination[] arr) {
    		int n = arr.length;
    		int no=factorial(n);
    		destination[][] result=new destination[no][n];
	    	int[] index=new int[n];
    		for(int i=0;i<n;i++){
        		index[i]=i;
    		}
    		int count = 0;
    		while(count<no) {
        		for(int i=0;i<n;i++) {
            		result[count][i]=(arr[index[i]]);
        		}
        		count++;
        		int k=n-2;
        		while(k>=0 && index[k]>index[k+1]){
            		k--;
        		}
        		if(k<0){
            		break;
        		}
			int l = n - 1;
        		while(index[k] > index[l]){
            		l--;
        		}
			swap(index, k, l);
        		int p=k+1,q=n-1;
        		while(p<q){
            		swap(index,p++,q--);
        		}
    		}
		return result;
	}

	void swap(int[] arr,int i,int j) {
    		int temp=arr[i];
    		arr[i]=arr[j];
    		arr[j]=temp;
	}
	
	int factorial(int n) {
    		int result = 1;
    		for (int i = 2; i <= n; i++) {
        		result *= i;
    		}
    		return result;
	}

	public double[][][] estimate_fuel(int[][] dist,int cont){
		int n = dist.length;
		double fuel[][][]=new double[n][n][cont+1];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				for(int k=0;k<=cont;k++){
					double temp_mileage=(this.mileage)-(k*(this.mileage)*efficiency_loss);
					fuel[i][j][k]=(dist[i][j])/temp_mileage;
				}
			}
		}
		return fuel;
	}

	int[][] shortest_dist(int[][] dis) {
        	int n=dis.length;
        	int[][] dist=new int[n][n];
        	for(int i=0;i<n;i++){
            	for(int j=0;j<n;j++){
                	dist[i][j]=dis[i][j];
            	}
        	}
        	for(int k=0;k<n;k++){
            	for(int i=0;i<n;i++){
                	for(int j=0;j<n;j++){
                    	if(dist[i][k]!=INF && dist[k][j]!=INF && dist[i][k]+dist[k][j] < dist[i][j]){
                        	dist[i][j]=dist[i][k]+dist[k][j];
                    	}
                	}
            	}
        	}
        	return dist;
    	}
    	
	int[][][] shortest_path(int[][] dis) {
    		int n=dis.length;
    		int[][] distance=new int[n][n];
    		int[][][] result_path=new int[n][n][];
    		for(int i=0;i<n;i++){
        		for(int j=0;j<n;j++){
            		distance[i][j]=dis[i][j];
            		if(dis[i][j]!=INF && i!=j){
                		result_path[i][j]=new int[] {i, j};
            		} 
				else{
                		result_path[i][j]=new int[0];
            		}
        		}
    		}
    		for(int k=0;k<n;k++){
        		for(int i=0;i<n;i++){
            		for(int j=0;j<n;j++){
                		if(distance[i][k]!=INF && distance[k][j]!=INF && distance[i][k]+distance[k][j] < distance[i][j]){
                    		distance[i][j]=distance[i][k] + distance[k][j];
                    		result_path[i][j]=append(result_path[i][k],Arrays.copyOfRange(result_path[k][j],1,result_path[k][j].length));
                		}
            		}
        		}
    		}
		return result_path;
	}

	int[] append(int[] a,int[] b) {
    		int[] temp=new int[a.length+b.length];
    		System.arraycopy(a,0,temp,0,a.length);
    		System.arraycopy(b,0,temp,a.length,b.length);
    		return temp;
	}
}