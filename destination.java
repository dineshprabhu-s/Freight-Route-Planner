import java.util.*;

public class destination{
	String name;
	int code,container;
	int distance;
	destination(int code,int cont,city[] map){
		this.code=code;
		for(int i=0;i<map.length;i++){
			if(this.code==map[i].code) this.name=map[i].name;
		}
		this.container=cont;
		this.distance=0;
		System.out.println(name);
	}
	void display(){
		System.out.println("\nName: "+this.name+"\tContainers: "+this.container);
	}
}