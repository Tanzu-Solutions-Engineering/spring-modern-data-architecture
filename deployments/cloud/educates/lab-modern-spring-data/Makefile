WORKSHOP_NAME = lab-modern-spring-data
REGISTRY = localhost:5001

# Use the default "all" target the first time you want to deploy the workshop.

all: publish-files deploy-workshop

# Use the "build-image" and "publish-image" targets if you want to build and
# publish a custom workshop base image using the "Dockerfile". The image will be
# pushed to the configured image registry.

build-image:
	docker build -t $(REGISTRY)/$(WORKSHOP_NAME)-image:latest .

publish-image: build-image
	docker push $(REGISTRY)/$(WORKSHOP_NAME)-image:latest

# Use the "publish-files" target to build and publish an OCI image artefact
# which contains the workshop content files. The artefact will be pushed to the
# configured image registry.

publish-files:
	imgpkg push -i $(REGISTRY)/$(WORKSHOP_NAME)-files:latest -f .

# Use the "deploy-workshop" target to deploy the workshop to your Kubernetes
# cluster. This will wait for the deployment of the training portal to be
# completed before returning.

deploy-workshop:
	kubectl apply -f resources/workshop.yaml
	kubectl apply -f resources/trainingportal.yaml
	STATUS=1; ATTEMPTS=0; ROLLOUT_STATUS_CMD="kubectl rollout status deployment/training-portal -n $(WORKSHOP_NAME)-ui"; until [ $$STATUS -eq 0 ] || $$ROLLOUT_STATUS_CMD || [ $$ATTEMPTS -eq 5 ]; do sleep 5; $$ROLLOUT_STATUS_CMD; STATUS=$$?; ATTEMPTS=$$((attempts + 1)); done

# Use the "update-workshop" target to update the workshop definition. When the
# training portal is configured to detect changes to the workshop definition
# the existing workshop environment will be shutdown and a new one created which
# uses the new workshop definition.

update-workshop:
	kubectl apply -f resources/workshop.yaml

# Use the "delete-workshop" target to delete the workshop from your Kubernetes
# cluster. This will wait for the deployment of the training portal to be
# finished before returning.

delete-workshop:
	kubectl delete -f resources/trainingportal.yaml --cascade=foreground
	kubectl delete -f resources/workshop.yaml

# Use the "open-workshop" target to open a web browser on the training portal
# which provides access to the workshop.

open-workshop:
	URL=`kubectl get trainingportal/$(WORKSHOP_NAME) -o go-template={{.status.educates.url}}`; (test -x /usr/bin/xdg-open && xdg-open $$URL) || (test -x /usr/bin/open && open $$URL) || true
