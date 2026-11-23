-- Ejecutar después de cargar los metadatos base de backoffice.sql.
-- La tabla sg_metadata_tables debe contener ya el id 1 (incidents).

INSERT INTO backoffice.sg_metadata_columns
    (id, sg_metadata_table_id, column_name, "label", label_description,
     needs_translation, metadata, ref_view_column_id)
SELECT 0135, 1, 'km_start', 'Km inicio', NULL, false,
       '{"position":35,"editable":false,"tableVisible":false,"formVisible":false}', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM backoffice.sg_metadata_columns
    WHERE sg_metadata_table_id = 1 AND column_name = 'km_start'
);

INSERT INTO backoffice.sg_metadata_columns
    (id, sg_metadata_table_id, column_name, "label", label_description,
     needs_translation, metadata, ref_view_column_id)
SELECT 0136, 1, 'km_end', 'Km fin', NULL, false,
       '{"position":36,"editable":false,"tableVisible":false,"formVisible":false}', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM backoffice.sg_metadata_columns
    WHERE sg_metadata_table_id = 1 AND column_name = 'km_end'
);

INSERT INTO backoffice.sg_metadata_columns
    (id, sg_metadata_table_id, column_name, "label", label_description,
     needs_translation, metadata, ref_view_column_id)
SELECT 0137, 1, 'croquis_x_top', 'X croquis superior', NULL, false,
       '{"position":37,"editable":false,"tableVisible":false,"formVisible":false}', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM backoffice.sg_metadata_columns
    WHERE sg_metadata_table_id = 1 AND column_name = 'croquis_x_top'
);

INSERT INTO backoffice.sg_metadata_columns
    (id, sg_metadata_table_id, column_name, "label", label_description,
     needs_translation, metadata, ref_view_column_id)
SELECT 0138, 1, 'croquis_x_bottom', 'X croquis inferior', NULL, false,
       '{"position":38,"editable":false,"tableVisible":false,"formVisible":false}', NULL
WHERE NOT EXISTS (
    SELECT 1 FROM backoffice.sg_metadata_columns
    WHERE sg_metadata_table_id = 1 AND column_name = 'croquis_x_bottom'
);
