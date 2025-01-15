import GraphicIconExtEntity from "../graphicIconExtEntity.js";

const PICTOGRAM_TYPE_PATH = "img/ext_entity_type";
const PICTOGRAM_UNKNOWN_TYPE = "undefined.png";


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
			
			if (this.extEntity.extEntitySubtype && this.extEntity.extEntitySubtype.icon_data) {	
								
				const dynamicId = `ExtEntityTypeParam:${this.extEntity.extEntityType.id.split(":")[1]}:8`;
				
				const colorActual = this.extEntity.extEntityValues.find(object => object.extEntityTypeParam?.id === dynamicId)?.value;
				
				let iconoValor = "";
					
				if (colorActual === undefined) {
									
					iconoValor = this.extEntity.extEntitySubtype.icon_data;
				
				} else {
					
					const iconData = JSON.parse(this.extEntity.extEntitySubtype.icon_data);
					
					const icono = iconData.iconos.find(icon => icon.color === colorActual);
					
					const specialIconId = `ExtEntityTypeParam:${this.extEntity.extEntityType.id.split(":")[1]}:12`;
					const specialIcon = this.extEntity.extEntityValues.find(object => object.extEntityTypeParam?.id === specialIconId)?.value;
					
					if (specialIcon){
						
						let changeIcon = iconData.especiales?.find(special => special.id === specialIcon);
						if (icono && changeIcon)
							iconoValor = changeIcon ? changeIcon.icono + "_" + colorActual + ".png" : PICTOGRAM_UNKNOWN_TYPE;
						else
							iconoValor = icono ? icono.valor : PICTOGRAM_UNKNOWN_TYPE;
					}else{
		
						iconoValor = PICTOGRAM_UNKNOWN_TYPE;
					}
				}
				
				this.gType.attr({ "xlink:href": `${PICTOGRAM_TYPE_PATH}/${iconoValor}` });
			}
			else if (this.extEntity.extEntityType && this.extEntity.extEntityType.icon_data) {
								
				const dynamicId = `ExtEntityTypeParam:${this.extEntity.extEntityType.id.split(":")[1]}:8`;
				
				const colorActual = this.extEntity.extEntityValues.find(object => object.extEntityTypeParam?.id === dynamicId)?.value;

				let iconoValor = "";
				
				if (colorActual === undefined) {
					
					iconoValor = this.extEntity.extEntitySubtype.icon_data;
				
				} else {
					
					const iconData = JSON.parse(this.extEntity.extEntityType.icon_data);
					
					const icono = iconData.iconos.find(icon => icon.color === colorActual);
									
					iconoValor = icono ? icono.valor : PICTOGRAM_UNKNOWN_TYPE;
				}
				
				this.gType.attr({ "xlink:href": `${PICTOGRAM_TYPE_PATH}/${iconoValor}` });
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

