---
name: azure-sql-ingestion-template
owner: wenqun
description:  xyz
tags: 
  - ABC
  - xyz
parameters:
  - bod_name?required=true&default=xxx
  - application
annotators:
  - project-properties?format=yaml
  - table-definition-query?format=sql
  - table-definition?format=csv
processors:
  - data-catalog-generator

---
# Azure SQL Ingestion Template


## [Project Properties Settings](annotator://project-properties)
Please setup following properties:

```ftl project-properties
basic_project_skills.bod_name: care_alert
basic_project_skills.application_name: care_alert_disposition_rules
basic_project_skills.source_type: AZURESQL
basic_project_skills.table_name_base: care_alert_disposition_rule
basic_project_skills.table_file_name_base: Care_Alert_Disposition_Rule
basic_project_skills.project_id: <<gcp_project_id>>
basic_project_skills.refined_dataset: <<gcp_project_ds_ref_pd>>
basic_project_skills.confirmed_dataset: <<gcp_project_ds_conf_pd>>
basic_project_skills.view_dataset: <<gcp_project_ds_views_rx>>
basic_project_skills.consumption_project_id: <<gcp_v_project_id>>
basic_project_skills.consumption_dataset: <<gcp_v_project_ds_views_rx>>
basic_project_skills.audit_table_name: 
basic_project_skills.staging_table_name: care_alert_disposition_rule_stg
basic_project_skills.staging_table_file: care_alert/sql/ddl/refined/Tbl_Care_Alert_Disposition_Rule_Stg.sql
basic_project_skills.refined_table_name: care_alert_disposition_rule_raw
basic_project_skills.refined_table_file: care_alert/sql/ddl/refined/Tbl_Care_Alert_Disposition_Rule_Raw.sql
basic_project_skills.confirmed_table_name: care_alert_disposition_rule
basic_project_skills.confirmed_table_file: care_alert/sql/ddl/confirmed/Tbl_Care_Alert_Disposition_Rule.sql
basic_project_skills.confirmed_view_name: care_alert_disposition_rule.sql
basic_project_skills.confirmed_view_file: Vw_Care_Alert_Disposition_Rule.sql
basic_project_skills.consumption_view_name: care_alert_disposition_rule
basic_project_skills.consumption_view_file: Vw_Care_Alert_Disposition_Rule.sql
azure_sql_ingestion_skills.source_server_name: 
azure_sql_ingestion_skills.source_database_name: 
azure_sql_ingestion_skills.source_table_name: disposition_rules
azure_sql_ingestion_skills.refined_procedure_name: sp_care_alert_disposition_rule_refined
azure_sql_ingestion_skills.refined_procedure_file: care_alert/sql/Sp_Care_Alert_Disposition_Rule_Refined.sql
azure_sql_ingestion_skills.confirmed_procedure_name: sp_care_alert_disposition_rule_ingestion
azure_sql_ingestion_skills.confirmed_procedure_file: care_alert/sql/Sp_Care_Alert_Disposition_Rule_Ingestion.sql
azure_sql_ingestion_skills.dag_task_flow_pattern: azure_sql_ingestion_dag.md
azure_sql_ingestion_skills.dag_extract_task_name:  care_alert_disposition_rule_stage
azure_sql_ingestion_skills.dag_extract_job_name:  CARE-ALERT-DISPOSITION-RULE
```


