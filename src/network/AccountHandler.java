package network;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class AccountHandler {

	public static final int SALT_BYTE_SIZE = 32;
	public static final int HASH_BYTE_SIZE = 32;
	public static final int PBKDF2_ITERATIONS = 10000;
	
	private SecureRandom saltGenerator;
	
	public AccountHandler() {
		saltGenerator = new SecureRandom();
	}
	
	/**
	 * 
	 * @param password
	 * @param anvndarinfosomjagfrtillflletskiteri
	 * @return
	 */
	public boolean createNewAccount(String password, String anvandarinfo){
		
		
		byte[] salt = generateSaltValue();
		byte[] hash = hash(password, salt);
		
		//anvndarInfo, salt, hash sparas i databas p ngot stt
		
		return true;
	}
	
	/**
	 * autentiserar anvndaren
	 * @param userId
	 * @param password
	 * @return
	 */
	public boolean authenticateUser(String userId,String password){
		
		byte[] salt= null;
		byte[] hashStored = null;
		
		//med hjlp av userId hmtas salt och hashat lsen frn databas
		
		//hash rknas ut med hjlp av salt och password
		byte[] hash = hash(password, salt);
		
		//kollar om de e samma
		if(hash.equals(hashStored)){
			return true;
		}
		return false;
		
	}
	
	
	

	/**
	 * genererar ett nytt salt vrde
	 * @return
	 */
	private byte[] generateSaltValue() {
		// se http://docs.oracle.com/javase/7/docs/api/java/security/SecureRandom.html
		byte[] salt = new byte[SALT_BYTE_SIZE];
		saltGenerator.nextBytes(salt);
		return salt;
	}


	/**
	 * anvnder PBKDF fr att hasha fram ett hashvrde mha av saltet och
	 * lsenordet. se
	 * https://crackstation.net/hashing-security.htm#javasourcecode fr exempel
	 * Har inte sjlv full koll p det riktigt
	 * @param password
	 * @param salt
	 * @return
	 */
	private byte[] hash(String password, byte[] salt) {

		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt,
				PBKDF2_ITERATIONS, HASH_BYTE_SIZE * 8);
		SecretKeyFactory skf;

		try {
			skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
