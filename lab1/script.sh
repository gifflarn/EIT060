#Start clean, remove everyhing except for the script
ls | grep ".sh" -v | xargs rm


#1
openssl genrsa -out privkey.pem 2048 -alias key1
openssl req -new -x509 -key privkey.pem -out publickey.cer < auto/data1

#2
keytool -keystore clienttruststore -importcert -file publickey.cer -alias rootCA < auto/data2

#3
keytool -genkeypair -keystore clientkeystore < auto/data3

#4
keytool -keystore clientkeystore -certreq -file publickey.csr < auto/data4

#5
openssl x509 -req -in publickey.csr -CA publickey.cer -CAkey privkey.pem -CAcreateserial -out newcert.cer < auto/data5

#6
keytool -keystore clientkeystore -importcert -file publickey.cer -alias rootCA < auto/data6
keytool -keystore clientkeystore -importcert -file newcert.cer < auto/data7

#9
keytool -genkeypair -keystore serverkeystore < auto/data8
keytool -keystore serverkeystore -certreq -file server.csr < auto/data9
openssl x509 -req -in server.csr -CA publickey.cer -CAkey privkey.pem -out servercert.cer < auto/data10
keytool -keystore serverkeystore -importcert -file publickey.cer -alias rootCA < auto/data11
keytool -keystore serverkeystore -importcert -file servercert.cer < auto/data12

#10
keytool -keystore servertruststore -importcert -file publickey.cer -alias rootCA < auto/data13
#cp clienttruststore servertruststore



#Run server
cd ssl

javac server.java client.java
java server 9878 &
java client localhost 9878

pkill java