### Project Properties
| Property Name            | Property Value                                                     |
| ------------------------ | ------------------------------------------------------------------ |
| bod_name                 | care_alert                                                         |
| application_name         | care_alert_disposition_rules                                       |
| source_type              | AZURESQL                                                           |
| table_name_base          | care_alert_disposition_rule                                        |
| table_file_name_base     | Care_Alert_Disposition_Rule                                        |
| project_id               | <<gcp_project_id>>                                                 |
| refined_dataset          | <<gcp_project_ds_ref_pd>>                                          |
| confirmed_dataset        | <<gcp_project_ds_conf_pd>>                                         |
| view_dataset             | <<gcp_project_ds_views_rx>>                                        |
| consumption_project_id   | <<gcp_v_project_id>>                                               |
| consumption_dataset      | <<gcp_v_project_ds_views_rx>>                                      |
| audit_table_name         |                                                                    |
| staging_table_name       | care_alert_disposition_rule_stg                                    |
| staging_table_file       | care_alert/sql/ddl/refined/Tbl_Care_Alert_Disposition_Rule_Stg.sql |
| refined_table_name       | care_alert_disposition_rule_raw                                    |
| refined_table_file       | care_alert/sql/ddl/refined/Tbl_Care_Alert_Disposition_Rule_Raw.sql |
| confirmed_table_name     | care_alert_disposition_rule                                        |
| confirmed_table_file     | care_alert/sql/ddl/confirmed/Tbl_Care_Alert_Disposition_Rule.sql   |
| confirmed_view_name      | care_alert_disposition_rule.sql                                    |
| confirmed_view_file      | Vw_Care_Alert_Disposition_Rule.sql                                 |
| consumption_view_name    | care_alert_disposition_rule                                        |
| consumption_view_file    | Vw_Care_Alert_Disposition_Rule.sql                                 |
| source_server_name       |                                                                    |
| source_database_name     |                                                                    |
| source_table_name        | disposition_rules                                                  |
| refined_procedure_name   | sp_care_alert_disposition_rule_refined                             |
| refined_procedure_file   | care_alert/sql/Sp_Care_Alert_Disposition_Rule_Refined.sql          |
| confirmed_procedure_name | sp_care_alert_disposition_rule_ingestion                           |
| confirmed_procedure_file | care_alert/sql/Sp_Care_Alert_Disposition_Rule_Ingestion.sql        |
| dag_task_flow_pattern    | azure_sql_ingestion_dag.md                                         |
| dag_extract_task_name    | care_alert_disposition_rule_stage                                  |
| dag_extract_job_name     | CARE-ALERT-DISPOSITION-RULE                                        |



## Table Definition Query
Please editing and run following sql query and put result csv into 'table-definition-query'

```sql table-definition-query
SELECT TABLE_NAME,COLUMN_NAME,DATA_TYPE,IS_NULLABLE,'NO' AS PK,COLUMN_NAME AS BQ_COLUMN_NAME
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'disposition_rules'
```


## Table Definition
Please put 'table-definition-query' result as csv format into 'table-definition'

```csv table-definition
TABLE_NAME,COLUMN_NAME,DATA_TYPE,IS_NULLABLE,PK,BQ_COLUMN_NAME
DISPOSITION_RULES,rule_id,bigint,NO,YES,rule_id
DISPOSITION_RULES,vax_name,varchar,YES,NO,vax_name
DISPOSITION_RULES,vax_group,varchar,YES,NO,vax_group
DISPOSITION_RULES,vax_disposition,varchar,YES,NO,vax_disposition
DISPOSITION_RULES,suppression_days,int,YES,NO,suppression_days
DISPOSITION_RULES,is_seasonal,bit,YES,NO,seasonal_ind
DISPOSITION_RULES,season_start_month,int,YES,NO,season_start_month
DISPOSITION_RULES,season_end_month,int,YES,NO,season_end_month
DISPOSITION_RULES,client,varchar,YES,NO,client
DISPOSITION_RULES,created_ts,datetime,YES,NO,source_created_ts
DISPOSITION_RULES,updated_ts,datetime,YES,NO,source_updated_ts
```


## Azure SQL Select Query
Azure SQL select query for extract data from source table.

```sql source-select-query
SELECT rule_id,vax_name,vax_group,vax_disposition,suppression_days,is_seasonal,season_start_month,season_end_month,client,created_ts,updated_ts FROM dbo.disposition_rules
```


## Data Catalog
Please editing the data catalog

