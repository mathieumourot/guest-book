#!/bin/bash
## provide name and message to run the guest-book logging system
##
for i in \
    '{"name": "John Doe1", "message": "Hello from John Doe1!"}' \
        '{"name": "John Doe2", "message": "Hello from John Doe2!"}'  \
        '{"name": "John Doe3", "message": "Hello from John Doe3!"}' \
        '{"name": "John Doe4", "message": "Hello from John Doe4!"}'; do 
    echo $i
    curl -v -i \
         -H "Accept: application/json" \
         -H "Content-Type: application/json" \
         -X POST -d "$i" \
         http://localhost:9000/v1/log
done
