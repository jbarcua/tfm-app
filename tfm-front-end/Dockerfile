FROM node:16
WORKDIR /app
RUN curl -L -o /usr/bin/jq https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64
RUN chmod u+rx /usr/bin/jq
COPY ["package.json", "package-lock.json", "./"]
RUN npm install --save --legacy-peer-dep && npm cache clean --force
COPY docker-env-js-to-script.sh /
RUN ["chmod", "+x", "/docker-env-js-to-script.sh"]
COPY . /app
EXPOSE 4200
ENTRYPOINT ["/docker-env-js-to-script.sh"]
CMD ["npm" , "start"]
