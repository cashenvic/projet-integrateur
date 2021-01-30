#!/bin/bash

echo "Utiliser la BDD Oracle Distante ? : [Yes] - [No] ?"
read answer
if [ "$answer" != "${answer#[Yy]}" ] ;then
  echo "Voulez-vous démarer le serveur JAVA : [Yes] - [No] ?"
  read answer
  if [ "$answer" != "${answer#[Yy]}" ] ;then
      echo echo "Serveur Java en cours de démarage "

      cd serveur-jetty/

      if [ -x "$(command -v mvn)" ]; then
        echo "Installation du driver ojdbc8"

        mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=19.3 -Dpackaging=jarmvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=19.3 -Dpackaging=jar

        echo "Compilation du SERVEUR JAVA"
        mvn compile

        echo "Démarage du SERVEUR JAVA sur le port 8090"
        mvn exec:java &

        #get PID of Server
        pidServeur=`ps -ef | grep -v grep | grep serveur-jetty | awk '{print $2}'`

        echo '>>>>>>>>>>>>>>>>>>>> PID du serveur : '$pidServeur

        #Vérifaction des dépendances pour le client Angular
        if [ -x "$(command -v node)" ]; then
          cd ..
          cd client/FestiBed/

          if [ -x "$(command -v ng)" ]; then

            echo "Voulez-vous installer les dépendances pour le client Angular : [Yes] - [No] ?"
            read answer

            if [ "$answer" != "${answer#[Yy]}" ] ;then
                echo echo "Installation des dépendances..."
                npm install
                ng serve --proxy-config proxy.conf.json &

                #get PID of Server
                pidClient=`ps -ef | grep -v grep | grep 'ng\s\+serve' | awk '{print $2}'`

                echo '>>>>>>>>>>>>>>>>>>>> PID du Client : '$pidServeur

                cd ..
                cd ..
                #Revenir au rout du projet
                # Demander la fin du SERVEUR Java
                echo "Voulez-vous Arrêter le serveur JAVA : [Yes] - [No] ?"
                read answer

                if [ "$answer" != "${answer#[Yy]}" ] ;then
                    kill -9 $pidServeur

                    echo "Voulez-vous Arrêter le client Angular : [Yes] - [No] ?"
                    read answer

                    if [ "$answer" != "${answer#[Yy]}" ] ;then
                        kill -9 $pidClient
                    fi
                fi
            else
                echo "Démarage du Client Angular sur le port 4200"
                ng serve --proxy-config proxy.conf.json &

                #get PID of Server
                pidClient=`ps -ef | grep -v grep | grep 'ng\s\+serve' | awk '{print $2}'`

                echo '>>>>>>>>>>>>>>>>>>>> PID du Client : '$pidServeur

                cd ..
                cd ..
                #Revenir au rout du projet
                # Demander la fin du SERVEUR Java
                echo "Voulez-vous Arrêter le serveur JAVA : [Yes] - [No] ?"
                read answer

                if [ "$answer" != "${answer#[Yy]}" ] ;then
                    kill -9 $pidServeur

                    echo "Voulez-vous Arrêter le client Angular : [Yes] - [No] ?"
                    read answer

                    if [ "$answer" != "${answer#[Yy]}" ] ;then
                        kill -9 $pidClient
                    fi
                fi
            fi

          else
              echo "Veuillez installer NG sur votre machine"
              echo "npm i @angular/cli"
          fi
        else
            echo "Veuillez installer Nodejs et NPM sur votre machine"
        fi

      else
          echo "Veuillez installer Maven sur votre machine"
      fi

  else
      echo "Fin du script :)"
      exit;
  fi
