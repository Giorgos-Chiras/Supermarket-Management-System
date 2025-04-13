package Hasher;

/**
 * Class that is used to hash password before storing them for protection
 */
public class Hasher {

    /**
     * Take password as input and does a lot of random operation in them to get as random and as a secure
     * hash as possible
     * Uses mostly prime numbers as those  provide the most random results
     */
    public static String hash(String input) {
        StringBuilder hashed = new StringBuilder();

        int state = 0;

       for(int i = 0; i < input.length(); i++) {
           //Get the character
           char c =input.charAt(i);
           //Change state based on character and input
           state ^= (c + i * 17);
           state = (state * 31) % 100000 + input.length() * 13;
           int ascii = 0;
           //Get the ascii value of each character and change it based on the state
           for(int j = 0; j < 3; j++) {
               ascii = (c + state + 100 ^ i ) % 10007;
               state ^= (c + i * 17);
           }
           //Add the hashed character to the string
           hashed.append(Integer.toHexString(ascii));
       }

       return hashed.toString();
    }
}