```csv data-catalog
"SOURCE_COLUMN","SOURCE_TYPE","STAGING_COLUMN_NAME","REFINED_COLUMN_NAME","REFINED_COLUMN_TYPE","CONFIRMED_COLUMN_NAME","CONFIRMED_COLUMN_TYPE","PK","REFINED_TRANSFORM","CONFIRMED_TRANSFORM","DESCRIPTION"
"rule_id","bigint","rule_id","rule_id","BIGNUMERIC","rule_id","BIGNUMERIC","YES","","","Vaccine rule identifier."
"vax_name","varchar","vax_name","vax_name","STRING","vax_name","STRING","NO","","","Vaccine name."
"vax_group","varchar","vax_group","vax_group","STRING","vax_group","STRING","NO","","","Vaccine group."
"vax_disposition","varchar","vax_disposition","vax_disposition","STRING","vax_disposition","STRING","NO","","","Vaccine disposition."
"suppression_days","int","suppression_days","suppression_days","INT64","suppression_days","INT64","NO","","","Suppression days."
"is_seasonal","bit","is_seasonal","seasonal_ind","BOOL","seasonal_ind","BOOL","NO","","","Is seassonal vaccine indicator"
"season_start_month","int","season_start_month","season_start_month","INT64","season_start_month","INT64","NO","","","Season start month."
"season_end_month","int","season_end_month","season_end_month","INT64","season_end_month","INT64","NO","","","Season end month."
"client","varchar","client","client","STRING","client","STRING","NO","","","Client"
"created_ts","datetime","created_ts","source_created_ts","DATETIME","source_created_ts","DATETIME","NO","","","Source created timestamp."
"updated_ts","datetime","updated_ts","source_updated_ts","DATETIME","source_updated_ts","DATETIME","NO","","","Source created timestamp."
"","","","","","source_application_cd","STRING","","","'care_alert_disposition_rules'","Source Application Code."
"","","","","","dw_create_ts","TIMESTAMP","","","CURRENT_TIMESTAMP()","The timestamp the record was inserted."
"","","","","","dw_last_update_ts","TIMESTAMP","","","CURRENT_TIMESTAMP()","When a record is updated.this would be the current timestamp."
"","","","","","dw_logical_delete_ind","BOOL","","","FALSE","Set to True when we receive a delete record for the primary key, else False."
"","","","","","dw_source_create_nm","STRING","","","''","The data source name of this insert."
"","","","","","dw_source_update_nm","STRING","","","''","The data source name of this update or delete."
```

| SOURCE_COLUMN      | SOURCE_TYPE | STAGING_COLUMN_NAME | REFINED_COLUMN_NAME | REFINED_COLUMN_TYPE | CONFIRMED_COLUMN_NAME | CONFIRMED_COLUMN_TYPE | PK  | REFINED_TRANSFORM | CONFIRMED_TRANSFORM            | DESCRIPTION                                                                  |
| ------------------ | ----------- | ------------------- | ------------------- | ------------------- | --------------------- | --------------------- | --- | ----------------- | ------------------------------ | ---------------------------------------------------------------------------- |
| rule_id            | bigint      | rule_id             | rule_id             | BIGNUMERIC          | rule_id               | BIGNUMERIC            | YES |                   |                                | Vaccine rule identifier.                                                     |
| vax_name           | varchar     | vax_name            | vax_name            | STRING              | vax_name              | STRING                | NO  |                   |                                | Vaccine name.                                                                |
| vax_group          | varchar     | vax_group           | vax_group           | STRING              | vax_group             | STRING                | NO  |                   |                                | Vaccine group.                                                               |
| vax_disposition    | varchar     | vax_disposition     | vax_disposition     | STRING              | vax_disposition       | STRING                | NO  |                   |                                | Vaccine disposition.                                                         |
| suppression_days   | int         | suppression_days    | suppression_days    | INT64               | suppression_days      | INT64                 | NO  |                   |                                | Suppression days.                                                            |
| is_seasonal        | bit         | is_seasonal         | seasonal_ind        | BOOL                | seasonal_ind          | BOOL                  | NO  |                   |                                | Is seassonal vaccine indicator                                               |
| season_start_month | int         | season_start_month  | season_start_month  | INT64               | season_start_month    | INT64                 | NO  |                   |                                | Season start month.                                                          |
| season_end_month   | int         | season_end_month    | season_end_month    | INT64               | season_end_month      | INT64                 | NO  |                   |                                | Season end month.                                                            |
| client             | varchar     | client              | client              | STRING              | client                | STRING                | NO  |                   |                                | Client                                                                       |
| created_ts         | datetime    | created_ts          | source_created_ts   | DATETIME            | source_created_ts     | DATETIME              | NO  |                   |                                | Source created timestamp.                                                    |
| updated_ts         | datetime    | updated_ts          | source_updated_ts   | DATETIME            | source_updated_ts     | DATETIME              | NO  |                   |                                | Source created timestamp.                                                    |
|                    |             |                     |                     |                     | source_application_cd | STRING                |     |                   | 'care_alert_disposition_rules' | Source Application Code.                                                     |
|                    |             |                     |                     |                     | dw_create_ts          | TIMESTAMP             |     |                   | CURRENT_TIMESTAMP()            | The timestamp the record was inserted.                                       |
|                    |             |                     |                     |                     | dw_last_update_ts     | TIMESTAMP             |     |                   | CURRENT_TIMESTAMP()            | When a record is updated.this would be the current timestamp.                |
|                    |             |                     |                     |                     | dw_logical_delete_ind | BOOL                  |     |                   | FALSE                          | Set to True when we receive a delete record for the primary key, else False. |
|                    |             |                     |                     |                     | dw_source_create_nm   | STRING                |     |                   | ''                             | The data source name of this insert.                                         |
|                    |             |                     |                     |                     | dw_source_update_nm   | STRING                |     |                   | ''                             | The data source name of this update or delete.                               |



