#Start clean, remove everyhing except for the scripts and folders
bash clean.sh

#1
openssl genrsa -out privkey.pem 2048
openssl req -new -x509 -key privkey.pem -out publickey.cer -subj '/CN=CA'

./user_cert.sh "harald"	 "/CN=Harald/OU=Urologen/O=Doctor"		"CN=Harald, OU=Urologen, O=Doctor"
./user_cert.sh "jonas"	 "/CN=Jonas/OU=Ortopedkliniken/O=Doctor"	"CN=Jonas, OU=Ortopedkliniken, O=Doctor"
./user_cert.sh "joel"	 "/CN=Joel/OU=Urologen/O=Doctor"		"CN=Joel, OU=Urologen, O=Doctor"
./user_cert.sh "mattias" "/CN=Mattias/OU=Ortopedkliniken/O=Nurse"	"CN=Mattias, OU=Ortopedkliniken, O=Nurse"
./user_cert.sh "max"	 "/CN=Max/OU=Urologen/O=Nurse"			"CN=Max, OU=Urologen, O=Nurse"
./user_cert.sh "jamil"	 "/CN=Jamil/OU=Ortopedkliniken/O=Patient"	"CN=Jamil, OU=Ortopedkliniken, O=Patient"
./user_cert.sh "lukas"	 "/CN=Lukas/OU=Urologen/O=Patient"		"CN=Lukas, OU=Urologen, O=Patient"


#openssl genrsa -out harald/privkey.pem 2048
#openssl genrsa -out jonas/privkey.pem 2048
#openssl genrsa -out joel/privkey.pem 2048
#openssl genrsa -out mattias/privkey.pem 2048
#openssl genrsa -out max/privkey.pem 2048
#openssl genrsa -out jamil/privkey.pem 2048
#openssl genrsa -out lukas/privkey.pem 2048

#openssl req -new -x509 -key harald/privkey.pem -out harald/publickey.cer -subj '/CN=Harald/OU=Urologen/O=Doctor'
#openssl req -new -x509 -key jonas/privkey.pem -out jonas/publickey.cer -subj '/CN=Jonas/OU=Ortopedkliniken/O=Doctor'
#openssl req -new -x509 -key joel/privkey.pem -out joel/publickey.cer -subj '/CN=Joel/OU=Urologen/O=Doctor'
#openssl req -new -x509 -key mattias/privkey.pem -out mattias/publickey.cer -subj '/CN=Mattias/OU=Ortopedkliniken/O=Nurse'
#openssl req -new -x509 -key max/privkey.pem -out max/publickey.cer -subj '/CN=Max/OU=Urologen/O=Nurse'
#openssl req -new -x509 -key jamil/privkey.pem -out jamil/publickey.cer -subj '/CN=Jamil/OU=Ortopedkliniken/O=Patient'
#openssl req -new -x509 -key lukas/privkey.pem -out lukas/publickey.cer -subj '/CN=Lukas/OU=Urologen/O=Patient'

#2
keytool -keystore clienttruststore -importcert -file publickey.cer -alias rootCA < data/data2

#keytool -keystore harald/truststore -importcert -file publickey.cer -alias rootCA < data/data2
#keytool -keystore jonas/truststore -importcert -file publickey.cer -alias rootCA < data/data2
#keytool -keystore joel/truststore -importcert -file publickey.cer -alias rootCA < data/data2
#keytool -keystore mattias/truststore -importcert -file publickey.cer -alias rootCA < data/data2
#keytool -keystore max/truststore -importcert -file publickey.cer -alias rootCA < data/data2
#keytool -keystore jamil/truststore -importcert -file publickey.cer -alias rootCA < data/data2
#keytool -keystore lukas/truststore -importcert -file publickey.cer -alias rootCA < data/data2

#3
keytool -genkeypair -keystore clientkeystore -dname "CN=CA" < data/data3

#keytool -genkeypair -keystore harald/keystore -dname "CN=Harald, OU=Urologen, O=Doctor" < data/data3
#keytool -genkeypair -keystore jonas/keystore -dname "CN=Jonas, OU=Ortopedkliniken, O=Doctor" < data/data3
#keytool -genkeypair -keystore joel/keystore -dname "CN=Joel, OU=Urologen, O=Doctor" < data/data3
#keytool -genkeypair -keystore mattias/keystore -dname "CN=Mattias, OU=Ortopedkliniken, O=Nurse" < data/data3
#keytool -genkeypair -keystore max/keystore -dname "CN=Max, OU=Urologen, O=Nurse" < data/data3
#keytool -genkeypair -keystore jamil/keystore -dname "CN=Jamil, OU=Ortopedkliniken, O=Patient" < data/data3
#keytool -genkeypair -keystore lukas/keystore -dname "CN=Lukas, OU=Urologen, O=Patient" < data/data3

#4
keytool -keystore clientkeystore -certreq -file publickey.csr < data/data4

#keytool -keystore harald/keystore -certreq -file harald/publickey.csr < data/data4
#keytool -keystore jonas/keystore -certreq -file jonas/publickey.csr < data/data4
#keytool -keystore joel/keystore -certreq -file joel/publickey.csr < data/data4
#keytool -keystore mattias/keystore -certreq -file mattias/publickey.csr < data/data4
#keytool -keystore max/keystore -certreq -file max/publickey.csr < data/data4
#keytool -keystore jamil/keystore -certreq -file jamil/publickey.csr < data/data4
#keytool -keystore lukas/keystore -certreq -file lukas/publickey.csr < data/data4

#5
openssl x509 -req -in publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out newcert.cer

#openssl x509 -req -in harald/publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out harald/newcert.cer
#openssl x509 -req -in jonas/publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out jonas/newcert.cer
#openssl x509 -req -in joel/publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out joel/newcert.cer
#openssl x509 -req -in mattias/publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out mattias/newcert.cer
#openssl x509 -req -in max/publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out max/newcert.cer
#openssl x509 -req -in jamil/publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out jamil/newcert.cer
#openssl x509 -req -in lukas/publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out lukas/newcert.cer

#6
keytool -keystore clientkeystore -importcert -file publickey.cer -alias rootCA < data/data6
keytool -keystore clientkeystore -importcert -file newcert.cer < data/data7

#keytool -keystore harald/keystore -importcert -file publickey.cer -alias rootCA < data/data6
#keytool -keystore jonas/keystore -importcert -file publickey.cer -alias rootCA < data/data6
#keytool -keystore joel/keystore -importcert -file publickey.cer -alias rootCA < data/data6
#keytool -keystore mattias/keystore -importcert -file publickey.cer -alias rootCA < data/data6
#keytool -keystore max/keystore -importcert -file publickey.cer -alias rootCA < data/data6
#keytool -keystore jamil/keystore -importcert -file publickey.cer -alias rootCA < data/data6
#keytool -keystore lukas/keystore -importcert -file publickey.cer -alias rootCA < data/data6

#keytool -keystore harald/keystore -importcert -file harald/newcert.cer < data/data7
#keytool -keystore jonas/keystore -importcert -file jonas/newcert.cer < data/data7
#keytool -keystore joel/keystore -importcert -file joel/newcert.cer < data/data7
#keytool -keystore mattias/keystore -importcert -file mattias/newcert.cer < data/data7
#keytool -keystore max/keystore -importcert -file max/newcert.cer < data/data7
#keytool -keystore jamil/keystore -importcert -file jamil/newcert.cer < data/data7
#keytool -keystore lukas/keystore -importcert -file lukas/newcert.cer < data/data7

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
