[CmdletBinding()]
param(
    [string]$DatabaseHost = '192.168.88.27',

    [ValidateRange(1, 65535)]
    [int]$Port = 5432,

    [string]$Database = 'jaen',

    [string]$DatabaseUser = 'jaen_app',

    [string]$Profile = 'maqueta_dgt_jaen',

    [ValidateSet('Backend', 'HistoricalAudit', 'Front')]
    [string]$Phase = 'Backend',

    [string]$PsqlPath,

    [string]$PasswordFile,

    [switch]$Apply,

    [string]$Confirmation
)

$ErrorActionPreference = 'Stop'

$requiredTablespaces = @(
    'tbl_conf',
    'tbl_master',
    'tbl_ui',
    'tbl_hist',
    'tbl_rt',
    'tbl_static'
)

$repositoryRoot = Split-Path -Parent $PSScriptRoot
$backDatabaseRoot = Join-Path $repositoryRoot 'ritsback/resources/db'
$frontDatabaseRoot = Join-Path $repositoryRoot 'ritsfront/resources/db'

$allScriptGroups = @(
    @{ Phase = 'Backend'; Name = 'DDL Backscada'; Path = (Join-Path $backDatabaseRoot 'ddl') },
    @{ Phase = 'Backend'; Name = 'DDL tiempo real'; Path = (Join-Path $backDatabaseRoot 'ddlrt') },
    @{ Phase = 'Backend'; Name = 'DDL histórico'; Path = (Join-Path $backDatabaseRoot 'ddlhist') },
    @{ Phase = 'Backend'; Name = 'DML maestro Backscada'; Path = (Join-Path $backDatabaseRoot 'dml/master') },
    @{ Phase = 'Backend'; Name = "DML Backscada ($Profile)"; Path = (Join-Path $backDatabaseRoot "dml/$Profile") },
    @{ Phase = 'Front'; Name = 'DDL Frontscada'; Path = (Join-Path $frontDatabaseRoot 'ddl') },
    @{ Phase = 'Front'; Name = "DDL Backoffice Frontscada ($Profile)"; Path = (Join-Path $frontDatabaseRoot "ddlbackoffice/$Profile") },
    @{ Phase = 'Front'; Name = 'DML maestro Frontscada'; Path = (Join-Path $frontDatabaseRoot 'dml/master') },
    @{ Phase = 'Front'; Name = "DML Frontscada ($Profile)"; Path = (Join-Path $frontDatabaseRoot "dml/$Profile") }
)

$scriptGroups = @($allScriptGroups | Where-Object { $_.Phase -eq $Phase })

