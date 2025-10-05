#!/bin/bash

# Archivos a desplegar o eliminar en orden
DEPLOYMENTS=(
    "persistence.yaml"
    "secret-jenkins.yaml"
    "service-account.yaml"
    "deployment-jenkins.yaml"
    "service.yaml"
)

# Función para desplegar los archivos
deploy() {
    for deployment in "${DEPLOYMENTS[@]}"; do
        if [ -f "$deployment" ]; then
            echo "Desplegando $deployment..."
            kubectl apply -f "$deployment"
        else
            echo "Archivo $deployment no encontrado!"
        fi
    done
    echo "Despliegue completado."
}

# Función para eliminar los deployments en orden inverso
delete() {
    for (( i=${#DEPLOYMENTS[@]}-1; i>=0; i-- )); do
        deployment=${DEPLOYMENTS[$i]}
        if [ -f "$deployment" ]; then
            echo "Eliminando $deployment..."
            kubectl delete -f "$deployment"
        else
            echo "Archivo $deployment no encontrado!"
        fi
    done
    echo "Eliminación completada."
}

# Menú interactivo
echo "Seleccione una opción:"
echo "1) Instalar deployments"
echo "2) Eliminar deployments"
echo "3) Salir"
read -p "Ingrese su opción: " option

case $option in
    1)
        echo "Iniciando instalación..."
        deploy
        ;;
    2)
        echo "Iniciando eliminación..."
        delete
        ;;
    3)
        echo "Saliendo..."
        exit 0
        ;;
    *)
        echo "Opción no válida."
        exit 1
        ;;
esac
