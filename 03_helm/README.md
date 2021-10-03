# Helm chart for SpringBoot app

## Install the Helm chart

```
helm dependency update ./learnspring

# Install Helm chart with Prometheus (default)
helm install learnspring ./learnspring

# Install Helm chart without Prometheus
# helm install learnspring ./learnspring --set kubeprometheusstack.enabled=false

kubectl get all
kubectl get ServiceMonitor

export NODE_PORT=$(kubectl get --namespace default -o jsonpath="{.spec.ports[0].nodePort}" services learnspring)
export NODE_IP=$(kubectl get nodes --namespace default -o jsonpath="{.items[0].status.addresses[0].address}")

echo App URL: http://$NODE_IP:$NODE_PORT/api/hello

curl http://$NODE_IP:$NODE_PORT/actuator/health | jq .
```

## Generate the custom metrics

```
# Increase custom counter
for i in $(seq 1 $((1 + $RANDOM % 10))); do curl http://$NODE_IP:$NODE_PORT/api/hello; done
for i in $(seq 1 $((1 + $RANDOM % 10))); do curl http://$NODE_IP:$NODE_PORT/api/hello/Csabi; done

# check hello_total counter in Prometheus and Grafana
prometheus_port=$(kubectl get service/learnspring-kubeprometheus-prometheus -o go-template='{{(index .spec.ports 0).nodePort}}')
echo Prometheus URL: http://$(minikube ip):$prometheus_port

grafana_port=$(kubectl get service/learnspring-grafana -o go-template='{{(index .spec.ports 0).nodePort}}')
echo Grafana URL: http://$(minikube ip):$grafana_port

GRAFANA_USER=$(kubectl get secret learnspring-grafana -o jsonpath="{.data.admin-user}" | base64 -d)
GRAFANA_PASSWORD=$(kubectl get secret learnspring-grafana -o jsonpath="{.data.admin-password}" | base64 -d)
echo "$GRAFANA_USER / $GRAFANA_PASSWORD"
```

## Cleanup

```
helm delete learnspring
```
