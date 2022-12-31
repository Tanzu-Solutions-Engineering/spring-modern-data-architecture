#!/bin/bash

kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"

#############################################################################

STATUS=1;
TIMEOUT=90;
DELAY=5;
ATTEMPTS=$((TIMEOUT/DELAY));
POD_STATUS_CMD="kubectl get pods -l app.kubernetes.io/name=rabbitmq-cluster-operator -n rabbitmq-system";

until [ $STATUS -eq 0 ] || $POD_STATUS_CMD || [ $ATTEMPTS -eq 0 ];
do sleep $DELAY; $POD_STATUS_CMD; STATUS=$?; ATTEMPTS=$((ATTEMPTS - 1));
done

if [[ $ATTEMPTS -le 0  && $STATUS -eq 1 ]]
then echo "Timeout Exceeded. Pod not created!"
else
    echo "Pod Created in $((TIMEOUT - (ATTEMPTS * DELAY))) seconds!"
    echo "Waiting for Pod to be Ready..."
fi