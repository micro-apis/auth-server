mongo

1. use admin
2. db.createUser(
     {
        user: "root",
        pwd: "root",   
        roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
      }
    )

3. db.grantRolesToUser(
       "root",
       [
         { role: "readWrite", db: "auth_server" }
       ]
    )



open cmd with admin level access

sc config MongoDB binPath="\"C:\Program Files\MongoDB\Server\4.2\bin\mongod.exe\" --auth --config \"C:\Program Files\MongoDB\Server\4.2\bin\mongod.cfg\" --service"

and restart service
