@echo off
set PROXY=http://proxy.glb.mapfre.net:80
set HTTP_PROXY=http://proxy.glb.mapfre.net:80
set HTTPS_PROXY=http://proxy.glb.mapfre.net:80

set CLOUD_ENV=env-pg6o95
set CLOUD_CLUSTER=lkc-zm9yo0
set KAFKA_CLUSTER=lkc-zm9yo0
set SCHEMA_CLUSTER=lsrc-12mdzj
set USER_POOL=pool-WkB7
@echo on

cmd /c "confluent login"

cmd /c "confluent iam rbac role-binding create --environment %CLOUD_ENV% --cloud-cluster %CLOUD_CLUSTER% --kafka-cluster %KAFKA_CLUSTER% --principal User:%USER_POOL% --role DeveloperRead --resource Group:consumer-sync-insured-subscriber"
cmd /c "confluent iam rbac role-binding create --environment %CLOUD_ENV% --cloud-cluster %CLOUD_CLUSTER% --kafka-cluster %KAFKA_CLUSTER% --principal User:%USER_POOL% --role DeveloperRead --resource Group:consumer-enriching-insured-subscriber"

cmd /c "confluent iam rbac role-binding create --environment %CLOUD_ENV% --cloud-cluster %CLOUD_CLUSTER% --kafka-cluster %KAFKA_CLUSTER% --principal User:%USER_POOL% --role DeveloperRead --resource Topic:map.tron.trn.esp.cdc.thp.activity"
cmd /c "confluent iam rbac role-binding create --environment %CLOUD_ENV% --cloud-cluster %CLOUD_CLUSTER% --kafka-cluster %KAFKA_CLUSTER% --principal User:%USER_POOL% --role DeveloperRead --resource Topic:map.tron.trn.esp.cdc.thp.insured"
cmd /c "confluent iam rbac role-binding create --environment %CLOUD_ENV% --cloud-cluster %CLOUD_CLUSTER% --kafka-cluster %KAFKA_CLUSTER% --principal User:%USER_POOL% --role DeveloperWrite --resource Topic:map.tron.trn.esp.cdc.thp.insured"

cmd /c "confluent iam rbac role-binding create --environment %CLOUD_ENV% --schema-registry-cluster %SCHEMA_CLUSTER%                      --principal User:%USER_POOL% --role DeveloperRead --resource Subject:map.tron.trn.esp.cdc.thp.activity-value"
cmd /c "confluent iam rbac role-binding create --environment %CLOUD_ENV% --schema-registry-cluster %SCHEMA_CLUSTER%                      --principal User:%USER_POOL% --role DeveloperRead --resource Subject:map.tron.trn.esp.cdc.thp.insured-value"
cmd /c "confluent iam rbac role-binding create --environment %CLOUD_ENV% --schema-registry-cluster %SCHEMA_CLUSTER%                      --principal User:%USER_POOL% --role DeveloperWrite --resource Subject:map.tron.trn.esp.cdc.thp.insured-value"
