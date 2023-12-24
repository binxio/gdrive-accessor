PROJECT_ID=$(shell gcloud config get-value core/project)
REPO_NAME=$(shell basename $(CURDIR))
COMMIT_SHA=$(shell git rev-parse --short head)

snapshot:
	@echo gcloud builds submit --substitutions _IMAGE=europe-docker.pkg.dev/$(PROJECT_ID)/$(REPO_NAME)/app:$(COMMIT_SHA);
	gcloud builds submit --substitutions _IMAGE=europe-docker.pkg.dev/$(PROJECT_ID)/$(REPO_NAME)/app:$(COMMIT_SHA)
