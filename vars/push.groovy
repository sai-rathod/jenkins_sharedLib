def call(String project, String imageTag) {
    withCredentials([usernamePassword(credentialsId:'dockerCred', passwordVariable:'dockerPass',
    usernameVariable:'dockerUser')]) {
        sh """
    cd my-app
    docker rm -f \$(docker ps -aq) || true
    docker build -t \$dockerUser/$project:$imageTag .
    echo "image building completed"
    docker image prune -f
    docker images
    docker login -u \$dockerUser -p \$dockerPass
    docker push \$dockerUser/$project:$imageTag
    """
    }
}
