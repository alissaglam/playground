# playground

## This repository contains POC works around Microservices, Kubernates Cluster, Java, SpringBoot, Kotlin, Postgres

## Instructions

### k8n
This folder contains kubernates instructions which are used to deploy microservices, postgres and other components within the cluster.

#### Some useful kubernates commands 

##### kubectl create -f <instructions file like team.yaml>
This command is used to create k8n objects defined in the file
##### kubectl delete -f <instructions file like team.yaml>
This command is used to delete k8n objects defined in the file
##### kubectl get services
This command is used retrieve active services within the k8n cluster
##### kubectl get pods
This command is used retrieve active pods within the k8n cluster
##### kubectl get deployments
This command is used retrieve active deployments within the k8n cluster
##### kubectl scale --replicas=<replica count like 3> deployment <deployment name like tournament-microservice>
This command is used scale up/down an existing deployment within the cluster
#### kubectl exec -it <postgres-pod-name> -- psql -h localhost -p 5432 -U postgres
This command us used to connect postgres db in the cluster with psql command line tool

### team
A microservice used to manage team (soccer team) data and written with Kotlin & SpringBoot

### tournament
A microservice used to manage tournaments and written with Java & SpringBoot