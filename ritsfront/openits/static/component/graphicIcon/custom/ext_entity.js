import GraphicIconExtEntity from "../graphicIconExtEntity.js";

const PICTOGRAM_TYPE_PATH = "img/ext_entity_type";
const PICTOGRAM_UNKNOWN_TYPE = "img/ext_entity_type/undefined.png";


export default class ExtEntity {
	
	constructor(s, g, extEntity, events) {

		this.s = s;
		this.g = g;
		this.extEntity = extEntity;
		this.events = events;

		this.gType = this.g.select("#" + GraphicIconExtEntity.G_ID_TYPE);
		this.gStatus = this.g.select("#" + GraphicIconExtEntity.G_ID_STATUS);
	}
	
	render(extEntity) {

		this.extEntity = extEntity;

		// Update type
		if (this.gType) {
			
			if (this.extEntity.extEntitySubtype && this.extEntity.extEntitySubtype.icon) {
				
				this.gType.attr( { "xlink:href": PICTOGRAM_TYPE_PATH + "/" + this.extEntity.extEntitySubtype.icon });
			}
			else if (this.extEntity.extEntityType.icon) {
					
				this.gType.attr( { "xlink:href": PICTOGRAM_TYPE_PATH + "/" + this.extEntity.extEntityType.icon });
			}
			else {
				
				this.gType.attr( { "xlink:href": PICTOGRAM_UNKNOWN_TYPE });
			}
		}
		
		// TODO Update status if aplicable
		if (this.gStatus) {
			
		}
	}
}

