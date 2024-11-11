import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const G_ID_TYPE = "type";
const PICTOGRAM_TYPE_PATH = "img/maps"

export default class MapIcon extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gType = this.g.select("#" + G_ID_TYPE);
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		
		this.elementTypeStatesNoTreatment = [];
	}
	
	render(elementInfo) {
		
		let elementType;

		this.elementInfo = elementInfo;			
		
		window.em.getByField(EntityType.ELEMENT_TYPE, "id", "ElementType:2").default_svg

		if (this.gState) {
			
			elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", "ElementType:" + elementInfo.id.split(":")[1]);
			
			this.gState.attr({ fill: elementInfo.state.color });
			this.gType.attr( { "xlink:href": PICTOGRAM_TYPE_PATH + "/" + elementType.map_icon });
		}else {
				
			this.gType.attr( { "xlink:href": PICTOGRAM_UNKNOWN_TYPE });
		}
	}
}

