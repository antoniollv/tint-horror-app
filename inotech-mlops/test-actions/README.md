# Test GitHub Acctions

Hola, pues quiero iniciar un nuevo proyecto y espero que puedas ayudarme.

Consiste en un repositorio en GitHub que contiene un fichero YAML llamado `coordenadas.yaml` en el encontraremos estos valores `x: 45`, `y: 50` `z: 15`. La labor a realizar es mediante GitHub Acctions, en una ejecución manual pedir valores para `x`,`y` y `z` y si estos valores, en general, son mejores que los valores en el fichero `coordenadas.yaml` actualizaremos el fichero YAML con los nuevos valores en el repositorio en la misma rama donde nos encontremos. El commit de actualización llevara la hora y los nuevos valores. Debemos mostrar un mensaje indicando si se han actualizado o no los valores.

Como puedes ver es un proyecto para una prueba de concepto didáctica de modo que en la medida de lo posible detalla al máximo las acciones a realizar para logra este objetivo. ¡Muchas gracias!

Hola, Quiero un dibujo que conste de 6 viñetas, en ellas se verá la evolución de un diablo, de diablillo a señor del averno, a tener en cuenta

- estilo cartoon
- plano sin volumen ni dimensiones
- Fondo blanco
- trazos negros como de tinta a pluma
- uso de poco color y que no lo cubra, tonos pasteles, el color que simule acuarela
- El demonio estará en posición distinta en cada progreso, pulgar hacia arriba, mostrador OK, brazos sacando musculo...
- Solo aparecerá el diablo en cada viñeta, nada más , pero este podrá llevar complemento, armadura hacha  espada...
- Recuerda se ha de apreciar el progreso de la evolución

¡Encantado de ayudarte! Aquí tienes un resumen de todos los archivos que debes crear en tu proyecto para implementar este flujo de trabajo con GitHub Actions. 

### 1. `coordenadas.yaml`

Este archivo contiene las coordenadas iniciales y se actualizará automáticamente en función de las nuevas coordenadas introducidas en la ejecución manual.

```yaml
x: 45
y: 50
z: 15
```

### 2. `.github/workflows/actualizar-coordenadas.yml`

Este primer workflow permite actualizar el archivo `coordenadas.yaml` según los nuevos valores proporcionados en la ejecución manual. Este archivo también decidirá si los nuevos valores deben actualizarse según los criterios de "Mejora Directa" o "Aumento Neto".

