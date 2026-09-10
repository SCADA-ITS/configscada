# Ejecutor Windows de la BBDD OpenITS

`Invoke-OpenItsDatabase.ps1` reemplaza los scripts Bash de carga para una BBDD
vacía de Jaén. Se ejecuta en tres fases: Backend (DDL base, de tiempo real e
histórico de Backscada y sus DML), HistoricalAudit (tablas de auditoría) y
Front (DDL y DML de Frontscada), con el perfil `maqueta_dgt_jaen`.

No guarda ni muestra la contraseña. Requiere `psql.exe` del cliente de
PostgreSQL. El túnel Kubernetes hacia TimescaleDB debe estar activo antes de
ejecutarlo. Fuerza `UTF8` para leer los SQL con caracteres internacionales y
solo muestra el avance y los errores de `psql`. Conserva el orden ordinal de
los scripts Bash originales.

Por defecto solicita la contraseña de forma interactiva. Para automatizar una
ejecución local puede leerse desde un fichero temporal de una sola línea con
`-PasswordFile`; el ejecutor no muestra ni guarda su contenido.

Primero muestra el plan, sin conectarse a la BBDD:

```powershell
Set-Location C:\Datos\GIT\rits
.\tools\Invoke-OpenItsDatabase.ps1
```

Para cargar la fase Backend, solicita la contraseña de `jaen_app` y exige una
confirmación explícita porque los DDL eliminan y recrean los esquemas `conf`,
`static`, `master` y `rt`. El ejecutor crea `rt` vacío si no existe, requisito
implícito del DDL histórico:

```powershell
.\tools\Invoke-OpenItsDatabase.ps1 `
  -Phase Backend -Apply -Confirmation REINICIAR-JAEN
```

La fase Backend crea automáticamente las tablas de auditoría histórica. En una
instalación ordinaria las crea `historical-manager`, pero este despliegue
inicia el executor de auditoría en el hilo 1 y el código solo las inicializa
en el hilo 0. `HistoricalAudit` permite crearlas sin repetir Backend si una
carga anterior se detuvo antes de Front:

```powershell
.\tools\Invoke-OpenItsDatabase.ps1 `
  -Phase HistoricalAudit -Apply -Confirmation REINICIAR-JAEN
```

Después carga Front. El ejecutor comprueba que existan las tablas históricas
requeridas antes de modificar los esquemas de Frontscada:

```powershell
.\tools\Invoke-OpenItsDatabase.ps1 `
  -Phase Front -Apply -Confirmation REINICIAR-JAEN
```

Los valores de conexión pueden cambiarse sin editar el script:

```powershell
.\tools\Invoke-OpenItsDatabase.ps1 `
  -DatabaseHost 192.168.88.27 -Port 5432 `
  -Database jaen -DatabaseUser jaen_app `
  -PsqlPath 'C:\Program Files\PostgreSQL\17\bin\psql.exe' `
  -Phase Backend -Apply -Confirmation REINICIAR-JAEN
```
