package my.interest.lang.tamil;

import java.io.*;
import java.security.MessageDigest;

/**
 * Created by velsubra on 6/10/16.
 */
public final class EncodingUtil {
    public EncodingUtil() {
    }
    private static final byte[] reverseBase64Chars = new byte[0x100];
    private static final int END_OF_INPUT = -1;
    private static final int NON_BASE_64 = -1;
    private static final int NON_BASE_64_WHITESPACE = -2;
    private static final int NON_BASE_64_PADDING = -3;

    private static final byte[] base64Chars =
            { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
                    'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                    'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
                    '4', '5', '6', '7', '8', '9', '+', '/', };


    static {

        for (int i = 0; i < reverseBase64Chars.length; i++) {
            reverseBase64Chars[i] = NON_BASE_64;
        }

        for (byte i = 0; i < base64Chars.length; i++) {
            reverseBase64Chars[base64Chars[i]] = i;
        }
        reverseBase64Chars[' '] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['\n'] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['\r'] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['\t'] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['\f'] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['='] = NON_BASE_64_PADDING;
    }


    public static boolean isBase64(String string,
                                   String enc) throws UnsupportedEncodingException {
        return isBase64(string.getBytes(enc));
    }

    public static boolean isBase64(String string) throws UnsupportedEncodingException {
        return isBase64(string.getBytes(TamilUtils.ENCODING));
    }

    public static boolean isBase64(byte[] bytes) {
        try {
            return isBase64(new ByteArrayInputStream(bytes));
        } catch (IOException x) {

            return false;
        }
    }

    public static boolean isBase64(InputStream in) throws IOException {
        long numBase64Chars = 0;
        int numPadding = 0;
        int read;

        while ((read = in.read()) != -1) {
            read = reverseBase64Chars[read];
            if (read == NON_BASE_64) {
                return false;
            } else if (read == NON_BASE_64_WHITESPACE) {
            } else if (read == NON_BASE_64_PADDING) {
                numPadding++;
                numBase64Chars++;
            } else if (numPadding > 0) {
                return false;
            } else {
                numBase64Chars++;
            }
        }
        if (numBase64Chars == 0)
            return false;
        if (numBase64Chars % 4 != 0)
            return false;
        return true;
    }

    public static String encode(String string)  throws UnsupportedEncodingException {

        return new String(encode(string.getBytes(TamilUtils.ENCODING)), TamilUtils.ENCODING);

    }

    public static String encode(String string,
                                String enc) throws UnsupportedEncodingException {
        return new String(encode(string.getBytes(enc)), enc);
    }

    public static String encodeToString(byte[] bytes) {
        return encodeToString(bytes, false);
    }

    public static String encodeToString(byte[] bytes, boolean lineBreaks) {
        try {
            return new String(encode(bytes, lineBreaks), "ASCII");
        } catch (UnsupportedEncodingException iex) {
            // ASCII should be supported
            throw new RuntimeException(iex);
        }
    }

    public static byte[] encode(byte[] bytes) {
        return encode(bytes, false);
    }

