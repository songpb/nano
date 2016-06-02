package nano.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MiscUtil {
	protected static Log log = LogFactory.getLog(MiscUtil.class);
	
	private static MessageDigest digester;

	private MiscUtil() {
	}

	public static int ip4Bytes2Int(byte[] bs) {
		return (bs[0] & 0xff) << 24 | (bs[1] & 0xff) << 16
				| (bs[2] & 0xff) << 8 | (bs[3] & 0xff);
	}

	public static byte[] ip4Int2Bytes(int ip) {
		byte[] bs = new byte[4];
		bs[0] = (byte) (ip >> 24 & 0xff);
		bs[1] = (byte) (ip >> 16 & 0xff);
		bs[2] = (byte) (ip >> 8 & 0xff);
		bs[3] = (byte) (ip & 0xff);
		return bs;
	}

	public static byte[] ReverseIntBytes(int aInt) {
		byte[] bs = new byte[4];
		bs[3] = (byte) (aInt >> 24 & 0xff);
		bs[2] = (byte) (aInt >> 16 & 0xff);
		bs[1] = (byte) (aInt >> 8 & 0xff);
		bs[0] = (byte) (aInt & 0xff);
		return bs;
	}

	public static String ip4Int2Str(int ip) {
		StringBuilder buf = new StringBuilder();
		buf.append(ip >>> 24 & 0xff);
		buf.append('.');
		buf.append(ip >>> 16 & 0xff);
		buf.append('.');
		buf.append(ip >>> 8 & 0xff);
		buf.append('.');
		buf.append(ip & 0xff);
		return buf.toString();
	}

	public static int ip4Str2Int(String ipStr) {
		// TODO: check later JDK version
		String s = ipStr.replace('.', ':');
		String[] ss = s.split(":");
		if (4 != ss.length) {
			return 0;
		} else {
			return Integer.parseInt(ss[0]) << 24
					| Integer.parseInt(ss[1]) << 16
					| Integer.parseInt(ss[2]) << 8 | Integer.parseInt(ss[3]);
		}
	}

	// hash algrithm from JDK's String
	public static int hash(byte[] bs) {
		int hash = 0;
		for (int i = 0; i < bs.length; i++) {
			hash = 31 * hash + bs[i];
		}
		return hash;
	}

	public static boolean isBytesEqual(byte[] bs1, byte[] bs2) {
		if (null == bs1)
			return (null == bs2);
		if (null == bs2)
			return (null == bs1);
		int len = bs1.length;
		if (len != bs2.length)
			return false;
		for (int i = 0; i < len; i++) {
			if (bs1[i] != bs2[i])
				return false;
		}
		return true;
	}

	/**
	 * trans second to String
	 * 
	 * @param s
	 * @return
	 */
	public static String toLongDateString(long s) {
		try {
			Calendar cl = Calendar.getInstance();
			cl.setTimeInMillis(s * 1000);
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return myFmt.format(cl.getTime());
		} catch (Exception ex) {
			log.error(ex);
			log.error(MiscUtil.traceInfo(ex));
			return "";
		}
	}

	/**
	 * trans current time to compare with timestamp
	 * 
	 * @return
	 */
	public static String getCurTime2Int() {
		try {
			Calendar cl = Calendar.getInstance();
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMddHHmmss");
			return myFmt.format(cl.getTime());
		} catch (Exception ex) {
			log.error(ex);
			log.error(MiscUtil.traceInfo(ex));
			return "";
		}
	}

	public static String traceInfo(Throwable e) {
		StringBuilder sf = new StringBuilder(4096);
		sf.append(e.toString()).append("\n");
		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i < trace.length; i++)
			sf.append("\tat ").append(trace[i]).append("\n");
		return sf.toString();
	}

	public synchronized static byte[] sha_1(byte[] plain) {
		if(null == plain || 0 == plain.length)
			return null;
		if (null == digester) {
			try {
				digester = MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				return plain; // will not happen
			}
		}
		return digester.digest(plain);
	}

	public static String byte2HexString(byte[] b) {
		StringBuilder sb = new StringBuilder(40);
		for (int n = 0; n < b.length; n++) 
			sb.append(Integer.toHexString(b[n] & 0XFF));
		return sb.toString().toUpperCase();
	}
	
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}
}
