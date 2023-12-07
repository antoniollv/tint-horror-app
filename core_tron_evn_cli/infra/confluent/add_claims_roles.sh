#!/bin/zsh
CLOUD_ENV=env-pg6o95
CLOUD_CLUSTER=lkc-zm9yo0
KAFKA_CLUSTER=lkc-zm9yo0
SCHEMA_CLUSTER=lsrc-12mdzj
USER_POOL=pool-WkB7

confluent login

# Si no funciona quitar el "-id" del final de "--kafka-cluster-id"
confluent iam rbac role-binding create --environment $CLOUD_ENV --cloud-cluster $CLOUD_CLUSTER --kafka-cluster-id $KAFKA_CLUSTER --principal User:$USER_POOL --role DeveloperRead --resource Group:consumer-sync-insured-subscriber
confluent iam rbac role-binding create --environment $CLOUD_ENV --cloud-cluster $CLOUD_CLUSTER --kafka-cluster-id $KAFKA_CLUSTER --principal User:$USER_POOL --role DeveloperRead --resource Group:consumer-enriching-insured-subscriber

confluent iam rbac role-binding create --environment $CLOUD_ENV --cloud-cluster $CLOUD_CLUSTER --kafka-cluster-id $KAFKA_CLUSTER --principal User:$USER_POOL --role DeveloperRead --resource Topic:map.tron.trn.esp.cdc.thp.activity
confluent iam rbac role-binding create --environment $CLOUD_ENV --cloud-cluster $CLOUD_CLUSTER --kafka-cluster-id $KAFKA_CLUSTER --principal User:$USER_POOL --role DeveloperRead --resource Topic:map.tron.trn.esp.cdc.thp.insured

# Para el schema-registry hace falta establecer el proxy
export proxy=http://proxy.glb.mapfre.net:80
export http_proxy=http://proxy.glb.mapfre.net:80
export https_proxy=http://proxy.glb.mapfre.net:80

# Si no funciona quitar el "-id" del final de "--schema-registry-cluster-id"
confluent iam rbac role-binding create --environment $CLOUD_ENV --schema-registry-cluster-id $SCHEMA_CLUSTER                     --principal User:$USER_POOL --role DeveloperRead --resource Subject:map.tron.trn.esp.cdc.thp.activity-value
confluent iam rbac role-binding create --environment $CLOUD_ENV --schema-registry-cluster-id $SCHEMA_CLUSTER                     --principal User:$USER_POOL --role DeveloperRead --resource Subject:map.tron.trn.esp.cdc.thp.insured-value
confluent iam rbac role-binding create --environment $CLOUD_ENV --schema-registry-cluster-id $SCHEMA_CLUSTER                     --principal User:$USER_POOL --role DeveloperWrite --resource Subject:map.tron.trn.esp.cdc.thp.insured-value
