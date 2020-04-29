import {genericTable} from "../data/genericTable.js";

export default class Table {
	constructor(data, metadata){
		this.data= data;
		if(metadata==null){
			this.metadata=genericTable;
		}else{
			this.metadata=metadata;
		}
	}
	getComponent(){
		if(this.data.includes("http") || this.data.includes("GraphQL")){
			this.metadata.url=this.data;
		}else{
			this.metadata.data=this.data;
		}
		return this.metadata;
	}
}