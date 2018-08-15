#!/bin/sh
fly -t home sp -p hazelcast-dns-service-discovery \
    -c `dirname $0`/pipeline.yml \
    -l `dirname $0`/credentials.yml