    public static byte[] encode(byte[] bytes, boolean lineBreaks) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);

        int mod;
        int length = bytes.length;
        if ((mod = length % 3) != 0) {
            length += 3 - mod;
        }
        length = length * 4 / 3;
        ByteArrayOutputStream out = new ByteArrayOutputStream(length);
        try {
            encode(in, out, lineBreaks);
        } catch (IOException x) {

            throw new RuntimeException(x);
        }
        return out.toByteArray();
    }

    public static void encode(InputStream in,
                              OutputStream out) throws IOException {
        encode(in, out, true);
    }

    public static void encode(InputStream in, OutputStream out,
                              boolean lineBreaks) throws IOException {
        // Base64 encoding converts three bytes of input to
        // four bytes of output
        int[] inBuffer = new int[3];
        int lineCount = 0;

        boolean done = false;
        while (!done && (inBuffer[0] = in.read()) != END_OF_INPUT) {
            // Fill the buffer
            inBuffer[1] = in.read();
            inBuffer[2] = in.read();


            out.write(base64Chars[inBuffer[0] >> 2]);
            if (inBuffer[1] != END_OF_INPUT) {
                // B's: last two bits of first byte, first four bits of second byte
                out.write(base64Chars[((inBuffer[0] << 4) & 0x30) |
                        (inBuffer[1] >> 4)]);
                if (inBuffer[2] != END_OF_INPUT) {
                    // C's: last four bits of second byte, first two bits of third byte
                    out.write(base64Chars[((inBuffer[1] << 2) & 0x3c) |
                            (inBuffer[2] >> 6)]);
                    // D's: last six bits of third byte
                    out.write(base64Chars[inBuffer[2] & 0x3F]);
                } else {
                    // C's: last four bits of second byte
                    out.write(base64Chars[((inBuffer[1] << 2) & 0x3c)]);
                    // an equals sign for a character that is not a Base64 character
                    out.write('=');
                    done = true;
                }
            } else {
                // B's: last two bits of first byte
                out.write(base64Chars[((inBuffer[0] << 4) & 0x30)]);
                // an equal signs for characters that is not a Base64 characters
                out.write('=');
                out.write('=');
                done = true;
            }
            lineCount += 4;
            if (lineBreaks && lineCount >= 76) {
                out.write('\n');
                lineCount = 0;
            }
        }
        if (lineBreaks && lineCount >= 1) {
            out.write('\n');
            lineCount = 0;
        }
        out.flush();
    }

    public static byte[] decodetoByteArray(String s) throws IOException {
        if (s == null) return null;
        ByteArrayInputStream in = new ByteArrayInputStream(s.getBytes(TamilUtils.ENCODING));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        decode(in, out);
        return out.toByteArray();
    }

    public static byte[] decodetoByteArray(byte[] data) throws IOException {
        if (data == null) return null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        decode(in, out);
        return out.toByteArray();
    }

    public static String decode(String s) throws IOException {
        byte[] data = decodetoByteArray(s);
        if (data == null) return null;
        return new String(data, TamilUtils.ENCODING);
    }

    public static void decode(InputStream in,
                              OutputStream out) throws IOException {
        decode(in, out, true);
    }

    public static void decode(InputStream in, OutputStream out,
                              boolean throwExceptions) throws IOException {

        int[] inBuffer = new int[4];


        boolean done = false;
        while (!done &&
                (inBuffer[0] = readBase64(in, throwExceptions)) != END_OF_INPUT &&
                (inBuffer[1] = readBase64(in, throwExceptions)) !=
                        END_OF_INPUT) {
            // Fill the buffer
            inBuffer[2] = readBase64(in, throwExceptions);
            inBuffer[3] = readBase64(in, throwExceptions);

            out.write(inBuffer[0] << 2 | inBuffer[1] >> 4);
            if (inBuffer[2] != END_OF_INPUT) {
                // four B and four C
                out.write(inBuffer[1] << 4 | inBuffer[2] >> 2);
                if (inBuffer[3] != END_OF_INPUT) {
                    // two C and six D
                    out.write(inBuffer[2] << 6 | inBuffer[3]);
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }
        out.flush();
    }


    private static final int readBase64(InputStream in,
                                        boolean throwExceptions) throws IOException {
        int read;
        int numPadding = 0;
        do {
            read = in.read();
            if (read == END_OF_INPUT)
                return END_OF_INPUT;
            read = reverseBase64Chars[(byte) read];
            if (throwExceptions &&
                    (read == NON_BASE_64 || (numPadding > 0 && read >
                            NON_BASE_64))) {
                throw new IOException("unexpected char:" + read);
            }
            if (read == NON_BASE_64_PADDING) {
                numPadding++;
            }
        } while (read <= NON_BASE_64);
        return read;
    }

    public static String digestAndEncode(String text) throws UnsupportedEncodingException {
        return digestAndEncode(text, "SHA");
    }

    public static String digestAndEncode(byte[] content)  {
        return digestAndEncode(content, "SHA");
    }

    public static String digestAndEncode(String text, String algo) throws UnsupportedEncodingException {
        if (text == null) return null;
        return digestAndEncode(text.getBytes(TamilUtils.ENCODING), algo);
    }

    public static String digestAndEncode(byte[] content, String algo) {
        try {
            if (content == null) return null;
            MessageDigest md = null;

            md = MessageDigest.getInstance(algo);
            byte[] data = encode( md.digest(content));
            String ret = new String(data, TamilUtils.ENCODING);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String digestAndEncodeRmSlashEncode(String text) throws Exception {

        return digestAndEncodeReplaceNonFriendlyChars(text);
        /* String ret = digestAndEncode(text);
       if (ret == null) return null;
       ret = ret.replaceAll("/", "");
       ret = encodeURL(ret);
       return ret;*/

    }

    public static String digestAndEncodeRmSlashEncodeForFileSystem(String text) throws Exception {
        /* if (text == null) return null;
       MessageDigest md = null;
       md = MessageDigest.getInstance("SHA");
       byte[] data = encode( md.digest(text.getBytes()));
       String ret = new String(data);
       ret = ret.replaceAll("/", "");
       ret = ret.replaceAll("\\+", "");
       ret = ret.replaceAll("=", "");
       return ret;*/

        return digestAndEncodeReplaceNonFriendlyChars(text);

    }



    public static String digestAndEncodeReplaceNonFriendlyChars(String text) throws Exception {
        if (text == null) return null;
        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA");
        byte[] data = encode( md.digest(text.getBytes(TamilUtils.ENCODING)));
        String ret = new String(data, TamilUtils.ENCODING);
        ret = ret.replaceAll("/", "___");
        ret = ret.replaceAll("\\+", "__");
        ret = ret.replaceAll("=", "_");
        // just to ensure that the string never start with a digit to form a valid identifier.
        ret = "_" + ret;
        return ret;

    }

    public static String digestToAVariableName(String s) {
        try {
            return digestAndEncodeReplaceNonFriendlyChars(s);
        } catch (Exception e) {
            throw new RuntimeException("Error while digesting:" , e);
        }
    }

    public static String encodeURL(String s) {
        if (s == null) return null;
        return s.replaceAll("\\+", "%2B");
    }


}
