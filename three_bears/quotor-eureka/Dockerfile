FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER wangiegie@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /quotor-eureka

WORKDIR /quotor-eureka

EXPOSE 8761

ADD ./quotor-eureka/target/quotor-eureka.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar quotor-eureka.jar