## Data Catalog Spreadsheet
Please editing the data catalog spreadsheet

```csv data-catalog-spreadsheet
"TABLE_NAME","COLUMN_NAME","DATA_TYPE","PK","SOURCE_TYPE","SOURCE_NAME","SOURCE_MAPPING","DESCRIPTION"
"care_alert_disposition_rule","rule_id","BIGNUMERIC","YES","AZURESQL","disposition_rules","rule_id","Vaccine rule identifier."
"care_alert_disposition_rule","vax_name","STRING","NO","AZURESQL","disposition_rules","vax_name","Vaccine name."
"care_alert_disposition_rule","vax_group","STRING","NO","AZURESQL","disposition_rules","vax_group","Vaccine group."
"care_alert_disposition_rule","vax_disposition","STRING","NO","AZURESQL","disposition_rules","vax_disposition","Vaccine disposition."
"care_alert_disposition_rule","suppression_days","INT64","NO","AZURESQL","disposition_rules","suppression_days","Suppression days."
"care_alert_disposition_rule","seasonal_ind","BOOL","NO","AZURESQL","disposition_rules","is_seasonal","Is seassonal vaccine indicator"
"care_alert_disposition_rule","season_start_month","INT64","NO","AZURESQL","disposition_rules","season_start_month","Season start month."
"care_alert_disposition_rule","season_end_month","INT64","NO","AZURESQL","disposition_rules","season_end_month","Season end month."
"care_alert_disposition_rule","client","STRING","NO","AZURESQL","disposition_rules","client","Client"
"care_alert_disposition_rule","source_created_ts","DATETIME","NO","AZURESQL","disposition_rules","created_ts","Source created timestamp."
"care_alert_disposition_rule","source_updated_ts","DATETIME","NO","AZURESQL","disposition_rules","updated_ts","Source created timestamp."
"care_alert_disposition_rule","source_application_cd","STRING","","AZURESQL","disposition_rules","","Source Application Code."
"care_alert_disposition_rule","dw_create_ts","TIMESTAMP","","AZURESQL","disposition_rules","","The timestamp the record was inserted."
"care_alert_disposition_rule","dw_last_update_ts","TIMESTAMP","","AZURESQL","disposition_rules","","When a record is updated.this would be the current timestamp."
"care_alert_disposition_rule","dw_logical_delete_ind","BOOL","","AZURESQL","disposition_rules","","Set to True when we receive a delete record for the primary key, else False."
"care_alert_disposition_rule","dw_source_create_nm","STRING","","AZURESQL","disposition_rules","","The data source name of this insert."
"care_alert_disposition_rule","dw_source_update_nm","STRING","","AZURESQL","disposition_rules","","The data source name of this update or delete."
```

