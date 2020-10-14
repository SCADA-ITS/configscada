if (window.app)
	app.adapter = {

		getElementTypeParamId(elementValueId) {

			let elementTypeParamId;

			let splitStr = elementValueId.split(":");

			elementTypeParamId = "ElementTypeParam" + ":" + splitStr[1] + ":" + splitStr[4] + ":" + splitStr[3];

			return elementTypeParamId;
		},

		ws: {

			element: {

				getList(data) {

					let gridData = {};

					if (data != undefined && Array.isArray(data.basicElementsVO) && data.basicElementsVO.length > 0) {

						gridData.data = [];

						data.basicElementsVO.forEach(element => {

							let dataItem = {};

							dataItem.id = element.id;
							
							if (element.state != undefined) {
							
								dataItem.state = element.state;
							}

							if (element.elementValues != undefined && Array.isArray(element.elementValues) && element.elementValues.length > 0) {

								element.elementValues.forEach(elementValue => {

									let elementTypeParamId = app.adapter.getElementTypeParamId(elementValue.id);

									dataItem[elementTypeParamId] = {};

									dataItem[elementTypeParamId].state = elementValue.state;
									dataItem[elementTypeParamId].value = elementValue.value;
								});
							}
							
							gridData.data.push(dataItem);
						});
					}
					
					return gridData;
				}
			}
		}
	};