function Initialize-HistoricalAuditTables {
    $sql = @'
CREATE SEQUENCE IF NOT EXISTS hist.audit_log_1_secuence START 1;
CREATE TABLE IF NOT EXISTS hist.audit_log_1 (
  audit_log_id int8 NOT NULL DEFAULT nextval('hist.audit_log_1_secuence'), user_name varchar NOT NULL,
  time_stamp timestamptz NOT NULL, level int4 NOT NULL, log_subtype_id int8 NULL, param_1 varchar NULL,
  created_at timestamptz NOT NULL, CONSTRAINT pk_audit_log_1 PRIMARY KEY (audit_log_id, time_stamp)
);
ALTER TABLE hist.audit_log_1 SET TABLESPACE tbl_hist;
CREATE INDEX IF NOT EXISTS idx_audit_log_1user_name ON hist.audit_log_1 USING btree (user_name);
CREATE INDEX IF NOT EXISTS idx_audit_log_1time_stamp ON hist.audit_log_1 USING btree (time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_log_1user_nametime_stamp ON hist.audit_log_1 USING btree (user_name, time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_log_1param_1 ON hist.audit_log_1 USING btree (param_1);

CREATE SEQUENCE IF NOT EXISTS hist.audit_plans_secuence START 1;
CREATE TABLE IF NOT EXISTS hist.audit_plans (
  audit_log_id int8 NOT NULL DEFAULT nextval('hist.audit_plans_secuence'), user_name varchar NOT NULL,
  time_stamp timestamptz NOT NULL, level int4 NOT NULL, log_subtype_id int8 NULL, plan varchar NULL,
  arg varchar NULL, comment varchar NULL, created_at timestamptz NOT NULL,
  CONSTRAINT pk_audit_plans PRIMARY KEY (audit_log_id, time_stamp)
);
ALTER TABLE hist.audit_plans SET TABLESPACE tbl_hist;
CREATE INDEX IF NOT EXISTS idx_audit_plansuser_name ON hist.audit_plans USING btree (user_name);
CREATE INDEX IF NOT EXISTS idx_audit_planstime_stamp ON hist.audit_plans USING btree (time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_plansuser_nametime_stamp ON hist.audit_plans USING btree (user_name, time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_plansplan ON hist.audit_plans USING btree (plan);

CREATE SEQUENCE IF NOT EXISTS hist.audit_commands_secuence START 1;
CREATE TABLE IF NOT EXISTS hist.audit_commands (
  audit_log_id int8 NOT NULL DEFAULT nextval('hist.audit_commands_secuence'), user_name varchar NOT NULL,
  time_stamp timestamptz NOT NULL, level int4 NOT NULL, log_subtype_id int8 NULL, command_type varchar NULL,
  element varchar NULL, command_values varchar NULL, comment varchar NULL, created_at timestamptz NOT NULL,
  CONSTRAINT pk_audit_commands PRIMARY KEY (audit_log_id, time_stamp)
);
ALTER TABLE hist.audit_commands SET TABLESPACE tbl_hist;
CREATE INDEX IF NOT EXISTS idx_audit_commandsuser_name ON hist.audit_commands USING btree (user_name);
CREATE INDEX IF NOT EXISTS idx_audit_commandstime_stamp ON hist.audit_commands USING btree (time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_commandsuser_nametime_stamp ON hist.audit_commands USING btree (user_name, time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_commandscommand_type ON hist.audit_commands USING btree (command_type);
CREATE INDEX IF NOT EXISTS idx_audit_commandselement ON hist.audit_commands USING btree (element);

CREATE SEQUENCE IF NOT EXISTS hist.audit_users_secuence START 1;
CREATE TABLE IF NOT EXISTS hist.audit_users (
  audit_log_id int8 NOT NULL DEFAULT nextval('hist.audit_users_secuence'), user_name varchar NOT NULL,
  time_stamp timestamptz NOT NULL, level int4 NOT NULL, log_subtype_id int8 NULL, session varchar NULL,
  comment varchar NULL, created_at timestamptz NOT NULL, CONSTRAINT pk_audit_users PRIMARY KEY (audit_log_id, time_stamp)
);
ALTER TABLE hist.audit_users SET TABLESPACE tbl_hist;
CREATE INDEX IF NOT EXISTS idx_audit_usersuser_name ON hist.audit_users USING btree (user_name);
CREATE INDEX IF NOT EXISTS idx_audit_userstime_stamp ON hist.audit_users USING btree (time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_usersuser_nametime_stamp ON hist.audit_users USING btree (user_name, time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_userssession ON hist.audit_users USING btree (session);

CREATE SEQUENCE IF NOT EXISTS hist.audit_processes_secuence START 1;
CREATE TABLE IF NOT EXISTS hist.audit_processes (
  audit_log_id int8 NOT NULL DEFAULT nextval('hist.audit_processes_secuence'), user_name varchar NOT NULL,
  time_stamp timestamptz NOT NULL, level int4 NOT NULL, log_subtype_id int8 NULL, process varchar NULL,
  running_since varchar NULL, comment varchar NULL, created_at timestamptz NOT NULL,
  CONSTRAINT pk_audit_processes PRIMARY KEY (audit_log_id, time_stamp)
);
ALTER TABLE hist.audit_processes SET TABLESPACE tbl_hist;
CREATE INDEX IF NOT EXISTS idx_audit_processesuser_name ON hist.audit_processes USING btree (user_name);
CREATE INDEX IF NOT EXISTS idx_audit_processestime_stamp ON hist.audit_processes USING btree (time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_processesuser_nametime_stamp ON hist.audit_processes USING btree (user_name, time_stamp);
CREATE INDEX IF NOT EXISTS idx_audit_processesprocess ON hist.audit_processes USING btree (process);
CREATE INDEX IF NOT EXISTS idx_audit_processesrunning_since ON hist.audit_processes USING btree (running_since);
'@

    & $PsqlPath -X -q -v ON_ERROR_STOP=1 -h $DatabaseHost -p $Port -U $DatabaseUser -d $Database -c $sql
    if ($LASTEXITCODE -ne 0) {
        throw 'No se pudieron crear las tablas de auditoría histórica.'
    }
}

function Initialize-HistoricalElementTables {
    $configsRoot = Join-Path (Split-Path -Parent $repositoryRoot) 'configs'
    $adapterDirectory = Join-Path $configsRoot "resources/project/$Profile/adapters/manager/historical-manager/element_type"
    if (-not (Test-Path -LiteralPath $adapterDirectory -PathType Container)) {
        throw "No existe la configuración histórica del perfil: $adapterDirectory"
    }

    $dataTypes = @{
        '1' = 'int4'
        '2' = 'varchar'
        '3' = 'float8'
        '4' = 'timestamptz'
        '5' = 'bool'
    }

    foreach ($adapterFile in Get-ChildItem -LiteralPath $adapterDirectory -File -Filter '*.xml') {
        $adapter = [xml](Get-Content -LiteralPath $adapterFile.FullName -Raw)
        $root = $adapter.DocumentElement
        $elementTypeId = $root.GetAttribute('element_type_id')
        $targetTable = $root.SelectSingleNode('//*[local-name()="target_table"]').InnerText
        $tableSpace = $root.SelectSingleNode('//*[local-name()="table_space"]').InnerText
        $elementIdField = $root.SelectSingleNode('//*[local-name()="field_element_id"]').InnerText
        $timestampNode = $root.SelectSingleNode('//*[local-name()="field_timestamp"]')
        $timestampField = if ($null -eq $timestampNode) { $null } else { $timestampNode.InnerText }

        if ($targetTable -notmatch '^hist\.[a-z_][a-z0-9_]*$' -or
            $tableSpace -notmatch '^tbl_[a-z_][a-z0-9_]*$' -or
            $elementIdField -notmatch '^[a-z_][a-z0-9_]*$') {
            throw "Adaptador histórico no válido: $($adapterFile.FullName)"
        }

        $columns = @("$elementIdField int8 NOT NULL")
        $primaryKey = @($elementIdField)
        if (-not [string]::IsNullOrWhiteSpace($timestampField)) {
            $columns += "$timestampField timestamptz NOT NULL"
            $primaryKey += $timestampField
        }
        $indexes = @($elementIdField)

        foreach ($field in $root.SelectNodes('//*[local-name()="field"]')) {
            $fieldName = $field.GetAttribute('name')
            $parameterId = $field.SelectSingleNode('./*[local-name()="element_type_param"]/*[local-name()="element_type_param_id"]').InnerText
            $parameterTypeId = $field.SelectSingleNode('./*[local-name()="element_type_param"]/*[local-name()="param_type_id"]').InnerText
            if ($fieldName -notmatch '^[a-z_][a-z0-9_]*$') {
                throw "Campo histórico no válido en $($adapterFile.FullName): $fieldName"
            }

            $dataTypeId = & $PsqlPath -X -q -At -v ON_ERROR_STOP=1 -h $DatabaseHost -p $Port -U $DatabaseUser -d $Database -c "SELECT data_type_id FROM master.element_type_params WHERE element_type_id = $elementTypeId AND element_type_param_id = $parameterId AND param_type_id = $parameterTypeId;"
            if ($LASTEXITCODE -ne 0) {
                throw "No se pudo resolver el tipo de $targetTable.$fieldName"
            }
            $sqlType = $dataTypes[($dataTypeId -join '').Trim()]
            if ($null -eq $sqlType) {
                continue
            }

            $columns += "$fieldName $sqlType NULL"
            if ($field.GetAttribute('pk') -eq 'true') {
                $primaryKey += $fieldName
            }
            if ($field.GetAttribute('indexed') -eq 'true') {
                $indexes += $fieldName
            }
        }

        $tableName = ($targetTable -split '\.')[1]
        $sql = @(
            "CREATE TABLE IF NOT EXISTS $targetTable ($($columns -join ', '), created_at timestamptz NOT NULL, CONSTRAINT pk_$tableName PRIMARY KEY ($($primaryKey -join ', ')));",
            "ALTER TABLE $targetTable SET TABLESPACE $tableSpace;"
        )
        foreach ($indexField in $indexes) {
            $sql += "CREATE INDEX IF NOT EXISTS idx_$tableName$indexField ON $targetTable USING btree ($indexField);"
        }

        & $PsqlPath -X -q -v ON_ERROR_STOP=1 -h $DatabaseHost -p $Port -U $DatabaseUser -d $Database -c ($sql -join [Environment]::NewLine)
        if ($LASTEXITCODE -ne 0) {
            throw "No se pudo crear la tabla histórica $targetTable"
        }
    }
}

function Get-SqlFiles {
    param([hashtable]$Group)

    if (-not (Test-Path -LiteralPath $Group.Path -PathType Container)) {
        throw "No existe la ruta de $($Group.Name): $($Group.Path)"
    }

    [System.IO.FileInfo[]]$files = @(
        Get-ChildItem -LiteralPath $Group.Path -Recurse -File -Filter '*.sql'
    )

    $comparer = [System.Collections.Generic.Comparer[System.IO.FileInfo]]::Create(
        [System.Comparison[System.IO.FileInfo]]{
            param($left, $right)
            [System.StringComparer]::Ordinal.Compare(
                $left.FullName.Substring($Group.Path.Length).Replace('\', '/'),
                $right.FullName.Substring($Group.Path.Length).Replace('\', '/')
            )
        }
    )
    [Array]::Sort($files, $comparer)

    if ($files.Count -eq 0) {
        throw "No hay scripts SQL en $($Group.Name): $($Group.Path)"
    }

    return $files
}

$executionPlan = foreach ($group in $scriptGroups) {
    [pscustomobject]@{
        Name = $group.Name
        Files = @(Get-SqlFiles -Group $group)
    }
}

$fileCount = @($executionPlan | ForEach-Object { $_.Files }).Count

Write-Host "Destino: postgresql://$DatabaseHost`:$Port/$Database ($DatabaseUser)"
Write-Host "Perfil: $Profile"
Write-Host "Fase: $Phase"
Write-Host "Scripts SQL: $fileCount"

foreach ($group in $executionPlan) {
    Write-Host ('  {0}: {1}' -f $group.Name, $group.Files.Count)
}

if (-not $Apply) {
    Write-Host ''
    Write-Host 'Previsualización completada. No se ha conectado ni modificado la BBDD.'
    Write-Host "Para ejecutar esta fase, añade: -Apply -Confirmation REINICIAR-JAEN"
    exit 0
}

if ($Confirmation -cne 'REINICIAR-JAEN') {
    throw 'La ejecución reinicia los esquemas de la aplicación. Usa -Confirmation REINICIAR-JAEN.'
}

if ([string]::IsNullOrWhiteSpace($PsqlPath)) {
    $psqlCommand = Get-Command psql.exe -ErrorAction SilentlyContinue
    if ($null -eq $psqlCommand) {
        throw 'No se encontró psql.exe. Instala el cliente de PostgreSQL o indica -PsqlPath.'
    }
    $PsqlPath = $psqlCommand.Source
}

if (-not (Test-Path -LiteralPath $PsqlPath -PathType Leaf)) {
    throw "No existe psql.exe: $PsqlPath"
}

$passwordPointer = [IntPtr]::Zero
$previousPassword = $env:PGPASSWORD
$previousClientEncoding = $env:PGCLIENTENCODING

try {
    if ([string]::IsNullOrWhiteSpace($PasswordFile)) {
        $password = Read-Host -Prompt "Contraseña de $DatabaseUser" -AsSecureString
        $passwordPointer = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($password)
        $env:PGPASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($passwordPointer)
    }
    else {
        if (-not (Test-Path -LiteralPath $PasswordFile -PathType Leaf)) {
            throw "No existe el fichero de contraseña: $PasswordFile"
        }
        $env:PGPASSWORD = (Get-Content -LiteralPath $PasswordFile -Raw).TrimEnd("`r", "`n")
        if ([string]::IsNullOrEmpty($env:PGPASSWORD)) {
            throw 'El fichero de contraseña está vacío.'
        }
    }
    $env:PGCLIENTENCODING = 'UTF8'

    $missingTablespaces = & $PsqlPath -X -q -At -v ON_ERROR_STOP=1 -h $DatabaseHost -p $Port -U $DatabaseUser -d $Database -c @'
WITH required(name) AS (
    VALUES ('tbl_conf'), ('tbl_master'), ('tbl_ui'),
           ('tbl_hist'), ('tbl_rt'), ('tbl_static')
)
SELECT name
FROM required
EXCEPT
SELECT spcname FROM pg_tablespace
ORDER BY name;
'@
    if ($LASTEXITCODE -ne 0) {
        throw 'No se pudo comprobar la conexión o los tablespaces.'
    }
    if (-not [string]::IsNullOrWhiteSpace(($missingTablespaces -join ''))) {
        throw "Faltan tablespaces requeridos: $($missingTablespaces -join ', ')."
    }

    if ($Phase -eq 'Backend') {
        # El DDL histórico conserva temporalmente las claves foráneas de rt antes
        # de recrear conf/static/master, pero presupone que rt existe.
        & $PsqlPath -X -q -v ON_ERROR_STOP=1 -h $DatabaseHost -p $Port -U $DatabaseUser -d $Database -c 'CREATE SCHEMA IF NOT EXISTS rt; DROP TABLE IF EXISTS rt.temp_fk_constraints_rt;'
        if ($LASTEXITCODE -ne 0) {
            throw 'No se pudo preparar el esquema rt requerido por el DDL histórico.'
        }
    }
    elseif ($Phase -eq 'Front') {
        $auditTables = & $PsqlPath -X -q -At -v ON_ERROR_STOP=1 -h $DatabaseHost -p $Port -U $DatabaseUser -d $Database -c @'
SELECT required.name
FROM (VALUES ('hist.audit_commands'), ('hist.audit_plans')) AS required(name)
WHERE to_regclass(required.name) IS NULL
ORDER BY required.name;
'@
        if ($LASTEXITCODE -ne 0) {
            throw 'No se pudieron comprobar las tablas de auditoría histórica.'
        }
        if (-not [string]::IsNullOrWhiteSpace(($auditTables -join ''))) {
            throw "Faltan tablas de auditoría histórica: $($auditTables -join ', '). Ejecuta la fase HistoricalAudit antes de la fase Front."
        }

        # El DDL Backoffice de Jaén hace DROP SCHEMA sin IF EXISTS antes de
        # recrear sus metadatos. Se asegura el esquema vacío en la primera carga.
        & $PsqlPath -X -q -v ON_ERROR_STOP=1 -h $DatabaseHost -p $Port -U $DatabaseUser -d $Database -c 'CREATE SCHEMA IF NOT EXISTS historical_data;'
        if ($LASTEXITCODE -ne 0) {
            throw 'No se pudo preparar el esquema historical_data para Frontscada.'
        }

        Write-Host 'Creando tablas históricas de elementos...'
        Initialize-HistoricalElementTables
    }

    $current = 0
    foreach ($group in $executionPlan) {
        Write-Host "Ejecutando $($group.Name)..."
        foreach ($file in $group.Files) {
            $current++
            $relativePath = $file.FullName.Substring($repositoryRoot.Length + 1)
            Write-Host ("[{0}/{1}] {2}" -f $current, $fileCount, $relativePath)
            & $PsqlPath -X -q -v ON_ERROR_STOP=1 -h $DatabaseHost -p $Port -U $DatabaseUser -d $Database -b -f $file.FullName
            if ($LASTEXITCODE -ne 0) {
                throw "Falló el script: $relativePath"
            }
        }
    }

    if ($Phase -eq 'Backend' -or $Phase -eq 'HistoricalAudit') {
        Write-Host 'Creando tablas de auditoría histórica...'
        Initialize-HistoricalAuditTables
    }
}
finally {
    if ($null -eq $previousPassword) {
        Remove-Item Env:PGPASSWORD -ErrorAction SilentlyContinue
    }
    else {
        $env:PGPASSWORD = $previousPassword
    }
    if ($null -eq $previousClientEncoding) {
        Remove-Item Env:PGCLIENTENCODING -ErrorAction SilentlyContinue
    }
    else {
        $env:PGCLIENTENCODING = $previousClientEncoding
    }
    if ($passwordPointer -ne [IntPtr]::Zero) {
        [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($passwordPointer)
    }
}

Write-Host 'Carga de la BBDD completada.'
