import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from '../graphicIcon.js';

// Parámetro de arc_level
const PARAM_ARC_LEVEL = "ElementTypeParam:73:2:1";

// IDs de las columnas en el SVG
const COLUMN_IDS = ["cinco", "seis", "siete", "ocho", "nueve"];

// Colores de las barras
const COLOR_ON = "yellow";
const COLOR_OFF = "gray";

// Estados del elemento 73
const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:73:0", "ElementTypeState:73:2"];

// Mapeo de valores a índice de columna máxima encendida
// 0 -> columna 0 ("cinco"), 100 -> columna 4 ("nueve")
const VALUE_TO_COLUMN_INDEX = {
    0: 0,
    2: 1,
    10: 2,
    20: 3,
    100: 4
};

export default class ArcLevelControl extends GraphicIconCustomType {

    constructor(s, g, elementInfo, events) {
        super(s, g, elementInfo, events);
        this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
        // Guardamos referencias a las columnas
        this.columns = COLUMN_IDS.map(id => this.g.select("#" + id));
    }

    render(elementInfo) {
        this.elementInfo = elementInfo;

        // Actualizar el color del estado normal (lo decide el sistema)
        if (this.gState) {
            this.gState.attr({ fill: elementInfo.state.color });
        }

        let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

        // Si NO_TREATMENT → apagar columnas y salir
        if (!treatmentValue) {
            this.columns.forEach(col => col && col.attr({ fill: COLOR_OFF }));
            return;
        }

        // Leer valor de arc_level
        let arcLevelValue = parseInt(this.getValue(PARAM_ARC_LEVEL));

        // Determinar hasta qué columna encender
        let maxIndex = VALUE_TO_COLUMN_INDEX[arcLevelValue];

        // Encender desde la primera hasta maxIndex
        this.columns.forEach((col, index) => {
            if (col) {
                col.attr({ fill: (index <= maxIndex) ? COLOR_ON : COLOR_OFF });
            }
        });
    }
}
