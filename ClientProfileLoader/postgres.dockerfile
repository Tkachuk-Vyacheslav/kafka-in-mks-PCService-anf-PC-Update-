FROM postgres

LABEL author="Arseny"
LABEL desciption="enjoy the database"
LABEL version="1.0"

COPY DB /docker-entrypoint-initdb.d/