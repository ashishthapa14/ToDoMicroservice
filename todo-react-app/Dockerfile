# # frontend/Dockerfile
# FROM node:18-alpine as build
# WORKDIR /app
# COPY package*.json ./
# RUN npm install
# COPY . .
# RUN npm run build

# FROM nginx:alpine
# COPY --from=build /app/build /usr/share/nginx/html
# COPY nginx.conf /etc/nginx/conf.d/default.conf
# EXPOSE 3000
# CMD ["nginx", "-g", "daemon off;"]
FROM node:18

WORKDIR /app
#Layer3 build package
COPY package*.json /app/

RUN npm install

COPY . .

ENV port=3000

EXPOSE 3000

CMD [ "npm","start" ]