| TABLE_NAME                  | COLUMN_NAME           | DATA_TYPE  | PK  | SOURCE_TYPE | SOURCE_NAME       | SOURCE_MAPPING     | DESCRIPTION                                                                  |
| --------------------------- | --------------------- | ---------- | --- | ----------- | ----------------- | ------------------ | ---------------------------------------------------------------------------- |
| care_alert_disposition_rule | rule_id               | BIGNUMERIC | YES | AZURESQL    | disposition_rules | rule_id            | Vaccine rule identifier.                                                     |
| care_alert_disposition_rule | vax_name              | STRING     | NO  | AZURESQL    | disposition_rules | vax_name           | Vaccine name.                                                                |
| care_alert_disposition_rule | vax_group             | STRING     | NO  | AZURESQL    | disposition_rules | vax_group          | Vaccine group.                                                               |
| care_alert_disposition_rule | vax_disposition       | STRING     | NO  | AZURESQL    | disposition_rules | vax_disposition    | Vaccine disposition.                                                         |
| care_alert_disposition_rule | suppression_days      | INT64      | NO  | AZURESQL    | disposition_rules | suppression_days   | Suppression days.                                                            |
| care_alert_disposition_rule | seasonal_ind          | BOOL       | NO  | AZURESQL    | disposition_rules | is_seasonal        | Is seassonal vaccine indicator                                               |
| care_alert_disposition_rule | season_start_month    | INT64      | NO  | AZURESQL    | disposition_rules | season_start_month | Season start month.                                                          |
| care_alert_disposition_rule | season_end_month      | INT64      | NO  | AZURESQL    | disposition_rules | season_end_month   | Season end month.                                                            |
| care_alert_disposition_rule | client                | STRING     | NO  | AZURESQL    | disposition_rules | client             | Client                                                                       |
| care_alert_disposition_rule | source_created_ts     | DATETIME   | NO  | AZURESQL    | disposition_rules | created_ts         | Source created timestamp.                                                    |
| care_alert_disposition_rule | source_updated_ts     | DATETIME   | NO  | AZURESQL    | disposition_rules | updated_ts         | Source created timestamp.                                                    |
| care_alert_disposition_rule | source_application_cd | STRING     |     | AZURESQL    | disposition_rules |                    | Source Application Code.                                                     |
| care_alert_disposition_rule | dw_create_ts          | TIMESTAMP  |     | AZURESQL    | disposition_rules |                    | The timestamp the record was inserted.                                       |
| care_alert_disposition_rule | dw_last_update_ts     | TIMESTAMP  |     | AZURESQL    | disposition_rules |                    | When a record is updated.this would be the current timestamp.                |
| care_alert_disposition_rule | dw_logical_delete_ind | BOOL       |     | AZURESQL    | disposition_rules |                    | Set to True when we receive a delete record for the primary key, else False. |
| care_alert_disposition_rule | dw_source_create_nm   | STRING     |     | AZURESQL    | disposition_rules |                    | The data source name of this insert.                                         |
| care_alert_disposition_rule | dw_source_update_nm   | STRING     |     | AZURESQL    | disposition_rules |                    | The data source name of this update or delete.                               |



## SQL Script from Staging Table to Refined Table
Please editing the insert script from staging table to refined table

```sql staging-to-refined-sql
INSERT INTO `<<gcp_project_id>>.<<gcp_project_ds_ref_pd>>.care_alert_disposition_rule_raw` (
    rule_id,
    vax_name,
    vax_group,
    vax_disposition,
    suppression_days,
    seasonal_ind,
    season_start_month,
    season_end_month,
    client,
    source_created_ts,
    source_updated_ts
) 
SELECT
    rule_id,
    vax_name,
    vax_group,
    vax_disposition,
    suppression_days,
    is_seasonal,
    season_start_month,
    season_end_month,
    client,
    created_ts,
    updated_ts
FROM `<<gcp_project_id>>.<<gcp_project_ds_ref_pd>>.care_alert_disposition_rule_stg`;
```


## Merging Script from Refined Table to Confirmed Table
Please editing the merging script from refined table to confirmed table

```sql refined-to-confirmed-merging-sql
-- merge to confirmed table
    MERGE INTO `<<gcp_project_id>>.<<gcp_project_ds_conf_pd>>.care_alert_disposition_rule` T
        USING (SELECT * FROM `<<gcp_project_id>>.<<gcp_project_ds_ref_pd>>.care_alert_disposition_rule_raw` 
        QUALIFY ROW_NUMBER() OVER (
        PARTITION BY rule_id
        ORDER BY rule_id
        DESC) = 1
        ) S
        ON T.rule_id = S.rule_id
    WHEN MATCHED THEN
    UPDATE SET
        rule_id = S.rule_id,
        vax_name = S.vax_name,
        vax_group = S.vax_group,
        vax_disposition = S.vax_disposition,
        suppression_days = S.suppression_days,
        seasonal_ind = S.seasonal_ind,
        season_start_month = S.season_start_month,
        season_end_month = S.season_end_month,
        client = S.client,
        source_created_ts = S.source_created_ts,
        source_updated_ts = S.source_updated_ts,
        source_application_cd = 'care_alert_disposition_rules',
        dw_create_ts = CURRENT_TIMESTAMP(),
        dw_last_update_ts = CURRENT_TIMESTAMP(),
        dw_logical_delete_ind = FALSE,
        dw_source_create_nm = '',
        dw_source_update_nm = ''
    WHEN NOT MATCHED THEN
    INSERT (
        rule_id,
        vax_name,
        vax_group,
        vax_disposition,
        suppression_days,
        seasonal_ind,
        season_start_month,
        season_end_month,
        client,
        source_created_ts,
        source_updated_ts,
        source_application_cd,
        dw_create_ts,
        dw_last_update_ts,
        dw_logical_delete_ind,
        dw_source_create_nm,
        dw_source_update_nm
    ) VALUES (
        S.rule_id,
        S.vax_name,
        S.vax_group,
        S.vax_disposition,
        S.suppression_days,
        S.seasonal_ind,
        S.season_start_month,
        S.season_end_month,
        S.client,
        S.source_created_ts,
        S.source_updated_ts,
        'care_alert_disposition_rules',
        CURRENT_TIMESTAMP(),
        CURRENT_TIMESTAMP(),
        FALSE,
        '',
        ''
    );
```