```yaml
name: Actualizar Coordenadas

on:
  workflow_dispatch:
    inputs:
      x:
        description: 'Nuevo valor para x'
        required: true
        default: '0'
      y:
        description: 'Nuevo valor para y'
        required: true
        default: '0'
      z:
        description: 'Nuevo valor para z'
        required: true
        default: '0'
      decision:
        description: '¿Deseas actualizar si el valor neto es mayor?'
        required: false
        type: choice
        options: 
          - "No"
          - "Sí"

jobs:
  actualizar-coordenadas:
    runs-on: ubuntu-latest

    steps:
    - name: Clonar el repositorio
      uses: actions/checkout@v2

    - name: Leer coordenadas actuales
      id: read-coordinates
      run: |
        COORDS=$(cat coordenadas.yaml)
        echo "current_coords=$COORDS" >> $GITHUB_ENV

    - name: Comparar valores
      id: compare-values
      run: |
        # Leer valores actuales
        CURRENT_X=$(echo "$current_coords" | grep 'x:' | awk '{print $2}')
        CURRENT_Y=$(echo "$current_coords" | grep 'y:' | awk '{print $2}')
        CURRENT_Z=$(echo "$current_coords" | grep 'z:' | awk '{print $2}')

        # Nuevos valores de entrada
        NEW_X=${{ github.event.inputs.x }}
        NEW_Y=${{ github.event.inputs.y }}
        NEW_Z=${{ github.event.inputs.z }}

        # Comparar cada valor
        if [ "$NEW_X" -gt "$CURRENT_X" ] && [ "$NEW_Y" -gt "$CURRENT_Y" ] && [ "$NEW_Z" -gt "$CURRENT_Z" ]; then
          echo "Los nuevos valores son mejores. Actualizando coordenadas."
          echo "update_direct=true" >> $GITHUB_ENV
        else
          # Cálculo de la suma total
          SUM_CURRENT=$((CURRENT_X + CURRENT_Y + CURRENT_Z))
          SUM_NEW=$((NEW_X + NEW_Y + NEW_Z))

          echo "sum_current=$SUM_CURRENT" >> $GITHUB_ENV
          echo "sum_new=$SUM_NEW" >> $GITHUB_ENV

          if [ "$SUM_NEW" -gt "$SUM_CURRENT" ]; then
            echo "Valor Neto mayor: el usuario debe decidir si quiere actualizar."
            echo "update_total=true" >> $GITHUB_ENV
          else
            echo "Los nuevos valores no son mejores. No se realizarán cambios."
            echo "update_total=false" >> $GITHUB_ENV
          fi
        fi

    - name: Condición de actualización
      if: env.update_direct == 'true' || (env.update_total == 'true' && github.event.inputs.decision == 'Sí')
      run: |
        echo "Actualizando archivo coordenadas.yaml con los nuevos valores."
        echo "x: ${{ github.event.inputs.x }}" > coordenadas.yaml
        echo "y: ${{ github.event.inputs.y }}" >> coordenadas.yaml
        echo "z: ${{ github.event.inputs.z }}" >> coordenadas.yaml
        git config --local user.name "github-actions"
        git config --local user.email "action@github.com"
        git add coordenadas.yaml
        git commit -m "Actualización de coordenadas: x=${{ github.event.inputs.x }}, y=${{ github.event.inputs.y }}, z=${{ github.event.inputs.z }} - $(date +"%Y-%m-%d %H:%M:%S")"
        git push origin HEAD
        echo "Las coordenadas fueron actualizadas."

    - name: Mensaje de resultado
      if: env.update_direct != 'true' && (env.update_total != 'true' || github.event.inputs.decision != 'Sí')
      run: |
        echo "No se realizaron cambios en las coordenadas."
```

### 3. `.github/workflows/construir-imagen.yml`

Este segundo workflow se ejecuta cuando `coordenadas.yaml` se actualiza con un commit que incluye "Actualización de coordenadas" en el mensaje. Este workflow construye una imagen de contenedor y la sube a Docker Hub o al registro que configures.

```yaml
name: Construir Imagen de Contenedor

on:
  push:
    paths:
      - 'coordenadas.yaml'  # Se activa cuando este archivo cambia

jobs:
  build-and-push:
    runs-on: ubuntu-latest

    steps:
    - name: Clonar el repositorio
      uses: actions/checkout@v2

    - name: Verificar mensaje del commit
      id: check-commit-message
      run: |
        LAST_COMMIT_MSG=$(git log -1 --pretty=%B)
        echo "Último mensaje de commit: $LAST_COMMIT_MSG"
        if [[ "$LAST_COMMIT_MSG" != *"Actualización de coordenadas"* ]]; then
          echo "El mensaje de commit no contiene 'Actualización de coordenadas'. Saliendo."
          exit 1
        fi

    - name: Autenticar en Docker
      if: steps.check-commit-message.outcome == 'success'
      run: echo "${{ secrets.DOCKER_PASSWORD }}" | docker login -u ${{ secrets.DOCKER_USERNAME }} --password-stdin

    - name: Construir y subir imagen de contenedor
      if: steps.check-commit-message.outcome == 'success'
      run: |
        # Crear el nombre de la imagen y etiqueta con los valores actuales
        IMAGE_NAME="usuario/coordenadas"
        IMAGE_TAG="$(date +'%Y%m%d%H%M%S')"

        # Construir la imagen
        docker build -t $IMAGE_NAME:$IMAGE_TAG .

        # Subir la imagen al registro
        docker push $IMAGE_NAME:$IMAGE_TAG
      env:
        DOCKER_USERNAME: ${{ secrets.DOCKER_USERNAME }}
        DOCKER_PASSWORD: ${{ secrets.DOCKER_PASSWORD }}
```
En cuanto a tu primera pregunta: sí, el runner de GitHub Actions que utiliza la imagen `ubuntu-latest` ya tiene Docker preinstalado. Esto permite ejecutar comandos de Docker sin necesidad de instalar Docker en el workflow.

