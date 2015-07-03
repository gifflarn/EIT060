function create_cert {
	openssl genrsa -out "$1/privkey.pem" 2048
	openssl req -new -x509 -key "$1/privkey.pem" -out "$1/publickey.cer" -subj "$2"
	keytool -keystore "$1/truststore" -importcert -file publickey.cer -alias rootCA < data/data2
	keytool -genkeypair -keystore "$1/keystore" -dname "$3" < data/data3
	keytool -keystore "$1/keystore" -certreq -file "$1/publickey.csr" < data/data4
	openssl x509 -req -in "$1/publickey.csr" -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out "$1/newcert.cer"
	keytool -keystore "$1/keystore" -importcert -file publickey.cer -alias rootCA < data/data6
	keytool -keystore "$1/keystore" -importcert -file "$1/newcert.cer" < data/data7

	rm "$1/newcert.cer"
	rm "$1/publickey.csr"
}


#Start clean, remove everyhing except for the scripts and folders
bash clean.sh

#1
openssl genrsa -out privkey.pem 2048
openssl req -new -x509 -key privkey.pem -out publickey.cer -subj '/CN=CA'

create_cert "harald"	"/CN=Harald/OU=Urologen/O=Doctor"		"CN=Harald, OU=Urologen, O=Doctor"
create_cert "jonas"	"/CN=Jonas/OU=Ortopedkliniken/O=Doctor"		"CN=Jonas, OU=Ortopedkliniken, O=Doctor"
create_cert "joel"	"/CN=Joel/OU=Urologen/O=Doctor"			"CN=Joel, OU=Urologen, O=Doctor"
create_cert "mattias"	"/CN=Mattias/OU=Ortopedkliniken/O=Nurse"	"CN=Mattias, OU=Ortopedkliniken, O=Nurse"
create_cert "max"	"/CN=Max/OU=Urologen/O=Nurse"			"CN=Max, OU=Urologen, O=Nurse"
create_cert "jamil"	"/CN=Jamil/OU=Ortopedkliniken/O=Patient"	"CN=Jamil, OU=Ortopedkliniken, O=Patient"
create_cert "lukas"	"/CN=Lukas/OU=Urologen/O=Patient"		"CN=Lukas, OU=Urologen, O=Patient"

#2
keytool -keystore clienttruststore -importcert -file publickey.cer -alias rootCA < data/data2

#3
keytool -genkeypair -keystore clientkeystore -dname "CN=CA" < data/data3

#4
keytool -keystore clientkeystore -certreq -file publickey.csr < data/data4

#5
openssl x509 -req -in publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out newcert.cer

#6
keytool -keystore clientkeystore -importcert -file publickey.cer -alias rootCA < data/data6
keytool -keystore clientkeystore -importcert -file newcert.cer < data/data7

#9
keytool -genkeypair -keystore serverkeystore -dname "CN=myserver" < data/data8
keytool -keystore serverkeystore -certreq -file server.csr < data/data9
openssl x509 -req -in server.csr -CA publickey.cer -CAkey privkey.pem -out servercert.cer < data/data10
keytool -keystore serverkeystore -importcert -file publickey.cer -alias rootCA < data/data11
keytool -keystore serverkeystore -importcert -file servercert.cer < data/data12

#10
keytool -keystore servertruststore -importcert -file publickey.cer -alias rootCA < data/data13
#cp clienttruststore servertruststore



##Run server
cd ssl

javac server.java client.java
java server 9878 &
java client localhost 9878

pkill java
