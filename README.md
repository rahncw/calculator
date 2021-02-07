# calculator
# here's another line

-had to change some docker config file to listen on all ports for the cloud thing to work 
(it's in "Continous Delivery With Docker and Jenkins") and then there's some command to reload the
config without restarting the docker service daemon
-had to add the following configuration to src/main/resources/application.properties
"server.port=8083"