pipeline {
    agent any

    tools {
        maven 'Maven3' 
        jdk 'JDK17'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_TOKEN = credentials('sonar-token') // el ID de tu token en Jenkins
            }
            steps {
                withSonarQubeEnv('SonarCloud') {
                    sh """
                    mvn sonar:sonar \
                      -Dsonar.projectKey=tu_usuario_tuRepo \
                      -Dsonar.organization=tu_organizacion \
                      -Dsonar.host.url=https://sonarcloud.io \
                      -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }
    }
}
