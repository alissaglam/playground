# playground

## This repository contains POC works around Microservices, Kubernates Cluster, Java, SpringBoot, Kotlin, Postgres


## k8n
This folder contains kubernates instructions which are used to deploy microservices, postgres and other components within the cluster.

### Execute following commands in the order to deploy microservices to kubernates cluster
**Create ConfigMap and Secrets**\
<code>kubectl create -f config_map.yaml</code>\
<code>kubectl create -f secret.yaml</code>\
**Deploy Postgres**\
<code>kubectl create -f postgres_persistent_volume.yaml</code>\
<code>kubectl create -f postgres.yaml</code>\
At his point you need to create database schemas of the microservices with the schema.sql files within their codebase. First you need to connect to the database and then execute the commands. In order to be able to connect to the database you can use the following commands.\
<code>kubectl exec -it <**postgres-pod-name**> -- psql -h localhost -p 5432 -U postgres</code>\
**Update Postgres Host IP in Configmap**\
<code>kubectl get services</code>\
Copy <code>CLUSTER-IP</code> of Postgres and paste it <code>postgres_host</code> in <code>config_map.yaml</code>\
<code>kubectl delete -f config_map.yaml</code>\
<code>kubectl create -f config_map.yaml</code>\
**Deploy Team Microservice**\
<code>kubectl create -f team.yaml</code>\
**Deploy Tournament Microservice**\
<code>kubectl create -f tournament.yaml</code>\
You can test your microservices with by <code>localhost:1010/teams</code> and <code>localhost:1011/tournaments</code>. team and tournament services are exposed on 1010 and 1011 ports respectively

### Some useful kubernates commands 

<code> kubectl create -f <**instructions file like team.yaml**></code>\
This command is used to create k8n objects defined in the file\
<code> kubectl delete -f <**instructions file like team.yaml**></code>\
This command is used to delete k8n objects defined in the file\
<code> kubectl get services</code>\
This command is used retrieve active services within the k8n cluster\
<code> kubectl get pods</code>\
This command is used retrieve active pods within the k8n cluster\
<code> kubectl get deployments</code>\
This command is used retrieve active deployments within the k8n cluster\
<code> kubectl scale --replicas=<**replica count like 3**> deployment <**deployment name like tournament-microservice**></code>\
This command is used scale up/down an existing deployment within the cluster\
<code> kubectl exec -it <**postgres-pod-name**> -- psql -h localhost -p 5432 -U postgres</code>\
This command us used to connect postgres db in the cluster with psql command line tool

## team
A microservice used to manage team (soccer team) data and written with Kotlin & SpringBoot

## tournament
A microservice used to manage tournaments and written with Java & SpringBoot