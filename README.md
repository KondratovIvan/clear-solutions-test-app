# clear-solutions-test-app

To run this application you must

**1. Docker**
* Download Docker [Here](https://docs.docker.com/get-docker/). Hint: Enable Hyper-V feature on windows and restart;
* Then open powershell and check:
```bash
docker info
```
or check docker version
```bash
docker -v
```
or docker compose version
```bash
docker-compose -v
```

**2. Spring boot app**
* Clone the repository:
```bash
git clone https://github.com/KondratovIvan/clear-solutions-test-app
```
* Build the maven project:
```bash
mvn clean install
```
* Running the containers:
This command will build docker image.
```bash
docker build -t clear-solution-test-app .
```  
This command will build the docker containers and start them.
```bash
docker-compose up
```

**Note**

All commands should be run from project root (where docker-compose.yml locates)

* If you have to want to see running containers. Checklist docker containers
```bash
docker container list -a
```
or
```bash
docker-compose ps
```

**3. Postman**

Download and install the Postman request client to send HTTP requests

**4. Stop app**

*  Stop containers:
```bash
docker-compose down
```
