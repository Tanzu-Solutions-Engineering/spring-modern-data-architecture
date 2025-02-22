docker network create tanzu

docker run --rm -d --name valkey-server \
    -e ALLOW_EMPTY_PASSWORD=yes \
    --network tanzu  -p 6379:6379 \
    bitnami/valkey:8.0.2


docker run -it --rm \
        -e VALKEY_PRIMARY_HOST=valkey-server \
        --network tanzu  -p 26379:26379 \
        bitnami/valkey-sentinel:8.0.2