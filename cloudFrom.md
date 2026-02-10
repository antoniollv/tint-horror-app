# Checklist para Desplegar en Amazon CloudFront

Este documento detalla los pasos necesarios para desplegar la aplicación en Amazon CloudFront y realizar una evaluación de costos en comparación con el alojamiento directo en un bucket de S3.

## Pasos para el Despliegue en CloudFront

1. **Preparar los archivos de la aplicación:**
   - Compilar la aplicación para producción.
     - Si se utiliza un framework moderno como React, Angular o Vue.js, ejecutar el comando de compilación correspondiente.
       - Ejemplo: `npm run build` (esto generará una carpeta de producción, como `build/` o `dist/`).

2. **Crear un bucket en S3:**
   - Crear un bucket en Amazon S3 para almacenar los archivos estáticos de la aplicación.
   - Configurar el bucket de S3 como “Hosting para sitio web estático” (Static Website Hosting) si fuese necesario para pruebas previas.
   - Subir los archivos generados de la carpeta de compilación al bucket de S3.

3. **Configurar permisos del bucket:**
   - Configurar una política de bucket que permita el acceso de solo lectura a los archivos para CloudFront (puede usarse la opción **Origin Access Identity - OAI** de CloudFront).

4. **Crear una distribución de CloudFront:**
   - Acceder a la consola de Amazon CloudFront en AWS.
   - Crear una nueva distribución y seleccionar el bucket de S3 configurado en el paso anterior como origen.
   - Configurar políticas de almacenamiento en caché:
     - Tiempo de vida (TTL): especificar el tiempo que los archivos se almacenarán en la caché.
   - Personalizar si se requiere:
     - Si tienes un dominio personalizado, habilitar el soporte para dominios personalizados y agregar un certificado SSL usando AWS Certificate Manager (ACM).

5. **Configurar el dominio personalizado (opcional):**
   - Usar Amazon Route 53 o la herramienta de gestión DNS de tu proveedor para asociar un registro CNAME o Alias al hostname generado por CloudFront (ej.: `xxxxxx.cloudfront.net`).

6. **Probar el despliegue:**
   - Accede a la URL generada por la distribución de CloudFront y prueba que el contenido se carga correctamente. Asegúrate de que todos los recursos se carguen sin errores (HTML, imágenes, CSS, JavaScript, etc.).

---

## Comparativa de Costos: CloudFront vs S3

1. **Alojamiento directo en S3:**
   - **Ventajas:**
     - Estructura simple y rápida de configurar.
     - Ideal para prototipos y aplicaciones con bajo tráfico inicial.
     - Costos bajos (se cobra por almacenamiento en GB y solicitudes, pero no por ancho de banda internacional).
   - **Desventajas:**
     - Sin una red de distribución de contenidos (CDN) dedicada, por lo que la latencia puede ser mayor para usuarios distantes de la región del bucket.

2. **Alojamiento con CloudFront:**
   - **Ventajas:**
     - Uso de una CDN global para garantizar una baja latencia en la entrega de contenido.
     - Mayor disponibilidad y redundancia a nivel global.
     - Mejora la seguridad con HTTPS y Web Application Firewall (WAF).
   - **Desventajas:**
     - Costo más elevado debido al uso de más recursos (tarifas por desempeño, ancho de banda y solicitudes).

### Factores a Considerar en los Costos

- El precio de **S3** varía según la cantidad de almacenamiento y el número de solicitudes.
- El costo de **CloudFront** incluye tarifas por TB transferido, solicitudes realizadas desde los puntos de distribución, y posibles funciones adicionales como el WAF o invalidaciones de caché.

**Conclusión:** Si la aplicación tiene un tráfico alto o usuarios distribuidos globalmente, el uso de CloudFront es ideal para mejorar la experiencia del usuario. Para aplicaciones pequeñas o utilizadas regionalmente con menos visitas, S3 puede ser más rentable. Es posible combinar ambos, utilizando S3 como almacenamiento base y CloudFront como capa CDN para optimizar el tráfico.
