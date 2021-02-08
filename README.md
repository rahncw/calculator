# calculator
# here's another line

-had to change some docker config file to listen on all ports for the cloud thing to work 
(it's in "Continous Delivery With Docker and Jenkins") and then there's some command to reload the
config without restarting the docker service daemon
-had to add the following configuration to src/main/resources/application.properties
"server.port=8083"
-to run jenkins master 
"docker run --name jenkins-8081-$INSTANCE_ID -p 8081:8080 -p 50000:50000 -v /home/chris/jenkins_docker_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkins:2.60.3"
-to update number of executors for an instance see ~/Dev/docker-images/jenkins_master_w_docker
-to install jenkins plugins in docker also see ~/Dev/docker-images/jenkins_master_w_docker
