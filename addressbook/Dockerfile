FROM ymmtmdk/gradle-kotlin

USER root
RUN apt-get update && apt-get upgrade -y
RUN apt-get install curl
RUN curl -sL https://deb.nodesource.com/setup_8.x | bash -
RUN apt-get install -y build-essential nodejs

ENV APP_HOME /app
RUN mkdir -p $APP_HOME
ADD . $APP_HOME
WORKDIR $APP_HOME

RUN npm install webpack -g

EXPOSE 8080 8088
