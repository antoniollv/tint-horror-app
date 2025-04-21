# Usar una imagen oficial de Node.js como base
FROM node:16 AS build

# Crear y establecer el directorio de trabajo
WORKDIR /usr/src/app

# Copiar los archivos necesarios para la compilación
COPY package*.json ./
RUN npm install
COPY . .

# Crear la build de producción
RUN npm run build

# Usar una imagen más ligera para servir el contenido (Nginx)
FROM nginx:alpine
COPY --from=build /usr/src/app/build /usr/share/nginx/html

# Exponer el puerto en producción
EXPOSE 80

# Comando para iniciar el servidor Nginx
CMD ["nginx", "-g", "daemon off;"]
