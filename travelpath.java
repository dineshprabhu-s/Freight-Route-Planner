import java.util.*;
import java.io.*;

public class travelpath{
	int[] path;
	int total_distance;
	double fuel_needed,fuel_cost;
	travelpath(destination[] path,int[][] dist,double[][][] fuel,int cont,int[][][] trace,double cost){
		this.total_distance=0;
		int cur_cont=cont;
		for(int i=0;i<(path.length-1);i++){
			int s=(path[i].code)-100;
			int d=(path[i+1].code)-100;
			combine(trace[s][d]);
			(this.total_distance)+=(dist[s][d]);
			(this.fuel_needed)+=(fuel[s][d][cur_cont]);
			(cur_cont)-=(path[i+1].container);
		}
		this.fuel_cost=fuel_needed*cost;
	}

	public void combine(int[] trace){	
		if (this.path == null) {
    			this.path = new int[trace.length];
    			System.arraycopy(trace, 0, this.path, 0, trace.length);
		} else {
    			int[] newArr = new int[this.path.length + trace.length -1];
    			System.arraycopy(this.path, 0, newArr, 0, this.path.length);
    			System.arraycopy(trace, 1, newArr,this.path.length, trace.length-1);
    			this.path = newArr;
		}
	}

	public void statement(){
		System.out.println("\n\n\n\n**********************************\n\n\n");
		System.out.print("\nOptimal Route:\n\t");
		for(int i=0;i<this.path.length;i++){
			System.out.print("|"+path[i]+"|=>");
		}System.out.print("END\n\n");
		System.out.println("\nTOTAL DISTANCE\t: "+this.total_distance+" KM");
		System.out.println("TOTAL FUEL NEED\t: "+this.fuel_needed+" litres");
		System.out.println("TOTAL FUEL COST\t: "+this.fuel_cost+" rupees\n\n\n\n**********************************");
	}
}