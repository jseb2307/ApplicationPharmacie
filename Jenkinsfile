
#
pipeline {

    environment {
        registry = "jseb23/my-application"
        registryCredential = 'DockerHub_Id'
        dockerImage = ''
    }

    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/jseb2307/ApplicationPharmacie.git'
            }
        }

        stage('Build Docker images') {
        steps {
            script {
                docker.build('jseb23/my-application:api-latest', '-f Dockerfile .')
                docker.build('jseb23/my-application:postgresql-latest', '-f Dockerfile_postgresql .')
                docker.build('jseb23/my-application:pgadmin-latest', '-f Dockerfile_pgadmin .')
                docker.build('jseb23/my-application:apache-latest', '-f Dockerfile_apache .')
            }
        }
    }

    stage('Push Docker images') {
        steps {
            script {
                docker.withRegistry('',registryCredential) {
                    docker.image('jseb23/my-application:api-latest').push()
                    docker.image('jseb23/my-application:postgresql-latest').push()
                    docker.image('jseb23/my-application:pgadmin-latest').push()
                    docker.image('jseb23/my-application:apache-latest').push()
                }
            }
        }
    }
        stage('Deploy to Docker Desktop') {
            steps {
                //  déploiement vers Docker Desktop
            bat 'docker-compose -f C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\pipeline\\docker-compose.yml up -d'
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}