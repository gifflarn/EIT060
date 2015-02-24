#Start clean, remove everyhing except for the script
ls | grep ".sh" -v | xargs rm


#1
openssl genrsa -out privkey.pem 2048 -alias key1
openssl req -new -x509 -key privkey.pem -out publickey.cer < data/data1

#2
keytool -keystore clienttruststore -importcert -file publickey.cer -alias rootCA < data/data2

#3
keytool -genkeypair -keystore clientkeystore < data/data3

#4
keytool -keystore clientkeystore -certreq -file publickey.csr < data/data4

#5
openssl x509 -req -in publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out newcert.cer < data/data5

#6
keytool -keystore clientkeystore -importcert -file publickey.cer -alias rootCA < data/data6
keytool -keystore clientkeystore -importcert -file newcert.cer < data/data7

#9
keytool -genkeypair -keystore serverkeystore < data/data8
keytool -keystore serverkeystore -certreq -file server.csr < data/data9
openssl x509 -req -in server.csr -CA publickey.cer -CAkey privkey.pem -out servercert.cer < data/data10
keytool -keystore serverkeystore -importcert -file publickey.cer -alias rootCA < data/data11
keytool -keystore serverkeystore -importcert -file servercert.cer < data/data12

#10
keytool -keystore servertruststore -importcert -file publickey.cer -alias rootCA < data/data13
#cp clienttruststore servertruststore



#Run server
cd ssl

javac server.java client.java
java server 9878 &
java client localhost 9878

pkill java
