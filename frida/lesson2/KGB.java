public class KGB{
    public static String decode(String str){
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length / 2; i++) {
            char c = charArray[i];
            charArray[i] = (char) (charArray[(charArray.length - i) - 1] ^ 'A');
            charArray[(charArray.length - i) - 1] = (char) (c ^ '2');
        }
        return new String(charArray);
    }
    public static void main(String[] args){
        String p = "V@]EAASB\u0012WZF\u0012e,a$7(&am2(3.\u0003";
        System.out.println(decode(p));
    }

}