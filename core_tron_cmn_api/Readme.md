## Configuracion inicial

## Guia 

- 1º- Descargar el proyecto en local.
- 2º- Abrir el proyecto con Eclipse, habiendo configurado el settings de maven en preferences.
- 3º- Abrir consola de comandos, ir a la raiz del proyecto, construir con el comando:
     mvn clean install -PENV_INT,INCLUDE_DEPENDENCIES -DskipTests -Dcobertura.skip.
- 4º- En el Eclipse, click derecho en zeroconfig de la API / Maven / select Maven profiles / ENV_LOCAL.
- 5º- En el Eclipse, click derecho en WEB de la API / Maven / select Maven profiles / INCLUDE_DEPENDENCIES.
- 6º- Si no está creado, crear un servidor con Tomcat 8.5, importar la carpeta de Tomcat que esta en la carpeta de MAPFRE-toolsuite.
- 7º- Seleccionar todas los proyectos de la API, Click derecho, Maven, update dependecies.
- 8º- Por ultimo, click derecho en el servidor tomcat, y Run.

## Zeroconfig

## Properties   
      
- <app.ins.val>TRN</app.ins.val>
- <gaia.artifact.folder>zeroConfig</gaia.artifact.folder>
- <app.mail.host>smtp01.mapfre.net</app.mail.host>
- <app.mail.port>25</app.mail.port>
- <app.mail.username>app-mapfremail</app.mail.username>
- <app.mail.password>app-mapfremail</app.mail.password>
- <app.sms.username>SMSWS</app.sms.username>
- <app.sms.password>SMSWS1</app.sms.password>
- <app.env.psypd.timeout>3000</app.env.psypd.timeout>
- <app.env.psypd.basePath></app.env.psypd.basePath>
- <app.env.psypd.clientId></app.env.psypd.clientId>
- <app.env.psypd.clientSecret></app.env.psypd.clientSecret>
- <app.env.psypd.scope></app.env.psypd.scope>
- <app.env.infovl.url>https://s44cb779.mapfre.net/wServiciosMisvGX/rest/RSMII_InfoVL</app.env.infovl.url>
- <app.env.infovl.username>APP-TRONFNX</app.env.infovl.username>
- <app.env.infovl.password>g4D9ZGPyvX</app.env.infovl.password>
- <app.env.infocestas.url>https://s44cb779.mapfre.net/wServiciosMisvGX/rest/RSMII_InfoCestas</app.env.infocestas.url>
- <app.env.infocestas.username>APP-TRONFNX</app.env.infocestas.username>
- <app.env.infocestas.password>g4D9ZGPyvX</app.env.infocestas.password>
- <default.app.language>ES</default.app.language>
- <default.app.cmpVal>1</default.app.cmpVal>
- <app.env.urlSFMCtoken>https://mcq6zgrtgr273f2ky047vrt3hz1y.auth.marketingcloudapis.com/v2/token</app.env.urlSFMCtoken>
- <app.env.clientIdSFMCtoken>p2qugbpes4dmx4nc6ia0d4r2</app.env.clientIdSFMCtoken>
- <app.env.clientSecretSFMCtoken>VENqRWh5aRgxTD3V10hYJK3O</app.env.clientSecretSFMCtoken>
- <app.env.urlSFMCevent>https://mcq6zgrtgr273f2ky047vrt3hz1y.rest.marketingcloudapis.com/interaction/v1/events</app.env.urlSFMCevent>
- <app.env.usernameSFTPSFM>518002943</app.env.usernameSFTPSFM>
- <app.env.passwordSFTPSFM>SFMCParaguay2021@</app.env.passwordSFTPSFM>
- <app.env.remoteHostSFTPSCM>mcq6zgrtgr273f2ky047vrt3hz1y.ftp.marketingcloudops.com</app.env.remoteHostSFTPSCM>
- <app.env.mvmIdnCommunication>3,4</app.env.mvmIdnCommunication>

## Links entorno

- <app.env.report.endpoint>http://localhost:8080/report_be-web/api/reports/</app.env.report.endpoint>
- <app.env.infovl.url>https://s44cb779.mapfre.net/wServiciosMisvGX/rest/RSMII_InfoVL</app.env.infovl.url>
- <app.env.infocestas.url>https://s44cb779.mapfre.net/wServiciosMisvGX/rest/RSMII_InfoCestas</app.env.infocestas.url>
- <app.env.urlSFMCtoken>https://mcq6zgrtgr273f2ky047vrt3hz1y.auth.marketingcloudapis.com/v2/token</app.env.urlSFMCtoken>
- <app.env.urlSFMCevent>https://mcq6zgrtgr273f2ky047vrt3hz1y.rest.marketingcloudapis.com/interaction/v1/events</app.env.urlSFMCevent>

