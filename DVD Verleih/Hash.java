import javax.crypto.SecretKeyFactory;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Beschreiben Sie hier die Klasse Hash.
 * https://www.baeldung.com/java-password-hashing
 * https://medium.com/@kasunpdh/how-to-store-passwords-securely-with-pbkdf2-204487f14e84
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Hash 
{
    public static byte[] HashPasswort(String passwort, byte[] salt){
        try{
            PBEKeySpec spec = new PBEKeySpec(passwort.toCharArray(), salt, 65536, 512);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return hash;
        }catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new RuntimeException( e );
        }
    }
    
    public static byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
