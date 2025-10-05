# ecosistema-devops
## Construcción de la imagen Docker para Jenkins



Asegúrate de estar en el directorio raíz donde se encuentra el `Dockerfile` y la estructura mostrada:

```sh
cd /home/[user]/projects/ecosistema-devops/dockerfile
```

Compila la imagen Docker:

```sh
docker login -u maikol555
docker build -t maikol555/ecosistema-jenkins:1.0 .
```

Sube la imagen a Docker Hub:

```sh
docker push [user]/ecosistema-jenkins:1.0
```

_Estructura de directorios utilizada:_

```
dockerfile/
│   ├── Dockerfile
│   ├── files/
│   │   ├── init.groovy
│   │   └── plugins.txt
│   ├── jcasc/
│   │   ├── credentials.yaml
│   │   ├── general.yaml
│   │   ├── nodes.yaml
│   │   └── seedJob.yaml
│   └── postgresql-42.7.5.jar
local/
  ├── deployment-jenkins.yaml
  ├── ingress.yaml
  ├── install.sh
  ├── persistence.yaml
  ├── secret-jenkins.yaml
  ├── service-account.yaml
  ├── service.yaml
  ├── values-grafana.yaml
  └── values-sonar.yaml
```

---

## Instalación de Jenkins en Kubernetes

1. **Accede a la carpeta de manifiestos**  
  Dirígete al directorio `local/` donde se encuentran los archivos de despliegue:
  ```sh
  cd /home/[user]/projects/ecosistema-devops/local
  ```

2. **Configura la imagen en el manifiesto**  
  Edita el archivo `deployment-jenkins.yaml` y asegúrate de que la imagen de Jenkins corresponda a la que construiste y subiste previamente (`[user]/ecosistema-jenkins:1.0`).

3. **Despliega los recursos en Kubernetes**  
  Puedes aplicar los manifiestos manualmente:
  ```sh
  kubectl apply -f deployment-jenkins.yaml
  kubectl apply -f ingress.yaml
  kubectl apply -f persistence.yaml
  kubectl apply -f secret-jenkins.yaml
  kubectl apply -f service-account.yaml
  kubectl apply -f service.yaml
  ```
  O ejecutar el script de instalación:
  ```sh
  ./install.sh
  ```

4. **Verifica la instalación**  
  Comprueba que los pods y recursos se hayan creado correctamente:
  ```sh
  kubectl get pods
  kubectl get all
  kubectl port-fortwart service/jenkins  
  ```

Sigue estos pasos para asegurar una correcta construcción, despliegue y verificación de Jenkins en tu entorno Kubernetes.