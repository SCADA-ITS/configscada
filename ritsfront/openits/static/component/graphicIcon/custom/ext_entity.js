import GraphicIconExtEntity from "../graphicIconExtEntity.js";

const PICTOGRAM_TYPE_PATH = "img/ext_entity";
const PICTOGRAM_UNKNOWN_TYPE = "img/ext_entity/undefined.png";


export default class ExtEntity {
	
	constructor(s, g, extEntity, events) {

		this.s = s;
		this.g = g;
		this.extEntity = extEntity;
		this.events = events;

		this.gType = this.g.select("#" + GraphicIconExtEntity.G_ID_TYPE);
	}
	
	render(extEntity) {

		this.extEntity = extEntity;

		// Update type
		if (this.gType) {
				
			if (this.extEntity.extEntityType.icon) {
					
				this.gType.attr( { "xlink:href": PICTOGRAM_TYPE_PATH + "/" + this.extEntity.extEntityType.icon });
			}
			else {
				
				this.gType.attr( { "xlink:href": PICTOGRAM_UNKNOWN_TYPE });
			}
		}
	}
}

