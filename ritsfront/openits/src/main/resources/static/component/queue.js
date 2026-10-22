export default class Queue {
	
	constructor() {
		
		this.items = {};
		this.headIndex = 0;
		this.tailIndex = 0;
	}

	push(item) {
		
		if (item) {
			
			this.items[this.tailIndex] = item;
			this.tailIndex++;	
		}
	}

	pop() {
		
		const item = this.items[this.headIndex];
		
		if (item) {
			
			delete this.items[this.headIndex];
			this.headIndex++;	
		}	
		
		return item;
	}

	front() {
		
		return this.items[this.headIndex];
	}

	get length() {
		
		return this.tailIndex - this.headIndex;
	}
}