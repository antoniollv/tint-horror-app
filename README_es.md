# tint-horror-app

[EN](README.md) | [ES](README_es.md)

Aplicación web React para visualizar tiras de cómics desde el navegador. La app carga un fichero de configuración YAML y dibuja las viñetas con sus bocadillos de texto. Este README está pensado para que puedas arrancar, desplegar y versionar sin problemas.

## Estructura

- App React: [tint-strips/](tint-strips)
- Configuración de releases: [.releaserc.json](.releaserc.json)
- Workflow de publicación: [.github/workflows/publish.yml](.github/workflows/publish.yml)
- Changelog: [CHANGELOG.md](CHANGELOG.md)

## Requisitos

- Node.js 22+
- npm 9+

## Desarrollo local

Desde la carpeta de la app, los pasos habituales son:

| Paso | Descripción | Comando |
| --- | --- | --- |
| 1 | Instalar dependencias. | `npm install` |
| 2 | Arrancar en desarrollo. | `npm run dev` |
| 3 | Compilar para producción. | `npm run build` |

### Scripts disponibles (Vite)

En [tint-strips/package.json](tint-strips/package.json) están definidos estos scripts:

| Script | Descripción | Comando |
| --- | --- | --- |
| `dev` | Servidor de desarrollo. | `npm run dev` |
| `start` | Alias de `dev`. | `npm start` |
| `build` | Build de producción. | `npm run build` |
| `preview` | Preview del build local. | `npm run preview` |

## Configuración de datos

La app carga un YAML con las tiras desde la ruta:

- `VITE_YAML_CONFIG_PATH` si está definida
- `/comics.yml` si no hay variable

El fichero debe estar en `public/` y se copiará al build.

Las imágenes deben estar en `tint-strips/public/imgs/` para que se resuelvan con la ruta `/imgs/`.

Variables alternativas compatibles:

- `REACT_APP_YAML_CONFIG_PATH`
- `REACT_APP_IMAGE_PATH`
- `REACT_APP_TRANSLATION_API_URL`
- `REACT_APP_TRANSLATION_API_KEY`

## Despliegue en S3 (SPA)

La app es una SPA sin rutas del lado cliente, por lo que S3 es suficiente.

Checklist rápido:

- Compilar: `npm run build`
- Subir **el contenido** de `tint-strips/build/` a S3
- Static website hosting:
  - Index document: `index.html`
  - Error document: `index.html` (por compatibilidad futura con rutas)

En [tint-strips/package.json](tint-strips/package.json) se usa `"homepage": "."` para rutas relativas.

### Política pública del bucket (si no usas CloudFront)

Recuerda habilitar acceso público (solo lectura) al bucket. Ejemplo de policy:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "PublicReadGetObject",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::TU_BUCKET/*"
    }
  ]
}
```

### Opcional: CloudFront

Si quieres HTTPS, mejor caché y dominio propio:

- Crea una distribución con origen en el bucket S3
- Configura `index.html` como Default Root Object
- (Si hay rutas en el futuro) usa un Custom Error Response 404 → `/index.html` (200)
- Activa invalidaciones al publicar nuevas versiones

## Versionado y releases (semantic-release)

El flujo automático está preparado para:

- Generar tag en `main` con formato `vX.Y.Z`
- Actualizar `CHANGELOG.md`
- Crear GitHub Release
- Hacer *bump* de versión en `develop` (package.json + package-lock.json)

### Configuración

- Configuración principal: [.releaserc.json](.releaserc.json)
- Workflow en `main`: [.github/workflows/publish.yml](.github/workflows/publish.yml)

### Convenciones de commit (Conventional Commits)

El tipo de commit determina la versión que se publica:

- `feat:` → **minor**
- `fix:` → **patch**
- `perf:` → **patch**
- `refactor:` → **patch** (si cambia comportamiento)
- `docs:`, `chore:`, `test:`, `build:`, `ci:` → **sin release**

Para **major**, añade `BREAKING CHANGE:` en el body del commit.

Ejemplos válidos:

- `feat: añadir selector de capítulos`
- `fix: corregir carga de comics.yml`
- `refactor: simplificar carga de tiras`

Nota: para un commit con cambio incompatible, escribe `BREAKING CHANGE:` en el cuerpo.

---

## Estado actual del proyecto

Resumen breve del estado del código y los puntos a revisar.

### Lo que está funcionando

- App React funcional con navegación de viñetas.
- Bocadillos dinámicos y traducción automática.
- Carga de configuración desde YAML.
- Docker de desarrollo y producción.

### Pendientes o a revisar

- Assets (comics.yml e imágenes SVG) deben existir en public/.
- Servicio de traducción debe estar disponible externamente.
- Tests aún no definidos.

### Archivos clave

- App principal: [tint-strips/src/App.jsx](tint-strips/src/App.jsx)
- Bocadillos: [tint-strips/src/components/ComicPanel.jsx](tint-strips/src/components/ComicPanel.jsx)
- Config YAML: [tint-strips/src/components/loadConfigStrips.js](tint-strips/src/components/loadConfigStrips.js)
- Traducción: [tint-strips/src/utils/translate.js](tint-strips/src/utils/translate.js)
- Docker: [tint-strips/Dockerfile.dev](tint-strips/Dockerfile.dev) y [tint-strips/Dockerfile.pro](tint-strips/Dockerfile.pro)

---

## Licencia

Este proyecto se publica como open source bajo:

- Código: MIT. Ver [LICENSE](LICENSE).
- Imágenes: Creative Commons Attribution 4.0 (CC BY 4.0). Ver [LICENSE-IMAGES](LICENSE-IMAGES).
