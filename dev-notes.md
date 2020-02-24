Creating one package where we have one url to generate JWT based on certain fields (username password)

(client secret pair) 

{
	"client":true,
	"client_id":"----",
	"client_secret":"--------"
}


{
	"client":false,
	"username":"----",
	"password":"--------"
}


clients

id  |  client_id   |    client_secret (encrypt, Bcrypt)   |    token_validity    | created | updated |  enabled | deleted


users

id  |  username   |    password (encrypt, Bcrypt)  | created | updated |  enabled | deleted | role_id |  user_profile_id


roles

id | role_name | created | updated | enabled | deleted 


user_profile

id | fname | lname | gender | created | updated | deleted


-------- JWT --------------

symmetric | asymmetric

JWKS - url expose

/.well-known/jwks
/api/jwt

/api/validate/jwt

plain symmetric JWT -> asymmetric JWT(JWS) ->  JWE

