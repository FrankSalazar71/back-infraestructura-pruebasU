pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    tools {
        maven 'maven'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Sonar Scanner') {
            steps {
                withCredentials([string(credentialsId: 'sonar', variable: 'SONAR_TOKEN')]) {
                    sh "mvn sonar:sonar -Dsonar.host.url=${env.SONAR_HOST_URL ?: 'http://sonarqube:9000'} -Dsonar.token=\$SONAR_TOKEN -Dsonar.projectName=ms_infraestructura -Dsonar.projectVersion=${env.BUILD_NUMBER} -Dsonar.projectKey=pe.edu.vallgrande:ms_infraestructura"
                }
            }
            post {
                success {
                    echo '✅ Análisis de código enviado exitosamente a SonarQube'
                }
                failure {
                    echo '❌ Falló el envío del análisis de código a SonarQube'
                }
            }
        }

    }

    post {
        success {
            slackSend color: 'good', message: "Build Success - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        }
        failure {
            slackSend color: 'danger', message: "Build Failure - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        }
        always {
            slackSend color: 'warning', message: "Build Finished - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        }
    }
}