# CI/CD Guía DevOps e (ES)

Proceso CI/CD de despliegue de la aplicación `tint-horror-app`

## Por donde empezar

1) Rellena la configuracion por entorno en [infra/prerequsites.json](infra/prerequsites.json).
2) Ejecuta el workflow de prerequisitos para crear el backend de Terraform y el rol OIDC.
3) Ejecuta el workflow de infraestructura para crear el bucket de hosting.
4) Ejecuta el workflow de publish para generar el tag de release.
5) Ejecuta el workflow de deploy para construir y subir la app al bucket.

## Que hay que rellenar

En [infra/prerequsites.json](infra/prerequsites.json) define, por entorno:

- `app_bucket_name`: nombre del bucket de hosting.
   Determinado en el momento de la creación. El nombre del bucket debe ser único en todo AWS; se recomienda usar el nombre de la aplicación mas el entorno.
- `aws_account_id`: tu cuenta AWS.
   Necesario para construir el `role_arn` en los workflows que usan OIDC. Debe ser el ID numérico de 12 dígitos de la cuenta.
- `aws_region`: region de AWS.
   Region donde se crean los recursos. Debe coincidir con la region del bucket de state y del bucket de hosting.
- `github_org`: organización/owner del repo.
   Se usa en la policy de confianza OIDC para limitar quien puede asumir el rol.
- `github_repo`: nombre del repo.
   Se usa en la policy de confianza OIDC y como prefijo de rutas en SSM.
- `iam_role_name` y `iam_policy_name`.
   Nombre del rol y de la policy que usaran los workflows con OIDC. Conviene incluir el entorno para distinguir `dev` y `prod`.
- `infra_tf_state_key`: key del state del bucket de hosting.
   Ruta dentro del bucket de state para este modulo de infraestructura. Debe incluir el entorno.
- `tf_state_bucket`: bucket del state de Terraform.
   Bucket de state compartido. Debe existir y tener versioning, encryption y public access block.
- `tf_state_key`: key del state principal.
   Ruta principal del state. Debe incluir el entorno para evitar colisiones.

También debes crear el GitHub Environment (dev o prod) y añadir los secretos de bootstrap:

- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`

Estos secretos solo se usan en el workflow de pre-requisitos.

### Creación de usuario IAM bootstrap en consola AWS

La policy de bootstrap debe permitir recursos generales. Se recomienda eliminar o desactivar la clave cuando termine el workflow.

Pasos:

1) Inicia sesión con la cuenta root y entra en IAM.
2) Crea un usuario IAM (por ejemplo `bootstrap-devops`).
3) En **Create access key**, elige **Interfaz de linea de comandos (CLI)**.
4) Asigna permisos, a continuación.
5) Crea el access key y copia `AWS_ACCESS_KEY_ID` y `AWS_SECRET_ACCESS_KEY` para el GitHub Environment.

Permisos requeridos por el workflow de pre-requisitos:

- S3: crear bucket, habilitar versioning, encryption y public access block.
- IAM: crear/actualizar el proveedor OIDC, rol y policy.
- SSM: crear/actualizar parámetros bajo `/repo/env/prerequisites`.

Opciones de permisos:

- Opción simple: adjuntar `AdministratorAccess` al usuario bootstrap y retirarla al terminar.
- Opcion custom (recursos generales): policy propia con acciones especificas. Evita `iam:*` porque la consola bloquea `iam:PassRole` y `iam:CreateServiceLinkedRole` con `Resource: "*"`.

Ejemplo de policy custom (alcance general, sin nombres de buckets):

```json
{
   "Version": "2012-10-17",
   "Statement": [
      {
         "Sid": "S3StateBucketBootstrap",
         "Effect": "Allow",
         "Action": [
            "s3:CreateBucket",
            "s3:ListBucket",
            "s3:GetBucketLocation",
            "s3:PutBucketVersioning",
            "s3:PutEncryptionConfiguration",
            "s3:PutBucketPublicAccessBlock"
         ],
         "Resource": "*"
      },
      {
         "Sid": "IAMBootstrap",
         "Effect": "Allow",
         "Action": [
            "iam:CreateOpenIDConnectProvider",
            "iam:ListOpenIDConnectProviders",
            "iam:GetOpenIDConnectProvider",
            "iam:UpdateOpenIDConnectProviderThumbprint",
            "iam:CreateRole",
            "iam:UpdateAssumeRolePolicy",
            "iam:GetRole",
            "iam:CreatePolicy",
            "iam:ListPolicies",
            "iam:GetPolicy",
            "iam:CreatePolicyVersion",
            "iam:ListPolicyVersions",
            "iam:DeletePolicyVersion",
            "iam:AttachRolePolicy"
         ],
         "Resource": "*"
      },
      {
         "Sid": "SSMPrerequisitesWrite",
         "Effect": "Allow",
         "Action": [
            "ssm:PutParameter",
            "ssm:GetParameter",
            "ssm:GetParameters"
         ],
         "Resource": "*"
      },
      {
         "Sid": "STSIdentity",
         "Effect": "Allow",
         "Action": "sts:GetCallerIdentity",
         "Resource": "*"
      }
   ]
}
```

## Workflows y responsabilidades

### 1. Pre-requisitos

Workflow: [tint-horror-app/.github/workflows/prerequisites.yml](tint-horror-app/.github/workflows/prerequisites.yml)

Que hace:

- Lee [infra/prerequsites.json](infra/prerequsites.json) según el entorno.
- Crea el bucket S3 del state (versioning, encryption, public access block).
- Crea proveedor OIDC si no existe.
- Crea o actualiza el rol IAM y su policy.
- Guarda datos en SSM bajo `/repo/env/prerequisites`.

SSM que se guarda:

- `aws_region`
- `aws_account_id`
- `tf_state_bucket`
- `tf_state_key`
- `aws_role_arn`

Si faltan secretos de bootstrap, el workflow falla con un mensaje claro.

### 2. Infraestructura (bucket de hosting)

Workflow: [tint-horror-app/.github/workflows/infra-deploy.yml](tint-horror-app/.github/workflows/infra-deploy.yml)

Que hace:

- Usa OIDC (sin claves AWS).
- Lee el backend desde SSM y la key desde JSON.
- Aplica Terraform en [infra/terraform/app-bucket](infra/terraform/app-bucket).
- Guarda en SSM los datos del bucket bajo `/repo/env/app`.

SSM que se guarda:

- `bucket_name`
- `website_endpoint`
- `website_domain`

### 3. Publish (tags)

Workflow: [tint-horror-app/.github/workflows/publish.yml](tint-horror-app/.github/workflows/publish.yml)

Que hace:

- Crea tags de release con semantic-release.
- No genera artefactos ni despliega.

### 4. Deploy de la app

Workflow: [tint-horror-app/.github/workflows/app-deploy.yml](tint-horror-app/.github/workflows/app-deploy.yml)

Que hace:

- Usa OIDC (sin claves AWS).
- Construye desde el ultimo tag `v*`, o uno especifico si se indica.
- Build en [tint-strips](tint-strips) y salida en [tint-strips/build](tint-strips/build).
- Despliega a S3 con `aws s3 sync`.
- Si no encuentra el bucket en SSM, usa `app_bucket_name` del JSON.

## Rutas y assets

Las imágenes y assets salen de [tint-strips/public](tint-strips/public) y se sirven desde el bucket de hosting.
El build se genera en [tint-strips/build](tint-strips/build). La base de Vite es `./`, por lo que las rutas son relativas.

## Orden recomendado de ejecución

1) Ejecutar `prerequisites` para el entorno.
2) Ejecutar `infra-deploy` con `action=apply`.
3) Ejecutar `publish` para generar el tag.
4) Ejecutar `app-deploy` para subir la app a S3.

## Rollback

Para volver a una version anterior:

- Ejecuta el workflow de `app-deploy` indicando un tag anterior.

## Seguridad y notas

- OIDC limita el acceso al repo configurado.
- No se publican secretos en summaries ni artifacts.
- SSM centraliza los parámetros de despliegue.
