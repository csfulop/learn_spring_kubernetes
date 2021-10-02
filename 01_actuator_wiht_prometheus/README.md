# Integrate Spring Actuator with Prometheus

## SpringBoot app with Actuator and Prometheus

https://spring.io/guides/gs/spring-boot-kubernetes/

```
your_dockerhub_username=FIXME

./gradlew bootBuildImage

docker run --rm -d -p 8080:8080 --name spring_kubernetes spring_boot_actuator:0.0.1-SNAPSHOT
curl http://localhost:8080/actuator | jq .
docker stop spring_kubernetes
docker tag spring_boot_actuator:0.0.1-SNAPSHOT $your_dockerhub_username/spring_kubernetes:v1
docker push $your_dockerhub_username/spring_kubernetes:v1
docker rmi spring_boot_actuator:0.0.1-SNAPSHOT $your_dockerhub_username/spring_kubernetes:v1

kubectl create deployment spring-kubernetes --image=$your_dockerhub_username/spring_kubernetes:v1 --dry-run -o=yaml > deployment.yaml
echo --- >> deployment.yaml
kubectl create service nodeport spring-kubernetes --tcp=8080:8080 --dry-run -o=yaml >> deployment.yaml

kubectl apply -f deployment.yaml
kubectl get all

node_port=$(kubectl get service/spring-kubernetes -o go-template='{{(index .spec.ports 0).nodePort}}')
echo SpringBoot app: http://$(minikube ip):$node_port/actuator
curl http://$(minikube ip):$node_port/actuator | jq .
```

## Install Prometheus

https://github.com/prometheus-community/helm-charts/tree/main/charts/kube-prometheus-stack

```
kubectl create namespace monitoring
#kubectl config set-context --current --namespace=monitoring

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

# helm show values prometheus-community/kube-prometheus-stack > prometheus-values.yaml
# helm show values grafana/grafana > grafana-values.yaml

helm install prometheus prometheus-community/kube-prometheus-stack --namespace monitoring \
  --set prometheus.service.type=NodePort \
  --set grafana.service.type=NodePort
# FIXME: add alertmanager

kubectl get all -n monitoring

prometheus_port=$(kubectl get service/prometheus-kube-prometheus-prometheus -n monitoring -o go-template='{{(index .spec.ports 0).nodePort}}')
echo Prometheus URL: http://$(minikube ip):$prometheus_port

grafana_port=$(kubectl get service/prometheus-grafana -n monitoring -o go-template='{{(index .spec.ports 0).nodePort}}')
echo Grafana URL: http://$(minikube ip):$grafana_port

GRAFANA_USER=$(kubectl get secret prometheus-grafana -n monitoring -o jsonpath="{.data.admin-user}" | base64 -d)
GRAFANA_PASSWORD=$(kubectl get secret prometheus-grafana -n monitoring -o jsonpath="{.data.admin-password}" | base64 -d)
echo "$GRAFANA_USER / $GRAFANA_PASSWORD"

# With ClusterIPs:
# kubectl --namespace monitoring port-forward svc/prometheus-kube-prometheus-prometheus 9090
# kubectl --namespace monitoring port-forward svc/prometheus-grafana 3000:80
# kubectl --namespace monitoring port-forward svc/prometheus-kube-prometheus-alertmanager 9093
```

## Create Prometheus ServiceMonitor

Based on: https://kruschecompany.com/kubernetes-prometheus-operator/#How_to_Install_and_Configure_Prometheus_Operator_A_Step-by-Step_Guide

```
kubectl get prometheuses.monitoring.coreos.com -n monitoring -o jsonpath="{.items[0].spec.podMonitorSelector}" | jq .
# label in service-monitor.yaml must match with this

kubectl apply -f service-monitor.yaml
kubectl describe ServiceMonitor spring-service-monitor
```

## Cleanup

```
helm delete prometheus -n monitoring
kubectl delete ServiceMonitor spring-service-monitor
kubectl delete deployment.apps/spring-kubernetes
```