## Annotate DAG Task flow
Please annotate DAG Task Flow:

```yaml dag-config
# File set to be generated: 
ingestion-pythone-file: dag/care_alert_disposition_rules_ingestion.py
dataflow-config-file: conf/care_alert_disposition_rules_job.yml
extract-task-config-file: conf/care_alert_disposition_rules_extract_task.yml

# Template Parameters Setting:
bod_name: care_alert
dag:
  dag_id: care_alert_disposition_rules_ingestion
  stage_task_id: care_alert_disposition_rules_stage
  stage_task_name: care_alert_disposition_rules_stage
  refined_task_id: care_alert_disposition_rules_refined
  refined_task_name: care_alert_disposition_rules_refined
  confirmed_task_id: care_alert_disposition_rules_confirmed
  confirmed_task_name: care_alert_disposition_rules_confirmed
  
dataflow_config:
  config_file_name: care_alert_disposition_rules_job.yml
  database: care_alert_database
  host_port: care_alert_host_port
  password: care_alert_password
  username: care_alert_username
extract_task:
  extract_task_name: care_alert_disposition_rule_stage
  extract_job_name: CARE-ALERT-DISPOSITION-RULE
  source_select_query: SELECT rule_id,vax_name,vax_group,vax_disposition,suppression_days,is_seasonal,season_start_month,season_end_month,client,created_ts,updated_ts FROM dbo.disposition_rules
  source_table_name: vendor_sms_delivery_info
  sql_delta_column_name: CAST(??? AS DATETIME2(3))
  sql_delta_column_type: DATETIMENTZ
  target_dataset: <<gcp_project_ds_ref_pd>>
  target_table: care_alert_disposition_rule_stg
```

## Processing Results
| RESULT  | PROCESSOR                                      | TYPE      | SKILLS                     |
| ------- | ---------------------------------------------- | --------- | -------------------------- |
| SUCCESS | annotate-properties-setting                    | ANNOTATOR | basic_project_skills       |
| SUCCESS | annotate-table-definition-query                | ANNOTATOR | azure_sql_ingestion_skills |
| SUCCESS | annotate-table-definition                      | ANNOTATOR | azure_sql_ingestion_skills |
| SUCCESS | annotate-select-query                          | ANNOTATOR | azure_sql_ingestion_skills |
| SUCCESS | annotate-data-catalog                          | ANNOTATOR | azure_sql_ingestion_skills |
| SUCCESS | annotate-data-catalog-spreadsheet              | ANNOTATOR | azure_sql_ingestion_skills |
| SUCCESS | annotate-sql-from-staging-to-refined           | ANNOTATOR | azure_sql_ingestion_skills |
| SUCCESS | annotate-merging-sql-from-refined-to-confirmed | ANNOTATOR | azure_sql_ingestion_skills |
| SUCCESS | annotate-dag-task-flow                         | ANNOTATOR | azure_sql_ingestion_skills |
| SUCCESS | clean_project                                  | GENERATOR | basic_project_skills       |
| SUCCESS | generate-data-catalog-spreadsheet              | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-staging-table                         | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-refine-table                          | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-confirmed-table                       | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-refined-procedure                     | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-confirmed-procedure                   | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-confirmed-view                        | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-consumption-view                      | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-dag-fileset                           | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-deployment-artifacts                  | GENERATOR | azure_sql_ingestion_skills |
| SUCCESS | generate-test-script                           | GENERATOR | azure_sql_ingestion_skills |