Para que el segundo workflow solo se active cuando el commit contiene "Actualización de coordenadas" en el mensaje, podemos agregar una verificación en el workflow. Esto se hace usando un paso que comprueba el mensaje del último commit y condiciona la ejecución del job. 

Aquí tienes el archivo `.github/workflows/construir-imagen.yml` actualizado:

```yaml
name: Construir Imagen de Contenedor

on:
  push:
    paths:
      - 'coordenadas.yaml'  # Se activa cuando este archivo cambia

jobs:
  build-and-push:
    runs-on: ubuntu-latest

    steps:
    - name: Clonar el repositorio
      uses: actions/checkout@v2

    - name: Verificar mensaje del commit
      id: check-commit-message
      run: |
        LAST_COMMIT_MSG=$(git log -1 --pretty=%B)
        echo "Último mensaje de commit: $LAST_COMMIT_MSG"
        if [[ "$LAST_COMMIT_MSG" != *"Actualización de coordenadas"* ]]; then
          echo "El mensaje de commit no contiene 'Actualización de coordenadas'. Saliendo."
          exit 1
        fi

    - name: Autenticar en Docker
      if: steps.check-commit-message.outcome == 'success'
      run: echo "${{ secrets.DOCKER_PASSWORD }}" | docker login -u ${{ secrets.DOCKER_USERNAME }} --password-stdin

    - name: Construir y subir imagen de contenedor
      if: steps.check-commit-message.outcome == 'success'
      run: |
        # Crear el nombre de la imagen y etiqueta con los valores actuales
        IMAGE_NAME="usuario/coordenadas"
        IMAGE_TAG="$(date +'%Y%m%d%H%M%S')"

        # Construir la imagen
        docker build -t $IMAGE_NAME:$IMAGE_TAG .

        # Subir la imagen al registro
        docker push $IMAGE_NAME:$IMAGE_TAG
      env:
        DOCKER_USERNAME: ${{ secrets.DOCKER_USERNAME }}
        DOCKER_PASSWORD: ${{ secrets.DOCKER_PASSWORD }}
```

### Explicación de los Cambios

- **Verificación del mensaje de commit**: Este paso usa el comando `git log -1 --pretty=%B` para obtener el mensaje del último commit. Si el mensaje no contiene "Actualización de coordenadas", el paso termina con `exit 1`, lo que detiene el workflow.
- **Condicional en los pasos posteriores**: Los pasos de autenticación y construcción de la imagen solo se ejecutan si el paso `check-commit-message` fue exitoso, lo cual garantiza que el workflow solo continúa si se cumplen las condiciones.

Así, este segundo workflow solo se ejecutará completamente cuando se detecte un cambio en `coordenadas.yaml` y el mensaje de commit incluya "Actualización de coordenadas".
### Resumen de Archivos

1. **`coordenadas.yaml`**: Archivo de coordenadas que almacena los valores `x`, `y`, y `z`.
2. **`.github/workflows/actualizar-coordenadas.yml`**: Workflow que actualiza `coordenadas.yaml` si los nuevos valores cumplen con los criterios de mejora.
3. **`.github/workflows/construir-imagen.yml`**: Workflow que construye y sube la imagen de contenedor cuando `coordenadas.yaml` se actualiza con un commit que contiene "Actualización de coordenadas".

Con esta configuración, tu proyecto está listo para que puedas probar los workflows. ¡Buena suerte!




