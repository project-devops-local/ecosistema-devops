FROM jenkins/jenkins:2.516.3-lts


ENV JAVA_OPTS="-Djava.awt.headless=true -Djenkins.install.runSetupWizard=false -Dmail.smtp.starttls.enable=false -Dmail.smtp.socketFactory.fallback"
#carga inicial y configuracion por script groovy jenkins
COPY --chown=jenkins:jenkins  files/init.groovy /usr/share/jenkins/ref/init.groovy.d/init.groovy


#driver postgresql
COPY --chown=jenkins:jenkins postgresql-42.7.5.jar /var/jenkins_home/war/WEB-INF/lib/postgresql-42.7.5.jar


#copia y ejecucion plugins jenkins
COPY --chown=jenkins:jenkins files/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt


ENV JENKINS_AUTHORIZATION_STRATEGY=Matrix


#pasar archivos de configuracion jcasc
RUN mkdir -p /usr/share/jenkins/ref/config
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/config
COPY --chown=jenkins:jenkins jcasc/*.yaml /usr/share/jenkins/ref/config