else
  # Contraite de Docker
  if [ -x "$(command -v docker)" ]; then

    #Contraite d'image Docker
    if [[ "$(docker images -q docker-oracle-m1 2> /dev/null)" == "" ]]; then
      echo "Creation de l'image Docker"

      git clone --depth=1 https://github.com/araczkowski/docker-oracle-apex-ords.git docker-oracle-m1

      cd docker-oracle-m1

      docker build -t docker-oracle-m1 --build-arg PASSWORD=GgQ3sGCe .

      docker run -d --name docker-oracle-m1 -p 49160:22 -p 8181:8080 -p 1521:1521 docker-oracle-m1

      cd ..

    else
      echo "Image Docker présente !"
      echo "Lancement de l'image docker-oracle-m1"
      #docker run -d --name docker-oracle-m1 -p 49160:22 -p 8181:8080 -p 1521:1521 docker-oracle-m1

      echo "####################################################"
      echo "Merci d'insaller un nouveau workspace"
      echo "URL         : http://localhost:8181/ords/apex"
      echo "workspace   : internal"
      echo "user        : admin"
      echo "password    : GgQ3sGCe"
      echo "####################################################"
      echo "Vous disposer dans le dossier /data/ du fichier FESTIBEDTEST.sql pour installer la BDD"
      ls data/
      echo "####################################################"

      echo "Voulez-vous démarer le serveur JAVA : [Yes] - [No] ?"
      read answer
      if [ "$answer" != "${answer#[Yy]}" ] ;then
          echo echo "Serveur Java en cours de démarage "

          cd serveur-jetty/

          if [ -x "$(command -v mvn)" ]; then
            echo "Installation du driver ojdbc8"

            mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=19.3 -Dpackaging=jarmvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=19.3 -Dpackaging=jar

            echo "Compilation du SERVEUR JAVA"
            mvn compile

            echo "Démarage du SERVEUR JAVA sur le port 8090"
            mvn exec:java &

            #get PID of Server
            pidServeur=`ps -ef | grep -v grep | grep serveur-jetty | awk '{print $2}'`

            echo '>>>>>>>>>>>>>>>>>>>> PID du serveur : '$pidServeur

            #Vérifaction des dépendances pour le client Angular
            if [ -x "$(command -v node)" ]; then
              cd ..
              cd client/FestiBed/

              if [ -x "$(command -v ng)" ]; then

                echo "Voulez-vous installer les dépendances pour le client Angular : [Yes] - [No] ?"
                read answer

                if [ "$answer" != "${answer#[Yy]}" ] ;then
                    echo echo "Installation des dépendances..."
                    npm install
                    ng serve --proxy-config proxy.conf.json &

                    #get PID of Server
                    pidClient=`ps -ef | grep -v grep | grep 'ng\s\+serve' | awk '{print $2}'`

                    echo '>>>>>>>>>>>>>>>>>>>> PID du Client : '$pidServeur

                    cd ..
                    cd ..
                    #Revenir au rout du projet
                    # Demander la fin du SERVEUR Java
                    echo "Voulez-vous Arrêter le serveur JAVA : [Yes] - [No] ?"
                    read answer

                    if [ "$answer" != "${answer#[Yy]}" ] ;then
                        kill -9 $pidServeur

                        echo "Voulez-vous Arrêter le client Angular : [Yes] - [No] ?"
                        read answer

                        if [ "$answer" != "${answer#[Yy]}" ] ;then
                            kill -9 $pidClient
                        fi
                    fi
                else
                    echo "Démarage du Client Angular sur le port 4200"
                    ng serve --proxy-config proxy.conf.json &

                    #get PID of Server
                    pidClient=`ps -ef | grep -v grep | grep 'ng\s\+serve' | awk '{print $2}'`

                    echo '>>>>>>>>>>>>>>>>>>>> PID du Client : '$pidServeur

                    cd ..
                    cd ..
                    #Revenir au rout du projet
                    # Demander la fin du SERVEUR Java
                    echo "Voulez-vous Arrêter le serveur JAVA : [Yes] - [No] ?"
                    read answer

                    if [ "$answer" != "${answer#[Yy]}" ] ;then
                        kill -9 $pidServeur

                        echo "Voulez-vous Arrêter le client Angular : [Yes] - [No] ?"
                        read answer

                        if [ "$answer" != "${answer#[Yy]}" ] ;then
                            kill -9 $pidClient
                        fi
                    fi
                fi

              else
                  echo "Veuillez installer NG sur votre machine"
                  echo "npm i @angular/cli"
              fi
            else
                echo "Veuillez installer Nodejs et NPM sur votre machine"
            fi

          else
              echo "Veuillez installer Maven sur votre machine"
          fi

      else
          echo "Fin du script :)"
          exit;
      fi

    fi
  else
      echo "Veuillez installer Docker sur votre machine"
  fi
fi
