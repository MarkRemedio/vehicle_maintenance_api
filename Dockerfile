FROM tomcat:9.0

LABEL maintainer="josephremedio@gmail.com"

RUN apt-get update && apt-get install -y sudo maven net-tools

RUN mkdir -p /root/vehiclerepair_api

COPY . /root/vehiclerepair_api/.

WORKDIR /root/vehiclerepair_api/

RUN mvn clean install

EXPOSE 8080

CMD ["catalina.sh",  "run"]