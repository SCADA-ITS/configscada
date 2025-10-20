#!/bin/bash

# Configuración
REMOTE_USER="admin"
REMOTE_HOST=$2
SCADA_HOST=$3
REMOTE_PATH="/home/admin/scripts"
ARCHIVO_TAR="backup_sql.tar.gz"
LOCAL_FOLDERS=("ritsback/resources/db" "ritsfront/resources/db")
PROYECTO=$1

# Comprimir las carpetas antes de subirlas
echo "Comprimiendo carpetas locales..."
tar -czf $ARCHIVO_TAR "${LOCAL_FOLDERS[@]}"

# Subir el archivo comprimido vía SFTP
echo "Subiendo $ARCHIVO_TAR a $REMOTE_HOST..."
sftp $REMOTE_USER@$REMOTE_HOST <<EOF
put $ARCHIVO_TAR
EOF

# Conectar por SSH, descomprimir y ejecutar scripts SQL
echo "Conectando a $REMOTE_HOST para descomprimir y ejecutar scripts..."
ssh $REMOTE_USER@$REMOTE_HOST <<EOF
mkdir -p $REMOTE_PATH
mv $ARCHIVO_TAR $REMOTE_PATH/
cd $REMOTE_PATH
tar -xzf $ARCHIVO_TAR
rm $ARCHIVO_TAR

# Ejecutar los scripts SQL en el servidor remoto
cd ritsback/resources/db
./make_param.sh $PROYECTO $REMOTE_HOST 5430 
cd ../../../ritsfront/resources/db
./make_param.sh $PROYECTO $REMOTE_HOST 5430 $SCADA_HOST
EOF

# Limpiar el archivo comprimido localmente
rm -rf $REMOTE_PATH

echo "Proceso completado exitosamente."
